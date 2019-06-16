package resources;

/**
 * Contains a list of all states in the US. Used to get the 2-letter
 * abbreviation of each state. Used to State validation.
 *
 * @author Aris Fernandez
 *
 */
public enum State {

	ALABAMA("AL"), ALASKA("AK"), ARIZONA("AZ"), ARKANSAS("AR"), CALIFORNIA("CA"), COLORADO("CO"), CONNECTICUT("CT"),
	DELAWARE("DE"), FLORIDA("FL"), GEORGIA("GA"), HAWAII("HI"), IDAHO("ID"), ILLINOIS("IL"), INDIANA("IN"), IOWA("IA"),
	KANSAS("KS"), KENTUCKY("KY"), LOUISIANA("LA"), MAINE("ME"), MARYLAND("MD"), MASSACHUSETTS("MA"), MICHIGAN("MI"),
	MINNESOTA("MN"), MISSISSIPPI("MS"), MISSOURI("MO"), MONTANA("MT"), NEBRASKA("NE"), NEVADA("NV"),
	NEW_HAMPSHIRE("NH"), NEW_JERSEY("NJ"), NEW_MEXICO("NM"), NEW_YORK("NY"), NORTH_CAROLINA("NC"), NORTH_DAKOTA("ND"),
	OHIO("OH"), OKLAHOMA("OK"), OREGON("OR"), PENNSYLVANIA("PA"), RHODE_ISLAND("RI"), SOUTH_CAROLINA("SC"),
	SOUTH_DAKOTA("SD"), TENNESSEE("TN"), TEXAS("TX"), UTAH("UT"), VERMONT("VT"), VIRGINIA("VA"), WASHINGTON("WA"),
	WEST_VIRGINIA("WV"), WISCONSIN("WI"), WYOMING("WY"), AL, MT, AK, NE, AZ, NV, AR, NH, CA, NJ, CO, NM, CT, NY, DE, NC,
	FL, ND, GA, OH, HI, OK, ID, OR, IL, PA, IN, RI, IA, SC, KS, SD, KY, TN, LA, TX, ME, UT, MD, VT, MA, VA, MI, WA, MN,
	WV, MS, WI, MO, WY;

	private String stateName;

	private State() {
		this.stateName = this.name();
	}

	private State(String stateName) {
		this.stateName = stateName;
	}

	@Override
	public String toString() {
		return this.stateName;
	}

}