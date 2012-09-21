package org.pu.open;


public class Constants {

	public static final String P_OPEN_SHELL = "open_extern_shell";
	public static final String P_OPEN_FOLDER = "open_extern_folder";
	
	public static final String PATH_VAR = "{0}";
//	public static final String OPEN_FOLDER_WINDOWS = "explorer "+ PATH_VAR;
	public static final String OPEN_FOLDER_WINDOWS = "cmd /C start explorer /select,/e,"+ PATH_VAR;
	public static final String OPEN_SHELL_WINDOWS = "cmd.exe /C start cmd /K cd "+ PATH_VAR;

	public static final String OPEN_FOLDER_LINUX_GNOME = "nautilus "+ PATH_VAR;
	public static final String OPEN_FOLDER_LINUX_KDE = "kfmclient "+ PATH_VAR;
	
	/**
	 * "color xterm," "regular xterm," and "gnome-terminal" 
	 */
	public static final String OPEN_SHELL_LINUX_GNOME = "gnome-terminal --working-directory="+ PATH_VAR;
	
	/**
	 * konsole" and "terminal"
	 */
	public static final String OPEN_SHELL_LINUX_KDE = "konsole "+ PATH_VAR + "";
}