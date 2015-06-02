import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

/**
 * Defines a GUI that displays the details of an individual
 * referee. Additionally, allows the user to update or remove the
 * details of the referee from the system.
 */
public class IndividualRefGUI extends JFrame implements ActionListener {
	
	/** GUI JButtons */
	private JButton saveButton, removeButton;
	
	/** GUI JTextFields */
	private JTextField nameField, idField, matchAllocationField;
	
	/** GUI JComboBoxes */
	private JComboBox<String> qualificationBox, homeLocationBox;

	/**GUI JCheckBox */
	private JCheckBox northCB, centralCB, southCB;
	
	/**GUI Component Dimension*/
	private final int INTERFACE_WIDTH = 400;
	private final int INTERFACE_HEIGHT = 300;
	private final int LOCATION_X_AXIS = 500;
	private final int LOCATION_Y_AXIS = 350;
	private final int GRID_LAYOUT_NORTH_ROWS = 5;
	private final int GRID_LAYOUT_NORTH_COLUMNS = 2;
	private final int GRID_LAYOUT_CENTRAL_ROWS = 1;
	private final int GRID_LAYOUT_CENTRAL_COLUMNS = 6;
	private final int GRID_LAYOUT_SOUTH_ROWS = 1;
	private final int GRID_LAYOUT_SOUTH_COLUMNS = 2;
	private final int FONT_SIZE = 14;
	private final int TEXTFEILD_WIDTH = 35;
	
	/**Controller class reference*/
	private MatchSchedule matchSchedule;
	
	/**
	 * Constructor for IndividualRefGUI class when the user wishes to add a new referee.
	 * @param refName a String representing the full name of the new referee
	 * @param schedule a MatchSchedule object containing a list of the existing referees
	 */
	public IndividualRefGUI(String refName, MatchSchedule schedule) {

		this.displayIndividualRefGUI();
		matchSchedule = schedule;
		updateDisplayForNewRef(refName);
	}
	
	/**
	 * Constructor for generating an instance of IndividualRefGUI class to edit an existing referee object
	 * @param ref a Referee object representing the referee whose details are to be edited
	 * @param schedule a MatchSchedule object which manages the list of all referee objects
	 */
	public IndividualRefGUI(Referee ref, MatchSchedule schedule) {

		this.displayIndividualRefGUI();
		matchSchedule = schedule;
		updateDisplayForExistingRef(ref);
		// the number of match allocations cannot be edited for an existing referee
		matchAllocationField.setEnabled(false);
	}
	
	/**
	 * Method to display the components of the user interface
	 */
	public void displayIndividualRefGUI(){
		
		this.setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(INTERFACE_WIDTH, INTERFACE_HEIGHT);
        setLocation(LOCATION_X_AXIS, LOCATION_Y_AXIS);

        //layout GUI components
        layoutNorth();
		layoutCenter();
		layoutSouth();
	}

	/**
	 * Updates the user display to reflect the attributes of the existing referee
	 * @param ref a Referee object representing the existing referee
	 */
	public void updateDisplayForExistingRef(Referee ref) {
		
		// The name of the referee is displayed in the JFrame title
		setTitle(ref.getFullName());
		// Display the value of each attribute in the appropriate component
		nameField.setText(String.format("%s", ref.getFullName()));
		idField.setText(ref.getRefID());
		matchAllocationField.setText(Integer.toString(ref.getMatchAllocations()));
		qualificationBox.setSelectedItem(ref.getQualification());
		homeLocationBox.setSelectedItem(ref.getHomeLocation());
		northCB.setSelected(ref.isAvailableNorth());
		centralCB.setSelected(ref.isAvailableCentral());
		southCB.setSelected(ref.isAvailableSouth());
	}

	/**
	 * Updates display to allow for user to enter details of new referee
	 * @param name a String representing the name of the new official
	 */
	public void updateDisplayForNewRef(String name) {
		
		// The proposed name supplied by the user is displayed in the title
		// of the JFrame
		setTitle(name);
		nameField.setText(name);
		// The ID is generated based upon the name supplied by the user
		idField.setText(matchSchedule.generateRefID(name));
		matchAllocationField.setText("");
		// By default, the availability of the referee is set to false
		// for each geographical area
		northCB.setSelected(false);
		centralCB.setSelected(false);
		southCB.setSelected(false);
	}
	
