package exceptions;

/**
 * Contains a list of user defined exceptions.
 * 
 * @author Aris Fernandez
 *
 */
public class CustomExceptions {
	/**
	 * Exception for when the order of two dates is not increasing. If the second
	 * date is earlier than the first, this exception must be thrown.
	 */
	public static class IncorrectDateOrderException extends Exception {

		public IncorrectDateOrderException() {
			super();

		}

		public IncorrectDateOrderException(String message) {
			super(message);

		}

		private static final long serialVersionUID = 1753656547720797225L;

	}

	/**
	 * Exception for when a day value is not valid, such as greater than 31 or less
	 * than 1.
	 */
	public static class InvalidDayValue extends Exception {

		public InvalidDayValue() {
			super();

		}

		private static final long serialVersionUID = 5446765800591945219L;

		public InvalidDayValue(String message) {
			super(message);

		}

	}

	/**
	 * Exception for when an e-mail address is not valid, such as missing the @ sign
	 * or having no domain name
	 */
	public static class InvalidEmailException extends Exception {

		public InvalidEmailException() {
			super();

		}

		public InvalidEmailException(String message) {
			super(message);

		}

		private static final long serialVersionUID = -2448559375668656447L;

	}

	/**
	 * Exception for when a month is not valid, such as greater than 12 or less than
	 * 1, or not a word corresponding to a month
	 */
	public static class InvalidMonthInputException extends Exception {

		public InvalidMonthInputException() {
			super();

		}

		public InvalidMonthInputException(String message) {
			super(message);

		}

		private static final long serialVersionUID = -5875285581371096478L;

	}

	/**
	 * Exception for when a phone number is not valid such as not 10 digits long or
	 * 7 digits long
	 */
	public static class InvalidPhoneNumberException extends Exception {

		public InvalidPhoneNumberException() {
			super();

		}

		public InvalidPhoneNumberException(String message) {
			super(message);

		}

		private static final long serialVersionUID = 5340840574039161259L;

	}

	/** Exception for when an input is not a valid US state name */
	public static class InvalidStateException extends Exception {

		public InvalidStateException() {
			super();

		}

		public InvalidStateException(String message) {
			super(message);

		}

		private static final long serialVersionUID = -1153263951040203814L;

	}

	/** Exception for when a transaction type is not found in the database */
	public static class InvalidTransactionType extends Exception {

		private static final long serialVersionUID = -7455664959148286503L;

		public InvalidTransactionType(String message) {
			super(message);

		}
	}

	/** Exception for when a year input is not in a valid format */
	public static class InvalidYearException extends Exception {

		public InvalidYearException() {
			super();
		}

		public InvalidYearException(String message) {
			super(message);

		}

		private static final long serialVersionUID = -6691031145088050299L;

	}

	/** Exception for when an inputed zip code is not in a valid format */
	public static class InvalidZipcodeException extends Exception {

		public InvalidZipcodeException() {
			super();

		}

		public InvalidZipcodeException(String message) {
			super(message);

		}

		private static final long serialVersionUID = -8005807937444353450L;

	}

	/** Exception for when a query returns an empty result set. */
	public static class NoResultsFoundException extends Exception {

		public NoResultsFoundException() {
			super();

		}

		public NoResultsFoundException(String message) {
			super(message);

		}

		private static final long serialVersionUID = -4413001304741634775L;

	}

	/** Exception for when an inputer credit card number is not 16 digits long */
	public static class WrongCreditCardFormat extends Exception {

		public WrongCreditCardFormat(String message) {
			super(message);

		}

		private static final long serialVersionUID = -4491447099554988930L;

	}

	/**
	 * Exception for when the inputed social security number is not 9 digits long
	 */
	public static class WrongSsnFormatException extends Exception {

		public WrongSsnFormatException(String message) {
			super(message);

		}

		private static final long serialVersionUID = 2359658814928209757L;

	}
}
