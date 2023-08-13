package quan_ly_cua_hang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private static final String INSERT_CUSTOMER_SQL = "INSERT INTO khachhang (idCustomer, loginCustomer, passwordCustomer, nameCustomer, cccdCustomer, emailCustomer, addressCustomer, phoneCustomer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_CUSTOMER_SQL = "UPDATE khachhang SET loginCustomer=?, passwordCustomer=?, nameCustomer=?, cccdCustomer=?, emailCustomer=?, addressCustomer=?, phoneCustomer=? WHERE idCustomer=?";
    private static final String DELETE_CUSTOMER_SQL = "DELETE FROM khachhang WHERE idCustomer=?";
    private static final String SELECT_ALL_CUSTOMERS_SQL = "SELECT * FROM khachhang";
    private static final String SELECT_CUSTOMER_BY_ID_SQL = "SELECT * FROM khachhang WHERE idCustomer=?";

    public void addCustomer(Customer customer) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CUSTOMER_SQL)) {
            preparedStatement.setString(1, customer.getIdCustomer());
            preparedStatement.setString(2, customer.getLoginCustomer());
            preparedStatement.setString(3, customer.getPasswordCustomer());
            preparedStatement.setString(4, customer.getNameCustomer());
            preparedStatement.setString(5, customer.getCccdCustomer());
            preparedStatement.setString(6, customer.getEmailCustomer());
            preparedStatement.setString(7, customer.getAddressCustomer());
            preparedStatement.setString(8, customer.getPhoneCustomer());
            preparedStatement.executeUpdate();           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCustomer(Customer customer) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_CUSTOMER_SQL)) {
            preparedStatement.setString(1, customer.getLoginCustomer());
            preparedStatement.setString(2, customer.getPasswordCustomer());
            preparedStatement.setString(3, customer.getNameCustomer());
            preparedStatement.setString(4, customer.getCccdCustomer());
            preparedStatement.setString(5, customer.getEmailCustomer());
            preparedStatement.setString(6, customer.getAddressCustomer());
            preparedStatement.setString(7, customer.getPhoneCustomer());
            preparedStatement.setString(8, customer.getIdCustomer());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCustomer(String idCustomer) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CUSTOMER_SQL)) {
            preparedStatement.setString(1, idCustomer);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_CUSTOMERS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setIdCustomer(resultSet.getString("idCustomer"));
                customer.setLoginCustomer(resultSet.getString("loginCustomer"));
                customer.setPasswordCustomer(resultSet.getString("passwordCustomer"));
                customer.setNameCustomer(resultSet.getString("nameCustomer"));
                customer.setCccdCustomer(resultSet.getString("cccdCustomer"));
                customer.setEmailCustomer(resultSet.getString("emailCustomer"));
                customer.setAddressCustomer(resultSet.getString("addressCustomer"));
                customer.setPhoneCustomer(resultSet.getString("phoneCustomer"));
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public Customer getCustomerById(String idCustomer) {
        Customer customer = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_CUSTOMER_BY_ID_SQL)) {
            preparedStatement.setString(1, idCustomer);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    customer = new Customer();
                    customer.setIdCustomer(resultSet.getString("idCustomer"));
                    customer.setLoginCustomer(resultSet.getString("loginCustomer"));
                    customer.setPasswordCustomer(resultSet.getString("passwordCustomer"));
                    customer.setNameCustomer(resultSet.getString("nameCustomer"));
                    customer.setCccdCustomer(resultSet.getString("cccdCustomer"));
                    customer.setEmailCustomer(resultSet.getString("emailCustomer"));
                    customer.setAddressCustomer(resultSet.getString("addressCustomer"));
                    customer.setPhoneCustomer(resultSet.getString("phoneCustomer"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
