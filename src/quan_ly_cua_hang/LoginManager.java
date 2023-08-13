package quan_ly_cua_hang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {
    private DatabaseConnection databaseConnection;

    public LoginManager(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Customer authenticateCustomer(String username, String password) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Customer customer = null;

        try {
            conn = databaseConnection.getConnection();
            String sql = "SELECT * FROM khachhang WHERE loginCustomer = ? AND passwordCustomer = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeResources(conn, preparedStatement, resultSet);
        }

        return customer;
    }

    public Staff authenticateStaff(String username, String password) {
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Staff staff = null;

        try {
            conn = databaseConnection.getConnection();
            String sql = "SELECT * FROM nhanvien WHERE loginStaff = ? AND passwordStaff = ?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                staff = new Staff();
                staff.setIdStaff(resultSet.getString("idStaff"));
                staff.setLoginStaff(resultSet.getString("loginStaff"));
                staff.setPasswordStaff(resultSet.getString("passwordStaff"));
                staff.setNameStaff(resultSet.getString("nameStaff"));
                staff.setPhoneStaff(resultSet.getString("phoneStaff"));
                staff.setCccdStaff(resultSet.getString("cccdStaff"));
                staff.setEmailStaff(resultSet.getString("emailStaff"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeResources(conn, preparedStatement, resultSet);
        }

        return staff;
    }
}
