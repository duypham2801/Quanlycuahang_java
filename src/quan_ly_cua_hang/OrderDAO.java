package quan_ly_cua_hang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private static final String INSERT_ORDER_SQL = "INSERT INTO donhang (idOrder, idCustomer, idTransport, timeOrder, totalPrice) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_ORDER_SQL = "UPDATE donhang SET idCustomer=?, idTransport=?, timeOrder=?, totalPrice=? WHERE idOrder=?";
    private static final String DELETE_ORDER_SQL = "DELETE FROM donhang WHERE idOrder=?";
    private static final String SELECT_ALL_ORDERS_SQL = "SELECT * FROM donhang";
    private static final String SELECT_ORDER_BY_ID_SQL = "SELECT * FROM donhang WHERE idOrder=?";

    public void addOrder(Order order) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_ORDER_SQL)) {
            preparedStatement.setString(1, order.getIdOrder());
            preparedStatement.setString(2, order.getIdCustomer());
            preparedStatement.setString(3, order.getIdTransport());
            preparedStatement.setString(4, order.getTimeOrder());
            preparedStatement.setDouble(5, order.getTotalPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrder(Order order) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_ORDER_SQL)) {
            preparedStatement.setString(1, order.getIdCustomer());
            preparedStatement.setString(2, order.getIdTransport());
            preparedStatement.setString(3, order.getTimeOrder());
            preparedStatement.setDouble(4, order.getTotalPrice());
            preparedStatement.setString(5, order.getIdOrder());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(String idOrder) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_ORDER_SQL)) {
            preparedStatement.setString(1, idOrder);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_ORDERS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Order order = new Order();
                order.setIdOrder(resultSet.getString("idOrder"));
                order.setIdCustomer(resultSet.getString("idCustomer"));
                order.setIdTransport(resultSet.getString("idTransport"));
                order.setTimeOrder(resultSet.getString("timeOrder"));
                order.setTotalPrice(resultSet.getDouble("totalPrice"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrderById(String idOrder) {
        Order order = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ORDER_BY_ID_SQL)) {
            preparedStatement.setString(1, idOrder);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    order = new Order();
                    order.setIdOrder(resultSet.getString("idOrder"));
                    order.setIdCustomer(resultSet.getString("idCustomer"));
                    order.setIdTransport(resultSet.getString("idTransport"));
                    order.setTimeOrder(resultSet.getString("timeOrder"));
                    order.setTotalPrice(resultSet.getDouble("totalPrice"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }
}
