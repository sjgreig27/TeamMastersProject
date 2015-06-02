/**
 * Defines an object defining a single instance of a referee
 * 
 */
public class Referee{

	private String refID; // unique identifier for each referee
	private String refFirstName;	// first name for the referee
	private String refSurname;	// the surname of the referee
	private String qualification; // the level of qualification of the official 
	private int matchAllocations; // the number of matches the referee has been allocated in the season
	private String homeLocation; // the geographical area the referee currently resides in
	private boolean isAvailableNorth; // the availability of the referee to officiate matches in the northern region
	private boolean isAvailableCentral; // the availability of the referee to officiate matches in the central region
	private boolean isAvailableSouth; // the availability of the referee to officiate matches in the southern region
	
	/**
	 * Constructor for referees added from the user interface 
	 * @param refID a String representing the unique ID of he referee
	 * @param refFirstName a String containing the first name of the official
	 * @param refSurname a String containing the surname of the referee
	 * @param qualification a String containing the level of qualification attained by the referee
	 * @param matchAllocations an int containing the number of matches the referee has been allocated
	 * @param homeLocation a String representing the geographical area the referee is based in
	 * @param isAvailableNorth a Boolean representing the availability of the referee to officiate matches
	 * in the northern geographical area
	 * @param isAvailableCentral a Boolean representing the availability of the referee to officiate matches
	 * in the central geographical area
	 * @param isAvailableSouth a Boolean representing the availability of the referee to officiate matches
	 * in the southern geographical area
	 */
	public Referee(String refID, String refFirstName, String refSurname,
			String qualification, int matchAllocations, String homeLocation,
			boolean isAvailableNorth, boolean isAvailableCentral,
			boolean isAvailableSouth) {
		this.refID = refID;
		this.refFirstName = refFirstName;
		this.refSurname = refSurname;
		this.qualification = qualification;
		this.matchAllocations = matchAllocations;
		this.homeLocation = homeLocation;
		this.isAvailableNorth = isAvailableNorth;
		this.isAvailableCentral = isAvailableCentral;
		this.isAvailableSouth = isAvailableSouth;
	}
	
	/**
	 * Constructor for referees added from file
	 * @param refID a String representing the unique ID of he referee
	 * @param refFirstName a String containing the first name of the official
	 * @param refSurname a String containing the surname of the referee
	 * @param qualification a String containing the level of qualification attained by the referee
	 * @param matchAllocations an int containing the number of matches the referee has been allocated
	 * @param homeLocation a String representing the geographical area the referee is based in
	 * @param isAvailableNorth a char representing the availability of the referee to officiate matches
	 * in the northern geographical area
	 * @param isAvailableCentral  a char representing the availability of the referee to officiate matches
	 * in the central geographical area
	 * @param isAvailableSouth a char representing the availability of the referee to officiate matches
	 * in the southern geographical area
	 */
	public Referee(String refID, String refFirstName, String refSurname,
			String qualification, int matchAllocations, String homeLocation,
			char isAvailableNorth, char isAvailableCentral,
			char isAvailableSouth){
		this.refID = refID;
		this.refFirstName = refFirstName;
		this.refSurname = refSurname;
		this.qualification = qualification;
		this.matchAllocations = matchAllocations;
		this.homeLocation = homeLocation;
		this.isAvailableNorth = this.convertCharAvailability(isAvailableNorth);
		this.isAvailableCentral = this.convertCharAvailability(isAvailableCentral);
		this.isAvailableSouth = this.convertCharAvailability(isAvailableSouth);
	}
	
	/**
	 * Accessor method for unique referee ID
	 * @return refID a String containing the unique identifier
	 */
	public String getRefID() {
		return refID;
	}
	
	// Note no mutator method for refID as the unique identifier cannot be changed
	// once the referee object has been created.
	
	/**
	 * Accessor method for the first name of the official
	 * @return a String containing the first name of the referee
	 */
	public String getRefFirstName() {
		return refFirstName;
	}
	
	// Note no mutator method for firstName as the name of the referee cannot be changed
	// once the referee object has been created.
	
	/**
	 * Accessor method for the surname of the official
	 * @return a String containing the surname of the referee
	 */
	public String getRefSurname() {
		return refSurname;
	}
	
	// Note no mutator method for surname as the referees do not change
	// their name for professional purposes regardless of marriage etc.
	
	/**
	 * Accessor method to return the full name of the official
	 * @return name a String containing the full name of the official
	 */
	public String getFullName(){
		String name = refFirstName+" "+refSurname;
		return name;
	}
	
