package quan_ly_cua_hang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    private static final String INSERT_ORDER_DETAIL_SQL = "INSERT INTO chitietdonhang (idOrder, idProduct, countOrder) VALUES (?, ?, ?)";
    private static final String UPDATE_ORDER_DETAIL_SQL = "UPDATE chitietdonhang SET countOrder=? WHERE idOrder=? AND idProduct=?";
    private static final String DELETE_ORDER_DETAIL_SQL = "DELETE FROM chitietdonhang WHERE idOrder=? AND idProduct=?";
    private static final String SELECT_ORDER_DETAILS_BY_ORDER_ID_SQL = "SELECT * FROM chitietdonhang WHERE idOrder=?";
    private static final String SELECT_ORDER_DETAIL_SQL = "SELECT * FROM chitietdonhang WHERE idOrder=? AND idProduct=?";

    public void addOrderDetail(OrderDetail orderDetail) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_ORDER_DETAIL_SQL)) {
            preparedStatement.setString(1, orderDetail.getIdOrder());
            preparedStatement.setString(2, orderDetail.getIdProduct());
            preparedStatement.setInt(3, orderDetail.getCountOrder());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_ORDER_DETAIL_SQL)) {
            preparedStatement.setInt(1, orderDetail.getCountOrder());
            preparedStatement.setString(2, orderDetail.getIdOrder());
            preparedStatement.setString(3, orderDetail.getIdProduct());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderDetail(String idOrder, String idProduct) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_ORDER_DETAIL_SQL)) {
            preparedStatement.setString(1, idOrder);
            preparedStatement.setString(2, idProduct);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OrderDetail> getOrderDetailsByOrderId(String idOrder) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ORDER_DETAILS_BY_ORDER_ID_SQL)) {
            preparedStatement.setString(1, idOrder);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setIdOrder(resultSet.getString("idOrder"));
                    orderDetail.setIdProduct(resultSet.getString("idProduct"));
                    orderDetail.setCountOrder(resultSet.getInt("countOrder"));
                    orderDetails.add(orderDetail);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public OrderDetail getOrderDetail(String idOrder, String idProduct) {
        OrderDetail orderDetail = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ORDER_DETAIL_SQL)) {
            preparedStatement.setString(1, idOrder);
            preparedStatement.setString(2, idProduct);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    orderDetail = new OrderDetail();
                    orderDetail.setIdOrder(resultSet.getString("idOrder"));
                    orderDetail.setIdProduct(resultSet.getString("idProduct"));
                    orderDetail.setCountOrder(resultSet.getInt("countOrder"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetail;
    }
}
