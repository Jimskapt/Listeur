import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.RecursiveTask;

import javax.swing.JTextArea;

public class Explorateur extends RecursiveTask<ArrayList>
{
	ArrayList<Explorateur> scans;
	protected ArrayList data;
	protected File dossier;
	protected static ArrayList<String> dossiersExclus=new ArrayList<String>(), fichiersExclus=new ArrayList<String>();
	
	public Explorateur(File dossier)
	{
		data=new ArrayList();
		scans=new ArrayList<>();
		
		this.dossier=dossier;
	}
	public Explorateur(File dossier, JTextArea source)
	{
		data=new ArrayList();
		scans=new ArrayList<>();
		
		this.dossier=dossier;
		
		String[] data=source.getText().split("\n");
		
		for( String in : data )
		{
			if( in.trim().endsWith("/") )
				dossiersExclus.add( in.trim() );
			else
				fichiersExclus.add( in.trim() );
		}
	}
	
	public File getDossier() { return dossier; }
	
	@Override
	protected ArrayList compute()
	{
		for( File fichier : dossier.listFiles() )
		{
			if( fichier.isDirectory() ) /*&& !Fenetre.exclusions.getText().split("\n") )*/
			{
				if( dossiersExclus.indexOf(fichier.getName()+"/")==-1 )
				{
					scans.add( new Explorateur(fichier) );
					scans.get( scans.size()-1 ).fork();
					
					data.add(fichier.getName()+"/");
					data.add("*~/"+fichier.getName());
				}
			}
			else
			{
				if( fichiersExclus.indexOf(fichier.getName())==-1 )
					data.add(fichier.getName());
				
				//Fenetre.compteurFichiers.increment();
			}
		}
		
		for( Explorateur scan : scans )
		{
			ArrayList incoming=scan.join();
			
			if( Fenetre.exclureDossiersVides.isSelected() && incoming.size()<=0 )
			{
				data.remove( data.indexOf(scan.getDossier().getName()+"/") );
				data.remove( data.indexOf("*~/"+scan.getDossier().getName()) );
			}
			else
				data.set( data.indexOf("*~/"+scan.getDossier().getName()), incoming);
			
			//Fenetre.compteurDossiers.increment();
		}
		
		return data;
	}
	
	public ArrayList getResultat() { return data; }
}
