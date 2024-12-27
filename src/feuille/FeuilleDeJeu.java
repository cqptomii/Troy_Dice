package feuille;
import java.util.ArrayList;
import core.Joueur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
public class FeuilleDeJeu {
	
	private ArrayList<Quartier> quartiers;
	private int[] indexSections;
	private Joueur owner;
	private int multOwned;
	
	private IntegerProperty scoreHabitant;
	private IntegerProperty scoreFeuille;
	
	public FeuilleDeJeu(int [] indexSections,Joueur j) {
		this.scoreHabitant = new SimpleIntegerProperty(0);
		this.scoreFeuille = new SimpleIntegerProperty(0);
		this.indexSections = indexSections;
		this.owner = j;
		this.quartiers = new ArrayList<Quartier>();
		this.multOwned = 0;
		initQuartiers();
	}
	private void initQuartiers() {
		for (int colorB = 0; colorB < 3; colorB++) {
			
            Quartier quartier = new Quartier(colorB,this); // 0= orange , 1 = bleu, 2 = gris
            for (int sec = 1; sec < 7; sec++) {
                int bonusId = colorB;
                int colorH = colorB;
                int amountHab = 0;
                boolean multType = false;
            	int indexMult = 1;
            	int[] ressource = new int[3];
            	int colorDe = 0;
            	ressource[0] = 0;
            	ressource[1] = 0;
            	ressource[2] = 0;
            	
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
            			
            			ressource[0] = 0;
            			ressource[1] = 0;
            			ressource[2] = 3;
            			break;
            		case 2:
            			amountHab = 2;
            			break;
            		case 3:
            			amountHab = 0;
            			
            			ressource[0] = 3;
            			ressource[1] = 0;
            			ressource[2] = 0;
            			colorDe = 1;
            			break;
            		case 4:
            			amountHab = 2;
            			colorDe = 1;
            			break;
            		case 5:
            			amountHab = 0;
            			
            			ressource[0] = 0;
            			ressource[1] = 3;
            			ressource[2] = 0;
            			colorDe = 2;
            			break;
            		case 6:
            			amountHab = 2;
            			colorDe = 2;
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
            	
            	Prestige prestige = new Prestige(this, "Prestige " + sec, amountHab,colorH,bonusId,ressource,colorDe,multType,indexMult,colorB);
                Travail travail = new Travail(this, "Travail " + sec, 2,colorH,colorB);

                // Créer la section
                Section section = new Section(prestige, travail);
                section.setIndex(sec);

                // Ajouter la section au quartier
                quartier.addSection(section);
            }
            
            //création des liens entre les batiments d'un quartier
            if(colorB == 1) {
	            Lien l1 = new Lien(1,1,0,0,0,quartier.getSection(0).getBatiment(0),quartier.getSection(1).getBatiment(0));
	            quartier.addLien(l1);
	            
	            Lien l2 = new Lien(2,1,0,0,0,quartier.getSection(0).getBatiment(1),quartier.getSection(1).getBatiment(1));
	            quartier.addLien(l2);
	            
	            Lien l3 = new Lien(1,2,0,0,0,quartier.getSection(2).getBatiment(0),quartier.getSection(3).getBatiment(0));
	            quartier.addLien(l3);
            
            }else if (colorB == 2) {
            	Lien l1 = new Lien(0,0,3,0,0,quartier.getSection(0).getBatiment(1),quartier.getSection(1).getBatiment(1));
	            quartier.addLien(l1);
	            
	            Lien l2 = new Lien(2,2,0,0,0,quartier.getSection(2).getBatiment(1),quartier.getSection(3).getBatiment(1));
	            quartier.addLien(l2);
            
            }else {
            	Lien l1 = new Lien(0,0,0,3,0,quartier.getSection(2).getBatiment(1),quartier.getSection(3).getBatiment(1));
	            quartier.addLien(l1);
	            
	            Lien l2 = new Lien(2,3,0,0,0,quartier.getSection(4).getBatiment(1),quartier.getSection(5).getBatiment(1));
	            quartier.addLien(l2);
            }
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
	
	public int computeScore() {
		if(quartiers.size() == 0) {
			return 0;
		}
		scoreFeuille.set(0);
		for(Quartier q : this.quartiers) {
			scoreFeuille.set(scoreFeuille.get() + q.getScore());
		}
		
		scoreHabitant.set(0);
		for(int i = 0; i < 3; ++i) {
			scoreHabitant.set(scoreHabitant.get() + this.owner.getHabitant(i));
		}
		
		return scoreFeuille.get() + scoreHabitant.get();
	}
	
	public IntegerProperty scoreFeuilleProperty(){
		return this.scoreFeuille;
	}
	public IntegerProperty scoreHabitantProperty() {
		return this.scoreHabitant;
	}
	public int getMultOwned() {
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
	public Quartier getQuartier(int color) {
		return this.quartiers.get(color);
	}
	public Joueur getJoueur() {
		return this.owner;
	}
}
