package presentationLayer;

import java.sql.SQLException;
import java.util.Scanner;

import dataAccessLayer.CustomersDaoImpl;
import exceptions.CustomExceptions.IncorrectDateOrderException;
import exceptions.CustomExceptions.NoResultsFoundException;
import exceptions.ExceptionResources;
import resources.Utilities;
import transferObjects.Customer;
import transferObjects.TransactionList;

/**
 * The presentation layer for the customer module. This program can be run independently of the Main program.
 *
 * @author Aris Fernandez
 */
public class CustomersModuleMain {

	/**
	 * This contains all the logic to be run in the main method.
	 *
	 * @param directlyCalled Must true if the method is being called directly from
	 *                       the CustomersModuleMain class.
	 * @return Returns true if upon closure of this class, the calling class must
	 *         stay running.
	 */
	public boolean toMain(boolean directlyCalled) {
		boolean clearScreen = false;
		Scanner scan = new Scanner(System.in);
		String exitMessage = (directlyCalled) ? "Type 'quit' to quit"
				: "Type 'quit' to quit, or type 'back' to go back to the main screen";
		customer_main_loop: while (true) {
			if (clearScreen) {
				Utilities.pseudoClearScreen(2);
			}
			System.out.println("\n1) Display the details of a customer");
			System.out.println("\n2) Update the details of a customer");
			System.out.println("\n3) Generate monthly bill for a customer");
			System.out.println("\n4) Show customer transactions in a date range");
			System.out.println("\n\n\n\n\n\n\n\n" + exitMessage);
			CustomersRunnable customerRun = new CustomersRunnable();
			String choice = scan.nextLine().trim().toLowerCase();
			String creditcard, ssn;
			switch (choice) {
			case "1":

				clearScreen = true;
				first_while: while (true) {
					ssn = Utilities.screenPrompt("\nPlease enter the customer's Social Security Number (9 digits, no dashes): ", scan, "ssn");
					creditcard = Utilities.screenPrompt("\nPlease enter the customer's credit card number (16 digits, no dashes): ", scan,
							"card");
					try {
						if (clearScreen) {
							Utilities.pseudoClearScreen(2);
						}
						Customer[] customer = { customerRun.getCustomerDetails(ssn, creditcard) };
						if (autoSaved) {
							Utilities.toCSV(customer);
						}
						System.out.println(
								"\n\n\n\n\n\n\n\nPress Enter to return to the module's main screen or type 'quit' to quit"
										.toUpperCase());

						switch (scan.nextLine().toLowerCase().trim()) {
						case "quit":
						case "q":
							scan.close();
							return false;
						default:
							continue customer_main_loop;

						}
					} catch (NoResultsFoundException except) {
						clearScreen = false;
						except.printStackTrace();
						Utilities.flushErrors();
						continue first_while;
					} catch (SQLException e) {
						return false;
					}

				}
			case "2":
				Customer oldCustomerInfo, newCustomerInfo;
				String column;
				Object value;
				if (clearScreen) {
					Utilities.pseudoClearScreen(2);
				}
				clearScreen = true;
				second_while: while (true) {
					ssn = Utilities.screenPrompt("\nPlease enter the customer's Social Security Number (9 digits, no dashes): ", scan, "ssn");
					creditcard = Utilities.screenPrompt("\nPlease enter the customer's credit card number (16 digits, no dashes): ", scan,
							"card");
					try {
						if (clearScreen) {
							Utilities.pseudoClearScreen(2);
						}
						boolean invalidChoice = false;
						oldCustomerInfo = new CustomersDaoImpl().getCustomerDetails(ssn, creditcard);
						newCustomerInfo = null;
						Customer[] oldNewCustomerInfo = new Customer[2];
						String custTableInfo = customerRun.customerTableInfo(oldCustomerInfo);
						System.out.println("Customer found");
						System.out.println("Current customer details are as follows:\n" + custTableInfo);
						int updateCount = 0;
						update_while: while (true) {
							oldNewCustomerInfo[0] = oldCustomerInfo;
							oldNewCustomerInfo[1] = newCustomerInfo;
							if ((updateCount > 0) && !invalidChoice) {
								boolean yesNo = false;
								do {

									System.out.println("\nWould you like to change anything else?(yes/no)\n");
									switch (scan.nextLine().trim().toLowerCase()) {
									case "yes":
									case "y":
										yesNo = false;
										break;
									case "n":
									case "no":
										if (autoSaved) {
											Utilities.toCSV(oldNewCustomerInfo);
										}
										System.out.println(
												"\n\n\n\n\n\n\n\nPress Enter to return to the module's main screen or type 'quit' to quit"
														.toUpperCase());

										switch (scan.nextLine().toLowerCase().trim()) {
										case "quit":
										case "q":
											scan.close();
											return false;
										default:
											continue customer_main_loop;

										}

									default:
										System.err.println("\nPlease type yes or no\n");
										Utilities.flushErrors();
										yesNo = true;
									}
								} while (yesNo);
							}

							System.out.println("\nWhat information would you like to change?\n");
							System.out.println("\n1)  First Name");
							System.out.println("\n2)  Middle Name");
							System.out.println("\n3)  Last Name");
							System.out.println("\n4)  Apartment Number");
							System.out.println("\n5)  Street Name");
							System.out.println("\n6)  City");
							System.out.println("\n7)  State");
							System.out.println("\n8)  Country");
							System.out.println("\n9)  Zipcode");
							System.out.println("\n10) Phone Number");
							System.out.println("\n11) E-mail Address");
							System.out.println(
									"\n\n\n\nType 'back' to return to the previous menu, or 'quit' to exit the program.");

							String input = scan.nextLine();

							switch (input.toLowerCase().trim()) {
							case "1":
								column = "FIRST_NAME";
								value = Utilities.screenPrompt("\nPlease enter the new first name: \n", scan, "");
								invalidChoice = false;
								break;
							case "2":
								column = "MIDDLE_NAME";
								value = Utilities.screenPrompt("\nPlease enter the new middle name: \n", scan, "");
								invalidChoice = false;
								break;
							case "3":
								column = "LAST_NAME";
								value = Utilities.screenPrompt("\nPlease enter the new last name: \n", scan, "");
								invalidChoice = false;
								break;
							case "4":
								column = "APT_NO";
								value = Utilities.screenPrompt("\nPlease enter the new apartment number: \n", scan, "");
								invalidChoice = false;
								break;
							case "5":
								column = "STREET_NAME";
								value = Utilities.screenPrompt("\nPlease enter the new street name: \n", scan, "");
								invalidChoice = false;
								break;
							case "6":
								column = "CUST_CITY";
								value = Utilities.screenPrompt("\nPlease enter the new city name: \n", scan, "");
								invalidChoice = false;
								break;
							case "7":
								column = "CUST_STATE";
								value = Utilities.screenPrompt("\nPlease enter the new state name (e.g. New York, or NY): \n", scan, "state");
								invalidChoice = false;
								break;
							case "8":
								column = "CUST_COUNTRY";
								value = Utilities.screenPrompt("\nPlease enter the new country name: \n", scan, "");
								invalidChoice = false;
								break;
							case "9":
								column = "CUST_ZIP";
								value = Utilities.screenPrompt("\nPlease enter the new zipcode: \n", scan, "zip");
								invalidChoice = false;
								break;
							case "10":
								column = "CUST_PHONE";
								value = Integer.valueOf(Utilities
										.screenPrompt("\nPlease enter the new phone number (10 digits, or 7 digits, no dashes): \n", scan, "phone"));
								invalidChoice = false;
								break;
							case "11":
								column = "CUST_EMAIL";
								value = Utilities.screenPrompt("\nPlease enter the new e-mail address (e.g. example@example.com) : \n", scan,
										"email");
								invalidChoice = false;
								break;
							case "b":
							case "back":
								if (updateCount > 0) {
									if (autoSaved) {
										Utilities.toCSV(oldNewCustomerInfo);
									}
								}
								column = null;
								break second_while;
							case "quit":
							case "q":
								if (updateCount > 0) {
									if (autoSaved) {
										Utilities.toCSV(oldNewCustomerInfo);
									}
								}
								column = null;
								scan.close();
								return false;
							default:
								column = null;
								value = null;
								invalidChoice = true;
								System.out.println("\nInvalid choice. Please try again.");
								continue update_while;

							}
							if (clearScreen) {
								Utilities.pseudoClearScreen(2);
							}
							newCustomerInfo = customerRun.updateCustomerDetails(ssn, creditcard, column, value);

							updateCount++;
						}

					} catch (NoResultsFoundException except) {
						clearScreen = false;
						except.printStackTrace();
						Utilities.flushErrors();
						continue second_while;
					} catch (SQLException e) {
						return false;
					}

				}
				break;
			case "3":
				TransactionList bill = new TransactionList();
				Customer customer;
				int year, month;
				if (clearScreen) {
					Utilities.pseudoClearScreen(2);
				}
				clearScreen = true;
				third_while: while (true) {
					ssn = Utilities.screenPrompt("\nPlease enter the customer's Social Security Number (9 digits, no dashes): ", scan, "ssn");
					creditcard = Utilities.screenPrompt("\nPlease enter the customer's credit card number (16 digits, no dashes): ", scan,
							"card");

					year = Integer.valueOf(Utilities.screenPrompt("\nPlease enter billing year (e.g. 2018, or 18):\n", scan, "year"));
					month = Integer.valueOf(Utilities.screenPrompt("\nPlease enter billing month (e.g. 01, 1, Jan, or January):\n", scan, "month"));

					try {
						if (clearScreen) {
							Utilities.pseudoClearScreen(2);
						}
						customer = new CustomersDaoImpl().getCustomerDetails(ssn, creditcard);
						bill = customerRun.getCustomerBill(customer, month, year);
						if (autoSaved) {
							Utilities.toCSV(bill, "bill",
									customer.getFirstName() + "_" + customer.getLastName() + "_" + year + "_" + month);
						}
						System.out.println(
								"\n\n\n\n\n\n\n\nPress Enter to return to the module's main screen or type 'quit' to quit"
										.toUpperCase());

						switch (scan.nextLine().toLowerCase().trim()) {
						case "quit":
						case "q":
							scan.close();
							return false;
						default:
							continue customer_main_loop;

						}
					} catch (NoResultsFoundException except) {
						except.printStackTrace();
						Utilities.flushErrors();
						clearScreen = false;
						System.out.println("\nPlease try again\n");
						continue third_while;
					} catch (SQLException e) {
						return false;
					}

				}

			case "4":
				TransactionList transactions = new TransactionList();
				int[] output = new int[6];
				if (clearScreen) {
					Utilities.pseudoClearScreen(2);
				}
				clearScreen = true;
				fourth_while: while (true) {
					ssn = Utilities.screenPrompt("\nPlease enter the customer's Social Security Number (9 digits, no dashes): ", scan, "ssn");
					creditcard = Utilities.screenPrompt("\nPlease enter the customer's credit card number (16 digits, no dashes): ", scan,
							"card");
					range_while: while (true) {

						if (clearScreen) {
							Utilities.pseudoClearScreen(2);
						}
						output[2] = Integer.valueOf(Utilities.screenPrompt("\nFrom Year (e.g. 2018, or 18):\n", scan, "year"));
						output[1] = Integer.valueOf(Utilities.screenPrompt("\nFrom Month (e.g. 01, 1, Jan, or January):\n", scan, "month"));
						output[0] = Integer.valueOf(Utilities.screenPrompt("\nFrom Day:\n", scan, "day"));
						output[5] = Integer.valueOf(Utilities.screenPrompt("\nTo Year:\n", scan, "year"));
						output[4] = Integer.valueOf(Utilities.screenPrompt("\nTo Month:\n", scan, "month"));
						output[3] = Integer.valueOf(Utilities.screenPrompt("\nFrom Day:\n", scan, "day"));

						try {
							ExceptionResources.checkDateOrder(output[0], output[1], output[2], output[3], output[4],
									output[5]);
						} catch (IncorrectDateOrderException except) {
							except.printStackTrace();
							Utilities.flushErrors();
							clearScreen = false;
							continue range_while;
						}
						break;
					}
					try {
						if (clearScreen) {
							Utilities.pseudoClearScreen(2);
						}
						transactions = customerRun.getTransactionRange(ssn, creditcard, output[0], output[1], output[2],
								output[3], output[4], output[5]);
						if (autoSaved) {
							Utilities.toCSV(transactions, "range", "FROM_" + output[2] + "_" + output[1] + "_"
									+ output[0] + "_TO_" + output[5] + "_" + output[4] + "_" + output[3]);
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
							continue customer_main_loop;

						}
					} catch (NoResultsFoundException except) {
						except.printStackTrace();
						Utilities.flushErrors();
						clearScreen = false;
						System.out.println("\nPlease try again\n");
						continue fourth_while;
					} catch (SQLException e) {
						return false;
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
				System.err.println("\nInvalid choice. Please try again.\n");
				clearScreen = false;
				Utilities.flushErrors();
				continue customer_main_loop;

			}

		}

	}

	private boolean autoSaved = true;

	/**
	 * This method can turn on and off the ability to save to CSV files. Sets the
	 * value of autoSaved.
	 *
	 * @param autoSaved true if saving to a file is desired.
	 */
	public void setAutosave(boolean autoSaved) {

		this.autoSaved = autoSaved;
	}

	public static void main(String[] args) {
		CustomersModuleMain customerMain = new CustomersModuleMain();

		customerMain.toMain(true);

	}
}
