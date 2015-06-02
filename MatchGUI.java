import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.*;

/**
 * Defining a GUI to allow the user to add a match to the schedule
 *
 */

public class MatchGUI extends JFrame implements ActionListener {
	
	//GUI instance variables
	private JButton addMatchButton, removeMatchButton;
	private JTextArea mathchViewArea, existingRefereesArea;
	private JComboBox<Integer> weekNumberBox;
	private JComboBox<String> levelBox, locationBox; 
	
	// The MatchSchedule which manages a list of matches and referees
	private MatchSchedule matchSchedule; 
	
	//setting the dimensions associated with the GUI
	private final int INTERFACE_WIDTH = 800;
	private final int INTERFACE_HEIGHT = 675;
	private final int LOCATION_X_AXIS = 400;
	private final int LOCATION_Y_AXIS = 100;
	private final int REFEREE_AREA_HEIGHT = 14;
	private final int MATCHES_AREA_HEIGHT = 8;
	private final int TEXTAREA_WIDTH = 95;
	private final int FONT_SIZE = 14;
	private final int SOUTH_GRID_ROWS = 4;
	private final int SOUTH_GRID_COLUMNS = 2;
	
	/**
	 * Constructor for the MatchGUI
	 * @param schedule a MatchSchedule object which manages a list of matches and referees
	 */
	public MatchGUI(MatchSchedule schedule) {
		
		matchSchedule = schedule;
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setTitle("Java Ball Matches");//setting title for GUI window
		setSize(INTERFACE_WIDTH, INTERFACE_HEIGHT);
		setLocation(LOCATION_X_AXIS, LOCATION_Y_AXIS);
		this.setResizable(false);//setting the window to be non-resizable
		
		//layout GUI components
		layoutNorth();
		layoutCenter();
		layoutSouth();
		
		//calls the update display method which prints a list of the current matches 
		updateMatchDisplay();
	}

	/**
	 * Generates the components in the north panel
	 */
	public void layoutNorth() {
		
		//generate north panel
		JPanel north = new JPanel();
		
		//creating text area which will show the list of referees
		existingRefereesArea = new JTextArea(REFEREE_AREA_HEIGHT, TEXTAREA_WIDTH);
		existingRefereesArea.setFont(new Font("Courier", Font.PLAIN, FONT_SIZE));
		existingRefereesArea.setEditable(false);//setting the area to not be editable
		JScrollPane scrollPane = new JScrollPane(existingRefereesArea);

		//adding scroll pane to the north panel
		north.add(scrollPane);
		north.setBorder(new TitledBorder("Available Referees for Match"));
		add(north, BorderLayout.NORTH);
	}
	
	/**
	 * Generate the components situated in the central JPanel
	 */
	public void layoutCenter() {
		
		//creating central panel
		JPanel center = new JPanel();
		
		//creating text area which will show the list of matches
		mathchViewArea = new JTextArea(MATCHES_AREA_HEIGHT, TEXTAREA_WIDTH);
		mathchViewArea.setFont(new Font("Courier", Font.PLAIN, FONT_SIZE));
		mathchViewArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(mathchViewArea);
				
		//add the scroll pane to the central panel
		center.add(scrollPane);
		center.setBorder(new TitledBorder("Scheduled Matches"));
		add(center, BorderLayout.CENTER);
	}

	/**
	 * Generate the components situated in the south JPanel
	 */
	public void layoutSouth() {
		
		JPanel south = new JPanel(new GridLayout(SOUTH_GRID_ROWS, SOUTH_GRID_COLUMNS));
		
		//create label to sit beside comboBox
		JLabel weekNumberLabel = new JLabel("Week in the Schedule");
		south.add(weekNumberLabel);//add label to panel
		
		//create a ComboBox for which week the match will take place on
		weekNumberBox = new JComboBox<Integer>();
		
		//creating loop to populate comboBox with the weeks in the season
		for(int index = 1; index <= matchSchedule.WEEKS_IN_A_SEASON; index++){
			weekNumberBox.addItem(index);//adds value of index to comboBox
		}
		
		south.add(weekNumberBox);//adding ComboBox to panel
		
		//Create label for match level comboBox
		JLabel levelLabel = new JLabel("Level of Participants");
		south.add(levelLabel);//adding comboBox to panel
		south.setBorder(new TitledBorder("Match Details"));
		
		//Create comboBox for match level
		levelBox = new JComboBox<String>();
		levelBox.addItem("Senior");
		levelBox.addItem("Junior");
		south.add(levelBox);//adding comboBox to panel
		
		//Create a label for location comboBox
		JLabel locationLabel = new JLabel("Match Venue");
		south.add(locationLabel);//adding label to panel
		
		//Create comboBox for different match locations
		locationBox = new JComboBox<String>();
		locationBox.addItem("North");
		locationBox.addItem("Central");
		locationBox.addItem("South");
		south.add(locationBox);

		//creating add and remove match buttons for panel
		addMatchButton = new JButton("Add Match");
		addMatchButton.addActionListener(this);//adding action listener to button
		south.add(addMatchButton);
		removeMatchButton = new JButton("Remove Match");
		removeMatchButton.addActionListener(this);//adding action listener to button
		south.add(removeMatchButton);

		//Add south panel to GUI
		add(south, BorderLayout.SOUTH);	
	}
	
