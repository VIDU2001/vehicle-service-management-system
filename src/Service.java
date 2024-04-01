import java.sql.*;

public class Service {
    static final String url = "jdbc:mysql://localhost:3306/service_center";
    static final String user = "root";
    static final String password = "root";

    private static int serviceId;
    private int vehicleId;
    private String serviceType;
    private Date serviceDate;
    private double serviceCost;

    public Service(int serviceId, int vehicleId, String serviceType, Date serviceDate, double serviceCost) {
        Service.serviceId = serviceId;
        this.vehicleId = vehicleId;
        this.serviceType = serviceType;
        this.serviceDate = serviceDate;
        this.serviceCost = serviceCost;
    }

    public static Service searchServiceById(int serviceId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Service service = null;

        try {

            connection = DriverManager.getConnection(url, user, password);


            String query = "SELECT * FROM services WHERE service_id = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, serviceId);


            resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {

                int id = resultSet.getInt("service_id");
                int vehicleId = resultSet.getInt("vehicle_id");
                String serviceType = resultSet.getString("service_type");
                Date serviceDate = resultSet.getDate("service_date");
                double serviceCost = resultSet.getDouble("service_cost");


                service = new Service(id, vehicleId, serviceType, serviceDate, serviceCost);
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

        return service;
    }

    public static Vehicle searchVehicleById(int vehicleId) {
        return null;
    }



        public void createService() {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/service_center", "root", "root");
                String query = "INSERT INTO services (service_id, vehicle_id, service_type, service_date, service_cost) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, serviceId);
                preparedStatement.setInt(2, this.vehicleId);
                preparedStatement.setString(3, this.serviceType);
                preparedStatement.setDate(4, this.serviceDate);
                preparedStatement.setDouble(5, this.serviceCost);
                preparedStatement.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        public void updateService() {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/service_center", "root", "root");
                String query = "UPDATE services SET vehicle_id=?, service_type=?, service_date=?, service_cost=? WHERE service_id=?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, this.vehicleId);
                preparedStatement.setString(2, this.serviceType);
                preparedStatement.setDate(3, this.serviceDate);
                preparedStatement.setDouble(4, this.serviceCost);
                preparedStatement.setInt(5, this.serviceId);
                preparedStatement.executeUpdate();
                conn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        public void deleteService() {
            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/service_center", "root", "root");
                String query = "DELETE FROM services WHERE service_id=?";
                try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
                    preparedStatement.setInt(1, this.serviceId);
                    preparedStatement.executeUpdate();
                }
                conn.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }



    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        Service.serviceId = serviceId;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }


}