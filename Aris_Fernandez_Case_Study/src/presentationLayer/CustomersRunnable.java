package presentationLayer;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dataAccessLayer.CustomersDaoImpl;
import exceptions.CustomExceptions.NoResultsFoundException;
import resources.Utilities;
import transferObjects.Customer;
import transferObjects.TransactionList;

/**
 * The CustomersRunnable class contains all the methods needed to display the
 * information for the customer module as well as the methods needed to save CSV
 * files for the customer module.
 * 
 * @author Aris Fernandez
 */
public class CustomersRunnable {

	public Customer getCustomerDetails(String ssn, String creditCardNo) throws NoResultsFoundException, SQLException {
		System.out.println();
		CustomersDaoImpl custImpl = new CustomersDaoImpl();
		System.out.println(Utilities.centerStringBy("Customer Details", 75));
		System.out.println();
		Customer customer = custImpl.getCustomerDetails(ssn, creditCardNo);
		System.out.println(customer);
		return customer;
	}

	public Customer updateCustomerDetails(String ssn, String creditCardNo, String columnName, Object columnValue)
			throws SQLException {

		CustomersDaoImpl custImpl = new CustomersDaoImpl();
		Customer customer = null;

		if (custImpl.updateCustomerDetails(ssn, creditCardNo, columnName, columnValue)) {
			System.out.println();
			System.out.println("The customer information was updated successfully.");
			System.out.println("The New Customer Details are as Follows:");
			System.out.println();
			try {
				customer = custImpl.getCustomerDetails(ssn, creditCardNo);
				System.out.println(this.customerTableInfo(customer));
			} catch (NoResultsFoundException except) {
				except.printStackTrace();
				Utilities.flushErrors();
			}

		}
		return customer;

	}

	public TransactionList getCustomerBill(Customer customerInfo, int month, int year)
			throws NoResultsFoundException, SQLException {

		CustomersDaoImpl custImpl = new CustomersDaoImpl();

		TransactionList transactions = new TransactionList();
		System.out.println();
		System.out.println(customerInfo.getFirstName() + " " + customerInfo.getMiddleName() + " "
				+ customerInfo.getLastName() + "\n" + customerInfo.getAptNum() + " " + customerInfo.getStreetName()
				+ "\n" + customerInfo.getCity() + ", " + customerInfo.getState() + " " + customerInfo.getZipcode()
				+ "\n" + customerInfo.getCountry());
		Date titleDate;
		try {
			titleDate = new SimpleDateFormat("MM yyyy")
					.parse(String.valueOf(String.valueOf(month) + " " + String.valueOf(year)));

			System.out.println();
			System.out.println(
					Utilities.centerStringBy(new SimpleDateFormat("MMMM yyyy").format(titleDate) + " Bill", 83));
			System.out.println();
			System.out.println(this.createDisplayHeader());
			System.out.println();
			transactions = custImpl.getCustomerBill(String.valueOf(customerInfo.getSsn()),
					customerInfo.getCreditCardNum(), month, year);
			transactions.forEach(System.out::println);
			return transactions;
		} catch (ParseException except) {
		}
		return null;
	}

	public TransactionList getTransactionRange(String ssn, String creditCardNo, int from_Day, int from_Month,
			int from_Year, int to_Day, int to_Month, int to_Year) throws NoResultsFoundException, SQLException {

		CustomersDaoImpl custImpl = new CustomersDaoImpl();
		Customer custInfo = custImpl.getCustomerDetails(ssn, creditCardNo);
		TransactionList transactions = new TransactionList();
		System.out.println();
		System.out.println(
				"Customer: " + custInfo.getFirstName() + " " + custInfo.getMiddleName() + " " + custInfo.getLastName());
		SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
		Date titleDate1, titleDate2;
		try {
			titleDate1 = date_format.parse(
					String.valueOf(from_Day) + "-" + String.valueOf(from_Month) + "-" + String.valueOf(from_Year));

			titleDate2 = date_format
					.parse(String.valueOf(to_Day) + "-" + String.valueOf(to_Month) + "-" + String.valueOf(to_Year));

			SimpleDateFormat titleDatesFormat = new SimpleDateFormat("MMMM dd yyyy");
			System.out.println();
			System.out.println(Utilities.centerStringBy("Transactions Between  " + titleDatesFormat.format(titleDate1)
					+ " and " + titleDatesFormat.format(titleDate2), 83));
			System.out.println();
			System.out.println(this.createDisplayHeader());
			System.out.println();
			transactions = custImpl.getTransactionRange(ssn, creditCardNo, from_Day, from_Month, from_Year, to_Day,
					to_Month, to_Year);
			transactions.forEach(System.out::println);
			return transactions;
		} catch (ParseException except) {
		}
		return null;
	}

