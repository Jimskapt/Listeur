package listeur.gui.settingswindow;

import java.util.PropertyResourceBundle ;

import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.layout.BorderPane ;
import javafx.stage.Modality ;
import javafx.stage.Stage ;
import javafx.stage.Window ;
import listeur.core.Main ;
import listeur.locales.Locales ;

public class SettingsWindow
{
	protected SettingsWindowCtrl ctrl;
	
	public SettingsWindow( Window parent )
	{
		try
		{
			Stage stage = new Stage();
			stage.setTitle( "Settings" );
			
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.initOwner( parent );
			
			FXMLLoader loader = new FXMLLoader(
					this.getClass().getResource( "SettingsWindow.fxml" ),
					new PropertyResourceBundle( Locales.class.getResource( Main.locale.getLanguage()+"_"+Main.locale.getCountry()+".properties" ).openStream())
			);
			
			BorderPane root=(BorderPane)loader.load();
			
			this.ctrl=(SettingsWindowCtrl)loader.getController();
			ctrl.setParentWindow( parent );
			
			stage.setScene(new Scene(root));
			
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
