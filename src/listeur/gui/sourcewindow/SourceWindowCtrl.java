package listeur.gui.sourcewindow;

import java.io.File ;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node ;
import javafx.scene.control.TextField ;
import javafx.scene.control.TreeView ;
import javafx.stage.DirectoryChooser ;
import javafx.stage.Stage ;

public class SourceWindowCtrl
{
    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private TextField localPathSelected;
    @FXML private TreeView<String> pathTree;

    @FXML void initialize()
    {
    	
    }
    
    @FXML void browseLocalEvent(ActionEvent event)
    {
    	DirectoryChooser chooser=new DirectoryChooser();
    	chooser.setTitle( "Select a local folder" );
    	
    	File choosen=chooser.showDialog( ((Node)event.getSource()).getScene().getWindow() );
    	
    	if(choosen!=null)
    	{
    		System.out.println( choosen );
    		
	    	if( choosen.exists() && choosen.isDirectory() )
	    		localPathSelected.setText( choosen.toString() );
    	}
    }

    @FXML void cancelEvent(ActionEvent event)
    {
    	this.closeWindow( event );
    }
    
    @FXML void okEvent(ActionEvent event)
    {
    	this.closeWindow( event );
    }
    
    private void closeWindow(ActionEvent event)
    {
    	this.closeWindow( (Node)(event.getSource()) );
    }
    private void closeWindow(Node source)
    {
    	((Stage)source.getScene().getWindow()).close();
    }
}
