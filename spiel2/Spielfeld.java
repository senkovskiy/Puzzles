package team19.spiel2;

import javax.swing.JButton;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import team19.main.*;
import team19.enumeration.*;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.Timer;
import team19.spiel2.GUI_Spiel_2;
import team19.highscore.HighscoreFenster;
	/**
	 * Spielfeld JPanel is created when the user starts Spiel 2.
	 * Spielfeld is displayed on the left side of the screen.
	 * Spielfeld displays the game cycle and contains the logic of the game.
	 * 
	 */
    
public class Spielfeld extends JPanel {
	/**
	 * The boolean GameCycle turns to true when the game starts
	 */
	boolean GameCycle = true;
	  
	/**
	 * The boolean exit turns to true when the game stops
	 */
	public boolean exit = false; 
	private static final long serialVersionUID = 1L;	
	/**
	 * amountOfSteins - how may Steins appear at once, k - size of the Stein, squaresz - size of the one square in pixels
	 */	
	public int amountOfSteins=0, k=0, squaresz=12;	
	/**
	 * The array of all possible Steins
	 */	
	public ArrayList<Stein> allSteins,
	/**
	 * Arrays of falling and parked Steins
	 */	
	fallingSteins = new ArrayList<>(),
	parkedSteins = new ArrayList<>();  
	/**
	 * The array Steins forming outlines (Umrisse), two Steins per Umrisse
	 */
	ArrayList<ArrayList<Stein>> Umrisse_list;
	/**
	 * The number of free columns between falling Steins
	 */
	private int freeColumnsBetween = 5;
	/**
	 * Starting point for all generated Steins
	 */
	private Point startPosition = new Point(0, 0);
	/**
	 * The boolean turns to true to show the game when the start button is pressed
	 */
	private boolean show_game = false; 
	/**
	 * The size of the play field (Spielfeld)
	 */
	public Dimension size;
	/**
	 * The selected Stein
	 */
	public Stein stein = null;
	/**
	 * The number of outlines (Umrisse)
	 */		
	int number_Umrisse;
	/**
	 * The boolean is true when the Stein is selected
	 */	
	private boolean paintStein = false;
	/**
	 * The array of boolean showing which position in Umrisse is occupied 
	 */	
	ArrayList<ArrayList<Boolean>> occupied_umrisse; 
	/**
	 * The integer array shows which Parkplatz (array index) and in which order (value) is occupied 
	 */
	int [] park_platze;
	/**
	 * Indexes of the selected Umrisse, Parkplatz, Stein
	 */
	int index_slected_umrisse;
	int index_slected_park;
	int selected_stein_index;	
	/**
	 * The booleans show whether the Umrisse of Parkplatz are selected
	 */
	boolean umrisse_selected = false;
	boolean park_selected = false;
	/**
	 * The borders of the Spielfeld
	 */
	int[] Borders = {squaresz*1, squaresz*2, squaresz*52, squaresz*35};
	/**
	 * The score, increases by 100 if two Steins are plased into the one Umrisse
	 */
	int score = 0;
	/**
	 * These booleans are true when ENTER is pressed to put Stein to Umrisse of Parkplatz correspondingly
	 */
	boolean Stein_to_Umrisse = false;
	boolean Stein_to_Park = false;
	/**
	 * Timer to run the game
	 */
	public Timer timer;
	/**
	 * The is a JFrame of the JPanle Spielfeld  
	 */
	private GUI_Spiel_2 mother;	
	/**
	 * Speed of the game
	 */
	public int chosen_speed = 1000;
	/**
	 * The number of chosen colors = chosen_color + 1
	 */
	public int chosen_color = 4;
	/**
	 * The array of Stein colors
	 */
	private ArrayList<Color> Stain_colors = new ArrayList<>();
	/**
	 * The boolean is true if acceleration is chosen
	 */
	public boolean acceleration = false;
	/**
	 * The integer sets the time between game updates, which is reducing by 40 ms after each 40 ms
	 */
	int acceleration_constant = 40;
	/**
	 * The is a constant to limit our speed 
	 */
	int speed_limit = 100;
	/**
	 * The boolean is true id pause is started (button P)
	 */
	boolean pause = false;
	/**
	 * The boolean is true when the Stein is accelerating (button SPACE)
	 */
	boolean stein_acceleration = false;
	/**
	 * Array lists of labels for Umrisse and Parkplatz
	 */
	public ArrayList<JLabel> label_umrisse;
	ArrayList<JLabel> label_park;
	/**
	 * Warnung turns to true if Stein does not fit to the Umrisse or when chosen Parkplatz is occupied
	 */
	boolean warnung = false;
	/**
	 * The time counter starts to count when the game is started, it shows the the duration of the game (in time steps)
	 */
	public int time_counter = 0;
	/**
	 * The interval between the game steps when new sets of Steins appear
	 * for the fast game, the interval = 700*20 ms = 14 s, 
	 * for the middle speed game, the interval = 1000*20 ms = 20 s,
	 * for the slow game, the inteval = 1500*20 ms = 30 s.
	 */
	public int interval = 20;
	 
	/**
	 * This message appears in pop-up window if the game over
	 */
	String message;
	/**
	 * Creates a new Spielfeld of the game 2 (Spielfeld) based on the JFrame Frame2
	 * 
	 * Based on the size given by the Frame2, all elements of the Spielfeld are sized (resized) accordingly.
	 * 
	 * @param JFrame_Game_2 is the Frame2
	 */
	public Spielfeld(GUI_Spiel_2 JFrame_Game_2) {		   
		mother = JFrame_Game_2;
		setFocusable(true);
		setBackground(Color.white);
		setLayout(null);
	}
	
	/**
	 * Sets the speed of the game
	 * 
	 * for the fast game, speed = 1 update/700 ms, 
	 * for the middle speed game, speed = 1 update/1000 ms,
	 * for the slow game, speed = 1 update/1500 ms.
	 * 
	 * @param speed is the speed of the game
	 */
	public void setSpeed (int speed) {
		chosen_speed = speed; 
	}
	
