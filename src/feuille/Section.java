package feuille;
import java.util.ArrayList;
public class Section {
	private int index;
	private boolean protegé;
	private ArrayList<Batiment> batiments;
	
	public Section(Prestige batPrestige,Travail batTravail) {
		this.batiments = new ArrayList<Batiment>();
		if(batPrestige != null) {
			this.batiments.add(batPrestige);
		}
		if(batTravail != null) {
			this.batiments.add(batTravail);
		}
	}
	public void detruireSection() {
		if(this.protegé == false) {
			for(Batiment b : batiments) {
				b.invaliderBatiment();
			}
		}
	}
	public void protegerSection() {
		this.protegé = true;
	}
	public boolean getProteger() {
		return this.protegé;
	}
	protected void setIndex(int value) {
		this.index = value;
	}
	public int getIndex() {
		return this.index;
	}
	protected Batiment getBatiment(int index) {
		return this.batiments.get(index);
	}
}
