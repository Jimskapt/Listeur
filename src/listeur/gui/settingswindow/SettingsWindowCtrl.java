package listeur.gui.settingswindow;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent ;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox ;
import javafx.scene.control.ComboBox ;
import javafx.stage.Window ;
import listeur.core.Main ;

public class SettingsWindowCtrl
{
	protected Window parent;
	
	@FXML private ResourceBundle resources;
	@FXML private URL location;
	@FXML private ComboBox<String> selectedLanguage;
	@FXML private ComboBox<String> selectedCountry;
	@FXML private CheckBox showConfigDeletePath;
	@FXML private CheckBox alwaysSavePaths;
	@FXML private CheckBox alwaysSaveFilters;
	@FXML private CheckBox alwaysSaveResultSettings;

	@FXML void initialize()
	{
		assert selectedLanguage != null : "fx:id=\"selectedLanguage\" was not injected: check your FXML file 'SettingsWindow.fxml'.";
		assert selectedCountry != null : "fx:id=\"selectedCountry\" was not injected: check your FXML file 'SettingsWindow.fxml'.";
		assert showConfigDeletePath != null : "fx:id=\"showConfigDeletePath\" was not injected: check your FXML file 'SettingsWindow.fxml'.";
		assert alwaysSavePaths != null : "fx:id=\"alwaysSavePaths\" was not injected: check your FXML file 'SettingsWindow.fxml'.";
		assert alwaysSaveFilters != null : "fx:id=\"alwaysSaveFilters\" was not injected: check your FXML file 'SettingsWindow.fxml'.";
		assert alwaysSaveResultSettings != null : "fx:id=\"alwaysSaveResultSettings\" was not injected: check your FXML file 'SettingsWindow.fxml'.";
		
		selectedLanguage.getItems().addAll( "en", "fr" );
		
		selectedLanguage.getSelectionModel().clearSelection();
		selectedLanguage.getSelectionModel().select( Main.locale.getLanguage() );
		
		/*
		// TO DO later ...
		selectedLanguage.setEditable( true );
		selectedLanguage.valueProperty().addListener( new ChangeListener<String>()
		{
			@Override
			public void changed(ObservableValue<? extends String> ov, String before, String after)
			{
				
			}
		});
		*/
	}
	
	@FXML void changedLanguage(ActionEvent event)
	{
		String[] availableCountries;
		
		switch( ((ComboBox<String>)event.getSource()).getValue() )
		{
			case "en":
				availableCountries=new String[]{"US"};
				break;
			case "fr":
				availableCountries=new String[]{"FR"};
				break;
			default:
				availableCountries=new String[]{};
				break;
		}
		
		selectedCountry.getItems().clear();
		selectedCountry.getItems().addAll( availableCountries );
		selectedCountry.setDisable( availableCountries.length<=0 );
		
		if( availableCountries.length>0 )
			selectedCountry.getSelectionModel().select( 0 );
	}
	
	public void setParentWindow( Window parent )
	{
		this.parent=parent;
	}
}
