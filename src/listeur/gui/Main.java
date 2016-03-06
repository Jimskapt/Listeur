package listeur.gui;

import java.io.IOException ;
import java.net.URL ;
import java.util.Locale ;
import java.util.PropertyResourceBundle ;

import javafx.application.Application ;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.layout.BorderPane ;
import javafx.stage.Stage ;
import locales.Locales ;

public class Main extends Application
{
	public Main()
	{
		
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			primaryStage.setTitle( "Listeur 0.2.1" );
			
			Locale locale=new Locale("en","US");
			FXMLLoader loader=new FXMLLoader(
					new URL("file:///DDData/Thomas/Génie Logiciel/IDEs/Eclipse/Listeur/bin/listeur/gui/MainWindow.fxml"),
					new PropertyResourceBundle( Locales.class.getResource( "MainWindow.locale.en_US.properties" ).openStream())
							);
			
			BorderPane root=(BorderPane)loader.load();
			MainWindowCtrlr controller=loader.getController();
			
			Scene scene=new Scene( root );
			primaryStage.setScene( scene );
			primaryStage.show();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main( String[] args )
	{
		launch( args );
	}
}
