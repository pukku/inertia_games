package stalbans.inertiagames;

public abstract class GameDisplayCard extends javax.swing.JPanel {
	public abstract String getTitle() ;
	public abstract void setupCard(java.awt.Container rootPane, String menuName) ;
	public abstract void activateCard() ;
}