	/**
	 * Initializes the Spielfeld when the start button is pressed:
	 * size, mouse listener, colors, timer
	 */
	public void initPanel() {
		size=this.getSize();
		size.height += 49;
		initListeners();
		initColors();
		repaint(); 
		
		timer = new Timer(0, new ActionListener() { //chosen_speed
		@Override
		public void actionPerformed(ActionEvent e) {
			dropSteins();
			time_counter++;
		}
		});
		timer.setDelay(chosen_speed);
		timer.setRepeats(true);
		timer.start();	
	}
	
	/**
	 * Resizes the Spielfeld according to the JFrame_Game_2 (mother)
	 * 
	 * @param prev_squaresz is the size of the squares before the size update
	 */
	public void resize(int prev_squaresz) {
		squaresz = mother.squaresz;
		Borders = new int[] {squaresz*1, squaresz*2, squaresz*52, squaresz*35};
		if (show_game) {
			for (Stein st : fallingSteins) {
				st.setSquareSize(squaresz);
				st.position.x = st.position.x/prev_squaresz*squaresz;
				st.position.y = st.position.y/prev_squaresz*squaresz;
			}
			
			for(int i = 0; i<Umrisse_list.size(); i++) { 
				ArrayList<Stein> currentUmrisse = new ArrayList<Stein>();
				currentUmrisse = Umrisse_list.get(i);
	
				Stein top = currentUmrisse.get(0);
				Stein bottom = currentUmrisse.get(1);
				
				top.setSquareSize(squaresz);
				bottom.setSquareSize(squaresz);
				
				top.position.y = top.position.y/prev_squaresz*squaresz;
				bottom.position.y = bottom.position.y/prev_squaresz*squaresz;
				
				top.position.x = top.position.x/prev_squaresz*squaresz;
				bottom.position.x = bottom.position.x/prev_squaresz*squaresz;
				
			}
			Add_labels_umrisse();
			Add_labels_park();
		}	
	}
			
	/**
	 * Initializes all possible colors of the falling Steins,
	 * warm light colors are chosen.
	 */	
	public void initColors() {
		Stain_colors.add(new Color(151, 195, 10));                
		Stain_colors.add(new Color(31, 190, 214));             
		Stain_colors.add(new Color(108, 160, 220));          
		Stain_colors.add(new Color(245, 214, 37));           
		Stain_colors.add(new Color(255, 113, 126));	      
	}
	
	/**
	 * Sets random Steins based on the chosen parameters from Vorauswahl_Spielfenster panel.
	 * 
	 * @param s - an array list of booleans which set correct enumeration of Steins
	 * @param k - the number of plates in the Stein
	 * @param amountOfSteins - the amount of falling Steins
	 */	
	public void setSteins(ArrayList<boolean[]> s, int k, int amountOfSteins) {
		
		this.k=k;
		this.amountOfSteins = amountOfSteins;
		int left_steins = 0;
		show_game = true;
		
		if(time_counter == 0) {
			fallingSteins.clear();
		}				
		if(fallingSteins!= null) {
			left_steins = fallingSteins.size();
		}		
		for (int i=0; i<amountOfSteins; i++) { 
			Random rn = new Random();
			int next;
			next = rn.nextInt(chosen_color);	
			Color col = Stain_colors.get(next);
			Stein copy = new Stein(k, new Point(0,0), Help.booleanToInt(s.get(i)), col, squaresz, true);
			copy.smallestColor = copy.smallestColors();	
			fallingSteins.add(copy);                                
		} 			
		
		int columns = k*amountOfSteins+(amountOfSteins-1)*freeColumnsBetween;
		startPosition.x = (size.width-squaresz*columns)/2 + squaresz/2;
		startPosition.y = squaresz*3;
		
		if(k==4 && amountOfSteins%2 != 0 ) {
			startPosition.x +=squaresz/2; 		
		};
		
		for (Stein st : fallingSteins) {
			if(st.position.y==0) {
				st.position.x = startPosition.x + (fallingSteins.indexOf(st) - left_steins)*squaresz*(k+freeColumnsBetween);
				st.position.y = startPosition.y;
			}
		}
	requestFocus();
	}
	
	/**
	 * Sets random Steins based on the chosen parameters from Vorauswahl_Spielfenster panel.
	 * 
	 * @param s - an array list of booleans which set correct enumeration of Steins
	 * @param number_Umrisse - the amount of outlines (Umrisse)
	 */		
	public void setUmrisse(ArrayList<boolean[]> s, int number_Umrisse) {
				
		Umrisse_list = new ArrayList<ArrayList<Stein>>();
		occupied_umrisse = new ArrayList<ArrayList<Boolean>>();
	
		for(int i = 0; i<number_Umrisse; i++) { //number_Umrisse                         
			ArrayList<Stein> currentSteins = new ArrayList<Stein>();
			ArrayList<Boolean> currentBoolean = new ArrayList<Boolean>();
			currentSteins = null;
						
			while(currentSteins == null) {
				currentSteins = single_Umrisse(s);
			}	
			
			Stein top = currentSteins.get(0);
			Stein bottom = currentSteins.get(1);
			int shift_x = Borders[0] + (k+1)*squaresz*i;
	
			top.position.x += shift_x;
			bottom.position.x += shift_x; 
		 	top.position.y += Borders[1] + Borders[3] + squaresz;
			bottom.position.y += Borders[1] + Borders[3] + squaresz;
			
			Umrisse_list.add(currentSteins);
			
			currentBoolean.add(false);
			currentBoolean.add(false);
			occupied_umrisse.add(currentBoolean);
			
			currentSteins = null;
			score = 0;
		}
		setParkPlatz(k);
		Add_labels_umrisse();
	}
	
