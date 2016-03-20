package listeur.gui.sourcewindow;

import javafx.stage.Window ;
import listeur.core.Source ;
import listeur.gui.tools.CustomModalWindow ;

public class SourceWindow extends CustomModalWindow
{
	public SourceWindow( Window parent )
	{
		this( parent, null );
	}
	
	public SourceWindow( Window parent, Source target )
	{
		super( parent, "Source" );
		
		if( target!=null )
			((SourceWindowCtrl)ctrl).setTarget(target);
	}
}
