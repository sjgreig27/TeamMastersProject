import java.util.*;

/**
 * Maintains a list of Referee and Match objects for the 
 * scheduling of matches.
 */
public class MatchSchedule{
	
	private List<Match> matchSchedule; // a list of matches in the season
	private List<Referee> officials; // a list of all potential referees for matches
	
	public final int MAXIMUM_NUMBER_OF_OFFICIALS = 12; // the maximum number of officials that can be stored
	private final int REFEREES_PER_MATCH = 2; // the number of officials required to schedule a match
	public final int WEEKS_IN_A_SEASON = 52; // the number of available weeks in a season

	/**
	 * Constructor for the MatchSchedule
	 */
	public MatchSchedule (){
		matchSchedule = new ArrayList<Match>();
		officials = new ArrayList<Referee>();
	}

	/**
	 * Accessor method for the number of matches currently scheduled
	 * @return numberOfMatches an integer containing the number of matches scheduled
	 */
	public int getNumberOfMatches (){
		int numberOfMatches = matchSchedule.size();
		return numberOfMatches;
	}

	/**
	 * Accessor method for the number of referees currently in the system
	 * @return officials.size() an integer containing the number of stored referees
	 */
	public int getNumberOfReferees(){
		return officials.size();
	}
	
	/**
	 * Accessor method for the matches allocated to the match schedule
	 * @return matchSchedule an ArrayList of matches allocated to the schedule
	 */
	public List<Match> getMatchSchedule(){
		return matchSchedule;
	}

	/**
	 * Method to add a referee to the list of referees from a file input.
	 * @param details a String that contains all the information for one referee in the format of the input file
	 * @return refereeAdded a boolean denoting if the referee was added successfully
	 */
	public boolean addReferee(String details){
		boolean refereeAdded = false;
		if (this.getNumberOfReferees()<this.MAXIMUM_NUMBER_OF_OFFICIALS){
			//Tokenise the details for the official from the String input
			//Assumes the format of the input file is correct
			String [] tokens = details.trim().split(" +");
			int tokenIndex = 0;
			// The details of the referee are stored in a specified format in the string
			String refID = tokens[tokenIndex++];
			String refFirstName = tokens[tokenIndex++];
			String refSurname = tokens[tokenIndex++];
			String refQualification = tokens[tokenIndex++];
			// the number of matches allocated to the official must be parsed
			// to an integer
			int refAllocations = Integer.parseInt(tokens[tokenIndex++].trim());
			String refHomeArea = tokens[tokenIndex++];
			// the availability of the referee is stored as a String of chars
			// which are not seperated by whitespace (e.g. "YNN")
			String availability = tokens[tokenIndex++];
			int availabilityIndex = 0;
			char isAvailableNorth = availability.charAt(availabilityIndex++);
			char isAvailableCentral = availability.charAt(availabilityIndex++);
			char isAvailableSouth = availability.charAt(availabilityIndex++);
			// generate a new referee object and add it to the list of referees
			Referee ref = new Referee(refID, refFirstName, refSurname, refQualification,
					refAllocations, refHomeArea, isAvailableNorth, isAvailableCentral,
					isAvailableSouth);
			officials.add(ref);
			refereeAdded = true;
		}
		return refereeAdded;
	}

