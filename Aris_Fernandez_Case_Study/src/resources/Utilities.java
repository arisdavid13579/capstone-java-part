package resources;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import exceptions.ExceptionResources;
import exceptions.CustomExceptions.InvalidDayValue;
import exceptions.CustomExceptions.InvalidEmailException;
import exceptions.CustomExceptions.InvalidMonthInputException;
import exceptions.CustomExceptions.InvalidPhoneNumberException;
import exceptions.CustomExceptions.InvalidStateException;
import exceptions.CustomExceptions.InvalidTransactionType;
import exceptions.CustomExceptions.InvalidYearException;
import exceptions.CustomExceptions.InvalidZipcodeException;
import exceptions.CustomExceptions.WrongCreditCardFormat;
import exceptions.CustomExceptions.WrongSsnFormatException;
import presentationLayer.CustomersRunnable;
import presentationLayer.TransactionsRunnable;
import transferObjects.Customer;
import transferObjects.Transaction;
import transferObjects.TransactionList;

/**
 * Class containing most of the methods used in other sections of the project.
 * The only purpose of this class is to serve as a method repository.
 *
 * @author Aris Fernandez
 */
public abstract class Utilities {

	/**
	 * Finds the complete absolute path to a given file.
	 *
	 * @param inputFile File object for which the absolute path is desired.
	 * @return A String with the absolute path to the given File object.
	 */
	public static String getAbsolutePath(File inputFile) {
		try {
			return inputFile.getCanonicalPath();
		} catch (Exception except) {
			except.printStackTrace();
			return null;
		}
	}

	/**
	 * Attempts to minimize/prevent the standard output overlapping the standard
	 * input. It forces the error output to print all bytes to screen, then suspends
	 * the current thread for 3 milliseconds.
	 */
	public static void flushErrors() {
		try {
			System.err.flush();
			Thread.sleep(3);
		} catch (InterruptedException except) {
			except.printStackTrace();
		}

	}

	/**
	 * Centers in string by a give
	 *
	 * @param inputString     The string that needs to be centered.
	 * @param totalOutputSize Total horizontal screen space taken by the resultant
	 *                        string (string size + padding)
	 * @return the string The input string padding to the correct size.
	 */
	public static String centerStringBy(String inputString, int totalOutputSize) {

		String outLeft, outRight;
		int inStringLength = inputString.length();
		outLeft = inputString.substring(0, (inStringLength / 2));
		outRight = inputString.substring(inStringLength / 2, inStringLength);
		int leftPadSize = totalOutputSize / 2;
		int rightPadSize = totalOutputSize - leftPadSize;
		outRight = String.format("%1$-" + rightPadSize + "s", outRight);
		outLeft = String.format("%1$" + leftPadSize + "s", outLeft);
		return outLeft + outRight;
	}

