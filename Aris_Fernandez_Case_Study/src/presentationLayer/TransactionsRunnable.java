package presentationLayer;

import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dataAccessLayer.TransactionsDaoImpl;
import exceptions.CustomExceptions.NoResultsFoundException;
import resources.Utilities;
import transferObjects.Transaction;
import transferObjects.TransactionList;

/**
 * @author Aris Fernandez
 */
public class TransactionsRunnable {

	private TransactionsDaoImpl daoImpl;

	public TransactionsRunnable() throws SQLException {
		this.daoImpl = new TransactionsDaoImpl();
	}

	private String createDisplayHeader() {
		String branch, date, id, value, cardNo, type, delimiter, updated;
		delimiter = "     ";
		branch = "BRANCH ";
		date = "DATE      ";
		id = "ID   ";
		value = "AMOUNT   ";
		cardNo = "CARD NUMBER     ";
		type = "TYPE         ";
		updated = "UPDATED            ";
		return id + delimiter + date + delimiter + cardNo + delimiter + type + delimiter + branch + delimiter + value
				+ delimiter + updated;
	}

	public static String createCsvHeader() {
		String branch, day, month, year, id, ssn, value, cardNo, type, updated, delimiter;
		delimiter = ",";
		branch = "BRANCH_CODE";
		day = "DAY";
		month = "MONTH";
		year = "YEAR";
		id = "TRANSACTION_ID";
		value = "TRANSACTION_AMOUNT";
		cardNo = "CARD_NUMBER";
		type = "TRANSACTION_TYPE";
		ssn = "CUSTOMER_SSN";
		updated = "LAST_UPDATED";
		return id + delimiter + day + delimiter + month + delimiter + year + delimiter + cardNo + delimiter + ssn
				+ delimiter + branch + delimiter + type + delimiter + value + delimiter + updated + "\r\n";
	}

	public static String createTotalsCsvHeader(boolean byType) {

		String value, count, by, delimiter;
		delimiter = ",";
		value = "TOTAL_TRANSACTION_AMOUNT";
		count = "TRANSACTIONS_COUNT";
		if (byType) {
			by = "TRANSACTIONS_TYPE";
		} else {
			by = "BRANCH_STATE";
		}
		return count + delimiter + value + by + "\n";

	}

	public TransactionList getTransactionsByZipcode(String zipcode, int duringMonth, int duringYear)
			throws NoResultsFoundException {

		SimpleDateFormat temp_date_format = new SimpleDateFormat("dd/M/yyyy");
		Date titleDate = null;
		try {
			titleDate = temp_date_format.parse("27/" + String.valueOf(duringMonth) + "/" + String.valueOf(duringYear));
		} catch (ParseException except) {
		}
		SimpleDateFormat date_format = new SimpleDateFormat("MMMM yyyy");
		System.out.println();
		System.out.println(Utilities.centerStringBy(
				"Transactions in Zipcode " + zipcode + " During " + date_format.format(titleDate), 107));
		System.out.println();
		System.out.println(this.createDisplayHeader());
		System.out.println();
		TransactionList result = this.daoImpl.getTransactionsByZipcode(zipcode, duringMonth, duringYear);
		for (Transaction t : result) {
			System.out.println(t);
		}
		return result;
	}

	public Transaction getTransactionsByState(String state) throws NoResultsFoundException {
		Transaction transact = this.daoImpl.getTransactionsByState(state);
		NumberFormat valueFormat = NumberFormat.getCurrencyInstance();
		String amount = valueFormat.format(transact.getTransactionValue());
		System.out.println();
		System.out.println(
				Utilities.centerStringBy("Transaction Summary for Transactions in " + state + " Branches", 85));
		System.out.println();
		System.out.println("Number of Transactions: " + transact.getAggregateCount());
		System.out.println();
		System.out.println("Total Transaction Amount: " + amount);
		return transact;
	}

	public Transaction getTransactionsByType(String type) throws NoResultsFoundException {
		Transaction transact = this.daoImpl.getTransactionsByType(type);
		NumberFormat valueFormat = NumberFormat.getCurrencyInstance();
		String amount = valueFormat.format(transact.getTransactionValue());
		System.out.println();
		System.out.println(Utilities.centerStringBy("Summary for " + "'" + type.substring(0, 1).toUpperCase()
				+ type.substring(1).toLowerCase() + "'" + " Transactions", 85));
		System.out.println();
		System.out.println("Number of Transactions: " + transact.getAggregateCount());
		System.out.println();
		System.out.println("Total Transaction Amount: " + amount);
		return transact;

	}
}
