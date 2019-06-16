package transferObjects;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Representation of a transaction from the CDW_SAPP database.
 * 
 * @author Aris Fernandez
 *
 */
public class Transaction {

	private int branchCode, transactionDay, transactionMonth, transactionYear, transactionID, custSsn, aggregateCount;
	private double transactionValue;
	private String creditCardNum, transactionType;
	protected Date lastUpdated;

	public int getCustSsn() {
		return custSsn;
	}

	public void setCustSsn(int custSsn) {
		this.custSsn = custSsn;
	}

	public int getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(int branchCode) {
		this.branchCode = branchCode;
	}

	public int getTransactionDay() {
		return transactionDay;
	}

	public void setTransactionDay(int transactionDay) {
		this.transactionDay = transactionDay;
	}

	public int getTransactionMonth() {
		return transactionMonth;
	}

	public void setTransactionMonth(int transactionMonth) {
		this.transactionMonth = transactionMonth;
	}

	public int getTransactionYear() {
		return transactionYear;
	}

	public void setTransactionYear(int transactionYear) {
		this.transactionYear = transactionYear;
	}

	public int getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public int getAggregateCount() {
		return aggregateCount;
	}

	public void setAggregateCount(int aggregateCount) {
		this.aggregateCount = aggregateCount;
	}

	public double getTransactionValue() {
		return transactionValue;
	}

	public void setTransactionValue(double trasactionValue) {
		this.transactionValue = trasactionValue;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String branch, day, month, date, id, amount, cardNo, type, delimiter, updated;
		delimiter = "     ";
		NumberFormat valueFormat = NumberFormat.getCurrencyInstance();
		amount = valueFormat.format(this.getTransactionValue());
		branch = String.format("%1$-7s", this.branchCode);
		day = (String.valueOf(this.transactionDay));
		day = (day.length() < 2) ? "0" + day : day;
		month = (String.valueOf(this.transactionMonth));
		month = (month.length() < 2) ? "0" + month : month;
		date = day + "-" + month + "-" + String.valueOf(this.transactionYear);
		id = String.format("%1$-5s", this.transactionID);
		amount = String.format("%1$-9s", amount);
		cardNo = String.format("%1$-16s", this.creditCardNum);
		type = String.format("%1$-13s", this.transactionType);
		updated = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(this.lastUpdated);
		return id + delimiter + date + delimiter + cardNo + delimiter + type + delimiter + branch + delimiter + amount
				+ delimiter + updated;
	}

	public String csvFormat() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String branch, day, month, year, id, value, ssn, cardNo, type, updated, delimiter;
		delimiter = ",";
		branch = String.valueOf(this.branchCode);
		day = String.valueOf(this.transactionDay);
		month = String.valueOf(this.transactionMonth);
		year = String.valueOf(this.transactionYear);
		id = String.valueOf(this.transactionID);
		value = String.valueOf(this.transactionValue);
		cardNo = String.valueOf(this.creditCardNum);
		type = String.valueOf(this.transactionType);
		ssn = String.valueOf(this.custSsn);
		updated = dateFormat.format(this.lastUpdated);

		return id + delimiter + day + delimiter + month + delimiter + year + delimiter + cardNo + delimiter + ssn
				+ delimiter + branch + delimiter + type + delimiter + value + delimiter + updated + "\n";
	}

}
