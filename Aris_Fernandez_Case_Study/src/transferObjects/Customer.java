package transferObjects;

import java.util.Date;

/**
 * Representation of a customer from the CDW_SAPP database.
 * 
 * @author Aris Fernandez
 *
 */
public class Customer {
	protected int ssn, custPhone;
	protected String aptNum, creditCardNum, city, country, email, state, zipcode, firstName, lastName, middleName,
			streetName;
	protected Date lastUpdated;

// *************************************************************************************************** //
//										GETTERS AND SETTERS		     		        				   //
// *************************************************************************************************** //

	public int getSsn() {
		return ssn;
	}

	public void setSsn(int ssn) {
		this.ssn = ssn;
	}

	public int getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(int custPhone) {
		this.custPhone = custPhone;
	}

	public String getAptNum() {
		return aptNum;
	}

	public void setAptNum(String aptNum) {
		this.aptNum = aptNum;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {

		this.lastUpdated = lastUpdated;

	}
// *************************************************************************************************** //
//												METHODS												   //
// *************************************************************************************************** //

	@Override
	public String toString() {

		String out;
		out = "FULL NAME: " + this.firstName + " " + this.middleName + " " + this.lastName + "\n\n";
		out += "ADDRESS: " + this.aptNum + " " + this.streetName + ". " + this.city + ", " + this.state + " "
				+ this.zipcode + ". " + this.country + "\n\n";
		out += "E-MAIL: " + this.email + "\n\n";
		out += "PHONE NO.: " + this.custPhone + "\n\n";
		out += "CREDIT CARD NO.: " + this.creditCardNum + "\n\n";
		out += "SOCIAL SECURITY: " + this.ssn + "\n\n";
		out += "LAST UPDATE: " + this.lastUpdated;
		return out;
	}

	/**
	 * Csv format.
	 *
	 * @return the string
	 */
	public String csvFormat() {
		String fName, mName, lName, ssn, creditcardNo, aptNo, street, city, state, country, zip, phone, email, updated,
				delimiter;
		delimiter = ",";
		fName = this.firstName;
		mName = this.middleName;
		lName = this.lastName;
		aptNo = this.aptNum;
		street = this.streetName;
		city = this.city;
		state = this.state;
		zip = this.zipcode;
		country = this.country;
		email = this.email;
		phone = String.valueOf(this.custPhone);
		creditcardNo = this.creditCardNum;
		ssn = String.valueOf(this.ssn);
		updated = String.valueOf(this.lastUpdated);
		return fName + delimiter + mName + delimiter + lName + delimiter + ssn + delimiter + creditcardNo + delimiter
				+ aptNo + delimiter + street + delimiter + city + delimiter + state + delimiter + country + delimiter
				+ zip + delimiter + phone + delimiter + email + delimiter + updated + "\n";
	}

	/**
	 * @return
	 */
	public String infoAsTable() {
		String fName, mName, lName, aptNo, street, city, state, country, zip, phone, email, updated, delimiter;
		delimiter = " ";
		fName = String.format("%1$-12s", this.firstName);
		mName = String.format("%1$-12s", this.middleName);
		lName = String.format("%1$-15s", this.lastName);
		aptNo = String.format("%1$-5s", this.aptNum);
		street = String.format("%1$-20s", this.streetName);
		city = String.format("%1$-20s", this.city);
		state = String.format("%1$-7s", this.state);
		zip = String.format("%1$-8s", this.zipcode);
		country = String.format("%1$-15s", this.country);
		email = String.format("%1$-28s", this.email);
		phone = String.format("%1$-13s", this.custPhone);
		updated = String.format("%1$-20s", (this.lastUpdated));
		return fName + delimiter + mName + delimiter + lName + delimiter + aptNo + delimiter + street + delimiter + city
				+ delimiter + state + delimiter + country + delimiter + zip + delimiter + phone + delimiter + email
				+ delimiter + updated + "\n";
	}
}
