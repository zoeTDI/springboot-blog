import java.sql.*;

/**
 * The DBManager class provides methods for database connection and operation.
 */
public class DBManager {

    /**
     * Gets a database connection.
     *
     * @param url The database connection URL.
     * @param user The database user.
     * @param password The database password.
     * @return Returns the Connection object, or null if the connection fails.
     */
    public Connection getConnection(String url, String user, String password) {
        Connection connection = null;
        try {
            // Load the MySQL database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection to the database
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected");
        } catch (Exception e) {
            System.out.println("Connect database failed: " + e);
        }
        return connection;
    }

    /**
     * Executes a SQL query and returns the result set.
     *
     * @param sql The SQL query statement.
     * @param connection The database connection object.
     * @return Returns the ResultSet object containing the query results, or null if the query fails.
     * @throws SQLException If an SQL exception occurs.
     */
    public ResultSet executeQuery(String sql, Connection connection) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            // Create a Statement object for executing SQL statements
            statement = connection.createStatement();
            // Execute the SQL query and get the result set
            resultSet = statement.executeQuery(sql);
            System.out.println("Query executed: " + sql);
        } catch (SQLException e) {
            System.out.println("Error executing query: " + e.getMessage());
            throw e;
        }
        return resultSet;
    }

    /**
     * Executes an SQL insert operation.
     *
     * @param sql The SQL insert statement.
     * @param conn The database connection object.
     * @return Returns true if the insert operation is successful, otherwise returns false.
     */
    public Boolean executeInsert(String sql, Connection conn) {
        try {
            // Create a Statement object for executing SQL statements
            Statement stmt = conn.createStatement();
            // Execute the SQL insert statement and get the number of affected rows
            int rowsAffected = stmt.executeUpdate(sql);
            // Close the Statement object
            stmt.close();
            // Return true if the insert operation affects more than 0 rows, otherwise return false
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Print the exception stack trace
            e.printStackTrace();
            return false;
        }
    }
}
