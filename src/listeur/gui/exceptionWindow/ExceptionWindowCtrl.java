package listeur.gui.exceptionWindow;

import java.io.PrintWriter ;
import java.io.StringWriter ;
import java.net.URL ;
import java.util.ResourceBundle ;

import javafx.event.ActionEvent ;
import javafx.fxml.FXML ;
import javafx.scene.Node ;
import javafx.scene.control.Button ;
import javafx.scene.control.TextArea ;
import javafx.scene.input.MouseEvent ;
import javafx.scene.text.Text ;
import javafx.stage.Stage ;
import javafx.stage.Window ;

public class ExceptionWindowCtrl
{
	protected Window parent;
	
	@FXML private ResourceBundle resources;
	@FXML private URL location;
	@FXML private Button ok;
	@FXML private Text description;
	@FXML private TextArea exceptionTrace;
	
	@FXML void okEvent(ActionEvent event)
	{
		this.closeWindow( event );
	}

	@FXML void initialize()
	{
		assert ok != null : "fx:id=\"ok\" was not injected: check your FXML file 'ExceptionWindow.fxml'.";
		assert description != null : "fx:id=\"description\" was not injected: check your FXML file 'ExceptionWindow.fxml'.";
		assert exceptionTrace != null : "fx:id=\"exceptionTrace\" was not injected: check your FXML file 'ExceptionWindow.fxml'.";

	}
	
	@FXML void refreshWindowSize(MouseEvent event)
	{
		ok.getScene().getWindow().sizeToScene();
    }
	
	private void closeWindow(ActionEvent event)
    {
    	this.closeWindow( (Node)(event.getSource()) );
    }
    
    private void closeWindow(Node source)
    {
    	((Stage)source.getScene().getWindow()).close();
    }
	
	public void setParentWindow( Window parent )
	{
		this.parent=parent;
	}
	
	public void setDescription( String description )
	{
		this.description.setText( description );
	}
	
	public void setException( Exception e )
	{
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		
		this.exceptionTrace.setText( sw.toString() );
	}
}
