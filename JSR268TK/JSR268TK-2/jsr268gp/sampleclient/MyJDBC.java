package jsr268gp.sampleclient;

import java.sql.*;

public class MyJDBC {
    // JDBC URL for MySQL database connection
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cp2_project";
    private static final String USERNAME = "root"; // Username for database connection
    private static final String PASSWORD = "cp2_project"; // Password for database connection

    // Establish a connection to the database
    public static Connection connectToDataBase() throws SQLException, ClassNotFoundException {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connect to the database using the provided URL, username, and password
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            // If the JDBC driver class is not found, print an error message
            System.err.println("Failed to load MySQL JDBC driver.");
            // Rethrow the exception to notify the caller about the failure
            throw e;
        } catch (SQLException e) {
            // If a SQL exception occurs during connection, print an error message
            System.err.println("Failed to connect to the database.");
            // Rethrow the exception to notify the caller about the failure
            throw e;
        }
    }


    public static Client[] getClients (Connection connection , int numberUsersPerPage , long pageNumber) throws SQLException {
        ResultSet foundClients = null ;
        long offset = (pageNumber - 1) * numberUsersPerPage;
        Client[] clients = new Client[numberUsersPerPage];
        String sqlQuery = "SELECT * FROM clients LIMIT ? OFFSET ?";
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(sqlQuery);
            // Set the placeholder to client card number
            statement.setLong(1, numberUsersPerPage);
            statement.setLong(2, offset);
            // Execute the search
            foundClients = statement.executeQuery();

            int index = 0;
            while (foundClients.next()) {
                Client client = new Client(foundClients.getLong(1),
                                           String.valueOf(foundClients.getString(2)),
                                           foundClients.getString(3),
                                           foundClients.getString(4),
                                           foundClients.getString(5),
                                           foundClients.getBytes(6),
                                           foundClients.getBytes(7));
                clients[index++] = client;
            }
            return clients;
        } catch (SQLException e) {
            // If a SQL exception occurs during client search, print an error message
            System.err.println("Error finding client: " + e.getMessage());
            // Rethrow the exception to propagate it
            throw e;
        } catch (UnsupportedClassVersionError e) {
            // Handle the UnsupportedClassVersionError
            System.err.println("Unsupported Java version. Please switch to JDK 1.7 or higher.");
            // Optionally, you can choose to gracefully terminate the program or log the error
            // depending on your application's requirements.
            // System.exit(1); // Terminate the program
            // log.error("Unsupported Java version", e); // Log the error
        } finally {
            // Close resources in finally block
            if (foundClients != null) {
                try {
                    foundClients.close();
                } catch (SQLException e) {
                    System.err.println("Error closing result set: " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Error closing statement: " + e.getMessage());
                }
            }
        }
		return clients;

    }

    // Search for client and retrieve their record
    public static boolean searchClient(Connection connection, long cardNumber) throws SQLException {
    	ResultSet foundClient = null;
    	String sqlQuery = "SELECT * FROM clients WHERE card_number = ?";
    	try {
    	    PreparedStatement statement = connection.prepareStatement(sqlQuery);
    	    // Set the placeholder to client card number
    	    statement.setLong(1, cardNumber);
    	    // Execute the search
    	    foundClient = statement.executeQuery();
    	    // Return true if a client is found, otherwise false
    	    return foundClient.next();
    	} catch (SQLException e) {
    	    // If a SQL exception occurs during client search, print an error message
    	    System.err.println("Error finding client: " + e.getMessage());
    	    // Rethrow the exception to propagate it
    	    throw e;
    	} catch (UnsupportedClassVersionError e) {
    	    // Handle the UnsupportedClassVersionError
    	    System.err.println("Unsupported Java version. Please switch to JDK 1.7 or higher.");
    	    // Optionally, you can choose to gracefully terminate the program or log the error
    	    // depending on your application's requirements.
    	    // System.exit(1); // Terminate the program
    	    // log.error("Unsupported Java version", e); // Log the error
    	} finally {
    	    // Close the ResultSet in the finally block
    	    if (foundClient != null) {
    	        try {
    	            foundClient.close();
    	        } catch (SQLException e) {
    	            System.err.println("Error closing result set: " + e.getMessage());
    	        }
    	    }
    	}
		return foundClient.next();
    }

    public static Client getClientData(Connection connection, long cardNumber) throws SQLException {
    	ResultSet foundClients = null;
    	Client client = null;
    	String sqlQuery = "SELECT * FROM clients WHERE card_number = ?";
    	try {
    	    PreparedStatement statement = connection.prepareStatement(sqlQuery);
    	    // Set the placeholder to client card number
    	    statement.setLong(1, cardNumber);
    	    // Execute the search
    	    foundClients = statement.executeQuery();

    	    if (foundClients.next()) {
    	        client = new Client(foundClients.getLong(1),
    	                            String.valueOf(foundClients.getString(2)),
    	                            foundClients.getString(3),
    	                            foundClients.getString(4),
    	                            foundClients.getString(5),
    	                            foundClients.getBytes(6),
    	                            foundClients.getBytes(7));
    	    }
    	    return client;
    	} catch (SQLException e) {
    	    // If a SQL exception occurs during client search, print an error message
    	    System.err.println("Error finding client: " + e.getMessage());
    	    // Rethrow the exception to propagate it
    	    throw e;
    	} catch (UnsupportedClassVersionError e) {
    	    // Handle the UnsupportedClassVersionError
    	    System.err.println("Unsupported Java version. Please switch to JDK 1.7 or higher.");
    	    // Optionally, you can choose to gracefully terminate the program or log the error
    	    // depending on your application's requirements.
    	    // System.exit(1); // Terminate the program
    	    // log.error("Unsupported Java version", e); // Log the error
    	} finally {
    	    // Close the ResultSet in the finally block
    	    if (foundClients != null) {
    	        try {
    	            foundClients.close();
    	        } catch (SQLException e) {
    	            System.err.println("Error closing result set: " + e.getMessage());
    	        }
    	    }
    	}
		return client;
    }

    // Add a client to the database
    public static void addClient(Connection connection, Client client) throws SQLException {
    	String sqlQuery = "INSERT INTO clients (card_number, card_expiring_date, first_name, last_name, user_adress, public_key, server_private_key) VALUES (?, ?, ?, ?, ?, ?, ?)";
    	try {
    	    PreparedStatement statement = connection.prepareStatement(sqlQuery);
    	    // Set the statement parameters for the client data
    	    setStatement(statement, client);
    	    // Execute the update to add the client to the database
    	    statement.executeUpdate();
    	} catch (SQLException e) {
    	    // If a SQL exception occurs during client addition, print an error message
    	    System.err.println("Error adding client: " + e.getMessage());
    	    // Rethrow the exception to propagate it
    	    throw e;
    	} catch (UnsupportedClassVersionError e) {
    	    // Handle the UnsupportedClassVersionError
    	    System.err.println("Unsupported Java version. Please switch to JDK 1.7 or higher.");
    	    // Optionally, you can choose to gracefully terminate the program or log the error
    	    // depending on your application's requirements.
    	    // System.exit(1); // Terminate the program
    	    // log.error("Unsupported Java version", e); // Log the error
    	}
    }

    // Set the statement parameters for adding a client
    public static void setStatement(PreparedStatement statement, Client client) throws SQLException {
        try {
            // Set each parameter of the prepared statement with client data
            statement.setLong(1, client.getCardNumber());
            statement.setDate(2, Date.valueOf(client.getExpiringDate()));
            statement.setString(3, client.getFirstName());
            statement.setString(4, client.getLastName());
            statement.setString(5, client.getUserAdress());
            statement.setBytes(6, client.getPublicKey());
            statement.setBytes(7,client.getServerPrivateKey());
        } catch (SQLException e) {
            // If a SQL exception occurs during parameter setting, print an error message
            System.err.println("Error setting statement: " + e.getMessage());
            // Rethrow the exception to propagate it
            throw e;
        }
    }

    // Delete client from database
    public static void deleteClient(Connection connection, long cardNumber) throws SQLException {
    	String sqlQuery = "DELETE FROM clients WHERE card_number = ?";
    	try {
    	    PreparedStatement statement = connection.prepareStatement(sqlQuery);
    	    // Set the placeholder to client card number
    	    statement.setLong(1, cardNumber);
    	    // Execute the update to delete the client from the database
    	    statement.executeUpdate();
    	} catch (SQLException e) {
    	    // If a SQL exception occurs during client deletion, print an error message
    	    System.err.println("Error deleting client: " + e.getMessage());
    	    // Rethrow the exception to propagate it
    	    throw e;
    	} catch (UnsupportedClassVersionError e) {
    	    // Handle the UnsupportedClassVersionError
    	    System.err.println("Unsupported Java version. Please switch to JDK 1.7 or higher.");
    	    // Optionally, you can choose to gracefully terminate the program or log the error
    	    // depending on your application's requirements.
    	    // System.exit(1); // Terminate the program
    	    // log.error("Unsupported Java version", e); // Log the error
    	}
    }

    // Edit client information in the database
    public static void editClient(Connection connection, Client client) throws SQLException {
    	String sqlQuery = "UPDATE clients SET first_name = ?, last_name = ?, user_adress = ? WHERE card_number = ?";
    	try {
    	    PreparedStatement statement = connection.prepareStatement(sqlQuery);
    	    // Set the new values for first name, last name, and user address
    	    statement.setString(1, client.getFirstName());
    	    statement.setString(2, client.getLastName());
    	    statement.setString(3, client.getUserAdress());
    	    // Set the placeholder to client card number
    	    statement.setLong(4, client.getCardNumber());
    	    // Execute the update to edit client information
    	    statement.executeUpdate();
    	} catch (SQLException e) {
    	    // If a SQL exception occurs during client edit, print an error message
    	    System.err.println("Error editing client: " + e.getMessage());
    	    // Rethrow the exception to propagate it
    	    throw e;
    	} catch (UnsupportedClassVersionError e) {
    	    // Handle the UnsupportedClassVersionError
    	    System.err.println("Unsupported Java version. Please switch to JDK 1.7 or higher.");
    	    // Optionally, you can choose to gracefully terminate the program or log the error
    	    // depending on your application's requirements.
    	    // System.exit(1); // Terminate the program
    	    // log.error("Unsupported Java version", e); // Log the error
    	}
    }

    // Close database connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                // Print message indicating successful closure of the connection
            } catch (SQLException e) {
                // If an error occurs while closing the connection, print an error message
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
}