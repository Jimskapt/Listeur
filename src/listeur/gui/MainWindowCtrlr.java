package listeur.gui;

import java.util.ArrayList ;
import java.util.Arrays ;
import java.util.ResourceBundle ;

import javafx.collections.FXCollections ;
import javafx.collections.ObservableList ;
import javafx.fxml.FXML ;
import javafx.scene.control.Accordion ;
import javafx.scene.control.Button ;
import javafx.scene.control.ListView ;
import javafx.scene.control.TitledPane ;

public class MainWindowCtrlr
{
	@FXML private ResourceBundle resources;
    @FXML private Accordion instructions;
    @FXML private TitledPane paths;
    @FXML private ListView<String> pathsList;
    @FXML private Button actionPath;
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
		instructions.setExpandedPane( paths );
		
		actionPath.setText( resources.getString( "Add" ) );
		
		Arrays.asList( actionShow, actionExclude, actionSaveAs, actionRenderRules ).stream()
		.forEach( e ->
		{
			e.setText( resources.getString( "New" ) );
			e.setDisable( false );
		});
		
		pathsList.setItems( FXCollections.observableArrayList("C:\\","D:\\") );
		
		showList.setItems( FXCollections.observableArrayList("Files","Empty Files","Folders","Empty Folders") );
		
		excludeList.setItems( FXCollections.observableArrayList("Folders with names exactly .git","Files with names like /\\.temp$/") );
		
		saveAsList.setItems( FXCollections.observableArrayList("C:\\extract.xlsx",".xml (temporary file)",".txt without hierarchy") );
		
		renderRulesList.setItems( FXCollections.observableArrayList("Folders are yellow background","Empty folders are #CCCCCC background","Files are bold font") );
	}
}
