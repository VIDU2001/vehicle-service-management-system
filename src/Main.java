import javax.swing.*; // For Swing components like JFrame, JPanel, etc.
import java.awt.*; // For AWT components and layout managers
import java.awt.event.ActionEvent; // For ActionEvent class
import java.awt.event.ActionListener; // For ActionListener interface
import java.sql.Date; // For handling SQL Date objects

public class Main extends JFrame implements ActionListener {
    // Declaration of various Swing components
    private JTextField userTextField; // Text field for username input
    private JPasswordField passwordField; // Password field for password input
    private final JPanel loginPanel; // Panel for login components
    private final JPanel customerPanel; // Panel for customer management components
    private final JPanel servicePanel; // Panel for service management components
    private final JPanel vehiclePanel; // Panel for vehicle management components
    private JTextArea searchResultArea; // Text area to display search results

    public Main() {
        // Setting up the main JFrame
        setTitle("Service Center Management System"); // Setting window title
        setSize(700, 400); // Setting window size
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Exiting the application when window is closed
        setLayout(new BorderLayout()); // Setting layout for the main frame
        setResizable(false); // Disabling frame resizing

        // Creating different panels for login, customer, service, and vehicle management
        loginPanel = createLoginPanel(); // Creating login panel
        customerPanel = createCustomerPanel(); // Creating customer management panel
        servicePanel = createServicePanel(); // Creating service management panel
        vehiclePanel = createVehiclePanel(); // Creating vehicle management panel

        // Adding the login panel to the main frame
        add(loginPanel, BorderLayout.CENTER);

        // Making the main frame visible
        setVisible(true);
    }

    // Creating login panel
    private JPanel createLoginPanel() {
        // Creating panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE); // Setting background color
        GridBagConstraints gbc = new GridBagConstraints(); // Creating constraints for GridBagLayout
        gbc.insets = new Insets(10, 10, 10, 10); // Setting insets for components

        // Adding title label to the login panel
        JLabel titleLabel = new JLabel("Service Center Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Setting font for title
        titleLabel.setForeground(Color.BLUE); // Setting text color for title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Spanning title label across 2 columns
        panel.add(titleLabel, gbc);

        // Adding username label and text field to the login panel
        JLabel userLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(userLabel, gbc);
        userTextField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(userTextField, gbc);

        // Adding password label and password field to the login panel
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        // Adding login button to the login panel
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this); // Adding action listener to the login button
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Setting horizontal fill for button
        panel.add(loginButton, gbc);

        return panel; // Returning the login panel
    }

    // Creating the customer panel with BorderLayout
    private JPanel createCustomerPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Creating labels and text fields for customer information
        JLabel idLabel = new JLabel("Customer ID:");
        JTextField idField = new JTextField();
        inputPanel.add(idLabel);
        inputPanel.add(idField);