	/**
	 * Adds labels to Umrisse.
	 * The font size and positions are scaled with respect to the size of Spielfeld.
	 */
	public void Add_labels_umrisse() {
		int font_size = squaresz - 1;
		Font font = new Font("Arial", Font.BOLD,font_size);
		if(label_umrisse!= null) {
			for(int i=0; i<label_umrisse.size(); i++) {
				remove(label_umrisse.get(i));
				validate();
				repaint();
			}
		}
		label_umrisse = new ArrayList<JLabel>();
		
		for(int i=0; i<Umrisse_list.size(); i++) {
			String str = Integer.toString(i+1);
			JLabel label = new JLabel(str);			
			int pos_y = Borders[1] + Borders[3]+2;// + squaresz*(k+1);
			int pos_x = Borders[0] + (k+1)*squaresz*i;	
			label.setFont(font);
			label.setBounds(pos_x, pos_y, 10, 10); // size.width, size.height
			label.setForeground(Color.RED);
			label_umrisse.add(label);
			add(label);
		}
	}
	
	/**
	 * Adds labels to Parkplatz. Creates label_park array.
	 * The font size and positions are scaled with respect to the size of Spielfeld.
	 */
	public void Add_labels_park() {
		int font_size = squaresz - 1;
		Font font = new Font("Arial", Font.BOLD,font_size);
		if(label_park!= null) {
			for(int i=0; i<park_platze.length; i++) {
				remove(label_park.get(i));
				validate();
				repaint();
			}
		}
		label_park = new ArrayList<JLabel>();
		
		for(int i=0; i<park_platze.length; i++) {
			String str = Integer.toString(i+1);
			JLabel label = new JLabel(str);	
			int pos_y = Borders[1] + Borders[3]+ squaresz*(k+1) + 1;// + squaresz*(k+1);
			int pos_x = Borders[0] + (k+1)*squaresz*i;
			
			label.setFont(font);
			label.setBounds(pos_x, pos_y, 10, 10); // size.width, size.height
			label.setForeground(Color.RED);
			label_park.add(label);
			add(label);			
		}		
	}
	
	/**
	 * Sets the list for the Parkplatz depending on the Stein size.
	 * The list shows which Parkplatz (index) and in which order (value) is occupied.
	 * 
	 * @param k - the number of plates in the Stein
	 */
	public void setParkPlatz(int k) {
		
		if(k == 3) {
			park_platze = new int[] {0, 0};
		}
		if(k == 4) {
			park_platze = new int[] {0, 0, 0, 0};
		}
		if(k == 5) {
			park_platze = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
		}
		Add_labels_park();
	}
	
	/**
	 * Creates a single Umrisse, which is a couple (array) of random steins (Stein_bottom, Stein_top).
	 * The process of generation: Stein_top is "falling" randomly on the Stein_bottom within the horizontal limit of k,
	 * then it checks the obtained dimensions (horizontal and vertical) - should not exceed k*k.
	 * The Umrisse is generated from randomly chosen steins of a given size.
	 * To do this, an array list of all steins is created - allSteins.
	 * 
	 * @param s - an array list of booleans which set correct enumeration of Steins
	 * @return list of steins for single Umrisse with proper position (Point), and size not exceeding the k
	 */
	public ArrayList<Stein> single_Umrisse(ArrayList<boolean[]> s) {
		Random rand = new Random();
		 
		allSteins = new ArrayList<>();
		for (boolean[] b : s) {
			Stein copy = new Stein(k, new Point(0,0), Help.booleanToInt(b), Color.black, squaresz, true); 
			copy.smallestColor = copy.smallestColors();	
			allSteins.add(copy);
		}
	
		Stein Stein_bottom;
		Stein Stein_top;
		
		Stein_bottom = allSteins.get(rand.nextInt(allSteins.size()));
		Stein_top = allSteins.get(rand.nextInt(allSteins.size()));
		
		if(Stein_top == Stein_bottom) {
			while (true) {
				Stein_top = allSteins.get(rand.nextInt(allSteins.size()));			
				if(Stein_top != Stein_bottom){
					break;
				}
			}
		}
			
		ArrayList<Integer> random_items = new ArrayList<Integer>();
		for(int i = 0; i<=RightZeroCols(Stein_top); i++) {
			random_items.add(i);
		} 
		
		Collections.shuffle(random_items, new Random());
		
		Stein_top.position.x = random_items.get(0)*squaresz;
		Stein_top.position.y = 0;
		Stein_bottom.position.x = 0;
		Stein_bottom.position.y = (k-BottomZeroRows(Stein_top))*squaresz;
		
		int shift_down = 0;
		
		for(int j_start = 0; j_start<random_items.size(); j_start++) {
			shift_down = 0;
			Stein_top.position.y = 0;
			Stein_top.position.x = random_items.get(j_start)*squaresz;
			if(checkBottom(Stein_top, Stein_bottom, random_items.get(j_start),shift_down)) {
				break;
			}
			else {
				for(int shift = 1; shift<k-BottomZeroRows(Stein_bottom)+1; shift++) {
					shift_down=shift;
					Stein_top.position.y+=squaresz;
					if(checkBottom(Stein_top, Stein_bottom, random_items.get(j_start),shift_down)) {
					//	System.out.println(checkBottom(Stein_top, Stein_bottom, random_items.get(j_start), shift_down));
					//	System.out.println("random_items = " + random_items.get(j_start));
						shift_down = 0;
						break;
					}
				}
			}
			if(checkLeft(Stein_top, Stein_bottom, random_items.get(j_start))) {
				//System.out.println("Stone on the left");
				break;
			}	
	
			if(checkBottom(Stein_top, Stein_bottom, random_items.get(j_start),shift_down)) {
				//System.out.println(checkBottom(Stein_top, Stein_bottom, random_items.get(j_start), shift_down));
				break;
			}
		} 		
		
		int height = (Stein_bottom.position.y - Stein_top.position.y)/squaresz + (k-BottomZeroRows(Stein_bottom));
		// System.out.println("height = " + height);
		if(height>k) {
		//	System.out.println("height is too large ");
			return null;
		}
		
		ArrayList<Stein> Current_list = new ArrayList<Stein>();
		
		int min_y = Math.min(Stein_top.position.y, Stein_bottom.position.y);
		Stein_top.position.y -= min_y;
		Stein_bottom.position.y -= min_y;
		
		Current_list.add(Stein_top);
		Current_list.add(Stein_bottom);
	
		allSteins.clear();
		return Current_list;
	}
	
