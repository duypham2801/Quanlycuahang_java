package quan_ly_cua_hang;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private static final String INSERT_CATEGORY_SQL = "INSERT INTO danhmucsanpham (idList, nameList) VALUES (?, ?)";
    private static final String UPDATE_CATEGORY_SQL = "UPDATE danhmucsanpham SET nameList=? WHERE idList=?";
    private static final String DELETE_CATEGORY_SQL = "DELETE FROM danhmucsanpham WHERE idList=?";
    private static final String SELECT_ALL_CATEGORIES_SQL = "SELECT * FROM danhmucsanpham";
    private static final String SELECT_CATEGORY_BY_ID_SQL = "SELECT * FROM danhmucsanpham WHERE idList=?";

    public void addCategory(Category category) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_CATEGORY_SQL)) {
            preparedStatement.setString(1, category.getIdList());
            preparedStatement.setString(2, category.getNameList());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(Category category) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_CATEGORY_SQL)) {
            preparedStatement.setString(1, category.getNameList());
            preparedStatement.setString(2, category.getIdList());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(String idList) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_CATEGORY_SQL)) {
            preparedStatement.setString(1, idList);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_ALL_CATEGORIES_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Category category = new Category();
                category.setIdList(resultSet.getString("idList"));
                category.setNameList(resultSet.getString("nameList"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public Category getCategoryById(String idList) {
        Category category = null;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_CATEGORY_BY_ID_SQL)) {
            preparedStatement.setString(1, idList);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    category = new Category();
                    category.setIdList(resultSet.getString("idList"));
                    category.setNameList(resultSet.getString("nameList"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }
}
