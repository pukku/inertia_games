package InertiaGames;

import java.awt.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.util.*;
import stalbans.inertiagames.*;

public class InertiaGames extends JApplet implements ActionListener {

	JPanel menuCard;
	Hashtable panelHash;
	
	ImageIcon displayIcon;
	
	public static final String MENUNAME = "Menu";
	
	private JPanel getListing(String game) {
		JPanel p = new JPanel();
		p.setLayout(null);
		
		JLabel l = new JLabel(game, displayIcon, SwingConstants.LEFT);
		l.setSize(new java.awt.Dimension(200, 30));
		l.setLocation(new java.awt.Point(0, 0));
		p.add(l);
		
		JButton b = new JButton("Go");
		b.setActionCommand(game);
		b.addActionListener(this);
		b.setSize(b.getPreferredSize());
		Dimension bs = b.getSize();
		int b_y = ((30 - bs.height)/2);
		b.setLocation(new java.awt.Point(200, b_y));
		p.add(b);

		p.setSize(new java.awt.Dimension((200 + bs.width), 30));
		
		return p;
	}
	
	private void addCard(GameDisplayCard card, Container pane) {
		card.setupCard(pane, MENUNAME);
		panelHash.put(card.getTitle(), card);
		pane.add(card, card.getTitle());
	}
	
	public void actionPerformed(ActionEvent evt) {
		String whichCard = (String)((JButton)evt.getSource()).getActionCommand();
		CardLayout cl = (CardLayout)(getContentPane().getLayout());
		cl.show(getContentPane(), whichCard);
		Object whichDisplay = panelHash.get(whichCard);
		if (whichDisplay != null) {
			((GameDisplayCard)whichDisplay).activateCard();
		}
	}

    public void init() {
        // set the default look and feel
        String laf = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(laf);
        } catch (UnsupportedLookAndFeelException exc) {
            System.err.println ("Warning: UnsupportedLookAndFeel: " + laf);
        } catch (Exception exc) {
            System.err.println ("Error loading " + laf + ": " + exc);
        }
		
		getContentPane().setSize(new java.awt.Dimension(550, 495));
		getContentPane().setLayout(new CardLayout());
		
		panelHash = new Hashtable();
		displayIcon = new ImageIcon(this.getClass().getClassLoader().getResource("images/heading2.gif"));
		
		// create the about panel
		GameDisplayCard aboutCard = new AboutCard();
		addCard(aboutCard, getContentPane());
		
		// Add the various games
		GameDisplayCard raceGame = new RaceGameEmbed();
		addCard(raceGame, getContentPane());
		
		GameDisplayCard dock1Game = new Dock1GameEmbed();
		addCard(dock1Game, getContentPane());
		
		GameDisplayCard dock2Game = new Dock2GameEmbed();
		addCard(dock2Game, getContentPane());
		
		GameDisplayCard corner1Game = new Corner1GameEmbed();
		addCard(corner1Game, getContentPane());
		
		GameDisplayCard corner2Game = new Corner2GameEmbed();
		addCard(corner2Game, getContentPane());
		
		GameDisplayCard corner3Game = new Corner3GameEmbed();
		addCard(corner3Game, getContentPane());
		
		GameDisplayCard corner4Game = new Corner4GameEmbed();
		addCard(corner4Game, getContentPane());
		
		GameDisplayCard dynatrackGame = new DynatrackGameEmbed();
		addCard(dynatrackGame, getContentPane());
		
		// create the menu card
		menuCard = new JPanel();
		menuCard.setLayout(null);
		JLabel title = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("images/title.gif")));
		title.setSize(title.getPreferredSize());
		title.setLocation(new java.awt.Point(20, 10));
		menuCard.add(title);
		
		JPanel aboutp = getListing(aboutCard.getTitle());
		aboutp.setLocation(new java.awt.Point(50, 100));
		menuCard.add(aboutp);
		JPanel racep = getListing(raceGame.getTitle());
		racep.setLocation(new java.awt.Point(50, 130));
		menuCard.add(racep);
		JPanel dock1p = getListing(dock1Game.getTitle());
		dock1p.setLocation(new java.awt.Point(50, 160));
		menuCard.add(dock1p);
		JPanel dock2p = getListing(dock2Game.getTitle());
		dock2p.setLocation(new java.awt.Point(50, 190));
		menuCard.add(dock2p);
		JPanel corner1p = getListing(corner1Game.getTitle());
		corner1p.setLocation(new java.awt.Point(50, 220));
		menuCard.add(corner1p);
		JPanel corner2p = getListing(corner2Game.getTitle());
		corner2p.setLocation(new java.awt.Point(50, 250));
		menuCard.add(corner2p);
		JPanel corner3p = getListing(corner3Game.getTitle());
		corner3p.setLocation(new java.awt.Point(50, 280));
		menuCard.add(corner3p);
		JPanel corner4p = getListing(corner4Game.getTitle());
		corner4p.setLocation(new java.awt.Point(50, 310));
		menuCard.add(corner4p);
		JPanel dynatrackp = getListing(dynatrackGame.getTitle());
		dynatrackp.setLocation(new java.awt.Point(50, 340));
		menuCard.add(dynatrackp);
		
		getContentPane().add(menuCard, MENUNAME);
		
		((CardLayout)getContentPane().getLayout()).show(getContentPane(), aboutCard.getTitle());
	}
	
}
