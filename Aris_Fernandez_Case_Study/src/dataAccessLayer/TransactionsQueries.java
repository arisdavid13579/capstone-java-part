package dataAccessLayer;

/**
 * Contains the MySQL queries used by the JDBC prepared statements for the
 * transaction modules.
 * 
 * @author Aris Fernandez
 *
 */
public interface TransactionsQueries {
	// 1.1
	String TRANSACTIONS_BY_ZIPCODE_QUERY = "SELECT cc.*"
			+ " FROM cdw_sapp_customer cust INNER JOIN cdw_sapp_creditcard cc "
			+ " ON cc.credit_card_no = cust.credit_card_no " + " AND cc.CUST_SSN = cust.SSN "
			+ " WHERE cust.cust_zip = ? " + " AND cc.MONTH = ? " + " AND cc.YEAR = ?	" + " ORDER BY 2 DESC";
	// 1.2
	String TRANSACTIONS_BY_TYPE_QUERY = "SELECT COUNT(TRANSACTION_VALUE) AS 'Transaction_Counts', SUM(TRANSACTION_VALUE) AS 'Sum_Values' "
			+ "FROM cdw_sapp_creditcard " + "WHERE transaction_type = ?";
	// 1.3
	String TRANSACTIONSP_BY_STATE_QUERY = "SELECT COUNT(TRANSACTION_VALUE) AS 'Transaction_Counts', SUM(TRANSACTION_VALUE) AS 'Sum_Values' "
			+ "FROM cdw_sapp_creditcard INNER JOIN cdw_sapp_branch ON cdw_sapp_creditcard.branch_code = cdw_sapp_branch.BRANCH_CODE "
			+ "WHERE BRANCH_STATE = ?";

	// List of allowed transaction types
	String ALLOWED_TRANSACTION_TYPES_QUERY = " SELECT DISTINCT transaction_type FROM cdw_sapp_creditcard";
}
