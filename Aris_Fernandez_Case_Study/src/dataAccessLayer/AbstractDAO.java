
package dataAccessLayer;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import resources.Utilities;

/**
 * Class for defining methods for connecting and disconnecting to the CDW_SAPP
 * database. These methods are for most of the remaining classes in this
 * project.
 * 
 * @author Aris Fernandez
 *
 */
public abstract class AbstractDAO {

	public Connection connection = null;
	public ResultSet resultSet = null;
	public PreparedStatement preparedStatament = null;

	/**
	 * Opens a connection to the database
	 * 
	 * @throws SQLException
	 */
	public void connect() throws SQLException {
		String username, password, server, host, port, database, url;
		try {

			FileReader propertiesReader = new FileReader("DataBaseConfig.ini");
			Properties connectionProperties = new Properties();
			connectionProperties.load(propertiesReader);

			username = connectionProperties.getProperty("username");
			password = connectionProperties.getProperty("password");
			server = connectionProperties.getProperty("server");
			host = connectionProperties.getProperty("host");
			port = connectionProperties.getProperty("port");
			database = connectionProperties.getProperty("database");
			url = "jdbc:" + server + "://" + host + ":" + port + "/" + database;

			connection = DriverManager.getConnection(url, username, password);

		} catch (SQLException except) {
			except.printStackTrace();
			Utilities.flushErrors();
			System.out.println("\nSomething went wrong while connecting to the database."
					+ "\nMake sure that the database is properly set up, and running.\nPlease try again later.");
			throw new SQLException();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/** Closes the database connection */
	public void disconnect() {

		try {
			connection.close();
		} catch (Exception except) {
			except.printStackTrace();
		}
	}
}
