package stalbans.inertiagames;import java.awt.*;import java.awt.event.*;import javax.swing.*;import java.lang.Math;public class Corner4GameEmbed extends SquareGameBoard {	JButton startButton;		boolean kicked;		public String getTitle() {		return "Corner 4";	}	public String getInstructionsLoc() {		return "text/corner4.html";	}	public RocketPanel newGamePanel() throws Exception {		return new CornerPanel(this);	}		public Rocket newStartingRocket() {		return new Rocket(20, 20, 2, 1, 5, 0, (5 * Math.sqrt(2)));	}		public void initGamePanelHook() {		((CornerPanel)gamePanel).setCorner(360, 210);		kicked = false;	}		public void setupCardHook() {				// we have a different kick button, so we invalidate the previous one		remove(kickButton);		kickButton = null;			kickButton = new JButton();		kickButton.setText("Kick");		kickButton.setDefaultCapable(false);		kickButton.setSize(new java.awt.Dimension(100, 20));		kickButton.setLocation(new java.awt.Point(430, 90));		kickButton.addActionListener(new ActionListener() {			public void actionPerformed(ActionEvent e) {				if (! kicked) {					gamePanel.getRocket().kick();					kicked = true;					kickButton.setEnabled(false);				}			}		});				add(kickButton);				startButton = new JButton();		startButton.setText("Start");		startButton.setDefaultCapable(false);		startButton.setSize(new java.awt.Dimension(100, 20));		startButton.setLocation(new java.awt.Point(430, 250));		startButton.setVisible(true);		startButton.setEnabled(true);		startButton.addActionListener(new ActionListener() {			public void actionPerformed(ActionEvent e) {				startButton.setVisible(false);				startButton.setEnabled(false);								kickButton.setEnabled(true);				rotateButton.setEnabled(true);				trackingButton.setEnabled(true);								gamePanel.start();			}		});				add(startButton);	}		public void replayHook() {		kickButton.setEnabled(false);		rotateButton.setEnabled(false);		trackingButton.setEnabled(false);				startButton.setEnabled(true);		startButton.setVisible(true);		repaint();	}		public boolean autoStartHook() {		return false;	}}