package org.pu.open.actions;



import java.io.File;

import org.eclipse.jface.action.IAction;
import org.pu.open.Activator;
import org.pu.open.preferences.PreferenceConstants;

public class OpenShellAction extends BaseOpenAction{

	@Override
	public void runAction(IAction action, String filePath) {
		Activator plugin = Activator.getDefault();
		String command = plugin.getPreferenceStore().getString(PreferenceConstants.P_OPEN_SHELL);
		File directory = new File(filePath);
		if (directory !=null && !directory.isDirectory()){
			directory = directory.getParentFile();
		}
		plugin.execCommand(command, directory.getAbsolutePath());
	}
}
