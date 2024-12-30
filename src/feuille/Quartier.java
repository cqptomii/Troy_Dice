package feuille;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
	
	private int score;
	private IntegerProperty scorePrestige;
	private IntegerProperty scoreTravail;
	private IntegerProperty scoreRessource;
	
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

	protected void addSection(Section section) {
		if(section != null && this.sections.size() < 7) {
			if(this.sections.contains(section) == false) {
				this.sections.add(section);
			}
		}
	}
	public Section getSection(int index) {
		return this.sections.get(index);
	}
	protected ArrayList<Section> getSections(){
		return this.sections;
	}
	protected void addLien(Lien lien) {
		if(lien != null) {
			this.liens.add(lien);
		}
	}
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
	public int getScore() {
		return this.score;
	}
	public IntegerProperty scorePrestigeProperty() {
		return this.scorePrestige;
	}
	public IntegerProperty scoreTravailProperty() {
		return this.scoreTravail;
	}
	public IntegerProperty scoreRessourceProperty() {
		return this.scoreRessource;
	}
	
	protected void addMultPrestige() {
		this.multPrestige = true;
	}
	protected void addMultTravail() {
		this.multTravail = true;
	}
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