	/**
	 * Checks if bottom_stein is right under the top_stein. This method is used to generate an Umrisse. 
	 * 
	 * @param top_stein - top Steins
	 * @param bottom_stein - bottom Steins
	 * @param start_j - random horizontal position (column j) of falling top Steins relative to the bottom Stein 
	 * @param shift_down - how much the top Stein is shifted down to check if the bottom Stein is below 
	 * @return boolean true if the bottom Stein is below the top Stein
	 */
	public boolean checkBottom(Stein top_stein, Stein bottom_stein, int start_j, int shift_down) {
		int [][] top = top_stein.SteinIn2D();
		int [][] bottom = bottom_stein.SteinIn2D();
		int delta = BottomZeroRows(top_stein);
		int count = 0;
		for (int i = 0; i<k-BottomZeroRows(top_stein); i++) {		
			for (int j = 0; j<k-RightZeroCols(top_stein); j++) {      
				if(top[i][j] == 1 && i + 1 - (k - delta) + shift_down>=0 && i + 1 - (k - delta) + shift_down<k) {
					if(bottom[i + 1 - (k - delta) + shift_down][j+start_j]==1) {
						count++;
					}
				}
			}	
		}
		if(count > 0) {
			return true;
		}
		else {
			return false;
		}		
	}
	
	/**
	 * Sets colors to the falling stein. 
	 * 
	 * @param st - the chosen stein
	 */
	public void setColors(Stein st) {
		for(int i =0; i<k; i++) {
			st.colors[i] = fallingSteins.get(selected_stein_index).colors[0];			
		}
	}
	
	/**
	 * Checks if bottom_stein is right on the left from the top_stein.
	 * This method is used to generate an Umrisse, and is called when the bottom_stein is not under the top_stein.
	 * 
	 * @param top_stein - top Steins
	 * @param bottom_stein - bottom Steins
	 * @param start_j - random horizontal position (column j) of falling top Steins relative to the bottom Stein 
	 * @return boolean true if the bottom Stein is on the left from the top Stein
	 */
	public boolean checkLeft(Stein top_stein, Stein bottom_stein, int start_j) {
		int [][] top = top_stein.SteinIn2D();
		int [][] bottom = bottom_stein.SteinIn2D();
		int count = 0;
		int delta = (bottom_stein.position.y - top_stein.position.y)/squaresz; 
	
		if(delta>=0) {
			for (int i = 0; i<k-BottomZeroRows(top_stein); i++) {				
				if(top[i][0] == 1 && start_j-1 >= 0 && i+delta<k) {
					if(bottom[i+delta][start_j-1] == 1) {				
								count++;
					}
				}
			}
		}
		else {
			for (int i = 0; i<k-BottomZeroRows(top_stein); i++) {				
				if(top[i][0] == 1 && start_j-1 >=0 && i-delta<k) {
					if(bottom[i-delta][start_j-1] == 1) {				
								count++;
					}
				}
			}
		}	
		if(count > 0) {
			return true;
		}
		else {
			return false;
		}		
	}	
	
	/**
	 * Makes stones falling. 
	 * Generates new Steins after certain time interval.
	 * Stops game if the steins cross the lower border.
	 * Accelerates stones if acceleration is chosen.
	 */
	public void dropSteins() {				
		for (Stein st: fallingSteins) {
			if (st.position.y + squaresz*(k - BottomZeroRows(st))< Borders[3]+Borders[1]) {
				st.position.y += squaresz;				
				if(st==stein) {              
						continue;
					}
				repaint();		
			}
			else {
				EndGame();				
				break;
			}
	
		}
	
		if(acceleration == true && timer.getDelay()>speed_limit) {
			timer.setDelay(timer.getDelay()-acceleration_constant);
		}
		
		if(time_counter % interval == 0) {
			getNewSteins();			
		}
	}
	
	/**
	 * Generates new falling steins.
	 */
	public void getNewSteins() {
		Enumeration steinEnum = new Enumeration(k);	
		ArrayList<boolean[]> right_enum = steinEnum.getRightEnum(false, false);
		setSteins(right_enum, k, amountOfSteins);
	}
	
