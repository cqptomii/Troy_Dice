package feuille;
import java.util.ArrayList;

public class Lien {
	private ArrayList<Batiment> batiments;
	
	private int[] recompense;
	private int habitants;
	private int colorH;
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
	
	public boolean lienEtablis() {
		boolean etat = true;
		for(Batiment b : this.batiments) {
			if(b.getEtat() != 1) {
				etat = false;
			}
		}
		return etat;
	}
	public int[] getRecompense() {
		if(this.lienEtablis())
			return this.recompense;
		
		return null;
	}
	public int getHabitant() {
		if(this.lienEtablis())
			return this.habitants;
		return 0;
	}
	public int getColorHabitant() {
		return this.colorH;
	}
	
}
