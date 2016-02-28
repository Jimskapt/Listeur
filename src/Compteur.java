import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Compteur extends JPanel
{
	protected ArrayList<Afficheur> afficheurs;
	protected int valeur=0;
	
	public Compteur(int valeur)
	{
		super();
		
		afficheurs=new ArrayList<>();
		
		this.setLayout(new GridLayout(1,1));
		
		this.setValeur(valeur);
	}
	
	public int getValeur() { return this.valeur; }
	
	public void setValeur(int valeur)
	{
		if(valeur<0)
			valeur=0;
		
		this.valeur=valeur;
		
		this.removeAll();
		
		int i=0;
		while(valeur>=Math.pow(10,i))
		{
			i++;
			
			if( afficheurs.size()<i )
				afficheurs.add(new Afficheur(0));
			
			afficheurs.get(i-1).setValue(new Integer(new String(valeur+"").charAt(i-1)+"" ) );
			
			this.add( afficheurs.get(i-1) );
		}
		
		((GridLayout)this.getLayout()).setColumns(i);
		
		this.revalidate();
		this.repaint();
	}
	
	public void increment()
	{
		this.setValeur( this.getValeur()+1 );
	}
}
