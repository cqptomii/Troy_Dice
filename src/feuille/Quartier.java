package feuille;
import java.util.ArrayList;
public class Quartier {
	/*
	 * 1 : orange
	 * 2 : bleu
	 * 3 : gris
	*/
	private int couleur;
	private int score;
	private ArrayList<Section> sections;
	private ArrayList<Lien> liens;
	
	private boolean multPrestige;
	private boolean multTravail;
	
	public Quartier(int couleur) {
		this.score = 0;
		if(couleur > 0 && couleur < 4) {
			this.couleur = couleur;
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
	protected Section getSection(int index) {
		return this.sections.get(index);
	}
	protected ArrayList<Section> getSections(){
		return this.sections;
	}
	protected boolean initLien(Batiment bat1,Batiment bat2) {
		if(bat1 != null) {
			if(bat2.equals(bat1) == false) {
				// Créer le lien
				
				return true;
			}
		}
		return false;
	}
	public int getScore() {
		return this.score;
	}
	protected void addMultPrestige() {
		this.multPrestige = true;
	}
	protected void addMultTravail() {
		this.multTravail = true;
	}
}
