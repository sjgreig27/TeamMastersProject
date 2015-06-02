/** 
 * Defines an object representing a single JavaBall match
 */
public class Match {
    
	private String location; // The geographical location of the match
	private String matchLevel; // The level allocated to the match i.e. Junior or Senior
	private Referee ref1; // The first referee allocated to the match
	private Referee ref2; // The second referee allocated to the match
	private int weekNo;	// The week the match has been allocated to
	
	/**
	 * Constructor
	 * @param area a String representing the geographical area hosting the match
	 * @param level a String representing the level of the participating teams
	 * @param week an integer representing the scheduled week of the match
	 */
	public Match (String area, String level, int week){
		location = area;
		matchLevel = level;
		weekNo = week;
		ref1 = null; 
		ref2 = null;
		// By default, no referees are allocated to the match
	}
	
	/**
	 * Accessor method for the match venue
	 * @return location a String representing the geographical location of the host stadium
	 */
	public String getLocation (){
		return location;
	}
	
	/**
	 * Accessor method for the level of the match (i.e. Junior or Senior)
	 * @return matchLevel a String representing the level of the participants (Junior/Senior)
	 */
	public String getMatchLevel(){
		return matchLevel;
	}
	
	/**
	 * Accessor method for the week the match is scheduled for
	 * @return weekNo an integer representing the week the match is scheduled for
	 */
	public int getWeekNo (){
		return weekNo;
	}
	
	// Note, there are no mutator methods for the location, matchLevel or weekNo as these cannot be
	// edited once a match has been scheduled.
	
	/**
	 * Accessor method for the first referee allocated to the match
	 * @return ref1 a Referee object representing the first official allocated to a match
	 */
	public Referee getReferee1 (){
		return ref1;
	}
	
	/**
	 * Accessor method for the second referee allocated to the match
	 * @return ref2 a Referee object representing the second official allocated to a match
	 */
	public Referee getReferee2(){
		return ref2;
	}
	
	/**
	 * Mutator method to set the first referee allocated to the match
	 * @param ref a Referee object representing the first official allocated to the match
	 */
	public void setReferee1 (Referee ref){
		ref1 = ref;
	}
	
	/** 	
	 * Mutator method to set the second referee allocated to the match
	 * @param ref a Referee object representing the second official allocated to the match 
	 */
	public void setReferee2 (Referee ref){
		ref2 = ref;
	}
	
	/**
	 * A method to return the details of the match in a format suitable for display to the
	 * user in the graphical interface.
	 * @return matchDescription a String containing the details of the match formatted for
	 * presentation in the user interface
	 */
	public String toString (){
		String matchDescription = "";
		if (ref1!=null&&ref2!=null){
			matchDescription = String.format(" %-10d %-12s %-12s %-20s %-20s\n", weekNo, matchLevel,
					location, ref1.getFullName(), ref2.getFullName());
		}
		return matchDescription;
	}
}