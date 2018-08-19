package org.pu.open.actions;

import java.io.File;

import org.eclipse.jface.action.IAction;
import org.pu.open.Activator;
import org.pu.open.Constants;

public class OpenAction extends BaseOpenAction {

	@Override
	public void runAction(IAction action, File file) {
		Activator plugin = Activator.getDefault();
		String command;
		if (file.isDirectory()) {
			command = plugin.getPreferenceStore().getString(Constants.P_OPEN_FOLDER);
		} else {
			command = plugin.getPreferenceStore().getString(Constants.P_OPEN_FILE);
		}
		//TODO Not open finder window having space in path
//		https://stackoverflow.com/questions/43783824/java-not-able-to-open-finder-window-having-space-in-path
		
		plugin.execCommand(command, file.getAbsolutePath());
	}
}