	/**
	 * Method to add a referee to the list of referees from the GUI.
	 * @param refID a String containing the ID of the referee
	 * @param refFirstName a String containing the first name of the referee
	 * @param refSurname a String containing the surname of the referee
	 * @param refQualification a String containing the qualification of the referee
	 * @param refAllocations an integer containing the number of matches a referee has already been allocated
	 * @param refHomeArea a String containing the geographical location of the referee's home
	 * @param isAvailableNorth a boolean representing the availability of the official for matches in the northern area
	 * @param isAvailableCentral a boolean representing the availability of the official for matches in the central area
	 * @param isAvailableSouth a boolean representing the availability of the official for matches in the southern area
	 * @return refereeAdded a boolean denoting if the referee was added successfully
	 */
	public boolean addReferee(String refID, String refFirstName, String refSurname, String refQualification, 
			int refAllocations, String refHomeArea, boolean isAvailableNorth, boolean isAvailableCentral,
			boolean isAvailableSouth){
		boolean refereeAdded = false;
		if (this.getNumberOfReferees()<this.MAXIMUM_NUMBER_OF_OFFICIALS){
			// As long as the number of referees does not exceed the maximum
			// Create a new instance of Referee class
			Referee ref = new Referee(refID, refFirstName, refSurname, refQualification, 
					refAllocations, refHomeArea, isAvailableNorth, isAvailableCentral, isAvailableSouth);
			// Add the referee object to the list of available referees
			officials.add(ref);
			refereeAdded = true;
		}
		return refereeAdded;
	}

	/**
	 * Method to remove a referee object from the list of referees.
	 * @param refID a String containing the ID of the referee
	 * @return refereeRemoved a boolean denoting that the referee was successfully removed
	 */
	public boolean removeReferee(String refID){
		Boolean refereeRemoved = false;
		Referee ref = this.getRefereeByID(refID);
		if (ref!=null){
			// if a referee exists with a matching refID
			// then remove the official from the list of available referees
			officials.remove(ref);
			refereeRemoved = true;
		}
		return refereeRemoved;
	}

	/**
	 * Method to alter the details of a particular referee.
	 * @param refID a String containing the ID of the referee
	 * @param refQualification a String containing the qualification of the referee
	 * @param refHomeArea refHomeArea a String containing the geographical location of the referee's home
	 * @param isAvailableNorth a boolean representing the availability of the official for matches in the northern area
	 * @param isAvailableCentral a boolean representing the availability of the official for matches in the central area
	 * @param isAvailableSouth a boolean representing the availability of the official for matches in the southern area
	 * @return refereeEdited a boolean denoting whether or not the referee was successfully edited 
	 */
	public boolean editReferee(String refID, String refQualification, String refHomeArea, boolean isAvailableNorth,
			boolean isAvailableCentral, boolean isAvailableSouth){
		boolean refereeEdited = false;
		Referee ref = this.getRefereeByID(refID);
		// ensure the changes apply to an existing referee
		if (ref!=null){
			// Update the all editable fields for the referee
			ref.setQualification(refQualification);
			ref.setHomeLocation(refHomeArea);
			ref.setAvailableNorth(isAvailableNorth);
			ref.setAvailableCentral(isAvailableCentral);
			ref.setAvailableSouth(isAvailableSouth);
			// store the new changes to the referee's details
			refereeEdited = true;
		}
		return refereeEdited;
	}

	/**
	 * Method to return a referee object based upon a unique name
	 * @param refName a String containing the first and surname of the referee
	 * @return ref a Referee object which corresponds to the name searched for
	 */
	public Referee getRefereeByName(String refName){
		Referee ref = null;
		// Search for a match to the first and surnames entered
		for (Referee official: officials){
			if (official.getFullName().equals(refName)){
				ref = official;
				break;
			}
		}
		// return null if no corresponding referee is identified
		return ref;
	}

	/**
	 * Method to search for a referee by ID.
	 * @param refID a String containing the ID of the referee
	 * @return ref a Referee object containing the details for the matched official 
	 * or null if no referee exists with that name
	 */
	public Referee getRefereeByID(String refID){
		Referee ref = null;
		// Search for an the unique ID in the list of referees
		for (Referee official: officials){
			if (official.getRefID().equals(refID)){
				ref = official;
				break;
			}
		}
		// if no match is located return null
		return ref;
	}