        JLabel firstNameLabel = new JLabel("First Name:");
        JTextField firstNameField = new JTextField();
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        JTextField lastNameField = new JTextField();
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        JLabel phoneLabel = new JLabel("Phone Number:");
        JTextField phoneField = new JTextField();
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);

        JLabel addressLabel = new JLabel("Address:");
        JTextField addressField = new JTextField();
        inputPanel.add(addressLabel);
        inputPanel.add(addressField);

        // Creating buttons for adding, updating, and deleting customers
        JButton addButton = new JButton("Add Customer");
        addButton.addActionListener(e -> {
            // Action performed when add button is clicked
            // Getting input values from text fields
            int customerId = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            int phoneNumber = Integer.parseInt(phoneField.getText());
            String address = addressField.getText();
            // Creating a new Customer object with input values
            Customer customer = new Customer(customerId, firstName, lastName, email, phoneNumber, address);
            // Calling the create() method to add the customer to the database
            customer.create();
            JOptionPane.showMessageDialog(Main.this, "Customer added successfully!");// Showing success message
        });

        JButton updateButton = new JButton("Update Customer");
        updateButton.addActionListener(e -> {
            int customerId = Integer.parseInt(idField.getText());
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            int phoneNumber = Integer.parseInt(phoneField.getText());
            String address = addressField.getText();
            Customer customer = new Customer(customerId, firstName, lastName, email, phoneNumber, address);
            customer.update();
            JOptionPane.showMessageDialog(Main.this, "Customer updated successfully!");
        });

        JButton deleteButton = new JButton("Delete Customer");
        deleteButton.addActionListener(e -> {
            int customerId = Integer.parseInt(idField.getText());
            Customer customer = new Customer(customerId, "", "", "", 0, "");
            customer.delete();
            JOptionPane.showMessageDialog(Main.this, "Customer deleted successfully!");
        });

        JButton searchServiceButton = getSearchServiceButton(); // Corrected method name
        inputPanel.add(searchServiceButton); // Added the correct button

        JButton searchVehicleButton = getSearchVehicleButton(); // New search vehicle button
        inputPanel.add(searchVehicleButton);

        JButton searchButton; // Corrected method name
        searchButton = getSearchButton();
        inputPanel.add(searchButton); // Added the correct button


        JButton manageServiceButton = new JButton("Manage Services");
        manageServiceButton.addActionListener(e -> {
            remove(customerPanel);
            add(servicePanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
        JButton manageVehicleButton = new JButton("Manage Vehicles");
        manageVehicleButton.addActionListener(e -> {
            remove(customerPanel);
            remove(servicePanel);
            add(vehiclePanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        // Adding components to the input panel
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(searchButton);
        inputPanel.add(searchServiceButton);
        inputPanel.add(searchVehicleButton);
        inputPanel.add(manageServiceButton);
        inputPanel.add(manageVehicleButton);

        // Adding the input panel and a scrollable text area for search results to the customer panel
        searchResultArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(searchResultArea);
        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createVehiclePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel vehicleIdLabel = new JLabel("Vehicle ID:");
        JTextField vehicleIdField = new JTextField();
        inputPanel.add(vehicleIdLabel);
        inputPanel.add(vehicleIdField);

        JLabel customerIdLabel = new JLabel("Customer ID:");
        JTextField customerIdField = new JTextField();
        inputPanel.add(customerIdLabel);
        inputPanel.add(customerIdField);

        JLabel makeLabel = new JLabel("Make:");
        JTextField makeField = new JTextField();
        inputPanel.add(makeLabel);
        inputPanel.add(makeField);

        JLabel modelLabel = new JLabel("Model:");
        JTextField modelField = new JTextField();
        inputPanel.add(modelLabel);
        inputPanel.add(modelField);

        JLabel yearLabel = new JLabel("Year:");
        JTextField yearField = new JTextField();
        inputPanel.add(yearLabel);
        inputPanel.add(yearField);

        JLabel mileageLabel = new JLabel("Mileage:");
        JTextField mileageField = new JTextField();
        inputPanel.add(mileageLabel);
        inputPanel.add(mileageField);

        JLabel lastServiceDateLabel = new JLabel("Last Service Date (yyyy-mm-dd):");
        JTextField lastServiceDateField = new JTextField();
        inputPanel.add(lastServiceDateLabel);
        inputPanel.add(lastServiceDateField);

        JLabel nextServiceDateLabel = new JLabel("Next Service Date (yyyy-mm-dd):");
        JTextField nextServiceDateField = new JTextField();
        inputPanel.add(nextServiceDateLabel);
        inputPanel.add(nextServiceDateField);

        JButton addButton = new JButton("Add Vehicle");
        addButton.addActionListener(e -> {
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            int customerId = Integer.parseInt(customerIdField.getText());
            String make = makeField.getText();
            String model = modelField.getText();
            int year = Integer.parseInt(yearField.getText());
            int mileage = Integer.parseInt(mileageField.getText());
            Date lastServiceDate = Date.valueOf(lastServiceDateField.getText());
            Date nextServiceDate = Date.valueOf(nextServiceDateField.getText());
            Vehicle vehicle = new Vehicle(vehicleId, customerId, make, model, year, mileage, lastServiceDate, nextServiceDate);
            vehicle.createVehicle();
            JOptionPane.showMessageDialog(Main.this, "Vehicle added successfully!");
        });

        JButton updateButton = new JButton("Update Vehicle");
        updateButton.addActionListener(e -> {
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            int customerId = Integer.parseInt(customerIdField.getText());
            String make = makeField.getText();
            String model = modelField.getText();
            int year = Integer.parseInt(yearField.getText());
            int mileage = Integer.parseInt(mileageField.getText());
            Date lastServiceDate = Date.valueOf(lastServiceDateField.getText());
            Date nextServiceDate = Date.valueOf(nextServiceDateField.getText());
            Vehicle vehicle = new Vehicle(vehicleId, customerId, make, model, year, mileage, lastServiceDate, nextServiceDate);
            vehicle.updateVehicle();
            JOptionPane.showMessageDialog(Main.this, "Vehicle updated successfully!");
        });

        JButton deleteButton = new JButton("Delete Vehicle");
        deleteButton.addActionListener(e -> {
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            Vehicle vehicle = new Vehicle(vehicleId, 0, "", "", 0, 0, null, null);
            vehicle.deleteVehicle();
            JOptionPane.showMessageDialog(Main.this, "Vehicle deleted successfully!");
        });

        JButton searchButton = new JButton("Search Vehicle");
        searchButton.addActionListener(e -> {
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            Vehicle vehicle = Vehicle.searchVehicleById(vehicleId);
            if (vehicle != null) {
                displaySearchResult(vehicle);
            } else {
                JOptionPane.showMessageDialog(Main.this, "Vehicle not found!");
            }
        });

        JButton backButton = new JButton("Back to Customers");
        backButton.addActionListener(e -> {
            remove(vehiclePanel);
            add(customerPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(backButton);


        panel.add(inputPanel, BorderLayout.NORTH);


        return panel;
    }

    private JPanel createServicePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel serviceIdLabel = new JLabel("Service ID:");
        JTextField serviceIdField = new JTextField();
        inputPanel.add(serviceIdLabel);
        inputPanel.add(serviceIdField);

        JLabel vehicleIdLabel = new JLabel("Vehicle ID:");
        JTextField vehicleIdField = new JTextField();
        inputPanel.add(vehicleIdLabel);
        inputPanel.add(vehicleIdField);

        JLabel serviceTypeLabel = new JLabel("Service Type:");
        JTextField serviceTypeField = new JTextField();
        inputPanel.add(serviceTypeLabel);
        inputPanel.add(serviceTypeField);

        JLabel serviceDateLabel = new JLabel("Service Date (yyyy-mm-dd):");
        JTextField serviceDateField = new JTextField();
        inputPanel.add(serviceDateLabel);
        inputPanel.add(serviceDateField);

        JLabel serviceCostLabel = new JLabel("Service Cost:");
        JTextField serviceCostField = new JTextField();
        inputPanel.add(serviceCostLabel);
        inputPanel.add(serviceCostField);

        JButton addButton = new JButton("Add Service");
        addButton.addActionListener(e -> {
            int serviceId = Integer.parseInt(serviceIdField.getText());
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            String serviceType = serviceTypeField.getText();
            Date serviceDate = Date.valueOf(serviceDateField.getText());
            double serviceCost = Double.parseDouble(serviceCostField.getText());
            Service service = new Service(serviceId, vehicleId, serviceType, serviceDate, serviceCost);
            service.createService();
            JOptionPane.showMessageDialog(Main.this, "Service added successfully!");

        });


        JButton updateButton = new JButton("Update Service");
        updateButton.addActionListener(e -> {
            int serviceId = Integer.parseInt(serviceIdField.getText());
            int vehicleId = Integer.parseInt(vehicleIdField.getText());
            String serviceType = serviceTypeField.getText();
            Date serviceDate = Date.valueOf(serviceDateField.getText());
            double serviceCost = Double.parseDouble(serviceCostField.getText());
            Service service = new Service(serviceId, vehicleId, serviceType, serviceDate, serviceCost);
            service.updateService();
            JOptionPane.showMessageDialog(Main.this, "Service updated successfully!");
        });

        JButton deleteButton = new JButton("Delete Service");
        deleteButton.addActionListener(e -> {
            int serviceId = Integer.parseInt(serviceIdField.getText());
            Service service = new Service(serviceId, 0, "", null, 0.0);
            service.deleteService();
            JOptionPane.showMessageDialog(Main.this, "Service deleted successfully!");
        });

        JButton searchButton = getjButton();

        JButton backButton = new JButton("Back to Customers");
        backButton.addActionListener(e -> {
            remove(servicePanel);
            add(customerPanel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });

        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(backButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        return panel;
    }

    private JButton getjButton() {
        return null;
    }

    // Method to create a button for searching a service by ID
    private JButton getSearchServiceButton() {
        JButton searchServiceButton = new JButton("Search Service"); // Create a new button with label "Search Service"
        searchServiceButton.addActionListener(e -> {
            // Action performed when the button is clicked
            String input = JOptionPane.showInputDialog(Main.this, "Enter the service ID to search:"); // Prompt the user to enter a service ID
            if (input != null && !input.isEmpty()) { // Check if the input is not empty
                try {
                    int serviceId = Integer.parseInt(input); // Parse the input as an integer
                    Service service = Service.searchServiceById(serviceId); // Search for the service by ID
                    if (service != null) { // Check if the service is found
                        displaySearchResult(service); // If found, display its details
                    } else {
                        JOptionPane.showMessageDialog(Main.this, "Service not found!"); // Show message if service not found
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Main.this, "Invalid input! Please enter a valid service ID."); // Show message for invalid input
                }
            } else {
                JOptionPane.showMessageDialog(Main.this, "No input provided!"); // Show message if no input is provided
            }
        });
        return searchServiceButton; // Return the created button
    }

    // Method to display the details of a service
    private void displaySearchResult(Service service) {
        // Display the details of the service in the searchResultArea
        searchResultArea.setText("Service ID: " + service.getServiceId() + "\n"
                + "Vehicle ID: " + service.getVehicleId() + "\n"
                + "Service Type: " + service.getServiceType() + "\n"
                + "Service Date: " + service.getServiceDate() + "\n"
                + "Service Cost: " + service.getServiceCost());
    }

    public JButton getSearchButton() {
        JButton searchButton = new JButton("Search Customer");
        searchButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(Main.this, "Enter the customer ID to search:");
            if (input != null && !input.isEmpty()) {
                try {
                    int customerId = Integer.parseInt(input);
                    Customer customer = Customer.searchCustomerById(customerId);
                    if (customer != null) {
                        displaySearchResult(customer);
                    } else {
                        JOptionPane.showMessageDialog(Main.this, "Customer not found!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Main.this, "Invalid input! Please enter a valid customer ID.");
                }
            } else {
                JOptionPane.showMessageDialog(Main.this, "No input provided!");
            }
        });
        return searchButton;
    }

    private void displaySearchResult(Customer customer) {
        searchResultArea.setText("Customer ID: " + customer.getCustomerId() + "\n"
                + "First Name: " + customer.getFirstName() + "\n"
                + "Last Name: " + customer.getLastName() + "\n"
                + "Email: " + customer.getEmail() + "\n"
                + "Phone Number: " + customer.getPhoneNumber() + "\n"
                + "Address: " + customer.getAddress());
    }

    private JButton getSearchVehicleButton() {
        JButton searchVehicleButton = new JButton("Search Vehicle");
        searchVehicleButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(Main.this, "Enter the vehicle ID to search:");
            if (input != null && !input.isEmpty()) {
                try {
                    int vehicleId = Integer.parseInt(input);
                    Vehicle vehicle = Vehicle.searchVehicleById(vehicleId);

                    if (vehicle != null) {
                        displaySearchResult(vehicle);
                    } else {
                        JOptionPane.showMessageDialog(Main.this, "Vehicle not found!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(Main.this, "Invalid input! Please enter a valid vehicle ID.");
                }
            } else {
                JOptionPane.showMessageDialog(Main.this, "No input provided!");
            }
        });
        return searchVehicleButton;
    }

    private void displaySearchResult(Vehicle vehicle) {
        searchResultArea.setText("Vehicle ID: " + vehicle.getVehicleId() + "\n"
                + "Customer ID: " + vehicle.getCustomerId() + "\n"
                + "Make: " + vehicle.getMake() + "\n"
                + "Model: " + vehicle.getModel() + "\n"
                + "Year: " + vehicle.getYear() + "\n"
                + "Mileage: " + vehicle.getMileage() + "\n"
                + "Last Service Date: " + vehicle.getLastServiceDate() + "\n"
                + "Next Service Date: " + vehicle.getNextServiceDate());
    }

    // Method to handle the login action
    public void actionPerformed(ActionEvent e) {
        // Get the username and password entered by the user
        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        // Check if the username and password match the admin credentials
        if (username.equals("admin") && password.equals("admin")) {
            // If login is successful, display a success message
            JOptionPane.showMessageDialog(this, "Login Successful!");
            // Remove the login panel and add the customer panel
            remove(loginPanel);
            add(customerPanel, BorderLayout.CENTER);
            // Revalidate and repaint the frame to reflect the changes
            revalidate();
            repaint();
        } else {
            // If login fails, display an error message
            JOptionPane.showMessageDialog(this, "Invalid username or password. Please try again.");
            // Clear the text fields
            userTextField.setText("");
            passwordField.setText("");
        }
    }

    // Main method to start the application
    public static void main(String[] args) {
        new Main(); // Create a new instance of the Main class
    }
}