	/**
	 * Accessor method for the referee's qualification
	 * @return a String representing the level of qualification achieved by the official
	 */
	public String getQualification() {
		return qualification;
	}
	
	/**
	 * Mutator method for the qualification of the referee
	 * @param qualification a String containing the new qualification level of the official
	 */
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	
	/**
	 * Accessor method for the number of matches the referee has been allocated
	 * @return matchAllocations an int containing the number of match allocations associated with the referee
	 */
	public int getMatchAllocations() {
		return matchAllocations;
	}
	
	/**
	 * Mutator method for the number of matches allocated to the official
	 * @param matchAllocations an int containing the updated number of matches allocated
	 * to the referee
	 */
	public void setMatchAllocations(int matchAllocations) {
		this.matchAllocations = matchAllocations;
	}
	
	/**
	 * Accessor method for the home location of the referee
	 * @return homeLocation a String representing the geographical location of the
	 * referee's home
	 */
	public String getHomeLocation() {
		return homeLocation;
	}
	
	/**
	 * Mutator method for the home location of the referee
	 * @param homeLocation a String representing the new home location of the referee
	 */
	public void setHomeLocation(String homeLocation) {
		this.homeLocation = homeLocation;
	}
	
	/**
	 * Accessor method for the availability of the official to referee matches
	 * in the northern area.
	 * @return isAvailableNorth a boolean representing availability to referee
	 * matches in the northern area.
	 */
	public boolean isAvailableNorth() {
		return isAvailableNorth;
	}
	
	/**
	 * Mutator method for the availability of the referee in the northern area
	 * @param isAvailableNorth a boolean representing the new availability of the referee
	 * in the northern area.
	 */
	public void setAvailableNorth(boolean isAvailableNorth) {
		this.isAvailableNorth = isAvailableNorth;
	}
	
	/**
	 * Accessor method for the availability of the referee in the central area
	 * @return isAvailableCentral a boolean representing the availability of the
	 * referee in the central area.
	 */
	public boolean isAvailableCentral() {
		return isAvailableCentral;
	}
	
	/**
	 * Mutator method for the availability of the referee in the central area
	 * @param isAvailableCentral a boolean representing the new availability of the referee
	 * in the central area.
	 */
	public void setAvailableCentral(boolean isAvailableCentral) {
		this.isAvailableCentral = isAvailableCentral;
	}
	
	/**
	 * Accessor method for the availability of the referee in the central area
	 * @return a boolean representing the availability of the
	 * referee in the southern area.
	 */
	public boolean isAvailableSouth() {
		return isAvailableSouth;
	}
	
	/**
	 * Mutator method for the availability of the referee in the southern area
	 * @param isAvailableSouth a boolean representing the new availability of the referee
	 * in the southern area.
	 */
	public void setAvailableSouth(boolean isAvailableSouth) {
		this.isAvailableSouth = isAvailableSouth;
	}
	
	/**
	 * Method to return whether or not the referee is suitably qualified to referee
	 * matches at the senior level
	 * @return canRefSeniors a boolean representing whether the official is eligible
	 * to referee senior matches
	 */
	public boolean canRefereeSeniors (){
		boolean canRefSeniors = false;
		char qualificationLevel = qualification.charAt(qualification.length()-1);
		if (qualificationLevel>'1'){
			canRefSeniors = true;
		}
		return canRefSeniors;
	}
	
	/**
	 * Method to convert the availability of the referee from a boolean to a String
	 * @param available a boolean representing the availability of the referee
	 * @return YorN a String containing the availability of the referee as a
	 * "Y" for yes or "N" for no.
	 */
	public String convertBooleanAvailability (boolean available){
		String YorN = "N";
		if (available){
			YorN = "Y";
		}
		return YorN;
	}
	
	/**
	 * Method to convert the availability of the referee from a char to a boolean
	 * @param available a char representing the availability of the referee as 'Y'
	 * for yes or 'N' for no.
	 * @return availability a boolean representing the availability of the referee
	 */
	public boolean convertCharAvailability (char available){
		boolean availability = false;
		if (available=='Y'){
			availability = true;
		}
		return availability;
	}
	
	/**
	 * Method to convert the referee object to a String object
	 * @return refDescription a String containing the values of the instance variables for the referee object
	 */
	public String toString (){
		String refDescription = String.format(" %-7s%-12s%-14s%-13s%4s%6s%-7s%5s%10s%10s\n", refID, refFirstName,
				refSurname, qualification, matchAllocations,"", homeLocation, convertBooleanAvailability(this.isAvailableNorth),
				convertBooleanAvailability(this.isAvailableCentral), convertBooleanAvailability(this.isAvailableSouth));
		return refDescription;
	}
}	
	