package quan_ly_cua_hang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO sanpham (idProduct, nameProduct, nameList, nameProducer, description, costProduct, availProduct) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE sanpham SET nameProduct=?, nameList=?, nameProducer=?, description=?, costProduct=?, availProduct=? WHERE idProduct=?";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM sanpham WHERE idProduct=?";
    private static final String SELECT_ALL_PRODUCTS_SQL = "SELECT * FROM sanpham";
    private static final String SELECT_PRODUCT_BY_ID_SQL = "SELECT * FROM sanpham WHERE idProduct=?";

    public void addProduct(Product product) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getIdProduct());
            preparedStatement.setString(2, product.getNameProduct());
            preparedStatement.setString(3, product.getNameList());
            preparedStatement.setString(4, product.getNameProducer());
            preparedStatement.setString(5, product.getDescription());
            preparedStatement.setDouble(6, product.getCostProduct());
            preparedStatement.setInt(7, product.getAvailProduct());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateProduct(Product product) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PRODUCT_SQL)) {
            preparedStatement.setString(1, product.getNameProduct());
            preparedStatement.setString(2, product.getNameList());
            preparedStatement.setString(3, product.getNameProducer());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setDouble(5, product.getCostProduct());
            preparedStatement.setInt(6, product.getAvailProduct());
            preparedStatement.setString(7, product.getIdProduct());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(String idProduct) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_PRODUCT_SQL)) {
            preparedStatement.setString(1, idProduct);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_PRODUCTS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Product product = new Product();
                product.setIdProduct(resultSet.getString("idProduct"));
                product.setNameProduct(resultSet.getString("nameProduct"));
                product.setNameList(resultSet.getString("nameList"));
                product.setNameProducer(resultSet.getString("nameProducer"));
                product.setDescription(resultSet.getString("description"));
                product.setCostProduct(resultSet.getDouble("costProduct"));
                product.setAvailProduct(resultSet.getInt("availProduct"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Product> getProductsByName(String keyword) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM sanpham WHERE nameProduct LIKE ?")) {
            preparedStatement.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setIdProduct(resultSet.getString("idProduct"));
                    product.setNameProduct(resultSet.getString("nameProduct"));
                    product.setNameList(resultSet.getString("nameList"));
                    product.setNameProducer(resultSet.getString("nameProducer"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCostProduct(resultSet.getDouble("costProduct"));
                    product.setAvailProduct(resultSet.getInt("availProduct"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public Product getProductById(String idProduct) {
        Product product = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_PRODUCT_BY_ID_SQL)) {
            preparedStatement.setString(1, idProduct);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product();
                    product.setIdProduct(resultSet.getString("idProduct"));
                    product.setNameProduct(resultSet.getString("nameProduct"));
                    product.setNameList(resultSet.getString("nameList"));
                    product.setNameProducer(resultSet.getString("nameProducer"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCostProduct(resultSet.getDouble("costProduct"));
                    product.setAvailProduct(resultSet.getInt("availProduct"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return product;
    }
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT nameList FROM sanpham"; 

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String category = resultSet.getString("nameList");
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }


    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM sanpham WHERE nameList = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, category);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<String> getAllProducers() {
        List<String> producers = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT DISTINCT nameProducer FROM sanpham");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                producers.add(resultSet.getString("nameProducer"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producers;
    }
    public List<Product> getProductsByProducer(String producer) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM sanpham WHERE nameProducer=?")) {
            preparedStatement.setString(1, producer);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setIdProduct(resultSet.getString("idProduct"));
                    product.setNameProduct(resultSet.getString("nameProduct"));
                    product.setNameList(resultSet.getString("nameList"));
                    product.setNameProducer(resultSet.getString("nameProducer"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCostProduct(resultSet.getDouble("costProduct"));
                    product.setAvailProduct(resultSet.getInt("availProduct"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Product> getProductsByCategoryAndProducer(String category, String producer) {
        List<Product> products = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM sanpham WHERE nameList=? AND nameProducer=?")) {
            preparedStatement.setString(1, category);
            preparedStatement.setString(2, producer);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setIdProduct(resultSet.getString("idProduct"));
                    product.setNameProduct(resultSet.getString("nameProduct"));
                    product.setNameList(resultSet.getString("nameList"));
                    product.setNameProducer(resultSet.getString("nameProducer"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCostProduct(resultSet.getDouble("costProduct"));
                    product.setAvailProduct(resultSet.getInt("availProduct"));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
