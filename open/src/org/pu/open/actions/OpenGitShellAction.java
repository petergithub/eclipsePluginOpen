package org.pu.open.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.pu.open.Activator;
import org.pu.open.Constants;

public class OpenGitShellAction extends BaseOpenAction{

	@Override
	public void runAction(IAction action, File file) {
		Activator plugin = Activator.getDefault();
		String command = plugin.getPreferenceStore().getString(Constants.P_OPEN_GIT_SHELL);
		if (file !=null && !file.isDirectory()){
			file = file.getParentFile();
		}
		plugin.execCommand(command, file.getAbsolutePath());
	}
}
