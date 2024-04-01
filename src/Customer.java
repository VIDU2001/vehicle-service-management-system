import java.sql.*;

public class Customer {
    // Database connection details
    static final String url = "jdbc:mysql://localhost:3306/service_center";
    static final String user = "root";
    static final String password = "root";

    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private String address;

    // Constructor
    public Customer(int customerId, String firstName, String lastName, String email, int phoneNumber, String address) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static Customer searchCustomerById(int customerId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer customer = null;

        try {
            // Establish connection
            connection = DriverManager.getConnection(url, user, password);

            // Create SQL query
            String query = "SELECT * FROM customers WHERE customer_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, customerId);

            // Execute query
            resultSet = preparedStatement.executeQuery();

            // Check if any result is returned
            if (resultSet.next()) {
                // Extract customer information from the result set
                int id = resultSet.getInt("customer_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                int phoneNumber = resultSet.getInt("phone_number");
                String address = resultSet.getString("address");

                // Create a Customer object with the retrieved information
                customer = new Customer(id, firstName, lastName, email, phoneNumber, address);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            // Close resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return customer;
    }


    // Create a new customer
    public void create() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement createStatement = connection.prepareStatement("INSERT INTO Customers (customer_id, first_name, last_name, email, phone_number, address) VALUES (?, ?, ?, ?, ?, ?)");
            createStatement.setInt(1, customerId);
            createStatement.setString(2, firstName);
            createStatement.setString(3, lastName);
            createStatement.setString(4, email);
            createStatement.setInt(5, phoneNumber);
            createStatement.setString(6, address);
            createStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Update an existing customer
    public void update() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE Customers SET first_name=?, last_name=?, email=?, phone_number=?, address=? WHERE customer_id=?");
            updateStatement.setString(1, firstName);
            updateStatement.setString(2, lastName);
            updateStatement.setString(3, email);
            updateStatement.setInt(4, phoneNumber);
            updateStatement.setString(5, address);
            updateStatement.setInt(6, customerId);
            updateStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Delete a customer
    public void delete() {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement deleteStatement = connection.prepareStatement("DELETE FROM Customers WHERE customer_id=?");
            deleteStatement.setInt(1, customerId);
            deleteStatement.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    // Getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
