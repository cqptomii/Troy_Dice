package feuille;
import java.util.ArrayList;

/**
 * Cette classe modélise un lien entre deux batiments
 * 
 * @version 1.0
 *
 * @see Lien
 * @author Tom FRAISSE
 */
public class Lien {
	private ArrayList<Batiment> batiments;
	
	private int[] recompense;
	private int habitants;
	private int colorH;
	
	
	/** 
	 * Cette méthode permet d'instancier un lien
	 *  
	 * @param       rHab : nombre d'habitants en récompense
	 * @param       colorH : couleur des habitants obtenus
	 * @param       rCred :  nombre de crédit en récompense
	 * @param       rCon : nombre de point de connaissance en récompense
	 * @param       rExp : nombre de point d'experience en récompense
	 * @param       batL : Premier batiment lié au lien
	 * @param 		batR : Deuxième batiment lié au lien
	 *	
	 * @see Lien#Lien 
	 * @author   Tom FRAISSE 
	 */
	public Lien(int rHab,int colorH,int rCred,int rCon,int rExp,Batiment batL,Batiment batR) {
		this.recompense = new int[3];
		if(rHab >= 0 && rHab < 3) {
			this.habitants = rHab;
			this.colorH = colorH;
		}
		if(rCred < 4 && rCred >= 0) {
			this.recompense[0] = rExp;
		}
		if(rCon < 4 && rCon >= 0) {
			this.recompense[1] = rCred;
		}
		if(rExp < 4 && rExp >= 0) {
			this.recompense[2] = rCon;
		}
		
		this.batiments = new ArrayList<Batiment>();
		this.batiments.add(batL);
		this.batiments.add(batR);
	}
	
	/** 
	 * Cette méthode permet de savoir si un lien est établi
	 *  
	 * @return  True : établi / False : non établi
	 *	
	 * @see Lien#lienEtabli 
	 * @author   Tom FRAISSE 
	 */
	public boolean lienEtabli() {
		boolean etat = true;
		for(Batiment b : this.batiments) {
			if(b.getEtat() != 1) {
				etat = false;
			}
		}
		return etat;
	}
	
	/** 
	 * Cette méthode permet d'obtenir les récompenses du lien
	 *  
	 * @return  Tableau contenant les récompense du lien
	 *	
	 * @see Lien#getRecompense 
	 * @author   Tom FRAISSE 
	 */
	public int[] getRecompense() {
		if(this.lienEtabli())
			return this.recompense;
		
		return null;
	}
	
	/** 
	 * Cette méthode permet d'obtenir le nombre d'habitant obtenu lors de la création du lien
	 *  
	 * @return  nombre d'habitant
	 *	
	 * @see Lien#getHabitant 
	 * @author   Tom FRAISSE 
	 */
	public int getHabitant() {
		if(this.lienEtabli())
			return this.habitants;
		return 0;
	}
	
	/** 
	 * Cette méthode permet d'obtenir la couleur des habitants du lien
	 *  
	 * @return  couleur des habitants
	 *	
	 * @see Lien#getColorHabitant
	 * @author   Tom FRAISSE 
	 */
	public int getColorHabitant() {
		return this.colorH;
	}
}