	/**
	 * Prepares csv files for transactions by zip code, customer bills, and customer
	 * transactions between two dates.
	 *
	 * @param transaction_list           The TransactionList object containing all
	 *                                   the transactions desired to be saved to a
	 *                                   file.
	 * @param transaction_grouping_type  A String indicating the type of transaction
	 *                                   to be saved. This word can be "zipcode" for
	 *                                   transactions by zip code, "bill" for
	 *                                   customer bills, or "range" for transactions
	 *                                   between two dates.
	 * @param transaction_grouping_value The value associated with the transaction
	 *                                   type. In other words, a string with a
	 *                                   zipcode number, a bill date, or a
	 *                                   combination of two dates in a date range.
	 *                                   This parameter is not very relevant to the
	 *                                   operation of the method but its value will
	 *                                   be reflected in the file name of the output
	 *                                   csv file.
	 */
	public static void toCSV(TransactionList transaction_list, String transaction_grouping_type,
			String transaction_grouping_value) {
		String filePath, fileName;
		filePath = "Output/";

		switch (transaction_grouping_type.toLowerCase().trim()) {
		case "zipcode":
			transaction_grouping_type = " by_zipcode";
			filePath = filePath + "TransactionsModule/TransactionsByZipcode/";
			break;
		case "bill":
			filePath = filePath + "CustomersModule/Bills/";
			break;
		case "range":
			filePath = filePath + "CustomersModule/TransactionsBetweenDates/";
			break;
		default:
			filePath = null;
			System.err.println("Something went wrong while preparing the csv file.");
			flushErrors();
		}
		fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
				.format(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")))) + "UTC_"
				+ transaction_grouping_type.toUpperCase().trim() + "_" + transaction_grouping_value.toUpperCase();
		try {
			File path = new File(filePath);
			path.mkdirs();
			File outfile = new File(path, fileName.toUpperCase() + ".csv");
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			writer.write(TransactionsRunnable.createCsvHeader());
			for (Transaction t : transaction_list) {
				writer.write(t.csvFormat());
			}
			writer.close();
			System.out.println();
			System.out.println("The operation was performed successfully.\nA copy of the results was saved to:\n"
					+ getAbsolutePath(outfile));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Prepares csv files for transactions by type, and transactions by branch
	 * state.
	 *
	 * @param transaction                The Transaction object containing the count
	 *                                   and value of the transactions desired to be
	 *                                   saved to file.
	 * 
	 * @param transaction_grouping_type  A String indicating the type of transaction
	 *                                   to be saved. This word can be ", or "type"
	 *                                   for transactions of specific type.
	 * @param transaction_grouping_value The value associated with the transaction
	 *                                   grouping type. In other words, a string
	 *                                   with US state, or a transaction type. This
	 *                                   parameter is not very relevant to the
	 *                                   operation of the method but its value will
	 *                                   be reflected in the file name of the output
	 *                                   csv file.
	 */
	public static void toCSV(Transaction transaction, String transaction_grouping_type,
			String transaction_grouping_value) {
		String filePath, fileName;
		filePath = "Output/";
		boolean byType;
		switch (transaction_grouping_type.toLowerCase().trim()) {
		case "state":
			filePath = filePath + "TransactionsModule/TransactionsByBranchState/";
			byType = false;
			break;
		case "type":
			filePath = filePath + "TransactionsModule/TransactionsByType/";
			byType = true;
			break;
		default:
			filePath = null;
			byType = false;
			System.err.println("Something went wrong while preparing the csv file. ");
			System.err.flush();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss")
				.format(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")))) + "UTC" + "_BY_"
				+ transaction_grouping_type.toLowerCase().trim() + "_" + transaction_grouping_value;
		try {
			File path = new File(filePath);
			path.mkdirs();
			File outfile = new File(path, fileName.toUpperCase() + ".csv");
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			writer.write(TransactionsRunnable.createTotalsCsvHeader(byType));
			writer.write(transaction.getTransactionValue() + "," + transaction.getAggregateCount() + ","
					+ transaction_grouping_value);
			writer.close();
			System.out.println();
			System.out.println("The operation was performed successfully.\nA copy of the results was saved to:\n"
					+ getAbsolutePath(outfile));
		} catch (IOException except) {
			except.printStackTrace();
		}

	}

	/**
	 * Prepares a csv files with a given customer's details.
	 *
	 * @param customer A Customer object representing the customer whose information
	 *                 is to be saved to a csv file.
	 */
	public static void toCSV(Customer[] customer) {

		String filePath, fileName, grouping, name;
		boolean toUpdate;
		filePath = "Output/";

		switch (customer.length) {
		case 1:
			filePath = filePath + "CustomersModule/CustomerDetails/";
			grouping = "details";
			name = customer[0].getFirstName() + "_" + customer[0].getLastName();
			toUpdate = false;
			break;
		case 2:
			filePath = filePath + "CustomersModule/UpdatedCustomers/";
			grouping = "update";
			toUpdate = true;
			name = customer[1].getFirstName() + "_" + customer[1].getLastName();
			break;
		default:
			filePath = null;
			toUpdate = false;
			grouping = null;
			name = null;
			System.err.println("Something went wrong while preparing the csv file.");
			Utilities.flushErrors();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
			}
		}
		fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(
				Timestamp.valueOf(LocalDateTime.now(ZoneId.of("UTC")))) + "UTC" + "_BY_" + grouping + "_" + name;
		try {
			File path = new File(filePath);
			path.mkdirs();
			File outfile = new File(path, fileName.toUpperCase() + ".csv");
			BufferedWriter writer = new BufferedWriter(new FileWriter(outfile));
			writer.write(CustomersRunnable.createCsvHeader(toUpdate));
			if (toUpdate) {
				writer.write("BEFORE," + customer[0].csvFormat());
				writer.write("AFTER," + customer[1].csvFormat());

			} else {
				writer.write(customer[0].csvFormat());

			}

			writer.close();
			System.out.println();
			System.out.println("The operation was performed successfully.\nA copy of the results was saved to:\n"
					+ getAbsolutePath(outfile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints a prompt to the screen and validates the input. The prompt is
	 * continuously shown as long as the given inputs are not valid.
	 *
	 * @param prompt          The prompt message to appear on screen.
	 * @param keyboardScanner The scanner object used to connect to the standard
	 *                        input.
	 * @param promptType      The kind of prompt to be shown on screen. This
	 *                        parameter can be "ssn" for a social security number
	 *                        prompt, "card" for a credit card number prompt,
	 *                        "state" for a branch state prompt, "zip" for a zip
	 *                        code prompt, "email" for an email address prompt,
	 *                        "phone" for a phone number prompt, "year" for a
	 *                        calendar year, "month" for a calendar month, and "day"
	 *                        for a calendar day prompt.
	 * @return Returns a string representation of the validated input.
	 */
	public static String screenPrompt(String prompt, Scanner keyboardScanner, String promptType) {

		String result = null;
		prompt_while: while (true) {
			System.out.println(prompt);
			try {
				if (keyboardScanner.hasNext()) {
					switch (promptType) {
					case "":
						result = keyboardScanner.nextLine();
						break;
					case "ssn":
						result = ExceptionResources.ssnValidator(keyboardScanner.nextLine());
						break;
					case "card":
						result = ExceptionResources.creditCardValidator(keyboardScanner.nextLine());
						break;
					case "state":
						result = ExceptionResources.stateValidator(keyboardScanner.nextLine());
						break;

					case "zip":
						result = ExceptionResources.zipcodeValidator(keyboardScanner.nextLine());
						break;

					case "email":
						result = ExceptionResources.emailValidator(keyboardScanner.nextLine());
						break;

					case "phone":
						result = String.valueOf(ExceptionResources.phoneNoValidator(keyboardScanner.nextLine()));
						break;

					case "year":
						result = String.valueOf(ExceptionResources.yearValidator(keyboardScanner.nextLine()));
						break;

					case "month":
						result = String.valueOf(ExceptionResources.monthValidator(keyboardScanner.nextLine()));
						break;

					case "day":
						result = String.valueOf(ExceptionResources.dayValidator(keyboardScanner.nextLine()));
						break;
					default:
						System.err.println("Invalid prompt type. Please check your code");
						flushErrors();
						break prompt_while;
					}
				}
			} catch (WrongSsnFormatException | WrongCreditCardFormat | InvalidZipcodeException
					| InvalidMonthInputException | InvalidPhoneNumberException | InvalidEmailException
					| InvalidStateException | InvalidYearException | InvalidDayValue | NoSuchElementException except) {
				except.printStackTrace();
				flushErrors();
				continue prompt_while;
			}
			break;
		}
		return result;
	}

	/**
	 * Prints a prompt to the screen and validates the input. The prompt is
	 * continuously shown as long as the given inputs are not valid. Only used to
	 * validate transaction types.
	 * 
	 * @param prompt          The prompt message to appear on screen.
	 * @param keyboardScanner The Scanner object used to connect to the standard
	 *                        output.
	 * @param typesList       A list of strings with allowed transaction types.
	 * @return Returns a validated transaction type.
	 */
	public static String screenPrompt(String prompt, Scanner keyboardScanner, List<String> typesList) {
		prompt_loop: while (true) {
			try {

				String output = ExceptionResources.typeValidator(typesList,
						Utilities.screenPrompt(prompt, keyboardScanner, ""));
				return output;
			} catch (InvalidTransactionType | NoSuchElementException except) {
				except.printStackTrace();
				flushErrors();
				continue prompt_loop;
			}

		}
	}

	/**
	 * Prints to screen a given number of blank lines possibly to simulate clearing
	 * the console screen.
	 *
	 * @param lineCount The number of blank lines to be printed.
	 */
	public static void pseudoClearScreen(int lineCount) {
		System.out.print(Stream.generate(() -> "\n").limit(lineCount).collect(Collectors.joining("")));
	}
}
