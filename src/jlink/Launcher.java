package jlink;

import java.io.IOException;

import com.ptc.cipjava.jxthrowable;
import com.ptc.pfc.pfcCommand.DefaultUICommandActionListener;
import com.ptc.pfc.pfcCommand.UICommand;
import com.ptc.pfc.pfcGlobal.pfcGlobal;
import com.ptc.pfc.pfcSession.Session;

public class Launcher {
	public static String MSG_FILE = "messages.txt";

	public static void start() throws jxthrowable {
		Session currentSession = pfcGlobal.GetProESession();

		String commandString = currentSession.GetMessageContents(
            MSG_FILE, "-L.launcher.cmd", null);
		String iconFile = currentSession.GetMessageContents(
            MSG_FILE, "-L.launcher.ico", null);

		UICommand cmd = currentSession.UICreateCommand("launcher",
				new MenuButtonListener(commandString, currentSession));
		cmd.SetIcon(iconFile);
		cmd.Designate(MSG_FILE, "-L.launcher.label", null, null);
	}

	public static void stop() { /* EMPTY */ }
}

class MenuButtonListener extends DefaultUICommandActionListener {
	private String commandString;
    private Session currentSession;

	public MenuButtonListener(String cmd, Session session) {
		commandString = cmd;
        currentSession = session;
	}

	public void OnCommand() throws jxthrowable {
		try {
			ProcessBuilder pb = new ProcessBuilder(commandString);
			pb.start();
		} catch (IOException e) {
			currentSession.UIShowMessageDialog(
                "Could not start " + commandString, null);
		}
	}
}