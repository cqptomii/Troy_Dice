package feuille;
import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Cette classe modélise un des quartiers de la feuille de jeu
 * 
 * @version 1.0
 *
 * @see Quartier
 * @author Tom FRAISSE
 */
public class Quartier {
	/*
	 * 0 : orange
	 * 1 : bleu
	 * 2 : gris
	*/
	private int couleur;
	private ArrayList<Section> sections;
	private ArrayList<Lien> liens;
	
	private boolean multPrestige;
	private boolean multTravail;
	private FeuilleDeJeu currFeuille;
	
	private int score;	/* score total */
	private IntegerProperty scorePrestige; /* score lié à la construction des batiments de prestige */
	private IntegerProperty scoreTravail;  /* score lié à la construction des batiments de travail */
	private IntegerProperty scoreRessource; /* score lié aux nombre de ressource */ 
	
	/** 
	 * Cette méthode permet d'instancier un quartier
	 *  
	 * @param       couleur :  couleur du quartier crée
	 * @param       feuille : feuille de jeu associé au quartier
	 *	
	 * @see Quartier#Quartier 
	 * @author   Tom FRAISSE 
	 */
	public Quartier(int couleur,FeuilleDeJeu feuille) {
		this.score = 0;
		this.scorePrestige = new SimpleIntegerProperty(0);
		this.scoreTravail = new SimpleIntegerProperty(0);
		this.scoreRessource = new SimpleIntegerProperty(0);
		if(couleur >= 0 && couleur < 3) {
			this.couleur = couleur;
		}
		if(feuille != null) {
			this.currFeuille = feuille;
		}
		this.liens = new ArrayList<Lien>();
		this.sections = new ArrayList<Section>();
		this.multPrestige = false;
		this.multTravail = false;
	}

	/** 
	 * Cette méthode permet d'ajouter une section au quartier
	 *  
	 * @param       section :  section à ajouter
	 *	
	 * @see Quartier#addSection 
	 * @author   Tom FRAISSE 
	 */
	protected void addSection(Section section) {
		if(section != null && this.sections.size() < 7) {
			if(this.sections.contains(section) == false) {
				this.sections.add(section);
			}
		}
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'une des sections du quartier
	 *  
	 * @return Section n° index du quartier 
	 * @param       index :  index de la section à récuperer
	 *	
	 * @see Quartier#getSection 
	 * @author   Tom FRAISSE 
	 */
	public Section getSection(int index) {
		return this.sections.get(index);
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'ensemble des sections du quartier
	 *  
	 * @return ArrayList des sections du quartier
	 *	
	 * @see Quartier#getSections
	 * @author   Tom FRAISSE 
	 */
	protected ArrayList<Section> getSections(){
		return this.sections;
	}
	
	/** 
	 * Cette méthode permet d'ajouter un lien entre différents batiment dans le quartier
	 *  
	 * @param       lien : lien à ajouter
	 *	
	 * @see Quartier#addLien 
	 * @author   Tom FRAISSE 
	 */
	protected void addLien(Lien lien) {
		if(lien != null) {
			this.liens.add(lien);
		}
	}
	
	/** 
	 * Cette méthode permet de calculer le score du quartier
	 *	
	 * @see Quartier#computeScore 
	 * @author   Tom FRAISSE 
	 */
	public void computeScore() {
		scorePrestige.set(0);;
		scoreTravail.set(0);;
		scoreRessource.set(0);;
		if(this.multPrestige) {
			scorePrestige.set(this.currFeuille.getMultOwned() * this.getBatimentConstruit(true));
		}
		if(this.multTravail) {
			scoreTravail.set(this.currFeuille.getMultOwned() * this.getBatimentConstruit(false));
		}
		scoreRessource.set(this.currFeuille.getJoueur().getRessource(this.couleur) / 2);
		
		this.score = scorePrestige.get() + scoreTravail.get() + scoreRessource.get();
	}
	
	/** 
	 * Cette méthode permet d'obtenir le score actuel du quartier
	 *  
	 * @return score total du quartier
	 *	
	 * @see Quartier#Quartier 
	 * @author   Tom FRAISSE 
	 */
	public int getScore() {
		return this.score;
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty lié au scorePrestige
	 *  
	 * @return Instance de l'IntegerProperty lié au scorePrestige
	 * 
	 * @see Quartier#scorePrestigeProperty 
	 * @author   Tom FRAISSE 
	 */
	public IntegerProperty scorePrestigeProperty() {
		return this.scorePrestige;
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty lié au scoreTravail
	 *  
	 * @return Instance de l'IntegerProperty lié au scoreTravail
	 *	
	 * @see Quartier#scoreTravailProperty 
	 * @author   Tom FRAISSE 
	 */
	public IntegerProperty scoreTravailProperty() {
		return this.scoreTravail;
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty lié au scoreRessource
	 *  
	 * @return Instance de l'IntegerProperty lié au scoreRessource
	 *	
	 * @see Quartier#scoreRessourceProperty 
	 * @author   Tom FRAISSE 
	 */
	public IntegerProperty scoreRessourceProperty() {
		return this.scoreRessource;
	}
	
	/** 
	 * Cette méthode permet d'activer le multiplicateur sur les batiments de prestige
	 *	
	 * @see Quartier#addMultPrestige 
	 * @author   Tom FRAISSE 
	 */
	protected void addMultPrestige() {
		this.multPrestige = true;
	}
	
	/** 
	 * Cette méthode permet d'activer le multiplicateur sur les batiments de travail
	 *	
	 * @see Quartier#addMultTravail 
	 * @author   Tom FRAISSE 
	 */
	protected void addMultTravail() {
		this.multTravail = true;
	}
	
	/** 
	 * Cette méthode permet d'obtenir le nombre de batiment construit selon le type (Prestige / Travail)
	 *	
	 * @return nombre de batiment construit  
	 * @param       prestige : Type du batiment
	 *
	 * @see Quartier#getBatimentConstruit 
	 * @author   Tom FRAISSE 
	 */
	public int getBatimentConstruit(boolean prestige) {
		int amount = 0;
		int index = 0;
		
		if(prestige) {
			index = 0;
		}else {
			index = 1;
		}
		for(Section s : this.sections) {
			if(s.getBatiment(index).etat == 1) {
				amount++;
			}
		}
		return amount;
	}
}
