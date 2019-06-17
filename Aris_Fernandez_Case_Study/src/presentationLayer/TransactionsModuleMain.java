package presentationLayer;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import dataAccessLayer.TransactionsDaoImpl;
import exceptions.CustomExceptions.NoResultsFoundException;
import resources.Utilities;
import transferObjects.Transaction;
import transferObjects.TransactionList;

/**
 * Presentation layer for the transaction module. This program can be run independently of the Main program.
 *
 * @author Aris Fernandez
 */
public class TransactionsModuleMain {

	/**
	 * This contains all the logic to be run in the main method.
	 *
	 * @param directlyCalled Must true if the method is being called directly from
	 *                       the TransactionsModuleMain class.
	 * @return Returns true if upon closure of this class, the calling class must
	 *         stay running.
	 */
	public boolean toMain(boolean directlyCalled) {
		Scanner scan = new Scanner(System.in);
		String exitMessage = (directlyCalled) ? "Type 'quit' to quit"
				: "Type 'quit' to quit, or type 'back' to go back to the main screen";
		transactions_main_loop: while (true) {
			try {
				System.out.println("\n1) Display transactions in a given zipcode, month, and year");
				System.out.println("\n2) Display the total number and amount of transactions of a type");
				System.out.println("\n3) Display the total number and amount of transactions in a given State");
				Utilities.pseudoClearScreen(2);
				System.out.println(exitMessage);
				TransactionsRunnable transactionRun = new TransactionsRunnable();
				TransactionList transactions;
				Transaction transaction;
				String choice = scan.nextLine().trim().toLowerCase();
				switch (choice) {
				case "1":
					Utilities.pseudoClearScreen(2);
					String zipcode;
					int month, year;
					zipcode = Utilities.screenPrompt("\nPlease enter the zipcode: ", scan, "zip");
					month = Integer.valueOf(Utilities.screenPrompt("\nPlease enter the month (e.g. 01, 1, Jan, or January): ", scan, "month"));
					year = Integer.valueOf(Utilities.screenPrompt("\nPlease enter the year (e.g. 2018, or 18): ", scan, "year"));
					System.out.println("\n\n\n\n");
					transactions = transactionRun.getTransactionsByZipcode(zipcode, month, year);
					if (autosave) {
						Utilities.toCSV(transactions, "zipcode", zipcode);
					}
					System.out.println(
							"\n\n\n\n\n\n\n\nPress Enter to return to the module's main screen or type 'quit' to quit"
									.toUpperCase());
					switch (scan.nextLine().toLowerCase().trim()) {
					case "q":
					case "quit":
						scan.close();
						return false;
					default:
						Utilities.pseudoClearScreen(2);
						continue transactions_main_loop;
					}
				case "2":
					Utilities.pseudoClearScreen(2);
					String type;
					System.out.println();
					List<String> typesList = new TransactionsDaoImpl().getAllowedTransactionTypes();
					typesList.forEach(System.out::println);
					type = Utilities.screenPrompt("\nPlease enter a transaction type from the above list: ", scan,
							typesList);
					transaction = transactionRun.getTransactionsByType(type.trim().toLowerCase());
					if (autosave) {
						Utilities.toCSV(transaction, "type", type);
					}
					System.out.println(
							"\n\n\n\n\n\n\n\nPress Enter to return to the module's main screen or type 'quit' to quit"
									.toUpperCase());
					switch (scan.nextLine().toLowerCase().trim()) {
					case "q":
					case "quit":
						scan.close();
						return false;
					default:
						Utilities.pseudoClearScreen(2);
						continue transactions_main_loop;
					}
				case "3":
					Utilities.pseudoClearScreen(2);
					while (true) {
						String state = Utilities.screenPrompt("\nPlease enter branch state (e.g. New York, or NY): ", scan, "state");
						transaction = transactionRun.getTransactionsByState(state);
						if (autosave) {
							Utilities.toCSV(transaction, "state", state);
						}
						System.out.println(
								"\n\n\n\n\n\n\n\nPress Enter to return to the module's main screen or type 'quit' to quit"
										.toUpperCase());

						switch (scan.nextLine().toLowerCase().trim()) {
						case "q":
						case "quit":
							scan.close();
							return false;
						default:
							Utilities.pseudoClearScreen(2);
							continue transactions_main_loop;

						}
					}
				case "q":
				case "quit":
					scan.close();
					return false;
				case "b":
				case "back":
					if (!directlyCalled) {
						return true;
					}
				default:
					System.out.println("\nInvalid choice. Please try again.\n");
					continue transactions_main_loop;
				}
			} catch (NoResultsFoundException except) {
				except.printStackTrace();
				Utilities.flushErrors();
				continue transactions_main_loop;
			} catch (SQLException e) {
				return false;
			}
		}
	}

	private boolean autosave = true;

	/**
	 * This method can turn on and off the ability to save to CSV files. Sets the
	 * value of autoSaved.
	 *
	 * @param saveToFile True if saving to a file is desired.
	 */
	public void setAutosave(boolean saveToFile) {

		this.autosave = saveToFile;
	}

	public static void main(String[] args) {
		TransactionsModuleMain transactionMain = new TransactionsModuleMain();
		transactionMain.toMain(true);

	}
}
