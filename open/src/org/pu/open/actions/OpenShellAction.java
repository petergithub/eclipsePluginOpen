package org.pu.open.actions;



import org.eclipse.jface.action.IAction;
import org.pu.open.Activator;
import org.pu.open.preferences.PreferenceConstants;

public class OpenShellAction extends BaseOpenAction{

	@Override
	public void runAction(IAction action, String filePath) {
		Activator plugin = Activator.getDefault();
		String command = plugin.getPreferenceStore().getString(PreferenceConstants.P_OPEN_SHELL);
		plugin.execCommand(command, filePath);
	}
}
