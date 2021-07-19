package team19.spiel2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import team19.highscore.HighscoreFenster;
import team19.main.gui_Startfenster;
import javax.swing.Timer;
import team19.main.gui_Startfenster;

/**
 * GUI_Spiel_2 is the JFrame of the game 2.
 * GUI_Spiel_2 creates two JPanels of the game - Spielfeld and the Vorauswahl_Spielfenster. 
 * GUI_Spiel_2 sets the size of the JPanels and changes the size of the plates of steins (squaresz) accordingly.
 *
 */
public class GUI_Spiel_2 extends JFrame{  	 
	private static final long serialVersionUID = 1L;	
	
	/**
	 * JPanels Spielfeld and Vorauswahl_Spielfenster are separated using the JSplitPane splitpane
	 *
	 */
	public Spielfeld SpielfeldlPanel;
	public Vorauswahl_Spielfenster VorauswahlPanel;
	private JSplitPane splitpane;
	
	/**
	 * JPanels gui_Startfenster is the main window for all games
	 *
	 */
	public final gui_Startfenster startfenster;
	
	/**
	 * Sizewindow - window size, by default = 1 (0 - small, 1 - middle, 2 - large).
	 */	
    public int Sizewindow;
    
	/**
	 * squaresz - size of the one square in pixels (10 for small, 12 for middle, and 15 for large).
	 */
	public int squaresz=12;
	/**
	 * The score of the game 2
	 */
    public int score;
	
	public GUI_Spiel_2() {
		this(null);
	}
	
	/**
	 * Creates the GUI_Spiel_2 from the gui_Startfenster
	 */
	public GUI_Spiel_2(gui_Startfenster sf) {

		startfenster = sf;
		setSize(new Dimension(58*squaresz+163,54*squaresz+2));	
		setLocationRelativeTo(null);
		setResizable(false);

		initSplitPane();
		initPanels();

		setVisible(true);
		SpielfeldlPanel.requestFocus();
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				setVisible(false);				
				score = SpielfeldlPanel.score;
				HighscoreFenster.updateScore("resources/spiel2.txt", score);
				startfenster.setVisible(true);
				dispose();
				
			}
		});
	} 
	
	/**
	 * Initializes splitpane and set the border stile
	 */
	private void initSplitPane() {
		splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitpane.setResizeWeight(1.0); 
		splitpane.setDividerSize(0);
		splitpane.setBorder(null);
		
		add(splitpane); 
		
	    splitpane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),
	    		BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder())));		
	}
	
	/**
	 * Initializes Spielfeld and Vorauswahl_Spielfenster
	 */
	private void initPanels() {
	
		SpielfeldlPanel = new Spielfeld(this);
		splitpane.setLeftComponent(SpielfeldlPanel);
		
		VorauswahlPanel = new Vorauswahl_Spielfenster(this, SpielfeldlPanel);
		VorauswahlPanel.setSize(new Dimension(160,52*squaresz));
		splitpane.setRightComponent(VorauswahlPanel);
	
	}
	
	/**
	 * Refreshes the size of the panels when the button in Vorauswahl_Spielfenster is pressed
	 */
	public void refreshSize(int s, int prev_squaresz) {
				
		switch (s) {
		case 1: setSize(new Dimension(58*squaresz+167,60*squaresz)); // 58*10 +167 = 747, 60*10 = 600                
		        Sizewindow = 0;
				break;
		case 2: setSize(new Dimension(58*squaresz+163,54*squaresz+2));
		        Sizewindow = 1;
				break;
		case 3: setSize(new Dimension(58*squaresz+130,53*squaresz+5)); //58*15+130 = 1000, 53*15+5 = 800
		        Sizewindow = 2;
				break;
		}
	
		    setLocationRelativeTo(null);
			revalidate();
			SpielfeldlPanel.squaresz = squaresz;
			SpielfeldlPanel.resize(prev_squaresz);  
			SpielfeldlPanel.repaint();
			SpielfeldlPanel.requestFocus(); 
	}
	
	/**
	 * Closes the game 2 and returns to the gui_Startfenster
	 */
	public void close() {	
		setVisible(false);				
		score = SpielfeldlPanel.score;
		HighscoreFenster.updateScore("resources/spiel2.txt", score);
		startfenster.setVisible(true);
		dispose();
	}
}