	/**
	 * Method to add a new match to the match schedule
	 * @param area a String corresponding to the venue of the match
	 * @param level a String corresponding to the level of the match (Junior/Senior)
	 * @param week an int corresponding to the week the match is scheduled for
	 * @return matchAddedSuccessfully a boolean denoting whether the match was added successfully
	 */
	public boolean addMatchToSchedule(String matchLocation, String matchLevel, int matchWeek){
		boolean matchAddedSuccessfully = false;
		// Ensure the maximum number of matches isn't exceeded and the
		// specific week is available for scheduling
		if (this.getNumberOfMatches()<WEEKS_IN_A_SEASON&&this.getMatchByWeek(matchWeek)==null){
			Referee[] suitableRefs = determineSuitableRefs(matchLocation, matchLevel);
			if (suitableRefs.length>=REFEREES_PER_MATCH){
				// if there are sufficient suitable referees to officiate the match
				Match fixture = new Match (matchLocation, matchLevel, matchWeek);
				// Add the match to the schedule of matches
				matchSchedule.add(fixture);
				allocateOfficials (suitableRefs, fixture);
				// allocate suitable officials to the match
				matchAddedSuccessfully = true;
			}
		}
		return matchAddedSuccessfully;
	}

	/**
	 * Method to remove a match from the schedule of matches
	 * @param week an integer denoting the week the match is scheduled for
	 * @return matchRemovedSuccessfully a boolean denoting whether the match was removed successfully
	 */
	public boolean removeMatchFromSchedule(int week){
		boolean matchRemovedSuccessfully = false;
		Match fixture = this.getMatchByWeek(week);
		// Identify the match from within the schedule
		if (fixture!=null){
			// if there is an existing match allocated to a given week
			Referee ref1 = fixture.getReferee1();
			ref1.setMatchAllocations(ref1.getMatchAllocations()-1);
			// decrease the number of match allocations for referee 1
			Referee ref2 = fixture.getReferee2();
			ref2.setMatchAllocations(ref2.getMatchAllocations()-1);
			// decrease the number of match allocation for referee 2
			matchSchedule.remove(fixture);
			// remove the match from the match schedule
			matchRemovedSuccessfully = true;
		}
		return matchRemovedSuccessfully;
	}

	/**
	 * Method to return a Match object scheduled for a given week
	 * @param week an integer containing the week the match has been scheduled for
	 * @return game a Match object corresponding to the week searched for
	 */
	public Match getMatchByWeek(int week){
		Match fixture = null;
		for (Match game: matchSchedule){
			if (game.getWeekNo()==week){
				fixture = game;
			}
		}
		// return the match object or "null" if no match has been allocated to that week
		return fixture;
	}

	/**
	 * Method to create an ordered array of referees suitable for a match
	 * @param matchVenue a String containing the location of the match to be scheduled
	 * @param matchLevel a String containing the level of the match to be scheduled (i.e. Junior or Senior)
	 * @return refsAvailable an array of referees ordered by suitability for a given match
	 */
	public Referee[] determineSuitableRefs (String matchVenue, String matchLevel){
		// Determine the referees qualified and available referees for the proposed match
		List<Referee> availableRefs = this.getAvailableRefs(matchLevel, matchVenue);
		// Divide the referees by geographical location
		List<Referee> northRefs = this.getAvailableRefsByLocation(availableRefs, "North");			
		List<Referee> centralRefs = this.getAvailableRefsByLocation(availableRefs, "Central");
		List<Referee> southRefs = this.getAvailableRefsByLocation(availableRefs, "South");
		// Sort the list of referees arrange by location based upon the number of match allocations
		northRefs = this.sortByAllocations(northRefs);
		centralRefs = this.sortByAllocations(centralRefs);
		southRefs = this.sortByAllocations(southRefs);
		// Create an array of referees ordered by proximity based upon the venue of the proposed match
		Referee[] suitableRefs = this.generateSuitableRefs (northRefs, centralRefs, southRefs, matchVenue);
		return suitableRefs;
	}