	/**
	 * Paints all game components, depending on the game conditions.
	 */
	@Override 
	protected void paintComponent(Graphics g) {
	    Graphics2D gr = (Graphics2D) g;
		super.paintComponent(gr);
	    	
		if (show_game) {
			/**
			 * Paints horizontal and vertical lines of the Spielfeld.
			 */
			gr.setColor(Color.LIGHT_GRAY);
			
			size=this.getSize();
			size.height += 49;
					
			int line_cols = size.width/squaresz + 1;
			int line_rows = size.height/squaresz + 1;
		    for(int i = 0; i<line_cols-1;i++) {
		    	gr.drawLine(squaresz + i*squaresz,0, squaresz + i*squaresz, line_rows*squaresz);	    	
		    }
		    for(int i = 0; i<line_rows-1;i++) {
		    	gr.drawLine(0,squaresz + i*squaresz, line_cols*squaresz, squaresz + i*squaresz);   
		    }
		    	    
			/**
			 * Paints all Parkplatz.
			 */
		    for (int p = 0; p<park_platze.length; p++) {
		    	int thickness = 2;
		    	((Graphics2D) g).setStroke(new BasicStroke(thickness));
				
		    	int start_y = Borders[1] + Borders[3] + squaresz*(2+k);
				int start_x = Borders[0] + (k+1)*squaresz*p;
				
		    	g.setColor(Color.BLACK);	
				g.drawRect(start_x, start_y, squaresz*k, squaresz*k);
				
				/**
				 * Paints selected Parkplatz and stein in it.
				 */
				if(park_selected && p == index_slected_park) {
					
					thickness = 2;
					((Graphics2D) g).setStroke(new BasicStroke(thickness));
					
					g.setColor(Color.RED);
					g.drawRect(start_x, start_y, squaresz*k, squaresz*k);
					
					if(stein != null && Stein_to_Park == true 
							&& park_platze[p]>0 
							&& fallingSteins.contains(stein) == true) {
						warnung = true;
					}
					
					if(stein != null && Stein_to_Park == true 
							&& park_platze[p]==0 && parkedSteins.size()<park_platze.length 
							&& fallingSteins.contains(stein) == true) {
			    		
			    		stein = fallingSteins.get(selected_stein_index);
			    		stein.outline = true;                                          
			    		parkedSteins.add(stein);
			    		park_platze[p] = parkedSteins.size();
			    		fallingSteins.remove(selected_stein_index);

			    		stein.position.y = Borders[1] + Borders[3] + squaresz*(2+k);
			    		stein.position.x = Borders[0] + (k+1)*squaresz*p;			    	
			       		repaint();
			      		Stein_to_Park = false; 
			       	}
					else {
						Stein_to_Park = false;
					}
			    	
			    	thickness = 1;
			    	((Graphics2D) g).setStroke(new BasicStroke(thickness));				    	
				}
	
				thickness = 1;
		    	((Graphics2D) g).setStroke(new BasicStroke(thickness));
		    	
				/**
				 * Paints steins in Parkplatz if it is not selected.
				 */
	    		int index_stein = park_platze[p]-1;
	    		if(index_stein>=0) { 
	    			if(parkedSteins.get(index_stein) != stein) {
	    	       		//System.out.println("stein_index = " + index);
	    	    		parkedSteins.get(index_stein).paintGitter = true;
	    	    		parkedSteins.get(index_stein).outline = false;
	    	    		parkedSteins.get(index_stein).paint(gr);
	    	    		repaint();
	    			}
	    		
		    	}
	
		    }
		    		    
			/**
			 * Paints falling steins.
			 */
		    for (Stein st : fallingSteins) {
		    	if(st!=stein) {
					st.paintGitter = true;                                                   
					st.outline = false;
					st.setSquareSize(squaresz);	
					st.paint(gr);
		    	}	
		    }
			/**
			 * Paints selected stein.
			 */				
			if (paintStein && stein!=null) {
				stein.paintGitter = false;
				stein.outline = true;
				stein.paint_stein(gr, Color.red);
			}
			
			/**
			 * Paints Umrisse.
			 */
			if (Umrisse_list!=null) {
				for(int i = 0; i<Umrisse_list.size(); i++) {
					ArrayList<Stein> currentUmrisse = new ArrayList<Stein>();
					currentUmrisse = Umrisse_list.get(i);
	
					Stein top = currentUmrisse.get(0);
					Stein bottom = currentUmrisse.get(1);
					top.setSquareSize(squaresz);
					bottom.setSquareSize(squaresz);
					
					boolean b_top = occupied_umrisse.get(i).get(0);
					boolean b_bottom = occupied_umrisse.get(i).get(1);
					
					if(b_top && b_bottom) {
						score+=100;
						occupied_umrisse.get(i).set(0, false);
						occupied_umrisse.get(i).set(1, false);
					}		
					if(!b_top) {
						top.paintGitter = true;
						top.outline = true;
						top.paint(gr);
					}
					else {
						top.paintGitter = true;
						top.outline = false;
						top.paint_stein(gr, Color.LIGHT_GRAY);	 
					}						
					if(!b_bottom) {
						bottom.paintGitter = true;
						bottom.outline = true;
						bottom.paint(gr);
					}
					else {						
						bottom.paintGitter = true;
						bottom.outline = false;
						bottom.paint_stein(gr, Color.LIGHT_GRAY);	
					}
					
					/**
					 * Paints selected Umrisse and stein in it if stein fits to the Umrisse.
					 */
					if(umrisse_selected && i == index_slected_umrisse) {
						int thickness = 2;
						((Graphics2D) g).setStroke(new BasicStroke(thickness));
						g.setColor(Color.RED);
						
						int pos_y = Borders[1] + Borders[3] + squaresz;
						int pos_x = Borders[0] + (k+1)*squaresz*i;
												
						g.drawRect(pos_x, pos_y, k*squaresz, k*squaresz);
						
						thickness = 1;
						((Graphics2D) g).setStroke(new BasicStroke(thickness));
	
						if(stein != null && Stein_to_Umrisse == true) {
							
							if(compareStein(top) && occupied_umrisse.get(i).get(0) != true) {
								occupied_umrisse.get(i).set(0, true);
								top.paintGitter = false;
								top.paint(gr);
								
								if(fallingSteins.contains(stein)) {
									fallingSteins.remove(selected_stein_index);
								}
								
								else if(parkedSteins.contains(stein)) {
									int indx = parkedSteins.indexOf(stein);
									parkedSteins.remove(indx);
									
									for(int elem = 0; elem < park_platze.length; elem++) {
										if(park_platze[elem] == indx+1) {
											park_platze[elem] = 0;
										}
										else if (park_platze[elem]>indx+1){
											park_platze[elem] -= 1;
										}
									}
								}
	
								stein = null;
								Stein_to_Umrisse = false;
								repaint();
								break;
							}
							if(compareStein(bottom) && occupied_umrisse.get(i).get(1) != true){						
								occupied_umrisse.get(i).set(1, true);
								bottom.paintGitter = false;
						
								bottom.paint(gr);
								if(fallingSteins.contains(stein)) {
									fallingSteins.remove(selected_stein_index);
								}
								
								else if(parkedSteins.contains(stein)) {
									int indx = parkedSteins.indexOf(stein);
									parkedSteins.remove(indx);
			
									for(int elem = 0; elem < park_platze.length; elem++) {
										if(park_platze[elem] == indx+1) {
											park_platze[elem] = 0;
										}
										else if (park_platze[elem]>indx+1){
											park_platze[elem] -= 1;
										}
									}
								}				
								stein = null;
								Stein_to_Umrisse = false;
								repaint();
							}
							if(stein!= null && Stein_to_Umrisse == true) {
								warnung = true;
							}
							Stein_to_Umrisse = false;						
						}				
						else {
							Stein_to_Umrisse = false;
						}					
					}
				}
			}
		}
	}
	
