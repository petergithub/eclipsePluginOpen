package org.pu.open.preferences;


import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.pu.open.Activator;
import org.pu.open.Constants;
import org.pu.open.Desktop;
import org.pu.open.OS;

/**
 * Class used to initialize default preference values.
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(Constants.P_OPEN_FOLDER, getOpenFolderCommand());
		store.setDefault(Constants.P_OPEN_SHELL, getOpenShellCommand());
	}

	private String getOpenFolderCommand() {
		Activator plugin = Activator.getDefault();
		if (plugin.getOS() == OS.LINUX) {
			if (plugin.getDesktop() == Desktop.GNOME) {
				return Constants.OPEN_FOLDER_LINUX_GNOME;
			}

			if (plugin.getDesktop() == Desktop.KDE) {
				return Constants.OPEN_FOLDER_LINUX_KDE;
			}

		} else if (plugin.getOS() == OS.WINDOWS) {
			return Constants.OPEN_FOLDER_WINDOWS;
		}
		return "unknown";
	}

	private String getOpenShellCommand() {
		Activator plugin = Activator.getDefault();
		if (plugin.getOS() == OS.LINUX) {
			if (plugin.getDesktop() == Desktop.GNOME) {
				return Constants.OPEN_SHELL_LINUX_GNOME;
			}

			if (plugin.getDesktop() == Desktop.KDE) {
				return Constants.OPEN_SHELL_LINUX_KDE;
			}
		} else if (plugin.getOS() == OS.WINDOWS) {
			return Constants.OPEN_SHELL_WINDOWS;
		}

		return "unknown";
	}
}
