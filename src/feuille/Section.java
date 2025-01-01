package feuille;
import java.util.ArrayList;
/**
 * Cette classe modélise une section de deux batiments
 * 
 * @version 1.0
 *
 * @see Section
 * @author Tom FRAISSE
 */
public class Section {
	
	private int index;
	private boolean protegé;
	private ArrayList<Batiment> batiments;
	
	/** 
	 * Cette méthode permet de construire une section
	 *  
	 * @param       batPrestige :  batiment de prestige de la section crée 
	 * @param       batTravail : batiment de travail de  la section crée
	 *	
	 * @see Section#Section 
	 * @author   Tom FRAISSE 
	 */
	public Section(Prestige batPrestige,Travail batTravail) {
		this.batiments = new ArrayList<Batiment>();
		if(batPrestige != null) {
			this.batiments.add(batPrestige);
		}
		if(batTravail != null) {
			this.batiments.add(batTravail);
		}
	}
	
	/** 
	 * Cette méthode permet d'invalider les batiments de la section
	 *	
	 * @see Section#detruireSection 
	 * @author   Tom FRAISSE 
	 */
	public void detruireSection() {
		if(this.protegé == false) {
			for(Batiment b : batiments) {
				b.invaliderBatiment();
			}
		}
	}
	
	/** 
	 * Cette méthode permet de proteger les batiments de la section
	 *	
	 * @see Section#protegerSection 
	 * @author   Tom FRAISSE 
	 */
	public void protegerSection() {
		this.protegé = true;
	}
	
	/** 
	 * Cette méthode permet de construire une section
	 *  
	 * @return état de la section
	 *	
	 * @see Section#getProteger 
	 * @author   Tom FRAISSE 
	 */
	public boolean getProteger() {
		return this.protegé;
	}
	
	/** 
	 * Cette méthode permet de modifier l'index de la section
	 *  
	 * @param       value : valeur de l'index
	 *	
	 * @see Section#setIndex 
	 * @author   Tom FRAISSE 
	 */
	protected void setIndex(int value) {
		this.index = value;
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'index de la section
	 *  
	 * @return index de la section
	 *	
	 * @see Section#getIndex
	 * @author   Tom FRAISSE 
	 */
	public int getIndex() {
		return this.index;
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'un des batiments de la section
	 * 
	 * @return	Batiment de la section
	 * @param       index : index du batiment
	 *	
	 * @see Section#getBatiment 
	 * @author   Tom FRAISSE 
	 */
	public Batiment getBatiment(int index) {
		return this.batiments.get(index);
	}
}
