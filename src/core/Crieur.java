package core;
import plateau.De;
import java.util.Random;

public class Crieur extends Joueur {

	public Crieur(String name, int gpa) {
		super(name, gpa);
	}
	public Crieur(Joueur j) {
		super(j.name,j.gpa);
	}
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
	public int lancerDe() {
		Random randomEngine = new Random();
    	int value = randomEngine.nextInt(1,7);
    	return value;
	}
	
	public int[] choisirIndexGrille(int valeur, int mode) {
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
