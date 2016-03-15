package listeur.core;

import java.io.File ;
import java.io.FileOutputStream ;
import java.io.IOException ;
import java.io.InputStream ;
import java.net.URISyntaxException ;
import java.util.Locale ;
import java.util.Properties ;

public class Settings
{
	public static String selectedLanguage, selectedCountry;
	public static boolean showConfirmDialogDeletePath, alwaysSavePaths, alwaysSaveFilters, alwaysSaveResultSettings;
	
	public Settings()
	{
		try
		{
			Properties settings=new Properties();
			InputStream file = Settings.class.getResourceAsStream( "Settings.properties");
			settings.load( file );
			
			showConfirmDialogDeletePath=( settings.getProperty("showConfirmDialogDeletePath","true").equals("true") ) ? true : false;
			alwaysSavePaths=( settings.getProperty("alwaysSavePaths","false").equals("true") ) ? true : false;
			alwaysSaveFilters=( settings.getProperty("alwaysSaveFilters","true").equals("true") ) ? true : false;
			alwaysSaveResultSettings=( settings.getProperty("alwaysSaveResultSettings","true").equals("true") ) ? true : false;
			
			selectedLanguage=settings.getProperty("selectedLanguage","en");
			selectedCountry=settings.getProperty("selectedCountry","US");
			
			Main.locale=new Locale( selectedLanguage, selectedCountry );
			
			file.close();
		}
		catch( IOException e )
		{
			showConfirmDialogDeletePath=true;
			alwaysSavePaths=false;
			alwaysSaveFilters=true;
			alwaysSaveResultSettings=true;
			
			selectedLanguage="en";
			selectedCountry="US";
			
			e.printStackTrace();
		}
	}
	
	public void saveFile()
	{
		Properties settings=new Properties();
		settings.put( "showConfirmDialogDeletePath", (showConfirmDialogDeletePath) ? "true" : "false" );
		settings.put( "alwaysSavePaths", (alwaysSavePaths) ? "true" : "false" );
		settings.put( "alwaysSaveFilters", (alwaysSaveFilters) ? "true" : "false" );
		settings.put( "alwaysSaveResultSettings", (alwaysSaveResultSettings) ? "true" : "false" );
		
		try
		{
			FileOutputStream file=new FileOutputStream( new File(Settings.class.getResource( "Settings.properties" ).toURI()) );
			
			settings.store( file, "" );
			
			file.close();
		}
		catch( IOException | URISyntaxException e )
		{
			e.printStackTrace();
		}
		
		Main.locale=new Locale( selectedLanguage, selectedCountry );
	}
}
