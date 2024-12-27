package feuille;

public class Travail extends Batiment{


	public Travail(FeuilleDeJeu parent,String name, int nombreHabitants,int colorH,int colorB) {
		super(parent,name, nombreHabitants,colorH,colorB);
	}

	@Override
	public void construire(int nbDe) {
		//construire le batiment
		this.etat = 1;
	}
}
