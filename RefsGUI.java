import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.util.*;
import java.io.*;

/**
 * Defines a GUI that displays details of all current referees and contains
 * buttons to search for a referee by name. 
 */
public class RefsGUI extends JFrame implements ActionListener, WindowFocusListener{
	
	/** GUI JButtons */
	private JButton detailsButton, addRefButton, barChartButton, allocateButton, saveExitButton;

	/** GUI JTextFields */
	private JTextField nameField;

	/** Display of existing referees */
	private JTextArea refDisplay;
	
	/** IndividualRefGUI for displaying the details of an individual referee**/
	private IndividualRefGUI individualRefGUI;

	/**MatchSchedule class to manipulate a list of referees and matches**/
	private MatchSchedule schedule;

	/** Names of input text files */
	private final String refereesInFile = "RefereesIn.txt";
	private final String matchAllocs = "MatchAllocs.txt";
	private final String refereesOutFile = "RefereesOut.txt";
	
	/** Dimensions associated with user interface**/
	private final int INTERFACE_WIDTH = 800;
	private final int INTERFACE_HEIGHT = 450;
	private final int TEXTAREA_HEIGHT = 13;
	private final int TEXTAREA_WIDTH = 95;
	private final int TEXTFIELD_WIDTH = 20;
	private final int SQUARE_GRID_LAYOUT_SIZE = 2;
	private final int FONT_SIZE = 14;
	private final int VALID_NUMBER_NAMES =2;
	