	/**
	 * Method to display the components of the GUI in the north JPanel
	 */
	public void layoutNorth(){
		
		// Generate a new JPanel with a grid layout
		JPanel northPanel = new JPanel(new GridLayout(GRID_LAYOUT_NORTH_ROWS,GRID_LAYOUT_NORTH_COLUMNS));
		northPanel.setBorder(new TitledBorder("Referee Details"));
		
		// Generate a label and TextField for the referee's name
		JLabel nameLabel = new JLabel("Name: ");
		nameField = new JTextField(null ,TEXTFEILD_WIDTH);
		nameField.setFont(new Font("Courier", Font.PLAIN, FONT_SIZE));
		nameField.setEditable(false);
		northPanel.add(nameLabel);
		northPanel.add(nameField);
		
		//Generate a label and JTextField for the id of the referee
		JLabel idLabel = new JLabel("ID: ");
		idField = new JTextField(null ,TEXTFEILD_WIDTH);
		idField.setFont(new Font("Courier", Font.PLAIN, FONT_SIZE));
		idField.setEditable(false);
		northPanel.add(idLabel);
		northPanel.add(idField);
		
		//Generate a label and JTextField for the match allocations of the referee
		JLabel matchLabel = new JLabel("Match Allocations: ");
		matchAllocationField = new JTextField(null ,TEXTFEILD_WIDTH);
		matchAllocationField.setFont(new Font("Courier", Font.PLAIN, FONT_SIZE));
		northPanel.add(matchLabel);
		northPanel.add(matchAllocationField);
		
		// The qualification and home location of the referee are displayed in combo boxes
		JLabel qualificationLabel = new JLabel("Qualification: ");
		qualificationBox = new JComboBox<String>();
		JLabel homeLocationLabel = new JLabel("Home Location: ");
		homeLocationBox = new JComboBox<String>();
		
		//Populate the qualification comboBox
		String [] qualifications = {"NJB1", "NJB2", "NJB3", "NJB4", "IJB1", "IJB2", "IJB3", "IJB4"};
		for (String level: qualifications){
			qualificationBox.addItem(level);
		}
		
		//Populate the homeLocation comboBox
		String [] geographicalAreas = {"North", "Central", "South"};
		for (String location: geographicalAreas){
			homeLocationBox.addItem(location);
		}
		
		//Add the labels and comboBoxes to the JPanel
		northPanel.add(qualificationLabel);
		northPanel.add(qualificationBox);
		northPanel.add(homeLocationLabel);
		northPanel.add(homeLocationBox);
		
		//Finally add the panel to the JFrame
		add(northPanel, BorderLayout.NORTH);
	}
	
