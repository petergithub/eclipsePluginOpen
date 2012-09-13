package org.pu.open;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "open"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;

	private static final String OS_NAME = getSystemProperty("os.name");
	private static final boolean IS_OS_LINUX = getOSMatches("linux");
	private static final boolean IS_OS_MAC = getOSMatches("mac");
	private static final boolean IS_OS_WINDOWS = getOSMatches("windows");
	private Desktop desktop = null;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	public static void log(int status, String msg, Throwable t) {
		ILog log = getDefault().getLog();
		Status s = new Status(status, PLUGIN_ID, msg, t);
		log.log(s);
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		if (IS_OS_LINUX) {
			this.desktop = getLinuxDesktop();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public Desktop getDesktop() {
		return desktop;
	}

	public OS getOS() {
		if (IS_OS_LINUX) {
			return OS.LINUX;
		}
		if (IS_OS_WINDOWS) {
			return OS.WINDOWS;
		}
		if (IS_OS_MAC) {
			return OS.MAC;
		}
		return OS.UNKNOWN;
	}

	private Desktop getLinuxDesktop() {
		Process exec2;
		BufferedReader r = null;
		try {
			exec2 = Runtime.getRuntime().exec("ps ax");
			exec2.waitFor();
			InputStream inputStream = exec2.getInputStream();

			BufferedInputStream in = new BufferedInputStream(inputStream);
			r = new BufferedReader(new InputStreamReader(in));

			String l = r.readLine();
			while (l != null) {
				if (l.contains("kwin")) {
					return Desktop.KDE;
				}
				if (l.contains("gnome-settings-daemon")) {
					return Desktop.GNOME;
				}
				l = r.readLine();
			}
		} catch (Throwable e) {
			Activator.log(Status.ERROR, "ps aux command error", e);
		} finally {
			if (r != null) try {
				r.close();
			} catch (IOException e) {
			}
		}
		return Desktop.UNKNOWN;
	}

	private static String getSystemProperty(String property) {
		try {
			return System.getProperty(property).toLowerCase();
		} catch (SecurityException ex) {
			// we are not allowed to look at this property
			String msg = "Caught a SecurityException reading the system property '"
					+ property + "'; the property value will default to null.";
			Activator.log(Status.ERROR, msg, ex);
			return null;
		}
	}

	/**
	 * Decides if the operating system matches.
	 * 
	 * @param osNamePrefix
	 *            the prefix for the os name
	 * @return true if matches, or false if not or can't determine
	 */
	private static boolean getOSMatches(String osNamePrefix) {
		if (OS_NAME == null) {
			return false;
		}
		return OS_NAME.startsWith(osNamePrefix);
	}
	
	public void execCommand(String command, String filePath) {
		try {
			command = MessageFormat.format(command, new Object[] {filePath});
			Activator.log(Status.OK, "running: " + command, null);
			Runtime.getRuntime().exec(command);
		} catch (Throwable t) {
			Activator.log(Status.INFO, "Unable to handle the path", null);
		}
	}

}