	/**
	 * Method to generate a list of referees eligible and available to referee a proposed match
	 * @param matchLevel a String representing whether the match has Junior or Senior participants
	 * @param matchVenue a String representing the venue of the proposed match
	 * @return qualifiedRefs an ArrayList of Referees qualified and available to officiate the match
	 */
	private List<Referee> getAvailableRefs(String matchLevel, String matchVenue){
		List<Referee> qualifiedReferees = new ArrayList<Referee>();
		if (matchLevel.equals("Senior")){
			for (Referee ref: officials){
				if (ref.canRefereeSeniors()){
					qualifiedReferees.add(ref);
					// add the officials qualified to referee Senior matches
				}
			}
		}
		else{ // if the match level is Junior then all referees are eligible to officiate
			qualifiedReferees = officials;
			
		}
		List<Referee> availableReferees = new ArrayList<Referee>();
		if (matchVenue.equals("North")){
			// if the match being scheduled for the northern area
			for (Referee ref: qualifiedReferees){
				if (ref.isAvailableNorth()){
					// if the referee is available to officiate matches in
					// the north then add to the list
					availableReferees.add(ref);
				}
			}
		}
		else if (matchVenue.equals("Central")){
			// if the match being scheduled for the central area
			for (Referee ref: qualifiedReferees){
				if (ref.isAvailableCentral()){
					// Add the referee if they are available to officiate
					// matches in the central area
					availableReferees.add(ref);
				}
			}
		}
		else { // ie. match venue equals "South"
			for (Referee ref: qualifiedReferees){
				if (ref.isAvailableSouth()){
					// Add the officials available to referee matches in the south
					availableReferees.add(ref);
				}
			}
		}
		return availableReferees;
	}


	/**
	 * A method to divide the referees available for a match by their home location	
	 * @param availableRefs a list of qualified and available referees to officiate the match
	 * @param home a String representing the home location to be searched for
	 * @return homeRefs a list of referees which are based in the same geographical region
	 */
	private List<Referee> getAvailableRefsByLocation(List<Referee> availableRefs, String home){
		List<Referee> refsFromArea = new ArrayList<Referee>();
		for (Referee ref: availableRefs){
			if (ref.getHomeLocation().equals(home)){
				// if the location of the referee matches the location to be searched for
				// add the referee to the list of available referees from the area
				refsFromArea.add(ref);
			}
		}
		return refsFromArea;
	}

	/**
	 * Method to order the eligible and available officials based upon
	 * their proximity to the proposed venue of the match and number of match allocations
	 * @param refsNorth a list of available referees based in the Northern area
	 * @param refsCentral a list of available referees based in the Central area
	 * @param refsSouth a list of available referees based in the Southern area
	 * @param venue a String containing the proposed match venue
	 * @return suitableRefs an array of officials grouped by proximity to the match venue
	 */
	private Referee[] generateSuitableRefs(List<Referee> refsNorth, List<Referee> refsCentral, 
			List<Referee> refsSouth, String venue){	
		int totalSuitableRefs = refsNorth.size()+refsCentral.size()+refsSouth.size();
		// Determine the total number of qualified and available referees for a match
		Referee [] suitableRefs = new Referee[totalSuitableRefs];
		if (venue.equals("Central")){
			// if the venue of the match is in the Central area
			List<Referee> refsNorthAndSouth = new ArrayList<Referee>();
			// generate a list of available referees from both the northern and southern
			// areas, as both the north and south are given equal priority when the match
			// venue is in the central area
			for (Referee ref: refsNorth){
				refsNorthAndSouth.add(ref);
			}
			for (Referee ref: refsSouth){
				refsNorthAndSouth.add(ref);
			}
			refsNorthAndSouth=this.sortByAllocations(refsNorthAndSouth);
			// the list of north and south based referees must be sorted by match allocations
			suitableRefs=this.insertRefereesToArray(suitableRefs, refsCentral);
			suitableRefs=this.insertRefereesToArray(suitableRefs, refsNorthAndSouth);
			// Insert the central, then remaining officials into the ordered array
			return suitableRefs;
		}	
		else if (venue.equals("North")){
			// if the venue of the match is in the Northern area
			suitableRefs=this.insertRefereesToArray(suitableRefs, refsNorth);
			suitableRefs=this.insertRefereesToArray(suitableRefs, refsCentral);
			suitableRefs=this.insertRefereesToArray(suitableRefs, refsSouth);
			// insert the groups of officials in order of north, central and then southern areas
			return suitableRefs;
		}
		else {//venue.equals("South")
			// if the venue of the match is in the Southern area
			suitableRefs=this.insertRefereesToArray(suitableRefs, refsSouth);
			suitableRefs=this.insertRefereesToArray(suitableRefs, refsCentral);
			suitableRefs=this.insertRefereesToArray(suitableRefs, refsNorth);
			// insert the groups of officials in order of the south, central and northern areas
			return suitableRefs;
		}		
	}

