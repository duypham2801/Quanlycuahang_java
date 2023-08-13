package quan_ly_cua_hang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StaffDAO {
    private static final String INSERT_STAFF_SQL = "INSERT INTO nhanvien (idStaff, loginStaff, passwordStaff, nameStaff, phoneStaff, cccdStaff, emailStaff) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_STAFF_SQL = "UPDATE nhanvien SET loginStaff=?, passwordStaff=?, nameStaff=?, phoneStaff=?, cccdStaff=?, emailStaff=? WHERE idStaff=?";
    private static final String DELETE_STAFF_SQL = "DELETE FROM nhanvien WHERE idStaff=?";
    private static final String SELECT_ALL_STAFF_SQL = "SELECT * FROM nhanvien";
    private static final String SELECT_STAFF_BY_ID_SQL = "SELECT * FROM nhanvien WHERE idStaff=?";

    public void addStaff(Staff staff) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_STAFF_SQL)) {
            preparedStatement.setString(1, staff.getIdStaff());
            preparedStatement.setString(2, staff.getLoginStaff());
            preparedStatement.setString(3, staff.getPasswordStaff());
            preparedStatement.setString(4, staff.getNameStaff());
            preparedStatement.setString(5, staff.getPhoneStaff());
            preparedStatement.setString(6, staff.getCccdStaff());
            preparedStatement.setString(7, staff.getEmailStaff());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStaff(Staff staff) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_STAFF_SQL)) {
            preparedStatement.setString(1, staff.getLoginStaff());
            preparedStatement.setString(2, staff.getPasswordStaff());
            preparedStatement.setString(3, staff.getNameStaff());
            preparedStatement.setString(4, staff.getPhoneStaff());
            preparedStatement.setString(5, staff.getCccdStaff());
            preparedStatement.setString(6, staff.getEmailStaff());
            preparedStatement.setString(7, staff.getIdStaff());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStaff(String idStaff) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_STAFF_SQL)) {
            preparedStatement.setString(1, idStaff);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Staff> getAllStaff() {
        List<Staff> staffList = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_STAFF_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setIdStaff(resultSet.getString("idStaff"));
                staff.setLoginStaff(resultSet.getString("loginStaff"));
                staff.setPasswordStaff(resultSet.getString("passwordStaff"));
                staff.setNameStaff(resultSet.getString("nameStaff"));
                staff.setPhoneStaff(resultSet.getString("phoneStaff"));
                staff.setCccdStaff(resultSet.getString("cccdStaff"));
                staff.setEmailStaff(resultSet.getString("emailStaff"));
                staffList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public Staff getStaffById(String idStaff) {
        Staff staff = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_STAFF_BY_ID_SQL)) {
            preparedStatement.setString(1, idStaff);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return staff;
    }
}