	/**
	 * Method to display the components of the GUI in the central JPanel
	 */
	public void layoutCenter(){
		
		JPanel centerPanel = new JPanel(new GridLayout(GRID_LAYOUT_CENTRAL_ROWS,GRID_LAYOUT_CENTRAL_COLUMNS));
		centerPanel.setBorder(new TitledBorder("Match Availability"));
		
		//Generate JCheckBoxes and associated labels for referee availability
		JLabel northLabel = new JLabel("North");
		northCB = new JCheckBox();
		centerPanel.add(northLabel);
		centerPanel.add(northCB);
		
		JLabel centralLabel = new JLabel("Central");
		centralCB = new JCheckBox();
		centerPanel.add(centralLabel);
		centerPanel.add(centralCB);
		
		JLabel southLabel = new JLabel("South");
		southCB = new JCheckBox();
		centerPanel.add(southLabel);
		centerPanel.add(southCB);
	
		//Add the central panel to GUI
		add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Method to display the components of the GUI in the south JPanel
	 */
	public void layoutSouth(){
		
		JPanel southPanel = new JPanel(new GridLayout(GRID_LAYOUT_SOUTH_ROWS,GRID_LAYOUT_SOUTH_COLUMNS));
		
		// Generate a button to save the details and to remove the referee
		saveButton = new JButton("Save Referee Details");
		saveButton.addActionListener(this);
		removeButton = new JButton("Remove Referee");
		removeButton.addActionListener(this);
		
		//Add the buttons to panel
		southPanel.add(saveButton);
		southPanel.add(removeButton);
		
		//Add the south panel to GUI
		add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Processes the saving of a referees details or creates a new instance of a referee object
	 * if the referee does not currently exist in the system
	 */
	public void processSaveReferee() {
		
		//Retrieve the details to be saved from the interface components
		String uniqueID = idField.getText().trim();
		String refName = nameField.getText().trim();
		int allocation = Integer.parseInt(matchAllocationField.getText().trim());
		String refQualification = (String) qualificationBox.getSelectedItem();
		String refHome = (String) homeLocationBox.getSelectedItem();
		boolean isAvailableNorth = northCB.isSelected();
		boolean isAvailableCentral = centralCB.isSelected();
		boolean isAvailableSouth = southCB.isSelected();
		
		// Split the name into first and surnames
		String [] names = refName.split(" +");
		int nameIndex = 0;
		String refFirstName = names[nameIndex++];
		String refSurname = names[nameIndex++];
		
		// Determine whether this is the addition of a new referee
		// or an update to an existing referee 
		if (matchSchedule.getRefereeByID(uniqueID)==null){
			// Add the referee to the list of available referees
			matchSchedule.addReferee(uniqueID, refFirstName, refSurname, refQualification, allocation, 
					refHome, isAvailableNorth, isAvailableCentral, isAvailableSouth);
		}
		else {
			// Edit the details of the existing referee
			matchSchedule.editReferee(uniqueID, refQualification, refHome, isAvailableNorth, 
					isAvailableCentral, isAvailableSouth);
		}
		// Upon completion, remove the display
		this.dispose();
	}

	/**
	 * Removes a referee from the list of available referees stored in the system
	 */
	public void processRemoveReferee() {
		
		// Retrieve the id of the official from the TextField
		String uniqueID = idField.getText().trim();
		// Determine if the id refers to an existing referee
		if (matchSchedule.getRefereeByID(uniqueID)!=null){
			// Retrieve the name of the existing referee
			Referee existingRef = matchSchedule.getRefereeByID(idField.getText().trim());
			String existingName = existingRef.getFullName();
			// Ensure that the name associated with the id is correct
			if (nameField.getText().trim().equals(existingName)){
				matchSchedule.removeReferee(uniqueID);
			}
			// else - a name may not be correctly associated with a referee ID
			// if the user attempts to create two referees with identical initials
			// simultaneously. In this instance the referee object with a matching
			// ID should not be removed.
		}
		else{
			JOptionPane.showMessageDialog(null, "No record of referee found", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		// Dispose of the window upon completion
		this.dispose();
	}
	
	/**
	 * Method to check all fields of user data entry to ensure that details for a new referee
	 * or changes to an existing referee adhere to constraints
	 * @return valid a boolean denoting whether the data provided by the user is valid
	 */
	public boolean validData(){
		boolean valid = true;
		
		// Ensure the number of match allocations entered is valid
		int allocation = 0;
		try{
			allocation = Integer.parseInt(matchAllocationField.getText().trim());
			if (allocation<0){
				// The number of match allocations cannot be less than zero
				throw new NumberFormatException();
			}
		}
		catch(NumberFormatException nfx){
			// Notify the user that the number of match allocations is invalid
			JOptionPane.showMessageDialog(null, "Please enter the number of match allocations",
					"Invalid Allocations", JOptionPane.ERROR_MESSAGE);
			valid = false;
			// Reset the JTextField to allow for the user to make another attempt
			matchAllocationField.setText("");
			matchAllocationField.requestFocus();
		}
		
		// Ensure that the referee is available to referee in their home geographical area 
		String refHome = (String) homeLocationBox.getSelectedItem();
		boolean availableHome = true;
		// Check that the check box corresponding to the home location is selected
		if (refHome.equals("North")&&!northCB.isSelected()){
			// If not, then select the corresponding check box for the user
			northCB.setSelected(true);
			availableHome = false;
		}
		else if (refHome.equals("Central")&&!centralCB.isSelected()){
			centralCB.setSelected(true);
			availableHome = false;
		}
		else if (refHome.equals("South")&&!southCB.isSelected()){
			southCB.setSelected(true);
			availableHome = false;
		}
		
		//If the availability of the referee does not match with their home location
		if (!availableHome){
			// Notify the user that the referee must be available to officiate in their home region
			JOptionPane.showMessageDialog(null, "Referees must be available to officiate in home location",
					"Invalid Availability", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		
		// Ensure that the maximum number of officials hasn't been exceeded
		if (matchSchedule.getNumberOfReferees()>=matchSchedule.MAXIMUM_NUMBER_OF_OFFICIALS){
			JOptionPane.showMessageDialog(null, "Maximum number of Referees reached",
					"Error", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		
		// Ensure that the ID generated for the referee remains unique
		if (matchSchedule.getRefereeByID(idField.getText().trim())!=null){
			Referee existingRef = matchSchedule.getRefereeByID(idField.getText().trim());
			// Check that name associated with the referee ID matches the name stored in the JTextField
			String existingName = existingRef.getFullName();
			if (!nameField.getText().trim().equals(existingName)){
				JOptionPane.showMessageDialog(null, "The new referee's ID is no longer unique",
						"ID validation error", JOptionPane.ERROR_MESSAGE);
				valid = false;
			}
		}
		
		return valid;
	}

	/**
	 * Process button clicks.
	 * @param ae the ActionEvent
	 */
	public void actionPerformed(ActionEvent ae) {
		
		//if the save button is pressed the processSaveReferee method is called
		if (ae.getSource()== saveButton&&validData()){
			processSaveReferee();
		}
		
		//if the save button is pressed the processRemoveReferee method is called
		if (ae.getSource()== removeButton){
			processRemoveReferee();
		}
	}
}