	/**
	 * Method to add a match to the match schedule
	 */
	public void addMatch() {
		if (availableWeek()){
			//Get the match details from comboBoxes
			int matchWeek = (Integer) weekNumberBox.getSelectedItem();
			String matchLevel = (String) levelBox.getSelectedItem();
			String matchLocation = (String) locationBox.getSelectedItem();
			
			//Clear the display of available referees
			existingRefereesArea.setText("");
			
			//Add the headers to the display of referee details
			String heading = "";
			// The first line consists of the "Availability" heading
			heading = String.format("%84s\n", "Availability");
			existingRefereesArea.append(heading);
			// The second line of the heading aligns the names of the referee attributes
			// into distinct columns
			heading = String.format(" %-5s%-13s%-10s%-15s%-14s%-8s%5s%11s%9s\n", 
					"ID", "First Name", "Surname", "Qualification", "Allocations", 
					"Home", "North", "Central", "South");
			existingRefereesArea.append(heading);
			//add a line break between the heading and the data set
			for (int index = 0; index<TEXTAREA_WIDTH; index++){
				heading = "=";
				existingRefereesArea.append(heading);
			}
			existingRefereesArea.append("\n");
			
			//Add the match to the schedule
			if (!matchSchedule.addMatchToSchedule(matchLocation, matchLevel, matchWeek)){
				// However, if there aren't sufficient referees then notify the user
				JOptionPane.showMessageDialog(null, "Insufficient Referees to Schedule Match", 
						"Scheduling Error", JOptionPane.ERROR_MESSAGE);
			}
			else{ // The match was added successfully to the schedule
				// Update the display of scheduled matches to reflect the added match
				updateMatchDisplay();
			}
			
			// Retrieve the list of referees sorted by suitability for the match.
			// For each referee display their attributes in columns
			Referee[] display = matchSchedule.determineSuitableRefs(matchLocation, matchLevel);
			for (Referee ref: display) {
				String existingMatch = ref.toString();
				existingRefereesArea.append(existingMatch);
			}
			
		}
		else{
			JOptionPane.showMessageDialog(null, "Selected week for scheduling is unavailable", 
					"Scheduling conflict", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Method to determine whether the selected week has a matched scheduled
	 * @return available a boolean denoting the availability of the slot for scheduling
	 */
	public boolean availableWeek(){
		boolean available = false;
		// Determine which week the user wishes to schedule/remove a match
		int matchWeek = (Integer) weekNumberBox.getSelectedItem();
		// Check whether a match has been scheduled for the selected week
		if (matchSchedule.getMatchByWeek(matchWeek)==null){
			available = true;
		}
		return available;
	}
	
	/**
	 * Method to remove a match from the existing schedule
	 */
	public void removeMatch(){
		
		// Determine whether a match has been scheduled for the week selected
		if (!availableWeek()){
			//Get the week number the user has selected from comboBox
			int matchWeek = (Integer) weekNumberBox.getSelectedItem();
			//Remove the match from the schedule
			matchSchedule.removeMatchFromSchedule(matchWeek);
			// Update the display of matches to reflect the removal of a match
			updateMatchDisplay();
		}
		else {
			JOptionPane.showMessageDialog(null, "No match has been scheduled for this week", 
					"Scheduling Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Method to update the display of matches scheduled
	 */
	public void updateMatchDisplay(){

		//clear the match text area
		mathchViewArea.setText("");
		
		//adding a heading to the mathchViewArea
		String heading = "";
		mathchViewArea.setText(heading);
		// The first line of the heading reflect the attributes stored about each match
		heading = String.format(" %-10s %-12s %-12s %-20s %-20s\n", 
				"Week", "Level", "Area", "Referee 1", "Referee 2");
		mathchViewArea.append(heading);
		//adding a line break between the heading and the data set
		for (int index = 0; index<TEXTAREA_WIDTH; index++){
			heading = "=";
			mathchViewArea.append(heading);
		}
		mathchViewArea.append("\n");
		
		// Determine which matches exist in the schedule and display their details to
		// the display
		Collection<Match> currentMatches = matchSchedule.getMatchSchedule();
		for (Match fixture: currentMatches) {
			String match = fixture.toString();
			mathchViewArea.append(match);
		}
	}
	
	/**
	 * Method to handle actionEvents associated with JButtons
	 */
	public void actionPerformed(ActionEvent e) {
		//if add button is pressed addMatch method is called
		if (e.getSource() == addMatchButton){
			addMatch();
		}	
		//if remove button is called removeMatch method is called
		else if (e.getSource() == removeMatchButton){
			removeMatch();
		}
	}
}