	/**
	 * Constructor for Referees user interface
	 */
	public RefsGUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		setTitle("Referees for Allocation to JavaBall Matches");
		setSize(INTERFACE_WIDTH, INTERFACE_HEIGHT);
		// Layout the components of the display
		layoutTop();
		layoutBottom();
		addWindowFocusListener(this);
		schedule = new MatchSchedule();
		// Determine an initial list of referees from file
		initRefereeList();
		// Update the display to reflect addition of officials
		displayRefs();
	}

	/**
	 * Reads in from Referees file and add to list of available referees
	 */

	public void initRefereeList(){
		FileReader reader = null;
		Scanner in = null;
		try{	
			try {
				reader = new FileReader(refereesInFile);
				in = new Scanner(reader);
				// Read the lines of the file individually
				while (in.hasNextLine()){
					// while there are more lines
					String line = in.nextLine();
					// each line of the file corresponds to one referee
					schedule.addReferee(line.trim());
				}
			}	
			finally{
				if (reader!=null)
					reader.close();
				if (in!=null)
					in.close();
			}	
		}
		catch (IOException e) {
			// In the event that the file is inaccessible
			JOptionPane.showMessageDialog(null, "Error Accessing Referees Input File", 
					"File Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Method to display the available referees for match allocations
	 */
	public void displayRefs() {
		// Blank the display initially
		String heading = "";
		refDisplay.setText(heading);
		
		// Generate headings for the columns displaying the referees attributes
		// The first line of headings simply contains "Availability"
		heading = String.format("%84s\n", "Availability");
		refDisplay.append(heading);
		// The second line consists of the names of the attributes stored
		// about each referee
		heading = String.format(" %-5s%-13s%-10s%-15s%-14s%-8s%5s%11s%9s\n", 
				"ID", "First Name", "Surname", "Qualification", "Allocations", 
				"Home", "North", "Central", "South");
		refDisplay.append(heading);
		// To distinguish the headings, the third line of the heading is a border
		for (int index = 0; index<TEXTAREA_WIDTH; index++){
			heading = "=";
			refDisplay.append(heading);
		}
		refDisplay.append("\n");
		
		// A collection of referee objects are returned for display to the user in
		// order of ID
		Collection<Referee> currentRefs = schedule.sortRefsByID();
		for (Referee official: currentRefs) {
			// the toString() method display the referee's attributes in distinct columns
			String ref = official.toString();
			this.refDisplay.append(ref);
		}
	}
	
	/**
	 * Method to clear the search field and update the display of referees
	 */
	public void updateDisplay() {
		//clearing JTextFields
		nameField.setText("");
		displayRefs();
	}

	/**
	 * Method to display a JTextArea in the top of the window
	 */
	public void layoutTop() {
		JPanel top = new JPanel();
		top.setBorder(new TitledBorder("Referee Details"));
		// Create a JTextArea for displaying the referees details to the users
		refDisplay = new JTextArea(TEXTAREA_HEIGHT,TEXTAREA_WIDTH);
		refDisplay.setFont(new Font("Courier", Font.PLAIN, FONT_SIZE));
		refDisplay.setEditable(false);
		// The JTextArea is added to a JScrollPane so that all the referee
		// details are visible to the user
		JScrollPane scrollPane = new JScrollPane(refDisplay);
		add(scrollPane, BorderLayout.CENTER);
		top.add(scrollPane);
		add(top, BorderLayout.CENTER);
	}

	/**
	 * Method to add labels, text fields and buttons to the bottom of GUI
	 */
	public void layoutBottom() {
		// Instantiate panel for bottom of display
		JPanel bottom = new JPanel(new GridLayout(SQUARE_GRID_LAYOUT_SIZE,SQUARE_GRID_LAYOUT_SIZE));

		// The details panel allows for users to manage the list of referees
		JPanel detailsPanel = new JPanel();
		detailsPanel.setBorder(new TitledBorder("Manage Referees"));
		
		JLabel nameLabel = new JLabel("Enter Referee's Name:");
		detailsPanel.add(nameLabel, BorderLayout.WEST);
		// The nameField allows the user to search for an existing referee
		// Or provide a name for the addition of a new referee
		nameField = new JTextField("", TEXTFIELD_WIDTH);
		detailsPanel.add(nameField, BorderLayout.WEST);
		
		detailsButton = new JButton("View Referee's Details");
		detailsButton.addActionListener(this);
		detailsPanel.add(detailsButton, BorderLayout.CENTER);
		
		addRefButton = new JButton("Add Named Referee");
		addRefButton.addActionListener(this);
		detailsPanel.add(addRefButton, BorderLayout.EAST);
		
		bottom.add(detailsPanel);

		// Add buttons to lower part of bottom panel

		JPanel functionsPanel = new JPanel();
		functionsPanel.setBorder(new TitledBorder("Program Functions"));
		
		// JButton to allow the user to generate a bar chart of the number of
		// matches allocated to each official
		barChartButton = new JButton("View Referee Allocation Chart");
		barChartButton.addActionListener(this);
		functionsPanel.add(barChartButton);
		
		// JButton to allow the user to schedule and allocate referee's to matches
		allocateButton = new JButton("Allocate Referees to Matches");
		allocateButton.addActionListener(this);
		functionsPanel.add(allocateButton);

		saveExitButton = new JButton("Save and Exit");
		saveExitButton.addActionListener(this);
		functionsPanel.add(saveExitButton);

		bottom.add(functionsPanel);

		add(bottom, BorderLayout.SOUTH);
	}

	/**
	 * Method to generate the user interface for a new referee providing the
	 * name provided by the user is valid
	 * */
	public void processAddingRef() {
		// Retrieve the name of the new referee from the JTextField
		String name = nameField.getText();
		// Ensure the name provided is valid
		if (!testInput(name)){
			// In the event of an invalid name, the user is notified
			JOptionPane.showMessageDialog(null, "Please enter a valid name");
		}
		// Ensure that the maximum number of referees has not been exceeded
		else if (schedule.getNumberOfReferees()>=schedule.MAXIMUM_NUMBER_OF_OFFICIALS) {
			// Notify the user that no more referees may be added
			JOptionPane.showMessageDialog(null, "The max number of referees has been reached");
		}
		// Ensure that the name does not already exist in the system
		else if (schedule.getRefereeByName(name)!=null){
			// Notify the user if the name is already associated with a referee
			JOptionPane.showMessageDialog(null, "This referee is already stored in the system");
		}
		else{// the name provided is valid for a new referee
			// generate a new user interface to allow the user to provide additional details
			individualRefGUI = new IndividualRefGUI(name, schedule);
			individualRefGUI.setVisible(true);
		}	
	}

	/**
	 * Method to generate an IndividualRefGUI to display the details of the selected
	 * referee to the user.
	 * */
	public void processViewRef() {
		// Retrieve the name of the referee
		String name = nameField.getText().trim();
		// Retrieve the referee object corresponding to the name searched for
		Referee ref = schedule.getRefereeByName(name);
		// Ensure the name searched for is valid
		if (!testInput(name)) {
			JOptionPane.showMessageDialog(null, "Please enter a valid name");
		}
		// If the name was not found within the existing referees
		else if (ref==null){
			JOptionPane.showMessageDialog(null, "No such referee exists. Please add a new referee.");
		}
		else{// generate user interface to display the details of the existing referee
			individualRefGUI = new IndividualRefGUI(ref, schedule);
			individualRefGUI.setVisible(true);
		}
	}

	/**
	 * Method to generate a Bar chart of the number of match allocations of referees
	 */
	public void processBarChart() {
			BarChart barChart = new BarChart(schedule);
			barChart.setVisible(true);
	}
	
	/**
	 * Method to create a MatchGUI object for adding matches to the schedule
	 */
	public void processAllocateRef() {    
		MatchGUI matchGUI = new MatchGUI(schedule);
		matchGUI.setVisible(true);
	}
	
	/**
	 * Method to generate an output file for the matches and referees, respectively.
	 * Upon completion, the program terminates.
	 */
	public void processSaveAndClose() {
	    FileWriter writeRefs = null;
	    FileWriter writeMatches = null;
		try{
			try{
				writeMatches = new FileWriter (matchAllocs);
				writeRefs = new FileWriter (refereesOutFile);
				// The matchSchedule class generates the output reports
				// as a String
				writeMatches.write(schedule.matchReportWriter());
				writeRefs.write(schedule.refereeReportWriter());
			}	
			finally{// Close FileWriter objects if open
				if (writeMatches!=null){
					writeMatches.close();
				}
				if (writeRefs!=null){
					writeRefs.close();
				}
				System.exit(0);
				// Terminate the program
			}
		}	
		catch (IOException e){
			JOptionPane.showMessageDialog(null, "Could not write to output file", 
					"Save File Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Tests whether input is a valid name. ie two consecutive strings separated by only 1 space.
	 * Assuming that names in Hibernia do not contain accents, both names can only consist of
	 * upper and lower case letters or hyphens.
	 * */
	public boolean testInput(String name) {
		boolean test = true;
		String[] fullName = name.split(" +");
		// Ensure the name consists of a first and surname
		if (fullName.length!=VALID_NUMBER_NAMES){
			test = false;
		}	
		for (String eachName: fullName){
			// For each name, ensure that the characters are valid
			if (!eachName.matches("[a-zA-Z-]+")){
				test= false;
			}
		}
		return test;
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		
		if (ae.getSource()== addRefButton){
			processAddingRef();
			//if add button is pressed Individual RefGUI is shown
		}

		else if (ae.getSource()== detailsButton) {
			processViewRef();
			// if the details button is pressed Individual RefGUI displayed
		}

		if (ae.getSource()== barChartButton){
			processBarChart();
			//if add button is pressed BarChart is shown
		}
		
		if (ae.getSource()== allocateButton){
			processAllocateRef();
			//if add button is pressed MatchGUI is shown
		}
		
		if (ae.getSource()== saveExitButton){
			processSaveAndClose();
			//if save and exit button is pressed MatchGUI is shown
		}
	}

	/**
	 * Method to update the referees displayed when the window gains
	 * focus.
	 */
	public void windowGainedFocus(WindowEvent e) {
		this.updateDisplay();
	}

	/**
	 * Method required to implement WindowFocusListener interface
	 */
	public void windowLostFocus(WindowEvent e) {
		// No action is to be performed in the event of the window losing focus
	}

}