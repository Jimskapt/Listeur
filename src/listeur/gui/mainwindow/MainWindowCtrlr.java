package listeur.gui.mainwindow;

import java.nio.file.Paths ;
import java.util.Arrays ;
import java.util.Optional ;
import java.util.PropertyResourceBundle ;
import java.util.ResourceBundle ;

import javafx.beans.value.ChangeListener ;
import javafx.beans.value.ObservableValue ;
import javafx.collections.FXCollections ;
import javafx.event.ActionEvent ;
import javafx.fxml.FXML ;
import javafx.fxml.FXMLLoader ;
import javafx.scene.Node ;
import javafx.scene.Scene ;
import javafx.scene.control.Accordion ;
import javafx.scene.control.Alert ;
import javafx.scene.control.Alert.AlertType ;
import javafx.scene.control.Button ;
import javafx.scene.control.ButtonType ;
import javafx.scene.control.ListView ;
import javafx.scene.control.TitledPane ;
import javafx.scene.layout.BorderPane ;
import javafx.stage.Modality ;
import javafx.stage.Stage ;
import listeur.core.Source ;
import listeur.gui.sourcewindow.SourceWindow ;
import listeur.locales.Locales ;

public class MainWindowCtrlr
{
	@FXML private ResourceBundle resources;
    @FXML private Accordion instructions;
    @FXML private TitledPane paths;
    @FXML private ListView<Source> pathsList;
    @FXML private Button newPathButton;
    @FXML private Button editPathButton;
    @FXML private Button deletePathButton;
    @FXML private TitledPane filters;
    @FXML private ListView<String> showList;
    @FXML private Button actionShow;
    @FXML private ListView<String> excludeList;
    @FXML private Button actionExclude;
    @FXML private TitledPane resultSettings;
    @FXML private ListView<String> saveAsList;
    @FXML private Button actionSaveAs;
    @FXML private ListView<String> renderRulesList;
    @FXML private Button actionRenderRules;
    @FXML private Button launchScan;
    
	public MainWindowCtrlr() {}
	
	@FXML void initialize()
	{
		assert instructions != null : "fx:id=\"instructions\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert paths != null : "fx:id=\"paths\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert pathsList != null : "fx:id=\"pathsList\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert newPathButton != null : "fx:id=\"newPathButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert editPathButton != null : "fx:id=\"editPathButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert deletePathButton != null : "fx:id=\"deletePathButton\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert filters != null : "fx:id=\"filters\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert showList != null : "fx:id=\"showList\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert actionShow != null : "fx:id=\"actionShow\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert excludeList != null : "fx:id=\"excludeList\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert actionExclude != null : "fx:id=\"actionExclude\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert resultSettings != null : "fx:id=\"resultSettings\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert saveAsList != null : "fx:id=\"saveAsList\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert actionSaveAs != null : "fx:id=\"actionSaveAs\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert renderRulesList != null : "fx:id=\"renderRulesList\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert actionRenderRules != null : "fx:id=\"actionRenderRules\" was not injected: check your FXML file 'MainWindow.fxml'.";
        assert launchScan != null : "fx:id=\"launchScan\" was not injected: check your FXML file 'MainWindow.fxml'.";
		
		instructions.setExpandedPane( paths );
		
		Arrays.asList( actionShow, actionExclude, actionSaveAs, actionRenderRules ).stream()
		.forEach( e ->
		{
			e.setText( resources.getString( "New" ) );
			e.setDisable( false );
		});
		
		Source[] defaultPaths=new Source[2];
		defaultPaths[0]=new Source(Paths.get("C:\\"));
		defaultPaths[1]=new Source(Paths.get("D:\\"));
		
		pathsList.setItems( FXCollections.observableArrayList(defaultPaths) );
		pathsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Source>()
		{
			public void changed(ObservableValue<? extends Source> observable, Source oldValue, Source newValue)
			{
				if(newValue==null)
				{
					editPathButton.setDisable( true );
					deletePathButton.setDisable( true );
				}
				else
				{
					editPathButton.setDisable( false );
					deletePathButton.setDisable( false );
				}
			}
		});
		
		showList.setItems( FXCollections.observableArrayList("Files","Empty Files","Folders","Empty Folders") );
		
		excludeList.setItems( FXCollections.observableArrayList("Folders with names exactly .git","Files with names like /\\.temp$/") );
		
		saveAsList.setItems( FXCollections.observableArrayList("C:\\extract.xlsx",".xml (temporary file)",".txt without hierarchy") );
		
		renderRulesList.setItems( FXCollections.observableArrayList("Folders are yellow background","Empty folders are #CCCCCC background","Files are bold font") );
		
	}
	
	@FXML void closeWindow(ActionEvent event)
	{
		((Stage)((Button)(event.getSource())).getScene().getWindow()).close();
    }
	
	@FXML void showHelp(ActionEvent event)
	{
		Alert alert=new Alert(AlertType.INFORMATION);
		alert.setTitle( "About Listeur ..." );
		alert.setHeaderText( "Listeur is a free software under MIT licence.");
		alert.setContentText( "Visit us on our GitHub !\n\nhttps://github.com/Jimskapt/Listeur" );
		
		Optional<ButtonType> result=alert.showAndWait();
    }
	
	@FXML void newPathEvent(ActionEvent event)
	{
		try
		{
			Stage stage = new Stage();
			stage.setTitle( "Create a new path" );
			
			stage.initModality( Modality.APPLICATION_MODAL );
			stage.initOwner( ((Node)event.getSource()).getScene().getWindow() );
			
			FXMLLoader loader = new FXMLLoader(
					SourceWindow.class.getResource( "SourceWindow.fxml" ),
					new PropertyResourceBundle( Locales.class.getResource( "en_US.properties" ).openStream())
			);
			
			BorderPane root=(BorderPane)loader.load();
			
			stage.setScene(new Scene(root));
			
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
