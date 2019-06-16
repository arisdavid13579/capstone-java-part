package exceptions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import exceptions.CustomExceptions.IncorrectDateOrderException;
import exceptions.CustomExceptions.InvalidDayValue;
import exceptions.CustomExceptions.InvalidEmailException;
import exceptions.CustomExceptions.InvalidMonthInputException;
import exceptions.CustomExceptions.InvalidPhoneNumberException;
import exceptions.CustomExceptions.InvalidStateException;
import exceptions.CustomExceptions.InvalidTransactionType;
import exceptions.CustomExceptions.InvalidYearException;
import exceptions.CustomExceptions.InvalidZipcodeException;
import exceptions.CustomExceptions.NoResultsFoundException;
import exceptions.CustomExceptions.WrongCreditCardFormat;
import exceptions.CustomExceptions.WrongSsnFormatException;
import resources.Month;
import resources.State;

/**
 * The Class ExceptionResources.
 *
 * @author Aris Fernandez
 */
public abstract class ExceptionResources {

	/**
	 * Validates the type input for the transactions by type module. It compares a
	 * given type against a list of valid types.
	 *
	 * @param typeList The already valid list of transaction types.
	 * @param type     The given type to test.
	 * @return Returns the corresponding validated transaction type from the
	 *         database.
	 * @throws InvalidTransactionType If given transaction is not found in the
	 *                                Transaction list.
	 */
	public static String typeValidator(List<String> typeList, String type) throws InvalidTransactionType {
		String output = typeList.stream().filter(t -> type.trim().equalsIgnoreCase(t)).findFirst().orElse(null);

		if (output == null) {
			throw new InvalidTransactionType("\nInvalid transaction type.\nPlease type the correct type.");
		} else {
			return output;
		}
	}

	/**
	 * Validates the year input for the transactions by type module, transactions by
	 * zip code module, customer bill module, and the customer transactions in a
	 * date range module. If a 2-digit year is inputed, it gets converted to the
	 * 20XX format.
	 *
	 * @param year The input year.
	 * @return The validated year.
	 * @throws InvalidYearException If a year is not valid, such as not being a
	 *                              valid number.
	 */
	public static int yearValidator(String year) throws InvalidYearException {
		int output;
		year = year.replace('-', ' ').trim();
		try {
			if (year.length() == 2) {
				output = Integer.valueOf("20" + year); // 2-digit years will be considered to be from 20XX.
				return output;
			} else if (year.length() == 4) {
				output = Integer.valueOf(year);
				return output;

			} else {
				throw new InvalidYearException("The entered year is not valid.\nPlease try again.");
			}
		} catch (NumberFormatException e) {
			throw new InvalidYearException("The entered year is not valid.\nPlease try again.");
		}
	}

	/**
	 * Compares two dates two check if the initial date
	 *
	 * @param from_Day   The day of initial date in the range.
	 * @param from_Month The month of initial date in the range.
	 * @param from_Year  The year of initial date in the range.
	 * @param to_Day     The day of final date in the range.
	 * @param to_Month   The month of final date in the range.
	 * @param to_Year    The year of final date in the range.
	 * @return A TrasactionList object with a list transactions happening in the
	 *         given date range.
	 * @throws IncorrectDateOrderException the incorrect date order exception
	 */
	public static void checkDateOrder(int from_Day, int from_Month, int from_Year, int to_Day, int to_Month,
			int to_Year) throws IncorrectDateOrderException {
		String fDay, fMonth, fYear, tDay, tMonth, tYear, fDate, tDate;
		Date fromDate, toDate;
		fDay = String.valueOf(from_Day);
		fMonth = String.valueOf(from_Month);
		fYear = String.valueOf(from_Year);
		tDay = String.valueOf(to_Day);
		tMonth = String.valueOf(to_Month);
		tYear = String.valueOf(to_Year);
		fDate = fDay + "/" + fMonth + "/" + fYear;
		tDate = tDay + "/" + tMonth + "/" + tYear;
		try {
			fromDate = new SimpleDateFormat("dd/MM/yyyy").parse(fDate);
			toDate = new SimpleDateFormat("dd/MM/yyyy").parse(tDate);
			if (fromDate.compareTo(toDate) > 0) {
				System.out.println();
				throw new IncorrectDateOrderException("Invalid date range.\nThe FROM("
						+ new SimpleDateFormat("yyyy/MM/dd").format(fromDate) + ") date is more recent than the TO("
						+ new SimpleDateFormat("yyyy/MM/dd").format(toDate) + ") date.");
			}
		} catch (ParseException except) {
			except.printStackTrace();
		}

	}

