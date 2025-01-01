package core;
import plateau.De;
import java.util.Random;

/**
 * Cette classe modélise le crieur
 * 
 * @version 1.0
 *
 * @see De
 * @author Tom FRAISSE
 */
public class Crieur extends Joueur {
	
	/** 
	 * Cette méthode permet d'instancier un crieur dans le jeu.
	 *  
	 * @param        name : Nom du crieur 
	 * @param       gpa : Monant du gpa
	 * 
	 * @see Crieur#Crieur 
	 * @author Tom FRAISSE
	 */
	public Crieur(String name, int gpa) {
		super(name, gpa);
	}
	
	/** 
	 * Cette méthode permet d'instancier un crieur à partir d'un joueur
	 *  
	 * @param        j : Joueur à partir duquel le crieur est crée
	 * 
	 * @see Crieur#Crieur 
	 * @author Tom FRAISSE
	 */
	public Crieur(Joueur j) {
		super(j.name,j.gpa);
	}
	
	/** 
	 * Cette méthode permet de lancer les dés
	 *  
	 * @return Tableau contenant les dés
	 * 
	 * @see Crieur#lancerDes 
	 * @author Tom FRAISSE
	 */
	public De [] lancerDes() {
		Random randomEngine = new Random();
		De [] list = new De[4];
		int randomNumber = 0;
		
		for(int i=0; i < 4;++i) {
			randomNumber = randomEngine.nextInt(1, 7);
			if(i == 0) {
				list[i] = new De(randomNumber,-1);
			}else {
				list[i] = new De(randomNumber,1);
			}
		}
		
		return list;
	}
	
	/** 
	 * Cette méthode permet de lancer un dé.
	 *  
	 * @return : valeur du dé
	 * 
	 * @see Crieur#lancerDe 
	 * @author Tom FRAISSE
	 */
	public int lancerDe() {
		Random randomEngine = new Random();
    	int value = randomEngine.nextInt(1,7);
    	return value;
	}
	
	/** 
	 * Cette méthode permet d'instancier un crieur dans le jeu.
	 *  
	 * @return Tableau contenant les index des sections
	 * @param        valeur : valeur du dé pour l'ordre décroissanr et croissant 
	 * @param       mode : mode de création de l'index des sections
	 * 
	 * @exception IndexOutOfBoundsException : index hors limite
	 *
	 * @see Crieur#choisirIndexGrille 
	 * @author Tom FRAISSE
	 */
	public int[] choisirIndexGrille(int valeur, int mode)  throws IndexOutOfBoundsException{
	    int[] indexes = new int[6]; // Tableau pour les valeurs 1 à 6.
	    int currentIndex = 0;
	    try{
		    if (mode == 0) { // Croissant
		        for (int i = valeur; i <= 6; i++) {
		            indexes[currentIndex++] = i;
		        }
		        for (int i = 1; i < valeur; i++) {
		            indexes[currentIndex++] = i;
		        }
		    } else if(mode == 1){ // Décroissant
		        for (int i = valeur; i >= 1; i--) {
		            indexes[currentIndex++] = i;
		        }
		        for (int i = 6; i > valeur; i--) {
		            indexes[currentIndex++] = i;
		        }
		    }else if(mode == 2) { // Ordonée
	        	for(int i = 1;i <=6;i++) {
	        		indexes[i-1] = i;
	        	}
	        }else if(mode < 0) { //Aléatoire
		    	for (int i = 0; i < 6; i++) {
		            indexes[i] = i + 1;
		        }
		        shuffleArray(indexes);
		    }
		}catch (IndexOutOfBoundsException e) {
	        System.err.println("Erreur : Index hors limites lors du placement des dés : " + e.getMessage());
	    }
	    return indexes;
	}
	
	/** 
	 * Cette méthode permet de mélanger le tableau d'index avec la bibliothèque Random.
	 *  
	 * @return : tableau mélangé
	 * @param       array : tableay à mélanger
	 * 
	 * @see Crieur#shuffleArray 
	 * @author Tom FRAISSE
	 */
	private void shuffleArray(int[] array) {
	    Random rand = new Random();
	    for (int i = array.length - 1; i > 0; i--) {
	        int j = rand.nextInt(i + 1);
	        int temp = array[i];
	        array[i] = array[j];
	        array[j] = temp;
	    }
	}
}
