import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FenetreProgression extends JFrame implements Runnable
{
	public FenetreProgression()
	{
		super("Progression du scan ...");
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setSize(200,200);
		this.setLayout(new GridLayout(2,1));
		
		derniereMAJ=System.currentTimeMillis();
	}
	
	long derniereMAJ;
	@Override
	public void run()
	{
		while(true)
		{
			this.repaint();
		}
	}
}