	/**
	 * Paints main border of the game.
	 */
	@Override
	protected void paintBorder(Graphics g) {
		super.paintBorder(g);
		Graphics2D g1 = (Graphics2D) g;
		int thickness = 2;
		g1.setStroke(new BasicStroke(thickness));
		g1.setColor(Color.BLACK);
		if (show_game) {
			g1.drawRect(Borders[0], Borders[1], Borders[2], Borders[3]);
		}		
	}
			
	/**
	 * Checks if some stein from the falling steins (fallingSteins) is selected upon the mouse click.
	 * Assigns the index of selected stein in fallingSteins - selected_stein_index.
	 */
	private void checkStein(Point p) {
		
		for (Stein st : fallingSteins) {
			int current_indx = fallingSteins.indexOf(st);
			for (int i = 0; i<k; i++) {
				for (int j = 0; j<k; j++) {
					int xStart = st.position.x + j*squaresz;
					int yStart = st.position.y + i*squaresz; //+ run_count*squaresz;
					if (st.SteinIn2D()[i][j]==1  && p.x >=xStart && p.x <=xStart+squaresz
							&& p.y >=yStart && p.y <=yStart+squaresz) {
															
						if(stein != null) {
							if(stein == st) {
								stein = null;
							}
							else {
								selected_stein_index = current_indx;
								stein = st;
							}
						}
						else {
							selected_stein_index = current_indx;
							stein = st;
						}						
						paintStein = true; 
						repaint();
					}
				}		
			}	
		}		
	}	
	
	/**
	 * Checks if some Umrisse from the Umrisse_list is selected by the mouse click.
	 * Assigns the index of selected Umrisse in Umrisse_list - index_slected_umrisse.
	 * 
	 * @return boolean true if Umrisse is selected
	 */
	private boolean checkUmrisse(Point p) {
		int index = 0;
		for (ArrayList<Stein> Umrisse : Umrisse_list) {
			int xStart = Math.min(Umrisse.get(0).position.x, Umrisse.get(1).position.x);
			int yStart = Umrisse.get(0).position.y;
			
			if (p.x >=xStart && p.x <=xStart+squaresz*k
					&& p.y >=yStart && p.y <=yStart+squaresz*k) {
				
				index_slected_umrisse = index;				
				repaint();
				return true;				
			}			
			index++;					
		}
		repaint();
		return false;
	}
	
	/**
	 * Checks if some Parkplatz from the park_platze list is selected by the mouse click.
	 * Assigns the index of selected Parkplatz in the park_platze list - index_slected_park.
	 * 
	 * @return boolean true if Parkplatz is selected
	 */
	private boolean checkPark(Point p) {
		int index = 0;
		for (int i=0; i< park_platze.length; i++) {
			int yStart = Borders[1] + Borders[3] + squaresz*(2+k);
			int xStart = Borders[0] + (k+1)*squaresz*i;
			
			if (p.x >=xStart && p.x <=xStart+squaresz*k
					&& p.y >=yStart && p.y <=yStart+squaresz*k) {
				
				index_slected_park = index;				
				repaint();
				return true;				
			}			
			index++;					
		}
		repaint();
		return false;
	}
	
	
	/**
	 * Compares selected Umrisse with the stein.
	 * 
	 * @param st - stein from the Umrisse (top or bottom) to be compared with the selected falling stein
	 * @return boolean true if selected stein fits to the stein in Umrisse
	 */
	public boolean compareStein(Stein st) {
			int count = 0;
			for (int i = 0; i<k; i++) {
				for (int j = 0; j<k; j++) {
					
					if (st.SteinIn2D()[i][j]==1  && stein.SteinIn2D()[i][j]==1) { 
						count++;
					}
				}
			}		
			if (count == k) {
				return true;
			}
		return false;
	}
	
	public void pause() {
	    timer.stop();
	}
	
	public void resume() {
	    timer.restart();
	}
	
	/**
	 * Initiates Mouse Listener
	 */
	private void initListeners() {
		addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (pause == false) {
					checkStein(e.getPoint());
					if(checkUmrisse(e.getPoint())) {				
						umrisse_selected = true;
						park_selected = false;
					};
							
					if(checkPark(e.getPoint())) {
						park_selected = true;
						umrisse_selected = false;
						
						if(park_platze[index_slected_park]>0) {
				    		stein = parkedSteins.get(park_platze[index_slected_park]-1);
						}
					};
				}
			}
	
