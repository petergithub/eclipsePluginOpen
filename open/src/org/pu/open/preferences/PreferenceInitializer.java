package org.pu.open.preferences;


import java.io.File;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.pu.open.Activator;
import org.pu.open.Constants;

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
		store.setDefault(Constants.P_OPEN_FILE, getOpenFileCommand());
		store.setDefault(Constants.P_OPEN_SHELL, getOpenShellCommand());
		store.setDefault(Constants.P_OPEN_MINGW32, getOpenMINGW32Command());
	}

	private String getOpenFileCommand() {
		if (Platform.OS_LINUX.equals(Platform.getOS())) {
			if (new File("/usr/bin/nautilus").exists()) {
				return Constants.OPEN_FOLDER_LINUX_GNOME;
			}

		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return Constants.OPEN_FILE_WINDOWS;
		}
		return "unknown";
	}

	private String getOpenFolderCommand() {
		if (Platform.OS_LINUX.equals(Platform.getOS())) {
			if (new File("/usr/bin/nautilus").exists()) {
				return Constants.OPEN_FOLDER_LINUX_GNOME;
			}

		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return Constants.OPEN_FOLDER_WINDOWS;
		}
		return "unknown";
	}

	private String getOpenShellCommand() {
		if (Platform.OS_LINUX.equals(Platform.getOS())) {
			if (new File("/usr/bin/gnome-terminal").exists()) {
				return Constants.OPEN_SHELL_LINUX_GNOME;
			}

		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return Constants.OPEN_SHELL_WINDOWS;
		}

		return "unknown";
	}
	
	private String getOpenMINGW32Command() {
		if (Platform.OS_LINUX.equals(Platform.getOS())) {
			if (new File("/usr/bin/gnome-terminal").exists()) {
				return Constants.OPEN_SHELL_LINUX_GNOME;
			}
			
		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return Constants.OPEN_MINGW32_WINDOWS;
		}
		
		return "unknown";
	}
}
