package org.pu.open;


public class Constants {

	public static final String P_OPEN_SHELL = "open_shell";
	public static final String P_OPEN_MINGW32 = "open_MINGW32";
	public static final String P_OPEN_FOLDER = "open_folder";
	public static final String P_OPEN_FILE = "open_file";
	
	public static final String PATH_VAR = "{0}";
	public static final String OPEN_FOLDER_WINDOWS = "explorer "+ PATH_VAR;
	public static final String OPEN_FILE_WINDOWS = "explorer /select, /e," + PATH_VAR;
	public static final String OPEN_SHELL_WINDOWS = "cmd /C start cmd /K cd " + PATH_VAR;
	public static final String OPEN_MINGW32_WINDOWS = "D:\\ProgramFiles\\Git\bin\\sh.exe --login -i ";

	public static final String OPEN_FOLDER_LINUX_GNOME = "nautilus " + PATH_VAR;
	public static final String OPEN_FOLDER_LINUX_KDE = "kfmclient " + PATH_VAR;
	
	/**
	 * "color xterm," "regular xterm," and "gnome-terminal" 
	 */
	public static final String OPEN_SHELL_LINUX_GNOME = "gnome-terminal --working-directory=" + PATH_VAR;
	
	/**
	 * konsole" and "terminal"
	 */
	public static final String OPEN_SHELL_LINUX_KDE = "konsole " + PATH_VAR;
}
