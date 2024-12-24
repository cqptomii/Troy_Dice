package feuille;

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
	
	public abstract void construire();
	
	public void invaliderBatiment() {
		if(currSection != null) {
			if(currSection.getProteger() == false) {
				if(this.etat == 0) {
					this.etat = 2;
				}
			}
		}
	}
	
	public void setBonusLien(Lien lien) {
		if(this.bonusLien == null) {
			this.bonusLien = lien;
		}
	}
	public void setCurrSection(Section section) {
		if(this.currSection == null ) {
			this.currSection = section;
		}
	}
	public boolean isConstructible() {
		return this.etat != 2;
	}
	public int getEtat() {
		return this.etat;
	}
	public boolean equals(Batiment b) {
		if(b.name.equals(name) && b.habitant == this.habitant && b.etat == this.etat && this.currSection == b.currSection) {
			return true;
		}
		return false;
	}
	public String toString() {
		String temp = new String("Batiment :" + this.name + " , Habitant : " + this.habitant);
		return temp;
	}
}