	/**
	 * Method to insert a list of officials from one geographical area into a larger, ordered array
	 * containing all the qualified and available referees.
	 * @param totalArray an array for storing a composite list of all available referees from multiple geographical areas
	 * @param insertList a list of available referees from a single geographical region
	 * @return totalArray an array for storing a composite list of all available referees from multiple geographical areas
	 */
	private Referee[] insertRefereesToArray (Referee[] totalArray, List<Referee> insertList){
		int startPosition = 0;
		// denotes the index where the array is to be inserted
		for (int index = 0; index<totalArray.length; index++){
			if (totalArray[index]==null){
				// locate the first null element in the composite array
				startPosition = index;
				break;
			}
		}
		// insert the inserted array from the first null 1element
		int arrayIndex = startPosition;
		for (Referee ref: insertList){
			totalArray[arrayIndex]=ref;
			arrayIndex++;
			// increment the index of the composite array
		}
		return totalArray;	
	}

	/**
	 * Method to sort a list of referee objects in order of match allocations
	 * @param toSort a list of referee objects to be sorted
	 * @return toSort a list of referee objects sorted by match allocations
	 */
	public List<Referee> sortByAllocations (List<Referee> toSort){
		// Create a new comparator object and override the compare method
		Comparator<Referee> allocationComparitor = new Comparator<Referee>(){
			@Override
			public int compare(Referee ref1, Referee ref2) {
				int ref1Matches = ref1.getMatchAllocations();
				int ref2Matches = ref2.getMatchAllocations();
				// Determine the match allocations for each referee object
				if (ref1Matches>ref2Matches){
					return 1;
				}	
				else if (ref1Matches<ref2Matches){
					return -1;
				}
				else{ // if the matchAllocations are equal
					return 0;
				}
			}
		};	
		// Sort the list of referees based on the new comparator
		Collections.sort(toSort, allocationComparitor);
		return toSort;
	}

	/**
	 * Method to allocate eligible and available officials to a match
	 * @param suitableRefs an array of Referee objects eligible for the match
	 * @param fixture a Match object containing the details of the match
	 */
	public void allocateOfficials(Referee[] suitableRefs, Match fixture){
		int indexSuitableRef1 = 0;
		int indexSuitableRef2 = 1;
		int Ref1Allocation = suitableRefs[indexSuitableRef1].getMatchAllocations();
		suitableRefs[indexSuitableRef1].setMatchAllocations(Ref1Allocation+1);
		fixture.setReferee1(suitableRefs[indexSuitableRef1]);
		// increment the match allocations of the first referee and add the official
		// to the match
		int Ref2Allocation = suitableRefs[indexSuitableRef2].getMatchAllocations();
		suitableRefs[indexSuitableRef2].setMatchAllocations(Ref2Allocation+1);
		fixture.setReferee2(suitableRefs[indexSuitableRef2]);
		// increment the match allocations of the second referee and add the official
		// to the match
	}

