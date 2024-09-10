package com.comcast.crm.generic.databaseutility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseQueryWaitUtility1 {
	public static void main(String[] args) {
        String url = "jdbc:your_database_url";
        String user = "your_database_user";
        String password = "your_database_password";
        String query = "SELECT * FROM your_table WHERE condition";
        int interval = 1000;  // Interval in milliseconds
        long maxWaitTime = 60000;  // Maximum wait time in milliseconds (e.g., 60 seconds)

        try {
            boolean resultFound = waitForQueryCompletion(url, user, password, query, interval, maxWaitTime);
            if (resultFound) {
                System.out.println("Query result found and processed.");
            } else {
                System.out.println("No result found within the maximum wait period.");
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean waitForQueryCompletion(String url, String user, String password, String query, int interval, long maxWaitTime)
            throws SQLException, InterruptedException {
        long startTime = System.currentTimeMillis();

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            while (System.currentTimeMillis() - startTime <= maxWaitTime) {
                try (ResultSet rs = stmt.executeQuery(query)) {
                    if (rs.next()) {
                        // Process the result as needed
                        return true;
                    }
                }
                System.out.println("No result found, waiting...");
                Thread.sleep(interval);  // Wait for the specified interval before checking again
            }
        }
        return false;  // Return false if the maximum wait time is exceeded
    }

}
