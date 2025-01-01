package feuille;
import java.util.ArrayList;
import core.Joueur;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * Cette classe modélise les feuilles de jeu
 * 
 * @version 1.0
 *
 * @see FeuilleDeJeu
 * @author Tom FRAISSE
 */
public class FeuilleDeJeu {
	
	private ArrayList<Quartier> quartiers;
	private Joueur owner;
	private IntegerProperty multOwned;
	
	private IntegerProperty scoreHabitant;
	private IntegerProperty scoreFeuille;
	
	/** 
	 * Cette méthode permet d'instancier une feuille de jeu
	 *  
	 * @param 		indexSections : Tableau contenant l'ordre des index des sections
	 * @param 		j : Joueur associé à la feuille de jeu
	 *	
	 * @see FeuilleDeJeu#FeuilleDeJeu 
	 * @author   Tom FRAISSE 
	 */
	public FeuilleDeJeu(int [] indexSections,Joueur j) {
		this.scoreHabitant = new SimpleIntegerProperty(0);
		this.scoreFeuille = new SimpleIntegerProperty(0);
		this.owner = j;
		this.quartiers = new ArrayList<Quartier>();
		this.multOwned = new SimpleIntegerProperty(0);
		initQuartiers();
	}
	
	/** 
	 * Cette méthode permet de creer les différents quartiers de la feuille de jeu
	 *	
	 * @see FeuilleDeJeu#initQuartiers 
	 * @author   Tom FRAISSE 
	 */
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
            	if(colorB ==0) {
            		amountHab = 1;
            		if(sec == 1 || sec == 2) {
            			colorH = 0;
            		}else if(sec ==3 || sec == 4) {
            			colorH = 1;
            		}else {
            			colorH = 2;
            		}
            	}   
            	else if(colorB == 1) {
            		switch(sec) {
            		case 1:
            			amountHab = 0;
            			
            			ressource[0] = 3;
            			ressource[1] = 0;
            			ressource[2] = 0;
            			colorDe = 0;
            			break;
            		case 2:
            			amountHab = 2;
            			colorH = 0;
            			colorDe = 0;
            			break;
            		case 3:
            			amountHab = 0;
            			
            			ressource[0] = 0;
            			ressource[1] = 3;
            			ressource[2] = 0;
            			colorDe = 1;
            			break;
            		case 4:
            			amountHab = 2;
            			colorH = 1;
            			colorDe = 1;
            			break;
            		case 5:
            			amountHab = 0;
            			
            			ressource[0] = 0;
            			ressource[1] = 0;
            			ressource[2] = 3;
            			colorDe = 2;
            			break;
            		case 6:
            			amountHab = 2;
            			colorH = 2;
            			colorDe = 2;
            			break;
            		}
            	}else {
            		switch(sec) {
            		case 1:
            			multType = true;
            			indexMult = 0;
            			break;
            		case 2:
            			multType = false;
            			indexMult = 0;
            			break;
            		case 3:
            			multType = true;
            			indexMult = 1;
            			break;
            		case 4:
            			multType = false;
            			indexMult = 1;
            			break;
            		case 5:
            			multType = true;
            			indexMult = 2;
            			break;
            		case 6:
            			multType = false;
            			indexMult = 2;
            			break;
            		}
            	}
            	
            	Prestige prestige = new Prestige(this, "Prestige " + sec, amountHab,colorH,bonusId,ressource,colorDe,multType,indexMult,colorB);
            	Travail travail = new Travail(this, "Travail " + sec, 2,colorH,colorB);

                // Créer la section
                Section section = new Section(prestige, travail);
                section.setIndex(sec-1);
                	
                prestige.setCurrSection(section);
                travail.setCurrSection(section);
                
