import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Fenetre extends JFrame implements ActionListener
{
	protected JCheckBox uniquementDossiers,dossiersVides,dossiersVidesCVide;
	protected JTextArea exclusions;
	public static JCheckBox exclureDossiersVides=new JCheckBox("Exclure les dossiers vides", false);
	protected BoutonCouleur dossier, dossierVide, fichiers;
	protected JTextField dossierCible;
	protected JButton lancerScan, parcourir;
	protected JFileChooser jfc;
	protected XSSFCellStyle styleDossier, styleDossierVide, styleFichier;
	protected FenetreProgression fenetreProgression;
	protected Compteur compteurFichiers,compteurDossiers;
	
	public Fenetre()
	{
		super("Listeur 0.1D");
		
		this.setSize(500,500);
		
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel controle=new JPanel();
		controle.setLayout(new BorderLayout());
		
		JPanel selection=new JPanel();
		selection.add(new JLabel("Dossier cible :"));
		
		dossierCible=new JTextField(35);
		dossierCible.setEditable(false);
		dossierCible.addMouseListener(new MouseListener()
		{
			public void mouseReleased(MouseEvent e) { parcourir.doClick(); }
			public void mousePressed(MouseEvent e) { }
			public void mouseExited(MouseEvent e) { }
			public void mouseEntered(MouseEvent e) { }
			public void mouseClicked(MouseEvent e) { }
		});
		selection.add(dossierCible);
		
		jfc=new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		parcourir=new JButton("Parcourir");
		parcourir.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				int result=jfc.showDialog(null, "Choisir ce dossier");
				
				if(result==JFileChooser.APPROVE_OPTION)
					dossierCible.setText( jfc.getSelectedFile().getAbsolutePath() );
			}
		});
		
		selection.add(parcourir);
		controle.add(selection, BorderLayout.NORTH);
		
		JPanel center=new JPanel();
		center.setLayout(new GridLayout(3,1));
		
		// ------------------------------------
		
		JPanel configuration=new JPanel();
		configuration.setLayout(new GridLayout(3,1));
		configuration.setBorder(BorderFactory.createTitledBorder("Configuration"));
		
		uniquementDossiers=new JCheckBox("Lister uniquement les dossiers (et pas les fichiers)",false);
		dossiersVidesCVide=new JCheckBox("Considérer qu'un dossier rempli de dossiers vides est lui-même vide",true);
		dossiersVides=new JCheckBox("Identifier les dossiers vides",true);
		
		uniquementDossiers.addActionListener(this);
		dossiersVides.addActionListener(this);
		
		configuration.add(uniquementDossiers);
		configuration.add(dossiersVides);
		configuration.add(dossiersVidesCVide);
		
		center.add(configuration);
		
		// ------------------------------------
		
		JPanel panneauExclusions=new JPanel();
		panneauExclusions.setLayout(new BorderLayout());
		panneauExclusions.setBorder(BorderFactory.createTitledBorder("Exclusions"));
		
		exclusions=new JTextArea("OldVersions/\nlockfile.lck");
		exclureDossiersVides.addActionListener( this );
		
		panneauExclusions.add( new JScrollPane(exclusions, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER );
		panneauExclusions.add( exclureDossiersVides, BorderLayout.SOUTH );
		
		center.add(panneauExclusions);
		
		// ------------------------------------
		
		JPanel couleurs=new JPanel();
		couleurs.setLayout(new GridLayout(3,1));
		couleurs.setBorder(BorderFactory.createTitledBorder("Couleurs"));
		
		dossier=new BoutonCouleur(new Color(255-1,255,0), new Color(0+1,0,0), "DOSSIER");
		dossierVide=new BoutonCouleur(new Color(128-1,128,128), new Color(255-1,255,255), "DOSSIER VIDE");
		fichiers=new BoutonCouleur(new Color(0+1,255,255), new Color(0+1,0,0), "FICHIER");
		
		JPanel c1=new JPanel();
		c1.setLayout(new GridLayout(1,2));
		c1.add(new JLabel("Couleurs des dossiers"));
		c1.add(dossier);
		couleurs.add(c1);
		
		JPanel c2=new JPanel();
		c2.setLayout(new GridLayout(1,2));
		c2.add(new JLabel("Couleurs des dossiers vides"));
		c2.add(dossierVide);
		couleurs.add(c2);
		
		JPanel c3=new JPanel();
		c3.setLayout(new GridLayout(1,2));
		c3.add(new JLabel("Couleurs des fichiers"));
		c3.add(fichiers);
		couleurs.add(c3);
		
		center.add(couleurs);
		
		// ------------------------------------
		
		lancerScan=new JButton("Lancement du scan du dossier");
		lancerScan.addActionListener(this);
		controle.add(lancerScan, BorderLayout.SOUTH);
		
		// ------------------------------------
		
		controle.add(center, BorderLayout.CENTER);
		
		this.add(controle);
		
		this.setVisible(true);
		
		fenetreProgression=new FenetreProgression();
		
		compteurFichiers=new Compteur(0);
		compteurDossiers=new Compteur(0);
		
		fenetreProgression.add(compteurFichiers);
		fenetreProgression.add(compteurDossiers);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource().equals(dossiersVides))
		{
			if(dossiersVides.isSelected())
			{
				dossiersVidesCVide.setEnabled(true);
				dossierVide.setEnabled(true);
			}
			else
			{
				dossiersVidesCVide.setEnabled(false);
				dossierVide.setEnabled(false);
			}
		}
		else if(e.getSource().equals(exclureDossiersVides))
		{
			if(exclureDossiersVides.isSelected())
			{
				dossierVide.setEnabled(false);
				dossiersVides.setEnabled(false);
				dossiersVidesCVide.setEnabled(false);
			}
			else
			{
				dossierVide.setEnabled(true);
				dossiersVides.setEnabled(true);
				dossiersVidesCVide.setEnabled(true);
			}
		}
		else if(e.getSource().equals(uniquementDossiers))
		{
			if(uniquementDossiers.isSelected())
				fichiers.setEnabled(false);
			else
				fichiers.setEnabled(true);
		}
		else if(e.getSource().equals(lancerScan))
		{
			if( dossierCible.getText().trim().equals("") )
			{
				JOptionPane.showMessageDialog(	this,
												"Veuillez sélectionner un dossier à scanner",
												"Pas de dossier sélectionné",
												JOptionPane.INFORMATION_MESSAGE
											 );
				parcourir.doClick();
			}
			else
			{
				File cible=new File(dossierCible.getText());
				
				if( cible.exists() )
				{
					if(cible.canRead())
					{
						fenetreProgression.setVisible(true);
						
						Explorateur explorateur=new Explorateur(cible, exclusions);
						
						int procos=Runtime.getRuntime().availableProcessors();
						ForkJoinPool pool=new ForkJoinPool( procos );
						pool.invoke(explorateur);
						
						XSSFWorkbook dossierExcel=new XSSFWorkbook();
						XSSFSheet feuilleExcel=dossierExcel.createSheet();
						
						styleDossier = dossierExcel.createCellStyle();
						styleDossier.setFillPattern(CellStyle.SOLID_FOREGROUND);
						styleDossier.setFillForegroundColor(new XSSFColor(dossier.getFond()));
						XSSFFont police = dossierExcel.createFont();
						police.setFontHeightInPoints((short)8);
						Color couleur=dossier.getTexte();
						police.setColor(new XSSFColor(couleur));
						styleDossier.setFont(police);
						
						styleDossierVide = dossierExcel.createCellStyle();
						styleDossierVide.setFillPattern(CellStyle.SOLID_FOREGROUND);
						styleDossierVide.setFillForegroundColor(new XSSFColor(dossierVide.getFond()));
						XSSFFont police2 = dossierExcel.createFont();
						police2.setFontHeightInPoints((short)8);
						couleur=dossierVide.getTexte();
						police2.setColor(new XSSFColor(couleur));
						styleDossierVide.setFont(police2);
						
						styleFichier = dossierExcel.createCellStyle();
						styleFichier.setFillPattern(CellStyle.SOLID_FOREGROUND);
						styleFichier.setFillForegroundColor(new XSSFColor(fichiers.getFond()));
						XSSFFont police3 = dossierExcel.createFont();
						police3.setFontHeightInPoints((short)8);
						couleur=fichiers.getTexte();
						police3.setColor(new XSSFColor(couleur));
						styleFichier.setFont(police3);
						
						sauvegarder(explorateur.getResultat(), feuilleExcel, -1, 0);
						
						for (int i=0 ; i<=15 ; i++)
							feuilleExcel.autoSizeColumn(i);
						
						try
						{
							File fichierTemp=File.createTempFile(System.currentTimeMillis()+"", ".xlsx");
							DataOutputStream FOS=new DataOutputStream( new BufferedOutputStream( new FileOutputStream( fichierTemp ) ) );
							dossierExcel.write( FOS );
							FOS.close();
							
							try
							{
								Desktop.getDesktop().open(fichierTemp);
							}
							catch (Exception e1) { /*TODO a gérer*/ }
						}
						catch (IOException e1)
						{
							JOptionPane.showMessageDialog(	null,
															"Erreur lors de l'enregistrement du fichier !",
															"Erreur d'enregistrement !",
															JOptionPane.ERROR_MESSAGE	);
						}
						
						fenetreProgression.setVisible(false);
					}
					else
						JOptionPane.showMessageDialog(	this,
														"Vous n'avez pas les droits pour lire le dossier. Contactez l'administrateur réseau pour pouvoir le faire.",
														"Erreur droit de lecture",
														JOptionPane.ERROR_MESSAGE
													 );
				}
				else
				{
					JOptionPane.showMessageDialog(	this,
													"L'adresse du dossier est erronée, veuillez recommencer.",
													"Erreur adresse dossier",
													JOptionPane.ERROR_MESSAGE
												 );
					parcourir.doClick();
				}
			}
		}
	}
	
	public int sauvegarder(ArrayList data, XSSFSheet feuille, int ligne, int colonne)
	{
		for( int i=0 ; i<data.size() ; i++)
		{
			Object in=data.get(i);
			
			if( ArrayList.class.isInstance(in) )
				ligne=sauvegarder( (ArrayList)in, feuille, ligne, colonne+1);
			else
			{
				if( (!uniquementDossiers.isSelected() || (uniquementDossiers.isSelected() && ((String)in).trim().endsWith("/"))) )
				{
					ligne++;
					XSSFRow ligneXLS=feuille.createRow(ligne);
					XSSFCell cellule=ligneXLS.createCell(colonne);
					cellule.setCellValue( (String)in );
					
					if( ((String)in).trim().endsWith("/") )
					{
						cellule.setCellStyle(styleDossier);
						
						if( i+1<data.size() && dossiersVides.isSelected() )
						{
							if( estVide((ArrayList)data.get(i+1)) )
								cellule.setCellStyle(styleDossierVide);
						}
					}
					else
					{
						if( !uniquementDossiers.isSelected() )
							cellule.setCellStyle(styleFichier);
					}
				}
			}
		}
		
		return ligne;
	}
	
	public boolean estVide(ArrayList data)
	{
		boolean result=true;
		
		for(int i=0;i<data.size();i++)
		{
			if( String.class.isInstance( data.get(i) ) )
			{
				if( ((String)data.get(i)).trim().endsWith("/") && dossiersVidesCVide.isSelected() )
					result&=estVide((ArrayList)data.get(i+1));
				else
				{
					result=false;
					break;
				}
			}
		}
		
		return result;
	}
	
	public static void main(String[] args)
	{
		try {
	        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (InstantiationException e) {
	        e.printStackTrace();
	    } catch (IllegalAccessException e) {
	        e.printStackTrace();
	    } catch (UnsupportedLookAndFeelException e) {
	        e.printStackTrace();
	    }
		
		new Fenetre();
	}
}
