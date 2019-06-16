package dataAccessLayer;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.ExceptionResources;
import resources.Utilities;
import exceptions.CustomExceptions.NoResultsFoundException;
import transferObjects.Transaction;
import transferObjects.TransactionList;

/**
 * @author Aris Fernandez
 *
 */
public class TransactionsDaoImpl extends AbstractDAO implements TransactionsDaoI {

	public TransactionsDaoImpl() throws SQLException {
		this.connect();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dataAccessLayer.TransactionsDaoI#getTransactionsByZipcode(java.lang.String,
	 * int, int)
	 */
	@Override
	public TransactionList getTransactionsByZipcode(String zipcode, int duringMonth, int duringYear)
			throws NoResultsFoundException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			TransactionList transactionList = new TransactionList();
			this.preparedStatament = this.connection.prepareStatement(TRANSACTIONS_BY_ZIPCODE_QUERY);
			this.preparedStatament.setString(1, zipcode);
			this.preparedStatament.setInt(2, duringMonth);
			this.preparedStatament.setInt(3, duringYear);
			this.preparedStatament.execute();
			this.resultSet = this.preparedStatament.getResultSet();

			while (resultSet.next()) {
				int branchCode = this.resultSet.getInt("BRANCH_CODE");
				int day = this.resultSet.getInt("DAY");
				int month = this.resultSet.getInt("MONTH");
				int transactionID = this.resultSet.getInt("TRANSACTION_ID");
				int year = this.resultSet.getInt("YEAR".toLowerCase());
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
				transact.setTransactionMonth(month);
				transact.setTransactionID(transactionID);
				transact.setTransactionType(transactionType);
				transact.setTransactionValue(trasactionValue);
				transact.setTransactionYear(year);
				transact.setCustSsn(custSsn);
				transact.setLastUpdated(lastUpdated);
				transactionList.addTransaction(transact);

			}

			ExceptionResources.hasResults(this.resultSet,
					"\nNo results were found, make sure you inputed correct values");

			this.preparedStatament.close();
			this.resultSet.close();

			return transactionList;

		} catch (SQLException except) {
			except.printStackTrace();
			Utilities.flushErrors();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * dataAccessLayer.TransactionsDaoI#getTransactionsByState(java.lang.String)
	 */
	@Override
	public Transaction getTransactionsByState(String state) throws NoResultsFoundException {
		try {
			Transaction transact = new Transaction();
			this.preparedStatament = this.connection.prepareStatement(TRANSACTIONSP_BY_STATE_QUERY);
			this.preparedStatament.setString(1, state);
			this.preparedStatament.execute();
			this.resultSet = this.preparedStatament.getResultSet();
			if (this.resultSet.next()) {
				int transactionsCount = this.resultSet.getInt("Transaction_Counts");
				double trasactionsValue = this.resultSet.getDouble("Sum_Values");
				transact.setTransactionValue(trasactionsValue);
				transact.setAggregateCount(transactionsCount);
			}
			ExceptionResources.hasResults(this.resultSet,
					"\nNo results were found, make sure you inputted correct values");
			this.preparedStatament.close();
			this.resultSet.close();

			return transact;

		} catch (SQLException except) {
			except.printStackTrace();
			Utilities.flushErrors();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccessLayer.TransactionsDaoI#getTransactionsByType(java.lang.String)
	 */
	@Override
	public Transaction getTransactionsByType(String type) throws NoResultsFoundException {
		try {
			Transaction transact = new Transaction();
			this.preparedStatament = this.connection.prepareStatement(TRANSACTIONS_BY_TYPE_QUERY);
			this.preparedStatament.setString(1, type);
			this.preparedStatament.execute();
			this.resultSet = this.preparedStatament.getResultSet();
			if (resultSet.next()) {
				int transactionsCount = this.resultSet.getInt("Transaction_Counts");
				double trasactionsValue = this.resultSet.getDouble("Sum_Values");
				transact.setTransactionValue(trasactionsValue);
				transact.setAggregateCount(transactionsCount);

			}
			ExceptionResources.hasResults(this.resultSet,
					"\nNo results were found, make sure you inputted correct values");
			this.preparedStatament.close();
			this.resultSet.close();

			return transact;

		} catch (SQLException except) {
			except.printStackTrace();
			Utilities.flushErrors();
			return null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dataAccessLayer.TransactionsDaoI#getAllowedTransactionTypes()
	 */
	@Override
	public List<String> getAllowedTransactionTypes() {
		try {
			this.preparedStatament = this.connection.prepareStatement(ALLOWED_TRANSACTION_TYPES_QUERY);
			this.preparedStatament.execute();
			this.resultSet = this.preparedStatament.getResultSet();
			List<String> typeList = new ArrayList<String>();

			while (this.resultSet.next()) {
				typeList.add(this.resultSet.getString(1));
			}
			return typeList;

		} catch (SQLException e) {
			e.printStackTrace();
			Utilities.flushErrors();
		}

		return null;
	}

}
