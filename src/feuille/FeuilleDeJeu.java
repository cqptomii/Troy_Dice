package feuille;
import java.util.ArrayList;
import core.Joueur;
public class FeuilleDeJeu {
	
	private ArrayList<Quartier> quartiers;
	private int[] indexSections;
	private Joueur owner;
	private int multOwned;
	
	public FeuilleDeJeu(int [] indexSections,Joueur j) {
		this.indexSections = indexSections;
		this.owner = j;
		this.quartiers = new ArrayList<Quartier>();
		this.multOwned = 0;
		initQuartiers();
	}
	private void initQuartiers() {
		for (int colorB = 1; colorB < 4; colorB++) {
			
            Quartier quartier = new Quartier(colorB); // 1= orange , 2 = bleu, 3 = gris
            
            for (int sec = 1; sec < 7; sec++) {
                int bonusId = colorB;
                int colorH = colorB;
                int amountHab = 0;
                boolean multType = false;
            	int indexMult = 1;
            	int[] ressource = null;
                // Créer les bâtiments de prestige et de travail pour chaque section
            	if(colorB ==1) {
            		amountHab = 1;
            		if(sec == 1 || sec == 2) {
            			colorH = 1;
            		}else if(sec ==3 || sec == 4) {
            			colorH = 2;
            		}else {
            			colorH = 3;
            		}
            	}   
            	else if(colorB == 2) {
            		switch(sec) {
            		case 1:
            			amountHab = 0;
            			ressource = new int[3];
            			
            			ressource[0] = 0;
            			ressource[1] = 0;
            			ressource[2] = 3;
            			break;
            		case 2:
            			amountHab = 2;
            			break;
            		case 3:
            			amountHab = 0;
            			ressource = new int[3];
            			
            			ressource[0] = 3;
            			ressource[1] = 0;
            			ressource[2] = 0;
            			break;
            		case 4:
            			amountHab = 2;
            			break;
            		case 5:
            			amountHab = 0;
            			ressource = new int[3];
            			
            			ressource[0] = 0;
            			ressource[1] = 3;
            			ressource[2] = 0;
            			break;
            		case 6:
            			amountHab = 6;
            			break;
            		}
            	}else {
            		switch(sec) {
            		case 1:
            			multType = false;
            			indexMult = 1;
            			break;
            		case 2:
            			multType = true;
            			indexMult = 1;
            			break;
            		case 3:
            			multType = false;
            			indexMult = 2;
            			break;
            		case 4:
            			multType = true;
            			indexMult = 2;
            			break;
            		case 5:
            			multType = false;
            			indexMult = 3;
            			break;
            		case 6:
            			multType = true;
            			indexMult = 3;
            			break;
            		}
            	}
            	
            	Prestige prestige = new Prestige(this, "Prestige " + sec, amountHab,colorH,bonusId,ressource,multType,indexMult,colorB);
                Travail travail = new Travail(this, "Travail " + sec, 2,colorH,colorB);

                // Créer la section
                Section section = new Section(prestige, travail);
                section.setIndex(sec);

                // Ajouter la section au quartier
                quartier.addSection(section);
            }
            
            //création des liens entre les batiments d'un quartier
            this.quartiers.add(quartier);
        }

	}
	public void protegerSection(int index) {
        for (Quartier q : this.quartiers) {
           Section s = q.getSection(index);
           if (s.getIndex() == index) {
                s.protegerSection();
           }
        }
	}
	
	public void detruireSection(int color, int index) {
		// Rechercher la section et la détruire
		Quartier q = this.quartiers.get(color);
		Section s = q.getSection(index);
		s.detruireSection();
	}
	public int getBatimentConstruit(boolean prestige,int colorQuartier) {
		int amount = 0;
		Quartier q = this.quartiers.get(colorQuartier);
		int index = 0;
		
		if(prestige) {
			index = 0;
		}else {
			index = 1;
		}
		for(Section s : q.getSections()) {
			if(s.getBatiment(index).etat == 1) {
				amount++;
			}
		}
		return amount;
	}
	
	
	public int computeScore() {
		if(quartiers.size() == 0) {
			return 0;
		}
		int feuilleScore = 0;
		for(Quartier q : this.quartiers) {
			feuilleScore += q.getScore();
		}
		return feuilleScore;
	}
	
	protected int getMultOwned() {
		if(this.multOwned == 0) {
			return 0;
		}
		else if(this.multOwned <= 2) {
			return 1;
		}else if(this.multOwned >2 && this.multOwned <= 4) {
			return 2;
		}else {
			return 3;
		}
	}
	protected void addMult() {
		this.multOwned+=1;
	}
	protected Quartier getQuartier(int color) {
		return this.quartiers.get(color);
	}
}