	/**
	 * Day validator. A valid day must be greater than 0 and less than 32.
	 *
	 * @param day The day number to check
	 * @return Returns the validated day number.
	 * @throws InvalidDayValue If the input day is not valid.
	 */
	public static int dayValidator(String day) throws InvalidDayValue {
		long longDay = 0;
		int result = 0;
		try {
			longDay = Long.valueOf(day);
			if ((longDay < 1) || (longDay > 31)) {
				throw new InvalidDayValue("Inputed day is not valid.\n Please enter an integer from 1 to 31");
			}
		} catch (NumberFormatException except) {
			throw new InvalidDayValue("Inputed day is not valid.\n Please enter an integer from 1 to 31");
		}
		result = (int) longDay;
		return result;
	}

	/**
	 * Validates a given e-mail address. E-mail addresses must be at least of the
	 * form 'a@b.cde' so at least 7 characters-long with a . 3rd-to-last place and
	 * contain '@'
	 *
	 * @param email The e-mail address to validate.
	 * @return Returns the validated e-mail address.
	 * @throws InvalidEmailException If the input e-mail is not valid.
	 */
	public static String emailValidator(String email) throws InvalidEmailException {
		boolean condition;
		email = email.trim();

		condition = (email.charAt(email.length() - 4) == '.') && (email.contains("@"));
		condition = condition && (email.length() > 6);
		condition = condition && (email.indexOf("@") == email.lastIndexOf("@")); // checks for unique @
		condition = condition && ((email.indexOf("@") > 0)); // @ not the first character
		condition = condition && (email.indexOf("@") < (email.length() - 5)); // @ not of the last 4 characters.

		if (!condition) {
			throw new InvalidEmailException(
					"The inputed e-mail address is not valid.\n Please provide a valid e-mail address.");
		}
		return email;
	}

	/**
	 * Month validator. A valid month can be a month number or a month name.
	 *
	 * @param month The input month to validate.
	 * @return Returns the number of the validated month.
	 * @throws InvalidMonthInputException If the input month is not valid.
	 */
	public static int monthValidator(String month) throws InvalidMonthInputException {
		String errorMessage = "Invalid month entered.\nPlease enter either the whole month name, the abbreviated month name, or the month number.";
		try {
			month = month.trim().toUpperCase();
			month = (month.charAt(0) == '0' && month.length() > 1) ? month.substring(1) : month;
			month = (month.charAt(month.length() - 1) == '.') ? month.substring(0, month.length() - 1) : month;
			month = "_" + month;
			return Month.valueOf(month).getMonthNo();
		} catch (NumberFormatException except) {
			throw new InvalidMonthInputException(errorMessage);
		} catch (IllegalArgumentException except) {
			throw new InvalidMonthInputException(errorMessage);
		} catch (StringIndexOutOfBoundsException except) {
			throw new InvalidMonthInputException(errorMessage);
		}
	}

	/**
	 * Validates input phone numbers.
	 *
	 * @param phoneNo Input phone number
	 * @return Returns the validated phone number
	 * @throws InvalidPhoneNumberException If the input phone number is not valid.
	 *                                     Not ten or seven digits long.
	 */
	public static int phoneNoValidator(String phoneNo) throws InvalidPhoneNumberException {

		try {
			long temp_result = Long.valueOf(phoneNo);
			int result = (int) temp_result;

			if (phoneNo.length() != 7 | phoneNo.length() != 10) {
				throw new InvalidPhoneNumberException(
						"Phone Numbers must be 7 or 10 digits long.Do not include the area code.\nThe entered number is "
								+ phoneNo.length() + " digits long. Please provide a valid phone number.");
			} else {
				return result;
			}
		} catch (NumberFormatException except) {
			System.out.println();
			throw new InvalidPhoneNumberException(
					"The entered value is not an integer.\nValid phone numbers are 7-digit-long or 10-digit-long integers without any other symbol.");
		}
	}

