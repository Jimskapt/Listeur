package listeur.gui;

import java.io.IOException ;
import java.util.Locale ;
import java.util.PropertyResourceBundle ;

import javafx.application.Application ;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.layout.BorderPane ;
import javafx.stage.Stage ;
import listeur.gui.mainwindow.MainWindowCtrlr ;
import listeur.locales.Locales ;

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
			
			FXMLLoader loader = new FXMLLoader(
					MainWindowCtrlr.class.getResource( "MainWindow.fxml" ),
					new PropertyResourceBundle( Locales.class.getResource( "fr_FR.properties" ).openStream())
							) ;
			
			BorderPane root=(BorderPane)loader.load();
			MainWindowCtrlr controller=loader.getController();
			
			Scene scene=new Scene( root );
			primaryStage.setScene( scene );
			primaryStage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main( String[] args )
	{
		launch( args );
	}
}
