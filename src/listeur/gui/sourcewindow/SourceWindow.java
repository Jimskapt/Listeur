package listeur.gui.sourcewindow;

import java.util.PropertyResourceBundle ;

import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.layout.BorderPane ;
import javafx.stage.Modality ;
import javafx.stage.Stage ;
import javafx.stage.Window ;
import listeur.core.Source ;
import listeur.locales.Locales ;

public class SourceWindow
{
	protected SourceWindowCtrl ctrl;
	
	public SourceWindow( Window parent )
	{
		try
		{
			Stage stage = new Stage();
			stage.setTitle( "Create a new path" );
			
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.initOwner( parent );
			
			FXMLLoader loader = new FXMLLoader(
					this.getClass().getResource( "SourceWindow.fxml" ),
					new PropertyResourceBundle( Locales.class.getResource( "en_US.properties" ).openStream())
			);
			
			BorderPane root=(BorderPane)loader.load();
			
			this.ctrl=(SourceWindowCtrl)loader.getController();
			ctrl.setParentWindow( parent );
			
			stage.setScene(new Scene(root));
			
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public SourceWindow( Window parent, Source target )
	{
		this( parent );
		
		ctrl.setTarget(target);
	}
}
