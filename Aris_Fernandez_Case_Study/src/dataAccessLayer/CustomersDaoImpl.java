package dataAccessLayer;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import exceptions.ExceptionResources;
import exceptions.CustomExceptions.NoResultsFoundException;
import transferObjects.Customer;
import transferObjects.Transaction;
import transferObjects.TransactionList;

/**
 * Implements all the methods used to access and manipulate customer data from
 * the customer table in the CDW_SAPP database.
 *
 * @author Aris Fernandez
 */
public class CustomersDaoImpl extends AbstractDAO implements CustomersDaoI {

	public CustomersDaoImpl() throws SQLException {
		this.connect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccessLayer.CustomersDaoI#getCustomerDetails(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Customer getCustomerDetails(String ssn, String creditCardNo) throws NoResultsFoundException {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Customer customerInfo = new Customer();
			this.preparedStatament = this.connection.prepareStatement(CUSTOMER_DETAILS_QUERY);
			this.preparedStatament.setInt(1, Integer.valueOf(ssn));
			this.preparedStatament.setString(2, creditCardNo);
			this.preparedStatament.execute();
			this.resultSet = this.preparedStatament.getResultSet();

			while (this.resultSet.next()) {

				int cust_ssn = this.resultSet.getInt("SSN");
				int custPhone = this.resultSet.getInt("CUST_PHONE");
				String aptNum = this.resultSet.getString("APT_NO");
				String creditCardNum = this.resultSet.getString("CREDIT_CARD_NO");
				String city = this.resultSet.getString("CUST_CITY");
				String country = this.resultSet.getString("CUST_COUNTRY");
				String email = this.resultSet.getString("CUST_EMAIL");
				String state = this.resultSet.getString("CUST_STATE");
				String zipcode = this.resultSet.getString("CUST_ZIP");
				String firstName = this.resultSet.getString("FIRST_NAME");
				String lastName = this.resultSet.getString("LAST_NAME");
				String middleName = this.resultSet.getString("MIDDLE_NAME");
				String streetName = this.resultSet.getString("STREET_NAME");
				Timestamp lastUpdatedTimestamp = this.resultSet.getTimestamp("LAST_UPDATED");
				Date lastUpdated;
				try {
					lastUpdated = dateFormat.parse(dateFormat.format(lastUpdatedTimestamp));
				} catch (ParseException e) {
					lastUpdated = null;
					e.printStackTrace();
				}

				customerInfo.setFirstName(firstName);
				customerInfo.setLastName(lastName);
				customerInfo.setMiddleName(middleName);
				customerInfo.setCustPhone(custPhone);
				customerInfo.setEmail(email);
				customerInfo.setStreetName(streetName);
				customerInfo.setAptNum(aptNum);
				customerInfo.setCity(city);
				customerInfo.setState(state);
				customerInfo.setZipcode(zipcode);
				customerInfo.setCountry(country);
				customerInfo.setSsn(cust_ssn);
				customerInfo.setCreditCardNum(creditCardNum);
				customerInfo.setLastUpdated(lastUpdated);

			}
			ExceptionResources.hasResults(this.resultSet, // If the result set is empty, this throws an error
					"\nNo results were found.\nPlease try again and make sure you enter correct information.");
			return customerInfo;
		} catch (SQLException except) {
			except.printStackTrace();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccessLayer.CustomersDaoI#updateCustomerDetails(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.Object)
	 */
	@Override
	public boolean updateCustomerDetails(String ssn, String creditCardNo, String columnName, Object columnValue) {
		try {
			this.preparedStatament = this.connection
					.prepareStatement(CUSTOMER_MODIFY_DETAILS_QUERY.replaceFirst("REPLACE_COLUMN_NAME", columnName));

			this.preparedStatament.setObject(1, columnValue);
			this.preparedStatament.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
			this.preparedStatament.setString(3, ssn);
			this.preparedStatament.setString(4, creditCardNo);

			this.preparedStatament.execute();
			this.preparedStatament.close();

			return true;
		} catch (Exception except) {
			except.printStackTrace();
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccessLayer.CustomersDaoI#getCustomerBill(java.lang.String,
	 * java.lang.String, int, int)
	 */
	@Override
	public TransactionList getCustomerBill(String ssn, String creditCardNo, int month, int year)
			throws NoResultsFoundException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			TransactionList transactionList = new TransactionList();
			this.preparedStatament = this.connection.prepareStatement(CUSTOMER_BILL_QUERY);
			this.preparedStatament.setString(1, ssn);
			this.preparedStatament.setString(2, creditCardNo);
			this.preparedStatament.setInt(3, year);
			this.preparedStatament.setInt(4, month);
			this.preparedStatament.execute();
			this.resultSet = this.preparedStatament.getResultSet();

			while (this.resultSet.next()) {

				int branchCode = this.resultSet.getInt("BRANCH_CODE");
				int day = this.resultSet.getInt("DAY");
				int transactionMonth = this.resultSet.getInt("MONTH");
				int transactionID = this.resultSet.getInt("TRANSACTION_ID");
				int transactionYear = this.resultSet.getInt("YEAR");
				int custSsn = this.resultSet.getInt("CUST_SSN");
				double trasactionValue = this.resultSet.getDouble("TRANSACTION_VALUE");
				String creditCardNum = this.resultSet.getString("CREDIT_CARD_NO");
				String transactionType = this.resultSet.getString("TRANSACTION_TYPE");
				Timestamp lastUpdatedTimestamp = this.resultSet.getTimestamp("LAST_UPDATED");
				Date lastUpdated;
				try {
					lastUpdated = dateFormat.parse(dateFormat.format(lastUpdatedTimestamp));
				} catch (ParseException e) {
					lastUpdated = null;
					e.printStackTrace();
				}
				Transaction transact = new Transaction();
				transact.setBranchCode(branchCode);
				transact.setCreditCardNum(creditCardNum);
				transact.setTransactionDay(day);
				transact.setTransactionMonth(transactionMonth);
				transact.setTransactionID(transactionID);
				transact.setTransactionType(transactionType);
				transact.setTransactionValue(trasactionValue);
				transact.setTransactionYear(transactionYear);
				transactionList.addTransaction(transact);
				transact.setCustSsn(custSsn);
				transact.setLastUpdated(lastUpdated);
			}
			ExceptionResources.hasResults(this.resultSet, // If the result set is empty, this throws an error
					"\nNo results were found.\nPlease try again and make sure you enter correct information.");

			this.preparedStatament.close();
			this.resultSet.close();

			return transactionList;

		} catch (SQLException except) {
			except.printStackTrace();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccessLayer.CustomersDaoI#getTransactionRange(java.lang.String,
	 * java.lang.String, int, int, int, int, int, int)
	 */
	@Override
	public TransactionList getTransactionRange(String ssn, String creditCardNo, int from_Day, int from_Month,
			int from_Year, int to_Day, int to_Month, int to_Year) throws NoResultsFoundException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			TransactionList transactionList = new TransactionList();
			this.preparedStatament = this.connection.prepareStatement(CUSTOMER_TRANSACTIONS_QUERY);
			this.preparedStatament.setString(1, ssn);
			this.preparedStatament.setString(2, creditCardNo);
			this.preparedStatament.setInt(3, from_Day);
			this.preparedStatament.setInt(4, from_Month);
			this.preparedStatament.setInt(5, from_Year);
			this.preparedStatament.setInt(6, to_Day);
			this.preparedStatament.setInt(7, to_Month);
			this.preparedStatament.setInt(8, to_Year);
			this.preparedStatament.execute();

			this.resultSet = this.preparedStatament.getResultSet();

			while (this.resultSet.next()) {

				int branchCode = this.resultSet.getInt("BRANCH_CODE");
				int day = this.resultSet.getInt("DAY");
				int transactionMonth = this.resultSet.getInt("MONTH");
				int transactionID = this.resultSet.getInt("TRANSACTION_ID");
				int transactionYear = this.resultSet.getInt("YEAR");
				int custSsn = this.resultSet.getInt("CUST_SSN");
				double trasactionValue = this.resultSet.getDouble("TRANSACTION_VALUE");
				String creditCardNum = this.resultSet.getString("CREDIT_CARD_NO");
				String transactionType = this.resultSet.getString("TRANSACTION_TYPE");
				Timestamp lastUpdatedTimestamp = this.resultSet.getTimestamp("LAST_UPDATED");
				Date lastUpdated;
				try {
					lastUpdated = dateFormat.parse(dateFormat.format(lastUpdatedTimestamp));
				} catch (ParseException e) {
					lastUpdated = null;
					e.printStackTrace();
				}

				Transaction transact = new Transaction();
				transact.setBranchCode(branchCode);
				transact.setCreditCardNum(creditCardNum);
				transact.setTransactionDay(day);
				transact.setTransactionMonth(transactionMonth);
				transact.setTransactionID(transactionID);
				transact.setTransactionType(transactionType);
				transact.setTransactionValue(trasactionValue);
				transact.setTransactionYear(transactionYear);
				transactionList.addTransaction(transact);
				transact.setCustSsn(custSsn);
				transact.setLastUpdated(lastUpdated);
			}
			ExceptionResources.hasResults(this.resultSet, // If the result set is empty, this throws an error
					"\nNo results were found.\nPlease try again and make sure you enter correct information.");

			this.preparedStatament.close();
			this.resultSet.close();

			return transactionList;

		} catch (SQLException except) {
			except.printStackTrace();
			return null;
		}

	}

}
