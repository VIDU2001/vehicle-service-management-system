import java.sql.*;

public class Vehicle {
    static final String url = "jdbc:mysql://localhost:3306/service_center";
    static final String user = "root";
    static final String password = "root";

    private int vehicleId;
    private int customerId;
    private String make;
    private String model;
    private int year;
    private int mileage;
    private Date lastServiceDate;
    private Date nextServiceDate;

    public Vehicle(int vehicleId, int customerId, String make, String model, int year, int mileage, Date lastServiceDate, Date nextServiceDate) {
        this.vehicleId = vehicleId;
        this.customerId = customerId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.lastServiceDate = lastServiceDate;
        this.nextServiceDate = nextServiceDate;
    }

    public static Vehicle searchVehicleById(int vehicleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Vehicle vehicle = null;

        try {
            connection = DriverManager.getConnection(url, user, password);
            String query = "SELECT * FROM vehicles WHERE vehicle_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, vehicleId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("vehicle_id");
                int customerId = resultSet.getInt("customer_id");
                String make = resultSet.getString("make");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                int mileage = resultSet.getInt("mileage");
                Date lastServiceDate = resultSet.getDate("last_service_date");
                Date nextServiceDate = resultSet.getDate("next_service_date");

                vehicle = new Vehicle(id, customerId, make, model, year, mileage, lastServiceDate, nextServiceDate);
            }
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        return vehicle;
    }

    public void createVehicle() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "INSERT INTO vehicles (vehicle_id, customer_id, make, model, year, mileage, last_service_date, next_service_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, vehicleId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setString(3, make);
            preparedStatement.setString(4, model);
            preparedStatement.setInt(5, year);
            preparedStatement.setInt(6, mileage);
            preparedStatement.setDate(7, lastServiceDate);
            preparedStatement.setDate(8, nextServiceDate);
            preparedStatement.executeUpdate();
            conn.close();
            System.out.println("Vehicle inserted successfully.");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void updateVehicle() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "UPDATE vehicles SET customer_id=?, make=?, model=?, year=?, mileage=?, last_service_date=?, next_service_date=? WHERE vehicle_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, vehicleId);
            preparedStatement.setInt(2, customerId);
            preparedStatement.setString(3, make);
            preparedStatement.setString(4, model);
            preparedStatement.setInt(5, year);
            preparedStatement.setInt(6, mileage);
            preparedStatement.setDate(7, lastServiceDate);
            preparedStatement.setDate(8, nextServiceDate);

            preparedStatement.executeUpdate();
            conn.close();
            System.out.println("Vehicle updated successfully.");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void deleteVehicle() {
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            String query = "DELETE FROM vehicles WHERE vehicle_id=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, vehicleId);
            preparedStatement.executeUpdate();
            conn.close();
            System.out.println("Vehicle deleted successfully.");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }


    public int getVehicleId() {
        return vehicleId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public Date getLastServiceDate() {
        return lastServiceDate;
    }

    public Date getNextServiceDate() {
        return nextServiceDate;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setLastServiceDate(Date lastServiceDate) {
        this.lastServiceDate = lastServiceDate;
    }

    public void setNextServiceDate(Date nextServiceDate) {
        this.nextServiceDate = nextServiceDate;
    }
}
