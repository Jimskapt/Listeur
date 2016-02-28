import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JButton;
import javax.swing.JColorChooser;


public class BoutonCouleur extends JButton implements MouseListener
{
	protected Color fond,texte;
	protected String label;
	
	public BoutonCouleur(Color fond, Color texte, String label)
	{
		super();
		
		this.setFond(fond);
		this.setTexte(texte);
		this.label=label;
		
		this.addMouseListener(this);
		
		this.setToolTipText("Click gauche : modification du fond. Click droit : modification du texte.");
	}
	
	public void setFond(Color couleur) { this.fond=couleur; }
	public void setTexte(Color couleur) { this.texte=couleur; }
	
	public Color getFond() { return fond; }
	public Color getTexte() { return texte; }
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int x=(int)(0.1*getWidth());
		int y=(int)(0.1*getHeight());
		int w=(int)(0.8*getWidth());
		int h=(int)(0.8*getHeight());
		
		if( this.isEnabled() )
			g.setColor(fond);
		else
			g.setColor( new Color(220,220,220) );
		g.fillRect( x, y, w, h );
		
		g.setFont( new Font("arial", Font.CENTER_BASELINE, h/3) );
		FontMetrics fm   = g.getFontMetrics();
		Rectangle2D rect = fm.getStringBounds(label, g);
		
		if( this.isEnabled() )
			g.setColor(texte);
		else
			g.setColor( Color.GRAY );
		g.drawString(label, (int)(x+w/2-rect.getWidth()/2), (int)(y+h/2+rect.getHeight()/2));
		
		if( this.isEnabled() )
		{
			g.setColor(fond.darker());
			g.drawRect( x, y, w, h );
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) { }

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) { }

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(this.isEnabled())
		{
			if( e.getButton()==MouseEvent.BUTTON1 )
			{
				Color resultat=JColorChooser.showDialog(null, "Choix de la couleur de fond", fond);
				
				if(resultat!=null)
				{
					int r=resultat.getRed();
					if(r<=0)
						r=1;
					else if(r>=255)
						r=254;
					int g=resultat.getGreen();
					int b=resultat.getBlue();
					
					fond=new Color(r,g,b);
				}
			}
			else if( e.getButton()==MouseEvent.BUTTON3 )
			{
				Color resultat=JColorChooser.showDialog(null, "Choix de la couleur du texte", texte);
				
				if(resultat!=null)
				{
					int r=resultat.getRed();
					if(r<=0)
						r=1;
					else if(r>=255)
						r=254;
					int g=resultat.getGreen();
					int b=resultat.getBlue();
					
					texte=new Color(r,g,b);
				}
			}
		}
	}
}