	/**
	 * Method to generate a new referee ID based upon the individuals name and the existing IDs in the list of referees
	 * @param refName a String containing the first and surname of the referee.
	 * @return idOut a String containing the appropriate ID for the new referee.
	 */
	public String generateRefID(String refName){
		// tokenise the name of the referee to the first and last name
		String [] tokens = refName.split(" +");
		int tokensIndex = 0;
		String refFirstName = tokens[tokensIndex++];
		String refSurname = tokens[tokensIndex++];
		// Ensure the initials are in upper case
		int indexFirstChar = 0;
		int indexSecondChar = 1;
		char firstNameChar = refFirstName.toUpperCase().charAt(indexFirstChar);
		char surnameChar = refSurname.toUpperCase().charAt(indexFirstChar);
		// Set the default value of the identifying number to 1
		int idNumber = 1;
		// search for existing referees with the same initials
		for (Referee ref: officials){
			char idCharacter1 = ref.getRefID().charAt(indexFirstChar);
			char idCharacter2 = ref.getRefID().charAt(indexSecondChar);
			// Check if the initials of each referee match those of the proposed new referee
			if (idCharacter1==firstNameChar&&idCharacter2==surnameChar){
				idNumber++;
			}
		}
		// Generate a string for the id of the new referee
		String idOut = ""+firstNameChar+surnameChar+ idNumber;
		return idOut;
	}

	/**
	 * Method to sort the referees by ID.
	 * @return refereesToSort a list of referee objects sorted by their IDs.
	 */
	public List<Referee> sortRefsByID(){
		// Create a new comparator object and override the compare method
		Comparator<Referee> idComparitor = new Comparator<Referee>(){
			@Override
			public int compare(Referee ref1, Referee ref2) {
				String refID1 = ref1.getRefID();
				String refID2 = ref2.getRefID();
				// Determine the ids of the referees
				int idLength = refID1.length();
				for (int index=0; index<idLength; index++){
					if (refID1.charAt(index)!=refID2.charAt(index)){
						if (refID1.charAt(index)>refID2.charAt(index)){
							return 1;
						}
						else{
							return -1;
						}
					}
					// If both chars are equal proceed to the next char in each id
				}
				// If the IDs are equal, however, the program is designed to ensure
				// the IDs of referees are unique
				return 0;
			}	
		};	
		// Sort the list of officials by ID
		Collections.sort(officials, idComparitor);
		return officials;
	}
	
	/**
	 * Method to generate a report of the current referees available for officiating matches
	 * in a String for writing to a file
	 * @return refReport a String report containing the details of the available referees in the list of officials
	 */
	public String refereeReportWriter(){
		String refDescription = "";
		StringBuilder builder = new StringBuilder(refDescription);
		// For each official available to officiate matches
		for (Referee ref: officials){
			// Generate a string details all the stored attributes
			refDescription = String.format("%s %s %s %d %s %s%s%s\n", ref.getRefID(), ref.getFullName(),
					ref.getQualification(), ref.getMatchAllocations(), ref.getHomeLocation(), ref.convertBooleanAvailability(ref.isAvailableNorth()),
					ref.convertBooleanAvailability(ref.isAvailableCentral()), ref.convertBooleanAvailability(ref.isAvailableSouth()));
			// Add the string to the StringBuilder
			builder.append(refDescription);
		}
		// Generate the complete report as a String from the StringBuilder
		String refReport = builder.toString();
		return refReport;
	}

	/**
	 * Method to generate a report of the current matches scheduled for the season
	 * in a String for writing to a file
	 * @return matchReport a String report containing the details of all the scheduled matches in the list of matches
	 */
	public String matchReportWriter(){
		String matchDescription = "";
		StringBuilder builder = new StringBuilder(matchDescription);
		// For each match scheduled by the user
		for (Match fixture: matchSchedule){
			// Generate a string providing the details of the match
			matchDescription = fixture.toString();
			// Add the string to the StringBuilder
			builder.append(matchDescription);
		}
		String matchReport = builder.toString();
		return matchReport;
	}
}