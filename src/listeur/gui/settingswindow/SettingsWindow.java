package listeur.gui.settingswindow;

import javafx.stage.Window ;
import listeur.gui.tools.CustomModalWindow ;

public class SettingsWindow extends CustomModalWindow
{
	protected SettingsWindowCtrl ctrl;
	
	public SettingsWindow( Window parent )
	{
		super( parent, "Settings" );
	}
}
