package listeur.gui.exceptionWindow;

import java.util.PropertyResourceBundle ;

import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.layout.VBox ;
import javafx.stage.Modality ;
import javafx.stage.Stage ;
import javafx.stage.Window ;
import listeur.core.Main ;
import listeur.locales.Locales ;

public class ExceptionWindow
{
	protected ExceptionWindowCtrl ctrl;
	
	public ExceptionWindow( Window parent, String description, Exception exception )
	{
		try
		{
			Stage stage = new Stage();
			stage.setTitle( "Error : Exception" );
			stage.setResizable( true );
			
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.initOwner( parent );
			
			FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource( "ExceptionWindow.fxml" ),
				new PropertyResourceBundle( Locales.class.getResource( Main.locale.getLanguage()+"_"+Main.locale.getCountry()+".properties" ).openStream())
			);
			
			VBox root=(VBox)loader.load();
			
			this.ctrl=(ExceptionWindowCtrl)loader.getController();
			ctrl.setParentWindow( parent );
			ctrl.setDescription( description );
			ctrl.setException( exception );
			
			stage.setScene(new Scene(root));
			
			stage.showAndWait();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
