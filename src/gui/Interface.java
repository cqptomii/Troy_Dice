package gui;

import javafx.application.Application;
import javafx.stage.Stage;
import core.Crieur;

/**
 * Cette classe modélise l'interface du jeu
 * 
 * @version 1.0
 *
 * @see Interface
 * @author Tom FRAISSE
 */
public class Interface extends Application{
	private Stage primaryStage;
	private Crieur nomCrieur;
	
	/** 
	 * Cette méthode permet d'initialiser l'interface graphique
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#init 
	 * @author Tom FRAISSE
	 */
	public void init() throws Exception{
		super.init();
	
	}
	
	/** 
	 * Cette méthode permet de detruire l'interface graphique
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#stop 
	 * @author Tom FRAISSE
	 */
	public void stop() throws Exception{
		System.out.println("stop");
	}
	
	
	/** 
	 * Cette méthode permet d'initialiser l'interface graphique et de lancer la page du Menu
	 *  
	 * @param		primaryStage : Stage courant
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#start 
	 * @author Tom FRAISSE
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Troy Dice");
		primaryStage.setResizable(true);
		
		showMenuScene();
	}
	
	/** 
	 * Cette méthode permet d'afficher la scene du Menu
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#showMenuScene 
	 * @author Tom FRAISSE
	 */
	private void showMenuScene() throws Exception{
		MenuController menuController = new MenuController(this,primaryStage);
		primaryStage.setScene(menuController.getScene());
		primaryStage.setTitle("UTBM Dice - Menu");
		primaryStage.show();
		primaryStage.centerOnScreen();
	}
	
	/** 
	 * Cette méthode permet d'afficher la scene de choix de joueur
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#showNombreJoueursScene 
	 * @author Tom FRAISSE
	 */
	protected void showNombreJoueursScene() throws Exception{
		NombreJoueursController jController = new  NombreJoueursController(this,primaryStage);
		primaryStage.setScene(jController.getScene());
		primaryStage.setTitle("UTBM Dice - Joueurs");
		primaryStage.show();
	}
	
	/** 
	 * Cette méthode permet d'afficher la scene de saisi des joueurs de la partie
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#showSaisieJoueurScene 
	 * @author Tom FRAISSE
	 */
	protected void showSaisieJoueurScene() throws Exception{
		SaisiJoueurController sController = new SaisiJoueurController(this,primaryStage);
		primaryStage.setScene(sController.getScene());
		primaryStage.setTitle("UTBM Dice - créer joueur");
		primaryStage.show();
	}
	
	/** 
	 * Cette méthode permet d'afficher la scene du choix de mode de jeu
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#showModeJeuScene 
	 * @author Tom FRAISSE
	 */
	protected void showModeJeuScene(Crieur crieur) throws Exception{
		this.nomCrieur = crieur;
		ModeJeuController mController = new ModeJeuController(this,primaryStage,crieur);
		primaryStage.setScene(mController.getScene());
		primaryStage.show();
	}	
	
	/** 
	 * Cette méthode permet d'afficher la scene de jeu
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#showGameScene 
	 * @author Tom FRAISSE
	 */
	protected void showGameScene() throws Exception{
		
		GameController gController = new GameController(this,primaryStage,this.nomCrieur);
		primaryStage.setScene(gController.getScene());
		primaryStage.setTitle("UTBM Dice - Game");
		primaryStage.show();
	}
	
	/** 
	 * Cette méthode permet d'afficher la scene de fin de jeu
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#showEndScene 
	 * @author Tom FRAISSE
	 */
	protected void showEndScene() throws Exception{
		EndController eController = new EndController(this.primaryStage);
		primaryStage.setScene(eController.getScene());
		primaryStage.setTitle("UTBM Dice - End");
		primaryStage.show();
	}
	
	/** 
	 * Cette méthode permettant d'appeller l'initialisation de l'interface graphique
	 *  
	 * @exception Exception
	 * 
	 * @see Interface#run 
	 * @author Tom FRAISSE
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
