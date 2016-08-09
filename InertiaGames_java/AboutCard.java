package stalbans.inertiagames;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;

public class AboutCard extends GameDisplayCard {
	
	JLabel aboutLabel;
	JPanel menuButtonPane;
	JButton menuButton;
	
	Container rootPane;
	String menuName;
	
	public String getTitle() {
		return "About the Inertia Games";
	}
	
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
	
	public void setupCard(Container r, String m) {
		rootPane = r;
		menuName = m;

		aboutLabel = new JLabel();
		aboutLabel.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("images/title.gif")));
		aboutLabel.setText(inputStreamAsString(this.getClass().getClassLoader().getResourceAsStream("text/about.html")));
		aboutLabel.setFont(new java.awt.Font("Serif", 0, 12));
		aboutLabel.setIconTextGap(20);
		aboutLabel.setHorizontalAlignment(SwingConstants.CENTER);
		aboutLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		aboutLabel.setVerticalAlignment(SwingConstants.TOP);
		aboutLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		aboutLabel.setBackground(java.awt.Color.white);
		aboutLabel.setOpaque(true);
		aboutLabel.setSize(new java.awt.Dimension(510, 400));
		aboutLabel.setLocation(new java.awt.Point(20, 20));
		aboutLabel.setBorder(new CompoundBorder(new EtchedBorder(),
												new EmptyBorder(15, 10, 10, 10)));
		
		menuButton = new JButton(menuName);
		menuButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				CardLayout cl = (CardLayout)rootPane.getLayout();
				cl.show(rootPane, menuName);
			}
		});
		menuButton.setSize(menuButton.getPreferredSize());
		menuButtonPane = new JPanel();
		menuButtonPane.setSize(new java.awt.Dimension(510, 55));
		menuButtonPane.setLocation(new java.awt.Point(20, 420));
		menuButtonPane.setLayout(new FlowLayout());
		menuButtonPane.add(menuButton);
		
		setSize(new java.awt.Dimension(550, 495));
		setLayout(null);
		setBackground(null);
		add(aboutLabel);
		add(menuButtonPane);
	}
	
	public void activateCard() {
	}
	

}
