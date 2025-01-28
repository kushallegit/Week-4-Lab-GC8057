package mysqljdbc;

// Import necessary JDBC classes
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class TableDataPrinter {

    // Define JDBC connection parameters
    static final String URL = "jdbc:mysql://localhost:3306/week4_lab"; // Replace 'testdb' with your database name
    static final String USER = "root"; // Replace with your MySQL username
    static final String PASSWORD = "NHkushal1"; // Replace with your MySQL password

    public static void main(String[] args) {
        // Specify the table name you want to print data from
        String tableName = "employees"; // Replace 'users' with your table name

        // Call the method to print data from the specified table
        printTableData(tableName);
    }

    /**
     * Connects to the database and prints all data from the specified table.
     * The method dynamically handles any table structure.
     *
     * @param tableName the name of the table to retrieve data from
     */
    public static void printTableData(String tableName) {
        String query = "SELECT * FROM " + tableName;

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                System.out.println("Connected to the database successfully.");

                // Retrieve and print the metadata and data
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnName(i) + "\t");
                }
                System.out.println();

                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(rs.getObject(i) + "\t");
                    }
                    System.out.println();
                }

            }
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Make sure it is in the classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error retrieving data from table: " + tableName);
            e.printStackTrace();
        }
    }
}

