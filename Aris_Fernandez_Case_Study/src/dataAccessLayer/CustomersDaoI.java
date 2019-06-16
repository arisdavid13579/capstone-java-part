package dataAccessLayer;

import exceptions.CustomExceptions.NoResultsFoundException;
import transferObjects.Customer;
import transferObjects.TransactionList;

/**
 * Defines abstractly the methods needed to handle the customer data.
 *
 * @author Aris Fernandez *
 */
public interface CustomersDaoI extends CustomersQueries {

	/**
	 * Queries the CDW_SAPP customer table to obtain the first name, last name,
	 * middle name, street name, social security number, phone number, city,
	 * country, e-mail address, state, credit card number, and time stamp of last
	 * update for a given customer with the given credit card number and social
	 * security number.
	 * 
	 * @param ssn          The customer's social security number.
	 * @param creditCardNo The customer's credit card number.
	 * @return A Customer object containing all of the customer's information.
	 * @throws NoResultsFoundException If the no customer is found for the given
	 *                                 social security number and credit card
	 *                                 number.
	 */
	Customer getCustomerDetails(String ssn, String creditCardNo) throws NoResultsFoundException;

	/**
	 * For a given customer (Social security number and credit card), updates one
	 * field of information in the customer table.
	 *
	 * @param ssn          The customer's social security number.
	 * @param creditCardNo The customer's credit card number.
	 * @param columnName   The column name of the field to be updated.
	 * @param columnValue  The value with which the field will be updated.
	 * @return True if the update was successful, otherwise it returns false.
	 */
	boolean updateCustomerDetails(String ssn, String creditCardNo, String columnName, Object columnValue);

	/**
	 * Generates a list of transactions for a given customer in a given month, in a
	 * given year.
	 *
	 * @param ssn          The customer's social security number.
	 * @param creditCardNo The customer's credit card number.
	 * @param month        The month for which the customer bill will be generated.
	 * @param year         The year during which the transactions occurred.
	 * @return A TransactionList object containing the list of transactions for the
	 *         bill.
	 * @throws NoResultsFoundException If no transactions are found with the given
	 *                                 information.
	 */
	TransactionList getCustomerBill(String ssn, String creditCardNo, int month, int year)
			throws NoResultsFoundException;

	/**
	 * For a given customer, generates a list of transactions happening between two
	 * given dates.
	 *
	 *
	 * @param ssn          The customer's social security number.
	 * @param creditCardNo The customer's credit card number.
	 * @param from_Day     The day of initial date in the range.
	 * @param from_Month   The month of initial date in the range.
	 * @param from_Year    The year of initial date in the range.
	 * @param to_Day       The day of final date in the range.
	 * @param to_Month     The month of final date in the range.
	 * @param to_Year      The year of final date in the range.
	 * @return A TrasactionList object with a list transactions happening in the
	 *         given date range.
	 * @throws NoResultsFoundException If no transactions are found with the given
	 *                                 information.
	 */
	TransactionList getTransactionRange(String ssn, String creditCardNo, int from_Day, int from_Month, int from_Year,
			int to_Day, int to_Month, int to_Year) throws NoResultsFoundException;

}
