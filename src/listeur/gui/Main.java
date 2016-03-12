package listeur.gui;

import java.util.Locale ;
import java.util.PropertyResourceBundle ;

import javafx.application.Application ;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Scene ;
import javafx.scene.layout.BorderPane ;
import javafx.stage.Stage ;
import listeur.core.Setup ;
import listeur.gui.mainwindow.MainWindowCtrlr ;
import listeur.locales.Locales ;

public class Main extends Application
{
	public static Setup setup=new Setup();
	public static Locale locale=new Locale("en","US");
	
	public Main()
	{
		
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		try
		{
			primaryStage.setTitle( "Listeur 0.2.1" );
			
			FXMLLoader loader = new FXMLLoader(
					MainWindowCtrlr.class.getResource( "MainWindow.fxml" ),
					new PropertyResourceBundle( Locales.class.getResource( locale.getLanguage()+"_"+locale.getCountry()+".properties" ).openStream())
			);
			
			BorderPane root=(BorderPane)loader.load();
			
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
		launch(args);
	}
}
