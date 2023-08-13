package quan_ly_cua_hang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShippingInfoDAO {
    private static final String INSERT_SHIPPING_INFO_SQL = "INSERT INTO thongtinvanchuyen (idTransport, methodTransport, addressTransport) VALUES (?, ?, ?)";
    private static final String UPDATE_SHIPPING_INFO_SQL = "UPDATE thongtinvanchuyen SET methodTransport=?, addressTransport=? WHERE idTransport=?";
    private static final String DELETE_SHIPPING_INFO_SQL = "DELETE FROM thongtinvanchuyen WHERE idTransport=?";
    private static final String SELECT_ALL_SHIPPING_INFOS_SQL = "SELECT * FROM thongtinvanchuyen";
    private static final String SELECT_SHIPPING_INFO_BY_ID_SQL = "SELECT * FROM thongtinvanchuyen WHERE idTransport=?";

    public void addShippingInfo(ShippingInfo shippingInfo) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SHIPPING_INFO_SQL)) {
            preparedStatement.setString(1, shippingInfo.getIdTransport());
            preparedStatement.setString(2, shippingInfo.getMethodTransport());
            preparedStatement.setString(3, shippingInfo.getAddressTransport());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateShippingInfo(ShippingInfo shippingInfo) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_SHIPPING_INFO_SQL)) {
            preparedStatement.setString(1, shippingInfo.getMethodTransport());
            preparedStatement.setString(2, shippingInfo.getAddressTransport());
            preparedStatement.setString(3, shippingInfo.getIdTransport());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteShippingInfo(String idTransport) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_SHIPPING_INFO_SQL)) {
            preparedStatement.setString(1, idTransport);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ShippingInfo> getAllShippingInfos() {
        List<ShippingInfo> shippingInfos = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_SHIPPING_INFOS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                ShippingInfo shippingInfo = new ShippingInfo();
                shippingInfo.setIdTransport(resultSet.getString("idTransport"));
                shippingInfo.setMethodTransport(resultSet.getString("methodTransport"));
                shippingInfo.setAddressTransport(resultSet.getString("addressTransport"));
                shippingInfos.add(shippingInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingInfos;
    }

    public ShippingInfo getShippingInfoById(String idTransport) {
        ShippingInfo shippingInfo = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_SHIPPING_INFO_BY_ID_SQL)) {
            preparedStatement.setString(1, idTransport);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    shippingInfo = new ShippingInfo();
                    shippingInfo.setIdTransport(resultSet.getString("idTransport"));
                    shippingInfo.setMethodTransport(resultSet.getString("methodTransport"));
                    shippingInfo.setAddressTransport(resultSet.getString("addressTransport"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shippingInfo;
    }
}
