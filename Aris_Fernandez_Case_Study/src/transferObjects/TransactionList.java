package transferObjects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A list of transactions from the CDW_SAPP database.
 *
 * @author Aris Fernandez
 */
public class TransactionList implements Iterable<Transaction> {

	/** The transactions. */
	private List<Transaction> transactions = new ArrayList<Transaction>();

	@Override
	public Iterator<Transaction> iterator() {
		return this.transactions.iterator();
	}

	/**
	 * Adds the transaction.
	 *
	 * @param newTransaction the new transaction
	 */
	public void addTransaction(Transaction newTransaction) {
		this.transactions.add(newTransaction);

	}

	/**
	 * Gets the number of transactions.
	 *
	 * @return the number of transactions
	 */
	public int getNumberOfTransactions() {
		return this.transactions.size();
	}

	/**
	 * Gets the transaction.
	 *
	 * @param index the index
	 * @return the transaction
	 */
	public Transaction getTransaction(int index) {
		return this.transactions.get(index);
	}

}
