package listeur.gui.exceptionWindow;

import javafx.stage.Window ;
import listeur.gui.tools.CustomModalWindow ;
import listeur.gui.tools.CustomModalWindowCtrl ;

public class ExceptionWindow extends CustomModalWindow
{
	public ExceptionWindow( Window parent, String description, Exception exception )
	{
		super( parent, "Error : Exception" );
		
		((ExceptionWindowCtrl)ctrl).setDescription( description );
		((ExceptionWindowCtrl)ctrl).setException( exception );
	}
}
