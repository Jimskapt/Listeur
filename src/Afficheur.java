import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JComponent;

public class Afficheur extends JComponent
{
	protected int data=0;
	protected boolean[][] array=new boolean[][]{
			new boolean[]{true,true,false,true,true,true,true},
			new boolean[]{false,true,false,false,true,false,false},
			new boolean[]{true,true,true,false,false,true,true},
			new boolean[]{true,true,true,false,true,true,false},
			new boolean[]{false,true,true,true,true,false,false},
			new boolean[]{true,false,true,true,true,true,false},
			new boolean[]{true,false,true,true,true,true,true},
			new boolean[]{true,true,false,false,true,false,false},
			new boolean[]{true,true,true,true,true,true,true},
			new boolean[]{true,true,true,true,true,true,false}
	};
	
	public Afficheur( int data )
	{
		super();
		
		this.setValue(data);
	}
	
	public void setValue(int value)
	{
		if(value>9)
			value=9;
		if(value<0)
			value=0;
		
		this.data=value;
	}
	
	public void paintComponent(Graphics g)
	{
		Color couleurBaton=g.getColor();
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		g.setColor(couleurBaton);
		
		int epaisseur=Math.min(this.getWidth(), this.getHeight())/12;//5;
		
		int marge=epaisseur/4;
		
		int largeurBaton=this.getWidth()-4*epaisseur;
		int hauteurBaton=(this.getHeight()-5*epaisseur)/2;
		
		/* INDICES :
		____0____
		3		1
		____2____
		6		4
		____5____
		*/
		
		g.translate(epaisseur, epaisseur);
		
		if( (array[data][0]) )
		g.fillPolygon(	new int[]{0+marge,largeurBaton+2*epaisseur-marge,largeurBaton+epaisseur-marge,epaisseur+marge},
						new int[]{0,0,epaisseur,epaisseur},
						4);
		
		if( (array[data][1]) )
		g.fillPolygon(	new int[]{largeurBaton+2*epaisseur, largeurBaton+2*epaisseur, largeurBaton+epaisseur, largeurBaton+epaisseur},
						new int[]{0+marge,hauteurBaton+epaisseur+epaisseur/2-marge,hauteurBaton+epaisseur-marge,epaisseur+marge},
						4);
		
		if( (array[data][3]) )
		g.fillPolygon(	new int[]{0,0,epaisseur,epaisseur},
						new int[]{hauteurBaton+epaisseur+epaisseur/2-marge,0+marge,epaisseur+marge, hauteurBaton+epaisseur-marge},
						4);
		
		g.translate(0, hauteurBaton+epaisseur+epaisseur/2);
		
		if( (array[data][2]) )
		g.fillPolygon(	new int[]{2*epaisseur+largeurBaton-marge,epaisseur+largeurBaton-marge,epaisseur+marge,0+marge,epaisseur+marge,epaisseur+largeurBaton-marge},
						new int[]{0,epaisseur/2,epaisseur/2,0,-epaisseur/2,-epaisseur/2},
						6);
		
		if( (array[data][4]) )
		g.fillPolygon(	new int[]{largeurBaton+2*epaisseur, largeurBaton+2*epaisseur, largeurBaton+epaisseur, largeurBaton+epaisseur},
						new int[]{0+marge, hauteurBaton+epaisseur+epaisseur/2-marge,hauteurBaton+epaisseur/2-marge,epaisseur/2+marge},
						4);
		
		if( (array[data][6]) )
		g.fillPolygon(	new int[]{0,0,epaisseur,epaisseur},
						new int[]{epaisseur/2+hauteurBaton+epaisseur-marge, 0+marge, epaisseur/2+marge, hauteurBaton+epaisseur/2-marge},
						4);
		
		g.translate(0, hauteurBaton+epaisseur+epaisseur/2);
		
		if( (array[data][5]) )
		g.fillPolygon(	new int[]{2*epaisseur+largeurBaton-marge,0+marge,epaisseur+marge, largeurBaton+epaisseur-marge},
						new int[]{0,0,-epaisseur,-epaisseur},
						4);
		
		g.setColor(couleurBaton.brighter());
		
		g.setColor(couleurBaton);
	}
}