                // Ajouter la section au quartier
                quartier.addSection(section);
            }
            
            //création des liens entre les batiments d'un quartier
            if(colorB == 0) {
            	setLienBonusInModel(1,0,0,0,0,quartier,quartier.getSection(0).getBatiment(0),quartier.getSection(1).getBatiment(0));
	            setLienBonusInModel(2,0,0,0,0,quartier,quartier.getSection(0).getBatiment(1),quartier.getSection(1).getBatiment(1));
	            setLienBonusInModel(1,1,0,0,0,quartier,quartier.getSection(2).getBatiment(0),quartier.getSection(3).getBatiment(0));
	            setLienBonusInModel(1,2,0,0,0,quartier,quartier.getSection(4).getBatiment(0),quartier.getSection(5).getBatiment(0));
	            setLienBonusInModel(0,2,0,0,3,quartier,quartier.getSection(4).getBatiment(1),quartier.getSection(5).getBatiment(1));    
            }else if (colorB == 1) {
            	setLienBonusInModel(0,0,3,0,0,quartier,quartier.getSection(0).getBatiment(1),quartier.getSection(1).getBatiment(1));            
	            setLienBonusInModel(2,1,0,0,0,quartier,quartier.getSection(2).getBatiment(1),quartier.getSection(3).getBatiment(1));            
            }else if(colorB == 2){
            	setLienBonusInModel(0,0,0,3,0,quartier,quartier.getSection(2).getBatiment(1),quartier.getSection(3).getBatiment(1));
	            setLienBonusInModel(2,2,0,0,0,quartier,quartier.getSection(4).getBatiment(1),quartier.getSection(5).getBatiment(1));
            }
            this.quartiers.add(quartier);
            
		}
	}
	
	/** 
	 * Cette méthode permet de mettre en place un lien dans la feuille de jeu
	 *  
	 * @param       rHab : nombre d'habitants en récompense
	 * @param       colorH : couleur des habitants obtenus
	 * @param       rCred :  nombre de crédit en récompense
	 * @param       rCon : nombre de point de connaissance en récompense
	 * @param       rExp : nombre de point d'experience en récompense
	 * @param 		q : Quartier associé au lien
	 * @param       batL : Premier batiment lié au lien
	 * @param 		batR : Deuxième batiment lié au lien
	 *	
	 * @see FeuilleDeJeu#setLienBonusInModel 
	 * @author   Tom FRAISSE 
	 */
	private void setLienBonusInModel(int rHab,int colorH,int rCred,int rCon,int rExp,Quartier q,Batiment bL,Batiment bR) {
		Lien lien = new Lien(rHab,colorH,rCred,rCon,rExp,bL,bR);
		bL.setBonusLien(lien);
		bR.setBonusLien(lien);
        q.addLien(lien);
	}
	
	/** 
	 * Cette méthode permet de proteger une section de chacun des quartiers
	 *	
	 * @param 		index : section à proteger
	 *
	 * @see FeuilleDeJeu#protegerSection 
	 * @author   Tom FRAISSE 
	 */
	public void protegerSection(int index) {
        for (Quartier q : this.quartiers) {
           Section s = q.getSection(index);
           if (s.getIndex() == index) {
                s.protegerSection();
           }
        }
	}
	
	/** 
	 * Cette méthode permet de détruire une section précise d'un quartier
	 * 
	 * @param 		color : couleur du quartier ciblé
	 * @param 		index : section ciblé
	 *
	 * @see FeuilleDeJeu#detruireSection 
	 * @author   Tom FRAISSE 
	 */
	public void detruireSection(int color, int index) {
		// Rechercher la section et la détruire
		Quartier q = this.quartiers.get(color);
		Section s = q.getSection(index);
		s.detruireSection();
	}
	
	/** 
	 * Cette méthode permet de verifier si une section précise est protegé
	 * 
	 * @return True : section protegé / False : Sinon
	 * @param 		color : couleur du quartier ciblé
	 * @param 		index : section ciblé
	 *
	 * @see FeuilleDeJeu#isSectionProteger 
	 * @author   Tom FRAISSE 
	 */
	public boolean isSectionProteger(int color,int index) {
		Quartier q = this.quartiers.get(color);
		return q.getSection(index).getProteger();
	}
	
	/** 
	 * Cette méthode permet de calculer le score de la feuille de jeu
	 * 
	 * @return score de la feuille de jeu
	 *
	 * @see FeuilleDeJeu#computeScore 
	 * @author   Tom FRAISSE 
	 */
	public int computeScore() {
		if(quartiers.size() == 0) {
			return 0;
		}
		scoreFeuille.set(0);
		for(Quartier q : this.quartiers) {
			q.computeScore();
			scoreFeuille.set(scoreFeuille.get() + q.getScore());
		}
		
		scoreHabitant.set(0);
		for(int i = 0; i < 3; ++i) {
			scoreHabitant.set(scoreHabitant.get() + this.owner.getHabitant(i));
		}
		
		return scoreFeuille.get() + scoreHabitant.get();
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty lié au score de la feuille
	 * 
	 * @return Instance de la propriété observable lié au score de la feuille
	 *
	 * @see FeuilleDeJeu#scoreFeuilleProperty 
	 * @author   Tom FRAISSE 
	 */
	public IntegerProperty scoreFeuilleProperty(){
		return this.scoreFeuille;
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty lié au score des habitants
	 * 
	 * @return Instance de la propriété observable lié au score des habitants
	 *
	 * @see FeuilleDeJeu#scoreHabitantProperty 
	 * @author   Tom FRAISSE 
	 */
	public IntegerProperty scoreHabitantProperty() {
		return this.scoreHabitant;
	}
	
	/** 
	 * Cette méthode permet d'otenir le montant du multiplicateur appliqué au différents batiments
	 * 
	 * @return multiplicateur appliqué
	 *
	 * @see FeuilleDeJeu#getMultOwned 
	 * @author   Tom FRAISSE 
	 */
	public int getMultOwned() {
		if(this.multOwned.get() == 0) {
			return 0;
		}
		else if(this.multOwned.get() <= 2) {
			return 1;
		}else if(this.multOwned.get() >2 && this.multOwned.get() <= 4) {
			return 2;
		}else {
			return 3;
		}
	}
	
	/** 
	 * Cette méthode permet d'ajouter un multiplicateur au nombre de multiplicateur obtenu
	 * 
	 * @see FeuilleDeJeu#addMult 
	 * @author   Tom FRAISSE 
	 */
	protected void addMult() {
		this.multOwned.set(multOwned.get()+1);
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance IntegerProperty lié au nombre de multiplicateur
	 * 
	 * @return Instance de la propriété observable lié au nombre de multiplicateur
	 *
	 * @see FeuilleDeJeu#multOwnedProperty 
	 * @author   Tom FRAISSE 
	 */
	public IntegerProperty multOwnedProperty() {
		return this.multOwned;
	}
	
	/** 
	 * Cette méthode permet d'obtenir un des quartier de la feuille
	 *
	 * @return Instance du quartier de la couleur ciblé
	 * @param 		color : couleur du quartier ciblé
	 *
	 * @see FeuilleDeJeu#getQuartier 
	 * @author   Tom FRAISSE 
	 */
	public Quartier getQuartier(int color) {
		return this.quartiers.get(color);
	}
	
	/** 
	 * Cette méthode permet d'obtenir l'instance du joueur associé à la feuille de jeu
	 * 
	 * @return Instance du joueur
	 *
	 * @see FeuilleDeJeu#getJoueur 
	 * @author   Tom FRAISSE 
	 */
	public Joueur getJoueur() {
		return this.owner;
	}
	
}