	/**
	 * Creates the header used for the output of the customer bill and transactions
	 * between two dates submodules.
	 *
	 * @return Returns a concatenation of column names.
	 */
	private String createDisplayHeader() {
		String branch, date, id, value, cardNo, type, updated, delimiter;
		delimiter = "     ";
		branch = "BRANCH ";
		date = "DATE      ";
		id = "ID   ";
		value = "AMOUNT ";
		cardNo = "CARD NUMBER     ";
		type = "TYPE         ";
		updated = "LAST_UPDATED      ";
		return id + delimiter + date + delimiter + cardNo + delimiter + type + delimiter + branch + delimiter + value
				+ delimiter + updated;
	}

	/**
	 * Creates the table header used for when customer information is saved to a
	 * file. The header changes depending on whether the method is calling while
	 * updating customer information or just displaying the information.
	 *
	 * @param True if the method is called during an update.
	 * @return Returns a concatenation of the column names.
	 */
	public static String createCsvHeader(boolean toUpdate) {
		String fName, mName, lName, ssn, creditcardNo, aptNo, street, city, state, country, zip, phone, email, updated,
				delimiter, output, firstField;
		delimiter = ",";
		fName = "First_Name";
		mName = "Middle_Name";
		lName = "Last_Name";
		aptNo = "Apt_Number";
		street = "Street_Name";
		city = "City";
		state = "State";
		zip = "Zipcode";
		country = "Country";
		email = "Email";
		phone = "Phone_Number";
		creditcardNo = "Card_Number";
		ssn = "SSN";
		updated = "Last_Updated";
		if (toUpdate) {
			firstField = "%^,";
		} else {
			firstField = "%^";
		}

		output = firstField + fName + delimiter + mName + delimiter + lName + delimiter + ssn + delimiter + creditcardNo
				+ delimiter + aptNo + delimiter + street + delimiter + city + delimiter + state + delimiter + country
				+ delimiter + zip + delimiter + phone + delimiter + email + delimiter + updated + "\r\n";
		return output.toUpperCase().replace("%^", "");
	}

	/**
	 * Prepares the table column headers for displaying customer informaton in table
	 * format.
	 *
	 * @return A string concatenation of all column names.
	 */
	public String createTableHeader() {
		String fName, mName, lName, aptNo, street, city, state, country, zip, phone, email, updated, delimiter, output;
		delimiter = " ";
		fName = "First Name  ";
		mName = "Middle Name ";
		lName = "Last Name      ";
		aptNo = "Apt  ";
		street = "Street Name         ";
		city = "City                ";
		state = "State  ";
		zip = "Zipcode ";
		country = "Country        ";
		email = "Email Address               ";
		phone = "Phone Number ";
		updated = "Last Updated On     ";

		output = fName + delimiter + mName + delimiter + lName + delimiter + aptNo + delimiter + street + delimiter
				+ city + delimiter + state + delimiter + country + delimiter + zip + delimiter + phone + delimiter
				+ email + delimiter + updated + "\r\n";
		return output.toUpperCase().trim();
	}

	/**
	 * Customer information printed in table format.
	 *
	 * @param customer The customer object whose information is going to be
	 *                 displayed.
	 * @return A two-line string with the first line being column names and the
	 *         second the corresponding customer information.
	 */
	public String customerTableInfo(Customer customer) {
		System.out.println();
		return this.createTableHeader() + "\n" + customer.infoAsTable() + "\n";

	}

}
