package org.pu.open.actions;

import java.io.File;
import java.text.MessageFormat;

import org.eclipse.core.runtime.Status;
import org.eclipse.jface.action.IAction;
import org.pu.open.Activator;
import org.pu.open.Constants;

public class OpenGitShellAction extends BaseOpenAction {

	@Override
	public void runAction(IAction action, File file) {
		Activator plugin = Activator.getDefault();
		String command = plugin.getPreferenceStore().getString(Constants.P_OPEN_GIT_SHELL);
		if (file != null && !file.isDirectory()) {
			file = file.getParentFile();
		}
		execCommand(command, file);
	}

	public void execCommand(String command, File dir) {
		String msg = MessageFormat.format("running:{0}, dir={1}", command, dir);
		Activator.log(Status.OK, msg, null);
		try {
			Runtime.getRuntime().exec(command, null, dir);
		} catch (Throwable t) {
			Activator.log(Status.INFO, msg, t);
		}
	}
}
