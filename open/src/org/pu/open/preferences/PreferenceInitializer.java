package org.pu.open.preferences;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
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
		store.setDefault(Constants.P_OPEN_GIT_SHELL, getOpenGitShellCommand());
	}
	
	public static Map<String, String> openFileCommandMap = new HashMap<>();
	
	static {
	    openFileCommandMap.put(Platform.OS_WIN32, Constants.OPEN_FILE_WINDOWS);
	    openFileCommandMap.put(Platform.OS_MACOSX, Constants.OPEN_FILE_MAC);

        if (Platform.OS_LINUX.equals(Platform.getOS()) && new File("/usr/bin/nautilus").exists()) {
            openFileCommandMap.put(Platform.OS_LINUX, Constants.OPEN_FOLDER_LINUX_GNOME);
        }
	}
	
	private String getOpenFileCommand() {
		String cmd = openFileCommandMap.get(Platform.getOS());
		
		if (cmd != null) {
		    return cmd;
		}
		
		Activator.log(Status.ERROR, "Unknow os " + Platform.getOS(), null);
		return "unknown";
	}

	private String getOpenFolderCommand() {
		if (Platform.OS_LINUX.equals(Platform.getOS())) {
			if (new File("/usr/bin/nautilus").exists()) {
				return Constants.OPEN_FOLDER_LINUX_GNOME;
			}

		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return Constants.OPEN_FOLDER_WINDOWS;
		} else if (Platform.OS_MACOSX.equals(Platform.getOS())) {
            return Constants.OPEN_FILE_MAC;
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
		} else if (Platform.OS_MACOSX.equals(Platform.getOS())) {
            return Constants.OPEN_SHELL_MAC;
        }

		return "unknown";
	}
	
	private String getOpenGitShellCommand() {
		if (Platform.OS_LINUX.equals(Platform.getOS())) {
			if (new File("/usr/bin/gnome-terminal").exists()) {
				return Constants.OPEN_SHELL_LINUX_GNOME;
			}
			
		} else if (Platform.OS_WIN32.equals(Platform.getOS())) {
			return Constants.OPEN_GIT_SHELL_WINDOWS;
		} else if (Platform.OS_MACOSX.equals(Platform.getOS())) {
            return Constants.OPEN_SHELL_MAC;
        }
		
		return "unknown";
	}
}
