package presentationLayer;

import java.util.Scanner;

/**
 * @author Aris Fernandez
 *
 */
public class Main {

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		CustomersModuleMain customerMain = new CustomersModuleMain();
		TransactionsModuleMain transactionMain = new TransactionsModuleMain();
		main_loop: while (true) {
			try {
				System.out.println("\n1) Customer Module");
				System.out.println("\n2) Transaction Module");
				System.out.println("\n\n\n\n");
				System.out.println("Type 'quit' to exit program");
				String choice = scan.nextLine().trim().toLowerCase();

				switch (choice) {
				case "1":
					if (!customerMain.toMain(false)) {
						break main_loop;
					}
					break;
				case "2":
					if (!transactionMain.toMain(false)) {
						break main_loop;
					}
					break;

				case "quit":
				case "q":
					break main_loop;
				default:
					System.out.println("\nInvalid choice. Please try again.\n");
					continue main_loop;

				}

			} catch (Exception except) {
				except.printStackTrace();
			}
		}
		scan.close();

	}
}
