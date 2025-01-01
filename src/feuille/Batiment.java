package feuille;

/**
 * Classe modélisant la classe mere des batiments
 * 
 * @version 1.0
 *
 * @see Batiments
 * @author Tom FRAISSE
 */
public abstract class Batiment {
	private String name;
	/*
	 * 1 : orange
	 * 2 : bleu
	 * 3 : gris
	 */
	protected int colorBatiment;
	protected int habitant;
	protected int colorHabitant;
	/* Etat du batiment
	 * 0 : constructible
	 * 1 : construit
	 * 2 : détruit
	 */
	protected int etat;
	protected Lien bonusLien;
	protected Section currSection;
	protected FeuilleDeJeu currFeuille;
	
	/** 
	 * Cette méthode permet d'instancier le batiment
	 *  
	 * @param       parent : Feuille de jeu associé au batiment 
	 * @param       name : Nom du batiment 
	 * @param		nombreHabitants : nombre d'habitant du batiment
	 * @param 		colorH : Couleur des habitants
	 * @param       colorB : couleur du batiment
	 * 
	 * @see Batiment#Batiment
	 * @author  Tom FRAISSE 
	 */
	public Batiment(FeuilleDeJeu parent,String name,int nombreHabitants,int colorH,int colorB) {
		if(name != null) {
			this.name = name;
		}else {
			this.name = new String("untilted");
		}
		this.bonusLien = null;
		this.currSection = null;
		this.currFeuille = parent;
		this.etat = 0;
		if(nombreHabitants > 0 & nombreHabitants < 3) {
			this.habitant = nombreHabitants;
			this.colorHabitant = colorH;
		}
		this.colorBatiment = colorB;
	}
	
	/** 
	 * Cette méthode permet de Construire un batiment de travail
	 *  
	 * @param       nbDe : nombre de dé de la couleur lié au multiplicateur
	 * 
	 * @see Batiment#construire
	 * @author  Tom FRAISSE 
	 */
	public abstract void construire(int nbDe);
	
	/** 
	 * Cette méthode permet de Construire un batiment de travail
	 *  
	 * @see Batiment#invaliderBatiment
	 * @author  Tom FRAISSE 
	 */
	public void invaliderBatiment() {
		if(currSection != null) {
			if(currSection.getProteger() == false) {
				if(this.etat == 0) {
					this.etat = 2;
				}
			}
		}
	}
	
	/** 
	 * Cette méthode permet de modifier le lien associé au batiment
	 *  
	 * @param 		lien : lien lié au batiment
	 * 
	 * @see Batiment#setBonusLien
	 * @author  Tom FRAISSE 
	 */
	public void setBonusLien(Lien lien) {
		if(this.bonusLien == null) {
			this.bonusLien = lien;
		}
	}
	
	/** 
	 * Cette méthode permet de Construire un batiment de travail
	 *  
	 * @return instance vers le lien bonus
	 * 
	 * @see Batiment#getBonusLien
	 * @author  Tom FRAISSE 
	 */
	public Lien getBonusLien() {
		return this.bonusLien;
	}
	
	/** 
	 * Cette méthode permet de modifier la section associé avec le batiment
	 *  
	 * @param       section : section à associé avec le batiment
	 * 
	 * @see Batiment#setCurrSection
	 * @author  Tom FRAISSE 
	 */
	public void setCurrSection(Section section) {
		if(this.currSection == null ) {
			this.currSection = section;
		}
	}
	
	/** 
	 * Cette méthode permet de verifier si le batiment est constructible ou non 
	 *  
	 * @return  True : Constructible / False : non constructible
	 * 
	 * @see Batiment#isConstructible
	 * @author  Tom FRAISSE 
	 */
	public boolean isConstructible() {
		return (this.etat != 2&& this.etat != 1);
	}
	
	/** 
	 * Cette méthode permet de récuperer l'état du batiment
	 *  
	 * @return état du batiment
	 * 
	 * @see Batiment#getEtat
	 * @author  Tom FRAISSE 
	 */
	public int getEtat() {
		return this.etat;
	}
	
	/** 
	 * Cette méthode permet de savoir si deux batiments sont égale ou non
	 * 
	 * @return True : les deux batiments sont égales / False : les batiments sont différents
	 * @param       b : Batiment à verifier
	 * 
	 * @see Batiment#equals
	 * @author  Tom FRAISSE 
	 */
	public boolean equals(Batiment b) {
		if(b.name.equals(name) && b.habitant == this.habitant && b.etat == this.etat && this.currSection == b.currSection) {
			return true;
		}
		return false;
	}
	
	/** 
	 * Cette méthode permet d'obtenir la couleur des habitants dans le batiment
	 *  
	 * @return couleur des habitants du batiment
	 * 
	 * @see Batiment#getColorHabitant
	 * @author  Tom FRAISSE 
	 */
	public int getColorHabitant() {
		return this.colorHabitant;
	}
	
	/** 
	 * Cette méthode permet d'obtenir le nombre d'habitant du batiment
	 *  
	 * @return nombre d'habitants
	 * 
	 * @see Batiment#getAmountHabitant
	 * @author  Tom FRAISSE 
	 */
	public int getAmountHabitant() {
		return this.habitant;
	}
	
	/** 
	 * Cette méthode permet d'obtenir une chaine de caractère qui décrit le batiment
	 *  
	 * @return chaine de caractère
	 * 
	 * @see Batiment#toString
	 * @author  Tom FRAISSE 
	 */
	public String toString() {
		String temp = new String("Batiment :" + this.name + " , Habitant : " + this.habitant);
		return temp;
	}
}
