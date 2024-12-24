package feuille;

public class Travail extends Batiment{


	public Travail(FeuilleDeJeu parent,String name, int nombreHabitants,int colorH,int colorB) {
		super(parent,name, nombreHabitants,colorH,colorB);
	}

	@Override
	public void construire() {
		//construire le batiment
		this.etat = 1;
		
		//verifier l'état des eventuels liens
		if(this.bonusLien != null) {
			if(this.bonusLien.lienEtablis()) {
				int[] value = this.bonusLien.getRecompense();
						
				//Mettre à jour les ressources du joueurs
						
			}
		}
	}
}
