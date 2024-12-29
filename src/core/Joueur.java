   package core;
import feuille.FeuilleDeJeu;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Joueur {
	protected String name;
	protected int gpa;
	protected IntegerProperty score;
	protected IntegerProperty [] habitantObtenu;
	protected IntegerProperty [] ressources;
	protected FeuilleDeJeu feuille;
	
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
        	this.habitantObtenu[i] = new SimpleIntegerProperty(1);
        }
    }
	
	public void setFeuille(FeuilleDeJeu feuille) {
		if(feuille != null) {
			this.feuille = feuille;
		}else {
			System.out.println("Entrez une feuille de jeu valide");
		}
		
	}
	
	public int getScore() {
		return this.score.get();
	}
	public void updateScore() {
		this.score.set(this.feuille.computeScore());
	}
	public IntegerProperty scoreProperty() {
		return this.score;
	}
	public int getGpa() {
		return this.gpa;
	}
	public String getName() {
		return this.name;
	}
	public int getRessource(int type) {
		try {
			return this.ressources[type].get();
		}catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites lors du placement des dés : " + e.getMessage());
	    }
		return -1;
	}
	public boolean canUse(int type,int montant) {
		if(this.ressources[type].get() >= montant) {
			return true;
		}
		return false;
	}
	protected void ajouterRessource(int type, int montant) {
		if(isValidType(type)){
			this.ressources[type].set(this.ressources[type].get() + montant);
		}
	}
	protected void utiliserRessource(int type, int montant) {
		if(isValidType(type)) {
			this.ressources[type].set(this.ressources[type].get() - montant);
		}
	}
	private boolean isValidType(int type) {
        return type >= 0 && type < this.ressources.length;
    }
	public IntegerProperty ressourceProperty(int type) {
		try {
			return this.ressources[type];
		}catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites lors du placement des dés : " + e.getMessage());
	    }
		return null;
    }
	
	protected void ajouterHabitant(int type,int montant) {
		if(isValidType(type)) {
			this.habitantObtenu[type].set(this.habitantObtenu[type].get() + montant);
		}
	}
	
	public IntegerProperty habitantProperty(int type) {
		try {
			return this.habitantObtenu[type];
		}catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites lors du placement des dés : " + e.getMessage());
	    }
		return null;
    }
	
	public int getHabitant(int type){
		return this.habitantObtenu[type].get();
	}
	public FeuilleDeJeu getFeuille() {
		return this.feuille;
	}
}
