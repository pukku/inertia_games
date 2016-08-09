package stalbans.inertiagames;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

public abstract class SquareGameBoard extends GameDisplayCard implements RocketPanelCreator {
	Container rootPane;
	String menuName;
	
	RocketPanel gamePanel;
	
	JLabel titleLabel;
	JLabel gameStatus;
	
	JButton kickButton;
	JButton rotateButton;
	JCheckBox trackingButton;
	
	JLabel instructionsPane;
	
	JButton replayButton;
	JButton menuButton;
	
	String gameTitle;
	String instructionsLoc;
	
	private String inputStreamAsString(InputStream in) {
		StringBuffer sb = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String in_line;
			while((in_line = br.readLine()) != null) {
				sb.append(in_line);
			}
			br.close();
		} catch (java.io.IOException ex) {
			sb.append("Could not read stream: " + ex.toString() );
		}
		return sb.toString();		
	}
	
	
	public abstract String getTitle();
	public abstract String getInstructionsLoc();
	
	// Initialize the pane
	public void setupCardHook() { }
	
	public void setupCard(Container r, String m) 
	{
		rootPane = r;
		menuName = m;
		
		titleLabel = new JLabel(getTitle());
		titleLabel.setFont(new java.awt.Font("Serif", 0, 24));
		titleLabel.setSize(new java.awt.Dimension(270, 30));
		titleLabel.setLocation(new java.awt.Point(20, 10));
		
		gameStatus = new JLabel();
		gameStatus.setFont(new java.awt.Font("SansSerif", 0, 12));
		gameStatus.setSize(new java.awt.Dimension(100, 20));
		gameStatus.setLocation(new java.awt.Point(430, 50));
		
		kickButton = new JButton();
		rotateButton = new JButton();
		trackingButton = new JCheckBox();
		
		kickButton.setText("Kick");
		kickButton.setDefaultCapable(false);
		kickButton.setSize(new java.awt.Dimension(100, 20));
		kickButton.setLocation(new java.awt.Point(430, 90));
		kickButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.getRocket().kick();
			}
		});
		
		rotateButton.setText("Rotate L");
		rotateButton.setDefaultCapable(false);
		rotateButton.setSize(new java.awt.Dimension(100, 20));
		rotateButton.setLocation(new java.awt.Point(430, 120));
		rotateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.getRocket().rotateL();
			}
		});
	
		trackingButton.setText("Track");
		trackingButton.setSize(new java.awt.Dimension(100, 20));
		trackingButton.setLocation(new java.awt.Point(430, 150));
		trackingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gamePanel.toggleTracking();
			}
		});
			
		instructionsPane = new JLabel();
		instructionsPane.setFont(new java.awt.Font("SansSerif", 0, 12));
		instructionsPane.setText(inputStreamAsString(this.getClass().getClassLoader().getResourceAsStream(getInstructionsLoc())));
		instructionsPane.setVerticalAlignment(SwingConstants.TOP);
		instructionsPane.setBackground(null);
		instructionsPane.setSize(new java.awt.Dimension(510, 180));
		instructionsPane.setLocation(new java.awt.Point(20, 305));
		instructionsPane.setVisible(true);
		
		
		replayButton = new JButton();
		menuButton = new JButton();
		
		replayButton.setText("Replay");
		replayButton.setDefaultCapable(false);
		replayButton.setSize(new java.awt.Dimension(100, 20));
		replayButton.setLocation(new java.awt.Point(430, 250));
		replayButton.setEnabled(false);
		replayButton.setVisible(false);
		replayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replayButton.setVisible(false);
				replayButton.setEnabled(false);
				menuButton.setVisible(false);
				menuButton.setEnabled(false);
				replayGame();
			}
		});
		
		menuButton.setText(menuName);
		menuButton.setDefaultCapable(false);
		menuButton.setSize(new java.awt.Dimension(100, 20));
		menuButton.setLocation(new java.awt.Point(430, 280));
		menuButton.setEnabled(false);
		menuButton.setVisible(false);
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				replayButton.setVisible(false);
				replayButton.setEnabled(false);
				menuButton.setVisible(false);
				menuButton.setEnabled(false);
				CardLayout cl = (CardLayout)rootPane.getLayout();
				cl.show(rootPane, menuName);
			}
		});
		
		setSize(new java.awt.Dimension(550, 495));
		setLayout(null);
		add(titleLabel);
		add(gameStatus);
		add(kickButton);
		add(rotateButton);
		add(trackingButton);
		add(instructionsPane);
		add(replayButton);
		add(menuButton);
		
		setupCardHook();
	}
	
	// actually creates a new game panel, so that we can replay easily
	public abstract RocketPanel newGamePanel() throws Exception;
	public abstract Rocket newStartingRocket();
	
	public void initGamePanelHook() { }
	
	public void initGamePanel() throws Exception {
		gamePanel = newGamePanel();
		gamePanel.setSize(new java.awt.Dimension(400, 250));
		gamePanel.setLocation(new java.awt.Point(20, 50));
		gamePanel.setBackground(java.awt.Color.white);
		gamePanel.setVisible(true);
		gamePanel.setLayout(null);
		gamePanel.setGameStatusLabel(gameStatus);
		gamePanel.setRocket(newStartingRocket());
		initGamePanelHook();
	}

	public void replayHook() { }

	public boolean autoStartHook() {
		return true;
	}

	// in order to start a new game, call this
	public void replayGame() {
		// first, remove gamePanel from this frame
		if (gamePanel != null) {
			remove(gamePanel);
		}
		
		// next, deallocate the panel
		gamePanel = null;
		
		//next, create a new one
		try {
			initGamePanel();
		} catch (Exception ignored) {}
		add(gamePanel);
		
		// reenable the other controls
		kickButton.setEnabled(true);
		rotateButton.setEnabled(true);
		trackingButton.setEnabled(true);
		trackingButton.setSelected(false);
		gameStatus.setText(null);
		
		replayHook();
		
		// now, start it going
		if (autoStartHook()) {
			gamePanel.start();
		}
	}
	
	public void gameCompletedHook() { }
	
	public void gameCompleted() {
		kickButton.setEnabled(false);
		rotateButton.setEnabled(false);
		trackingButton.setEnabled(false);

		gameCompletedHook();
		
		replayButton.setVisible(true);
		replayButton.setEnabled(true);
		
		menuButton.setVisible(true);
		menuButton.setEnabled(true);
		
	}
	
	// When we switch to this panel, we should activate it
	public void activateCard() 
	{
		replayGame();
	}
}
