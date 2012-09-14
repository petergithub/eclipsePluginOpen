package org.pu.open.actions;

import java.io.File;
import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.pu.open.Activator;

public abstract class BaseOpenAction implements IObjectActionDelegate {
	private ISelection selection;
	protected Object selectedObject;
	protected IJavaProject selectedProject;
	
	abstract public void runAction(IAction action, String filePath);

	public void run(IAction action) {
		selectedObject = getSelectedObject(selection);
		File file = getSelectedFolder(selectedObject);
		if (null == file) {
			Activator.log(Status.INFO, "Unable to handle the path", null);
			return;
		}
		runAction(action, file.getAbsolutePath());
	}

	/**
	 * @param object
	 * @return null if selected is null
	 */
	private File getSelectedFolder(Object object) {
		if (null == object) return null;
		File directory = null;
		if (object instanceof IResource) {
			directory = new File(((IResource) object).getLocation().toOSString());
		} else if (object instanceof File) {
			directory = (File) object;
		}
		if (object instanceof IFile) {
			directory = directory.getParentFile();
		}
		if (object instanceof File) {
			directory = directory.getParentFile();
		}
		if (directory !=null && !directory.isDirectory()){
			directory = directory.getParentFile();
		}
		return directory;
	}

	@SuppressWarnings("restriction")
	public Object getSelectedObject(ISelection selection) {
		Object object = null;
		try {
			IAdaptable adaptable = null;
			if (selection instanceof IStructuredSelection) {
				adaptable = (IAdaptable) ((IStructuredSelection) selection).getFirstElement();
				if (adaptable instanceof IResource) {
					object = adaptable;
				} else if (adaptable instanceof PackageFragment && ((PackageFragment) adaptable).getPackageFragmentRoot() instanceof JarPackageFragmentRoot) {
					object = getJarFile(((PackageFragment) adaptable).getPackageFragmentRoot());
				} else if (adaptable instanceof JarPackageFragmentRoot) {
					object = getJarFile(adaptable);
				} else {
					object = (IResource) adaptable.getAdapter(IResource.class);
				}
			}
		} catch (Throwable e) {
			Activator.log(Status.INFO, "setSelectedObject error", e);
		}
		return object;
	}

	protected File getJarFile(IAdaptable adaptable) {
		IJavaElement je = (IJavaElement) adaptable;
		File selected = je.getPath().makeAbsolute().toFile();
		IJavaProject javaProject = je.getJavaProject();
		selectedProject = javaProject;
		if (!selected.exists()) {
			File projectFile = new File(javaProject.getProject()
					.getLocation().toOSString());
			selected = new File(projectFile.getParent() + selected.toString());
		}
		return selected;
	}

	public void selectionChanged(IAction action, ISelection selection) {
	      this.selection = selection;
	}

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * <pre>
	 * There is no method, which directly finds the corresponding class file resource. The proper way to get the corresponding output folder is to:
	 * 		    Convert the selection to IJavaElement, most probably ICompilationUnit (.java files)
	 * 		    Find the corresponding IPackageFragmentRoot (the source folder)
	 * 		    Find the resolved IClasspathEntry
	 * 		    Find the output location using IClasspathEntry.getOutputLocation() or the IJavaProject.getOutputLocation() method, depending on whether the classpath entry has separate output location.
	 * 		    Construct the path to the classpath based on the ICompilationUnit's package
	 * 
	 * 		The class file may or may not exist depending on whether the project has been build, whether there are build path errors, etc. So, there are additional heuristics, which you need to use to determine whether the result of the above algorithm will be usable.
	 * http://stackoverflow.com/questions/7542704/in-an-eclipse-plugin-whats-the-right-way-to-get-the-class-file-that-correspon?rq=1
	 * 
	 * You need to check if path is absolute (IPath.isAbsolute()). 
	 * If not absolute, call IProject.getLocation() to get project root and concat the two paths.
	 * </pre>
	 * 
	 * @param selection2
	 */
	@SuppressWarnings("unused")
	private void customize(ISelection selection2) {
		IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		//C:/sp/work/runtime-EclipseApplication
		IPath workspaceLocation = ResourcesPlugin.getWorkspace().getRoot().getLocation();
		if (selection instanceof IStructuredSelection) {
				Object sel = ((IStructuredSelection) selection).getFirstElement();
				if (sel instanceof IFile) {
					IFile file = (IFile) sel;
					IProject project = file.getProject();
					IPath path = project.getLocation();
					URI uri =project.getLocationURI();
				}
				if (sel instanceof PlatformObject) {
					PlatformObject p = (PlatformObject) sel;
					IResource resource = (IResource) p.getAdapter(IResource.class);
					if (resource != null){
						IPath resourceLocation = resource.getLocation();
					}
					IProject project = resource.getProject();
				}
		}
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		if (window != null) {
			ISelection selection = window.getSelectionService().getSelection();
			if (selection instanceof IStructuredSelection) {
				Object sel = ((IStructuredSelection) selection)
						.getFirstElement();
				if (sel instanceof IAdaptable) {
					IProject project = (IProject) ((IAdaptable) sel)
							.getAdapter(IProject.class);
				}
			}
		}
	}
}
