   package core;
import feuille.FeuilleDeJeu;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import plateau.De;

/**
 * Cette classe modélise un joueur
 * 
 * @version 1.0
 *
 * @see Joueur
 * @author Tom FRAISSE
 */
public class Joueur {
	protected String name;
	protected int gpa;
	protected IntegerProperty score;
	protected IntegerProperty [] habitantObtenu;
	protected IntegerProperty [] ressources;
	protected FeuilleDeJeu feuille;
	
	/** 
	 * Cette méthode permet d'instancier un joueur.
	 *  
	 * @param        name : Nom du joueur 
	 * @param       gpa : Montant du gpa du joueur 
	 * 
	 * @see Joueur#Joueur 
	 * @author Tom FRAISSE
	 */
	public Joueur(String name, int gpa) {
        this.name = (name != null) ? name : "Invité";
        this.gpa = (gpa >= 0 && gpa <= 100) ? gpa : 0;
        this.score = new SimpleIntegerProperty(0);

        this.ressources = new IntegerProperty[3];
        for (int i = 0; i < this.ressources.length; i++) {
            this.ressources[i] = new SimpleIntegerProperty(3);
        }
        
        this.habitantObtenu = new IntegerProperty[3];
        for(int i=0; i < this.habitantObtenu.length;++i) {
        	this.habitantObtenu[i] = new SimpleIntegerProperty(0);
        }
    }
	
	/** 
	 * Cette méthode permet de modifier la feuille de jeu associer au joueur
	 *  
	 * @param        feuille : feuille de jeu
	 * 
	 * @see Joueur#setFeuille 
	 * @author Tom FRAISSE
	 */
	public void setFeuille(FeuilleDeJeu feuille) {
		if(feuille != null) {
			this.feuille = feuille;
		}else {
			System.out.println("Entrez une feuille de jeu valide");
		}
		
	}
	
	/** 
	 * Cette méthode permet d'obtenir le score du joueur
	 *  
	 * @return score total du joueur
	 * 
	 * @see Joueur#getScore
	 * @author Tom FRAISSE
	 */
	public int getScore() {
		return this.score.get();
	}
	
	/** 
	 * Cette méthode permet de mettre à jour le score du joueur
	 *  
	 * @see Joueur#updateScore 
	 * @author Tom FRAISSE
	 */
	public void updateScore() {
		this.score.set(this.feuille.computeScore());
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty lié au score.
	 * 
	 * @return Instance de la propriété observable lié au score
	 * 
	 * @see Joueur#scoreProperty 
	 * @author Tom FRAISSE
	 */
	public IntegerProperty scoreProperty() {
		return this.score;
	}
	
	/** 
	 * Cette méthode permet d'obtenir le gpa du joueur
	 *  
	 * @return : gpa du joueur
	 * 
	 * @see Joueur#getGpa 
	 * @author Tom FRAISSE
	 */
	public int getGpa() {
		return this.gpa;
	}
	
	/** 
	 * Cette méthode permet d'obtenir le nom du joueur
	 *  
	 * @return : nom du joueur
	 * 
	 * @see Joueur#getName 
	 * @author Tom FRAISSE
	 */
	public String getName() {
		return this.name;
	}
	
	/** 
	 * Cette méthode permet d'obtenir le montant de la ressource selon son type.
	 *  
	 * @return : le montant de la ressource
	 * @param       type : type de ressource à récuperer
	 * 
	 * @see Joueur#getRessource 
	 * @author Tom FRAISSE
	 */
	public int getRessource(int type) {
		try {
			return this.ressources[type].get();
		}catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites lors du placement des dés : " + e.getMessage());
	    }
		return -1;
	}
	
	/** 
	 * Cette méthode permet de verifier si le joueur peut utiliser le montant de ressource
	 *  
	 * @return True : utilisable / False : sinon
	 * @param       type : type de la ressource 
	 * @param       montant : nombre de ressource
	 * 
	 * @see Joueur#canUse 
	 * @author Tom FRAISSE
	 */
	public boolean canUse(int type,int montant) {
		if(this.ressources[type].get() >= montant) {
			return true;
		}
		return false;
	}
	
	/** 
	 * Cette méthode permet d'ajouter un certain nombre de ressource au joueur
	 *  
	 * @param       type : type de la ressource 
	 * @param       montant : nombre de ressource
	 * 
	 * @see Joueur#ajouterRessource 
	 * @author Tom FRAISSE
	 */
	protected void ajouterRessource(int type, int montant) {
		if(isValidType(type)){
			this.ressources[type].set(this.ressources[type].get() + montant);
		}
	}
	
	/** 
	 * Cette méthode permet d'utiliser un certain nombre de ressource 
	 *  
	 * @param       type : type de la ressource 
	 * @param       montant : nombre de ressource
	 * 
	 * @see Joueur#utiliserRessource 
	 * @author Tom FRAISSE
	 */
	protected void utiliserRessource(int type, int montant) {
		if(isValidType(type)) {
			this.ressources[type].set(this.ressources[type].get() - montant);
		}
	}
	
	/** 
	 * Cette méthode permet de verifier si le tyoe de la ressource est valide.
	 *  
	 * @param       type : type de la ressource 
	 * 
	 * @see Joueur#isValidType 
	 * @author Tom FRAISSE
	 */
	private boolean isValidType(int type) {
        return type >= 0 && type < this.ressources.length;
    }
	
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty lié à la ressource.
	 * 
	 * @return : Instance de l'objet observable lié à la ressource
	 * @param       type : type de la ressource 
	 * 
	 * @see Joueur#ressourceProperty 
	 * @author Tom FRAISSE
	 */
	public IntegerProperty ressourceProperty(int type) {
		try {
			return this.ressources[type];
		}catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites lors du placement des dés : " + e.getMessage());
	    }
		return null;
    }
	
	/** 
	 * Cette méthode permet d'ajouter un habitant au joueur.
	 *  
	 * @param       type : type de la ressource 
	 * @param       montant : nombre de ressource
	 * 
	 * @see Joueur#ajouterHabitant 
	 * @author Tom FRAISSE
	 */
	protected void ajouterHabitant(int type,int montant) {
		if(isValidType(type)) {
			this.habitantObtenu[type].set(this.habitantObtenu[type].get() + montant);
		}
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty lié à la ressource.
	 * 
	 * @return : Instance de l'objet observable lié aux habitants
	 * @param       color : couleur de l'habitant 
	 * 
	 * @exception IndexOutOfBoundsException / Index hors limite
	 * 
	 * @see Joueur#habitantProperty 
	 * @author Tom FRAISSE
	 */
	public IntegerProperty habitantProperty(int color) throws IndexOutOfBoundsException {
		try {
			return this.habitantObtenu[color];
		}catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites lors du placement des dés : " + e.getMessage());
	    }
		return null;
    }
	
	/** 
	 * Cette méthode permet d'obtenir le nombre d'habitant que le joueur possède
	 *  
	 * @return : nombre d'habitants
	 * @param       color  : couleur de l'habitant 
	 * 
	 * @see Joueur#getHabitant 
	 * @author Tom FRAISSE
	 */
	public int getHabitant(int color){
		return this.habitantObtenu[color].get();
	}
	
	/** 
	 * Cette méthode permet d'obtenir la feuille de jeu lié au joueur.
	 *  
	 * @return : feuille de jeu associé au joueur
	 * 
	 * @see Joueur#getFeuille 
	 * @author Tom FRAISSE
	 */
	public FeuilleDeJeu getFeuille() {
		return this.feuille;
	}
}