			@Override
			public void mousePressed(MouseEvent e) {
			}	
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
			}	
			@Override
			public void mouseExited(MouseEvent e) {
			}	
		});
	
		addKeyListener(new KeyListener() {	
			@Override
			public void keyTyped(KeyEvent e) {	
			}	
			@Override
			public void keyPressed(KeyEvent e) {
				keyAction(e);
			}	
			@Override
			public void keyReleased(KeyEvent e) {	
			}	
		});
	}
	
	/**
	 * Selects the chosen Umrisse or Parkplatz depending on the chosen index (e.g. by the button).
	 * Updates the index of selected Umrisse or Parkplatz.
	 * 
	 * @param index - index of selected Umrisse or Parkplatz
	 */
	public void Select_Umrisse_Park(int index) {
		if(park_selected && park_platze.length>index) {
			index_slected_park = index;
			repaint();
		}
		if(umrisse_selected && Umrisse_list.size()>index) {
			index_slected_umrisse = index;
			repaint();
		}
	}
	
	/**
	 * Assigns the actions to be performed when the button is clicked.
	 */
	private void keyAction(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if (pause == false && !exit) {
			switch (keyCode) {
				/**
				 * Starts pause.
				 */
				case KeyEvent.VK_P:			
					pause = true;
					pause();
					break;
				/**
				 * Selects Umrisse or Parplatz regions, switches between them.
				 */	
				case KeyEvent.VK_V:	
					if(!park_selected && !umrisse_selected) {
						umrisse_selected = true;
						index_slected_umrisse = 0;
						repaint();
					}
					else if(!park_selected && umrisse_selected) {
						umrisse_selected = false;
						park_selected = true;
						index_slected_park = 0;
						repaint();
					}
					else if(park_selected && !umrisse_selected) {
						park_selected = false;
						umrisse_selected = true;
						index_slected_umrisse = 0;
						repaint();
					}
					break;
				/**
				 * Selects the Umrisse or Parplatz number by the buttons.
				 */
				case KeyEvent.VK_1:	
					Select_Umrisse_Park(0);
					break;					
				case KeyEvent.VK_2:	
					Select_Umrisse_Park(1);
					break;
				case KeyEvent.VK_3:	
					Select_Umrisse_Park(2);
					break;
				case KeyEvent.VK_4:	
					Select_Umrisse_Park(3);
					break;
				case KeyEvent.VK_5:	
					Select_Umrisse_Park(4);
					break;
				case KeyEvent.VK_6:	
					Select_Umrisse_Park(5);
					break;					
				case KeyEvent.VK_7:	
					Select_Umrisse_Park(6);
					break;
				case KeyEvent.VK_8:	
					Select_Umrisse_Park(7);
					break;
				case KeyEvent.VK_9:	
					Select_Umrisse_Park(8);
					break;
					
				/**
				 * Moves position of the selected Umrisse or Parplatz to the right when they are selected.
				 */		
				case KeyEvent.VK_RIGHT:
					if(park_selected && !umrisse_selected && park_platze.length>index_slected_park-1) {
						Select_Umrisse_Park(index_slected_park+1);
					}
					else if(!park_selected && umrisse_selected && Umrisse_list.size()>index_slected_umrisse-1) {
						Select_Umrisse_Park(index_slected_umrisse+1);
					}
					break;
					
				/**
				 * Moves position of the selected Umrisse or Parplatz to the left when they are selected.
				 */		
				case KeyEvent.VK_LEFT:
					if(park_selected && !umrisse_selected && index_slected_park>0) {
						Select_Umrisse_Park(index_slected_park-1);
					}
					else if(!park_selected && umrisse_selected && index_slected_umrisse>0) {
						Select_Umrisse_Park(index_slected_umrisse-1);
					}
					break;
				
			}	
			if(stein != null) {
				switch (keyCode) {
				
				/**
				 * Tries to put selected stein into the selected Umrisse or Parkplatz if it fits.
				 * Moves the selected stein from one Parkplatz to another.
				 */				
				case KeyEvent.VK_ENTER:
						if(umrisse_selected) {
							Stein_to_Umrisse = true; 
						}
						if(park_selected) {
							Stein_to_Park = true;
						}
						
						if (park_selected && parkedSteins.contains(stein)) { 
							
							int current_index = (stein.position.x - Borders[0])/((k+1)*squaresz);
							
							if(park_platze[index_slected_park] == 0) {						
								park_platze[index_slected_park] = park_platze[current_index];
								park_platze[current_index] = 0;
								stein.position.x = Borders[0] + (k+1)*squaresz*index_slected_park;
								repaint();
							}
							else if(park_platze[index_slected_park]!= 0 && current_index!=index_slected_park){
								warnung = true;
								repaint();
							}
						}
						
						repaint();
						break;
				/**
				 * Rotates selected stein to the left (counterclockwise). 
				 */		
				case KeyEvent.VK_Y:        
						int pos_x = stein.position.x;
						int pos_y = stein.position.y;
						stein.rotateLeft();
						if ((!isWithinBorders(stein) || isCrossing()) && fallingSteins.contains(stein)) {
							stein.rotateRight();
				    	}
						//stein.displayStein();
						if (!fallingSteins.contains(stein)) {
							stein.position.y = pos_y;
							stein.position.x = pos_x;
						}
						repaint();	
						break;
	
				/**
				 * Rotates selected stein to the right (clockwise). 
				 */
				case KeyEvent.VK_C:            
						pos_x = stein.position.x;
						pos_y = stein.position.y;
						stein.rotateRight();
						if ((!isWithinBorders(stein) || isCrossing()) && fallingSteins.contains(stein)) {
							stein.rotateLeft();
						}
						//stein.displayStein();
						if (!fallingSteins.contains(stein)) {
							stein.position.y = pos_y;
							stein.position.x = pos_x;
						}
						repaint();
						break;
				/**
				 * Flips the selected stein horizontally. 
				 */	
				case KeyEvent.VK_Q:
						pos_x = stein.position.x;
						pos_y = stein.position.y;
						stein.flipHorizontal();
						if ((!isWithinBorders(stein) || isCrossing()) && fallingSteins.contains(stein)) {
							stein.flipHorizontal();
						}
						stein.position.y = pos_y;
						stein.position.x = pos_x;
						repaint();
						break;				
				/**
				 * Flips the selected stein vertically. 
				 */	
				case KeyEvent.VK_E:
						pos_x = stein.position.x;
						pos_y = stein.position.y;
						stein.flipVertical();
						if ((!isWithinBorders(stein) || isCrossing()) && fallingSteins.contains(stein)) {
							stein.flipVertical();
						}
						stein.position.y = pos_y;
						stein.position.x = pos_x;
						repaint();
						break;
				/**
				 * Moves the selected stein left. 
				 */			
				case KeyEvent.VK_A:         
					stein.position.x -= squaresz;
					if (!isWithinBorders(stein) || isCrossing()) {
						stein.position.x += squaresz;
					}
					repaint();
					break;
				/**
				 * Moves the selected stein left if Parplatz or Umrisse are not selected. 
				 */	
				case KeyEvent.VK_LEFT:
					if(!park_selected && !umrisse_selected) {				
						stein.position.x -= squaresz;
						if (!isWithinBorders(stein) || isCrossing()) {
							stein.position.x += squaresz;
						}
					}
					repaint();	
					break;	
				/**
				 * Moves the selected stein right. 
				 */			
				case KeyEvent.VK_D:
					stein.position.x += squaresz;
					if (!isWithinBorders(stein) || isCrossing()) {
						stein.position.x -= squaresz;
					}
					repaint();
					break;
				/**
				 * Moves the selected stein right if Parplatz or Umrisse are not selected. 
				 */		
				case KeyEvent.VK_RIGHT:
					if(!park_selected && !umrisse_selected) {
						stein.position.x += squaresz;
						if (!isWithinBorders(stein) || isCrossing()) {
							stein.position.x -= squaresz;
						}
						
						repaint();
					}
					break;
				/**
				 * Movers the selected stein down. 
				 */	
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					if (stein.position.y + squaresz*(k - BottomZeroRows(stein))< Borders[3]+Borders[1]) {
						stein.position.y += squaresz;
						if (!isWithinBorders(stein) || isCrossing()) {
							stein.position.y -= squaresz;
						}
						repaint();
					}
					break;
				/**
				 * Accelerates the selected stein. 
				 */			
				case KeyEvent.VK_SPACE:
					if (isWithinBorders(stein) && stein_acceleration == false) { // 
						stein_acceleration = true;
						Stein_acceleration();
					}												
				repaint();			
				}
			}
		}
		
		else {
			/**
			 * This code is executed if pause is started. 
			 */	
			switch (keyCode) {
			/**
			 * Ends pause. 
			 */	
			case KeyEvent.VK_P:
				pause = false;
				resume();
				break;
			/**
			 * Finishes the game. 
			 */		
			case KeyEvent.VK_Z:
				EndGame();
				break;
			}
		}
	}
	
	/**
	 * Finishes the game. 
	 */
	public void EndGame() {
		exit = true;
		timer.stop();
		//message = "\n Game over";			
		HighscoreFenster.updateScore("resources/spiel2.txt", score);
		setVisible(false);
		mother.close();
	}
	
	/**
	 * Accelerates the chosen stein when SPACE is pressed.
	 */
	public void Stein_acceleration() {
		Timer timer_acc = new Timer(1, new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(stein!=null && stein_acceleration == true) { //stein!=null && stein_acceleration == true &&
					if (!isCrossing() && isWithinBorders(stein)) {
						stein.position.y++;
						repaint(); 
					}
					else {
						stein_acceleration = false;	
						stein.position.y--;
						revalidate();
						repaint(); 
					}
				}			
			}
		});
		timer_acc.setRepeats(true);
		timer_acc.start();
		
		if(stein_acceleration == false) {
			timer_acc.setRepeats(false);
			timer_acc.stop();
		}
	}
	
	/**
	 * Checks if stein is within the borders of the Spielfeld
	 * 
	 * @param stein to be checked
	 * @return true if stein is within the borders
	 */
	private boolean isWithinBorders(Stein st) {	
		if ((st.position.x + squaresz*(k - RightZeroCols(st))<= Borders[2]+Borders[0]) && (st.position.x >= Borders[0])) {						
			if ((st.position.y + squaresz*(k - BottomZeroRows(st))<= Borders[3]+Borders[1]) && (st.position.y >= Borders[1])) {
				return true;									
			}
		}
		return false;		
	}	
	
	/**
	 * Checks if selected stein is crossing the other falling steins
	 * 
	 * @return true if stein is crossing some falling stein
	 */
	private boolean isCrossing() {
		
		for(int item = 0; item<fallingSteins.size(); item++) {
			
			if(item!=selected_stein_index) {	
				
				int left_edge = fallingSteins.get(item).position.x;
				int right_edge = left_edge + squaresz*(k - RightZeroCols(fallingSteins.get(item)));
				int top_edge = fallingSteins.get(item).position.y;
				int bottom_edge = top_edge + squaresz*(k - BottomZeroRows(fallingSteins.get(item)));
				
				int stein_left = stein.position.x;
				int stein_right = stein_left+squaresz*(k - RightZeroCols(stein));
				int stein_top = stein.position.y;
				int stein_bottom = stein_top + squaresz*(k - BottomZeroRows(stein));
				
				if ((stein_left>=left_edge && stein_left<right_edge)
						|| (stein_right>left_edge && stein_right<right_edge)
						|| (left_edge>stein_left && left_edge<stein_right)
						|| (right_edge>stein_left && right_edge<stein_right)
						) {	 
										
					if ((stein_top>=top_edge && stein_top<bottom_edge)
							|| (stein_bottom>top_edge && stein_bottom<bottom_edge)
							|| (top_edge>stein_top && top_edge<stein_bottom)
							|| (bottom_edge>stein_top && bottom_edge<stein_bottom)
							) {													
						return true;
					}
				}		
			}
		}		
		return false;
	}
	
	/**
	 * Calculates the amount of free columns in the stein of size k*k
	 * 
	 * @return right_zero_cols - free columns in the stein of size k*k
	 */
	private int RightZeroCols(Stein st) {
		int right_zero_cols = 0;
		for (int j = k-1; j>0; j--) {
			int zeros_in_current_col = 0;
			for (int i = 0; i<k; i++) {	
				if (st.SteinIn2D()[i][j]==0) {
					zeros_in_current_col +=1;
				}
			}
			if (zeros_in_current_col==k) {
				right_zero_cols +=1;
			}
			else { break;}					
		}
		return right_zero_cols;
	}
	
	/**
	 * Calculates the amount of free rows in the stein of size k*k
	 * 
	 * @return bottom_zero_rows - free rows in the stein of size k*k
	 */
	private int BottomZeroRows(Stein st) {
		int bottom_zero_rows = 0;
		for (int i = k-1; i>0; i--) {
			int zeros_in_current_row = 0;
			for (int j = 0; j<k; j++) {	
				if (st.SteinIn2D()[i][j]==0) {
					zeros_in_current_row +=1;
				}
			}
			if (zeros_in_current_row==k) {
				bottom_zero_rows +=1;
			}
			else { break;}					
		}
		return bottom_zero_rows;
	}
}