package listeur.gui.tools;

import javafx.stage.Window ;

abstract public class CustomModalWindowCtrl
{
	protected Window parent;
	
	public void setParentWindow( Window parent )
	{
		this.parent=parent;
	}
}
