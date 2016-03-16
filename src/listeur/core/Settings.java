package listeur.core;

import java.io.File ;
import java.io.FileInputStream ;
import java.io.FileOutputStream ;
import java.io.IOException ;
import java.net.URISyntaxException ;
import java.nio.file.FileSystems ;
import java.nio.file.Paths ;
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
			if( getSaveFile().exists() )
			{
				FileInputStream file = new FileInputStream( getSaveFile() );
				
				settings.load( file );
				
				file.close();
			}
			
			showConfirmDialogDeletePath=( settings.getProperty("showConfirmDialogDeletePath","true").equals("true") ) ? true : false;
			alwaysSavePaths=( settings.getProperty("alwaysSavePaths","false").equals("true") ) ? true : false;
			alwaysSaveFilters=( settings.getProperty("alwaysSaveFilters","true").equals("true") ) ? true : false;
			alwaysSaveResultSettings=( settings.getProperty("alwaysSaveResultSettings","true").equals("true") ) ? true : false;
			
			selectedLanguage=settings.getProperty("selectedLanguage","en");
			selectedCountry=settings.getProperty("selectedCountry","US");
			
			Main.locale=new Locale( selectedLanguage, selectedCountry );
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
	
	public File getSaveFile()
	{
		try
		{
			return 
				Paths.get(
					FileSystems.getDefault().getPath(
						Paths.get( this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI() ).toString(),
						"listeur.settings"
					).toUri()
				).toFile();
		}
		catch( URISyntaxException e )
		{
			// TODO : warning, quick but dirty code, we have to improve it !
			//e.printStackTrace();
			
			return null;
		}
	}
	
	public void saveFile()
	{
		Properties settings=new Properties();
		settings.put( "showConfirmDialogDeletePath", (showConfirmDialogDeletePath) ? "true" : "false" );
		settings.put( "alwaysSavePaths", (alwaysSavePaths) ? "true" : "false" );
		settings.put( "alwaysSaveFilters", (alwaysSaveFilters) ? "true" : "false" );
		settings.put( "alwaysSaveResultSettings", (alwaysSaveResultSettings) ? "true" : "false" );
		settings.put( "selectedLanguage", selectedLanguage );
		settings.put( "selectedCountry", selectedCountry );
		
		try
		{
			if( !getSaveFile().exists() )
				getSaveFile().createNewFile();
			
			FileOutputStream file=new FileOutputStream( this.getSaveFile() );
			
			settings.store( file, " Settings for Listeur App (https://github.com/Jimskapt/Listeur)" );
			
			file.close();
		}
		catch( IOException e )
		{
			//e.printStackTrace();
		}
		
		Main.locale=new Locale( selectedLanguage, selectedCountry );
	}
}
