package listeur.gui.tools;

import java.util.PropertyResourceBundle ;

import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.layout.Pane ;
import javafx.stage.Modality ;
import javafx.stage.Stage ;
import javafx.stage.Window ;
import listeur.core.Main ;
import listeur.locales.Locales ;

abstract public class CustomModalWindow
{
	protected CustomModalWindowCtrl ctrl;
	
	public CustomModalWindow( Window parent, String title )
	{
		try
		{
			Stage stage = new Stage();
			stage.setTitle( title );
			
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.initOwner( parent );
			
			FXMLLoader loader = new FXMLLoader(
					this.getClass().getResource( this.getClass().getSimpleName().concat( ".fxml" ) ),
					new PropertyResourceBundle( Locales.class.getResource( Main.locale.getLanguage()+"_"+Main.locale.getCountry()+".properties" ).openStream())
			);
			
			Pane root=(Pane)loader.load();
			
			this.ctrl=(CustomModalWindowCtrl)loader.getController();
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