	/**
	 * Validates input US states. A valid US state can a two letter abbreviation
	 * such as NY or the whole state name such as New York
	 *
	 * @param state The input US state.
	 * @return Returns the validated US state.
	 * @throws InvalidStateException If the input state is not a valid US state.
	 */
	public static String stateValidator(String state) throws InvalidStateException {
		try {

			String properState;
			List<String> properStateList = Arrays.asList(state.trim().split(" ", 2));
			properStateList = properStateList.stream().map(x -> x.toUpperCase().trim()).collect(Collectors.toList());
			properState = String.join("_", properStateList);
			State usState = State.valueOf(properState);
			return usState.toString();

		} catch (IllegalArgumentException except) {
			System.out.println();
			throw new InvalidStateException(
					"The Inputed State is not a Valid US State.\nInput Must Be the State's Full Name or the 2-Letter Abbreviation.\nPlease input a valid state.");
		}

	}

	/**
	 * Validates input postal codes.
	 *
	 * @param zipcode The input zip code
	 * @return Returns a validated zip code
	 * @throws InvalidZipcodeException If the input zip code is not valid.
	 */
	public static String zipcodeValidator(String zipcode) throws InvalidZipcodeException {
		String message = "The given zipcode is not valid.\nZipcodes are 5-digit-long integers composed of just numbers without any symbols.\nThe input zipcode was "
				+ zipcode.length() + " characters long.\nPlease enter a valid zipcode.";
		try {

			if (zipcode.length() != 5) {
				throw new InvalidZipcodeException(message);
			}

			Integer.valueOf(zipcode);
			return zipcode;
		} catch (NumberFormatException except) {
			throw new InvalidZipcodeException(message);
		}
	}

	/**
	 * Checks if a query returns a non-empty result set.
	 *
	 * @param resultSet    The input result set to check for results.
	 * @param errorMessage The error message to print to screen if the result set is
	 *                     empty.
	 * @throws NoResultsFoundException If the result set is empty.
	 */
	public static void hasResults(ResultSet resultSet, String errorMessage) throws NoResultsFoundException {
		try {
			if (!resultSet.first()) {
				throw new NoResultsFoundException(errorMessage);
			}
			resultSet.beforeFirst();
		} catch (SQLException except) {
			except.printStackTrace();
		}
	}

	/**
	 * Validates credit card number. It checks if an input credit card number is
	 * 16-digits long.
	 *
	 * @param creditCard The input credit card number.
	 * @return Returns a string representation of the validated credit card number.
	 * @throws WrongCreditCardFormat If the credit card number is not valid.
	 */
	public static String creditCardValidator(String creditCard) throws WrongCreditCardFormat {
		String message = "\nInvalid credit card number.\n"
				+ "A valid credit cards number consists of 16-digit-long integers. Please provide a valid card number.";
		try {
			Long.valueOf(creditCard.trim()); // Exception will be thrown if the string is not a valid number
			if (creditCard.trim().length() != 16) {
				throw new WrongCreditCardFormat(message);
			}
			return creditCard.trim();
		} catch (NumberFormatException except) {
			throw new WrongCreditCardFormat(message);
		}

	}

	/**
	 * Validates input social security numbers. A valid ssn is 9-digits long.
	 *
	 * @param ssn The social security number to check.
	 * @return Returns a string representation of the validated credit card number.
	 * @throws WrongSsnFormatException If the credit card number is not valid.
	 */
	public static String ssnValidator(String ssn) throws WrongSsnFormatException {
		String message = "\nInvalid Social Security number.\n"
				+ "A valid  Social Security number consists of 9-digit-long integers. Please provide a number.";
		try {
			Long.valueOf(ssn.trim()); // Exception will be thrown if the string is not a valid number
			if (ssn.trim().length() != 9) {
				throw new WrongSsnFormatException(message);
			}
			return ssn.trim();
		} catch (NumberFormatException except) {
			throw new WrongSsnFormatException(message);
		}

	}

}
