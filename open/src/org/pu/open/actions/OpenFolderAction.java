package org.pu.open.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.pu.open.Activator;
import org.pu.open.Constants;

public class OpenFolderAction extends BaseOpenAction {

	@Override
	public void runAction(IAction action, File file) {
		Activator plugin = Activator.getDefault();
		String command;
		if (file.isDirectory()) {
			command = plugin.getPreferenceStore().getString(Constants.P_OPEN_FOLDER);
		} else {
			command = plugin.getPreferenceStore().getString(Constants.P_OPEN_FILE);
		}
		plugin.execCommand(command, file.getAbsolutePath());
	}
}
