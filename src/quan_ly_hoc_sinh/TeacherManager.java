package quan_ly_hoc_sinh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeacherManager {
    // Lấy thông tin giáo viên dựa vào teacherId
    public static User getTeacherById(int teacherId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM giaovien WHERE giaovien_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, teacherId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int userId = result.getInt("user_id");
                String hoTen = result.getString("ho_ten");
                String role = "giao_vien";
                User user = new User(userId, hoTen, "", role);
                return user;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Lấy thông tin giáo viên dựa vào username
    public static User getTeacherByUsername(String username) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int userId = result.getInt("user_id");
                int teacherId = getTeacherIdByUserId(userId);
                if (teacherId > 0) {
                    String hoTen = getTeacherNameByUserId(userId);
                    String role = "giao_vien";
                    User user = new User(userId, hoTen, "", role);
                    return user;
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Lấy danh sách học sinh của giáo viên chủ nhiệm dựa vào teacherId
    public static String getTeacherStudents(int teacherId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM hocsinh WHERE giaovien_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, teacherId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                System.out.println("Teacher Information:");
                System.out.println("ID: " + teacherId);
                System.out.println("Name: " + getTeacherNameByTeacherId(teacherId));

                System.out.println("Student List:");
                System.out.println("-------------------------");
                do {
                    int studentId = result.getInt("hocsinh_id");
                    String hoTen = result.getString("ho_ten");
                    System.out.println("ID: " + studentId + "\t\tName: " + hoTen);
                } while (result.next());
            } else {
                System.out.println("No students found for this teacher!");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return null;
    }

    // Cập nhật điểm của học sinh dựa vào studentId và subjectId
    public static void updateStudentMark(int studentId, int subjectId, float mark) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "UPDATE diem SET diem = ? WHERE hocsinh_id = ? AND monhoc_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setFloat(1, mark);
            statement.setInt(2, studentId);
            statement.setInt(3, subjectId);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Student mark updated successfully.");
            } else {
                System.out.println("Failed to update student mark.");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getTeacherIdByUserId(int userId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT giaovien_id FROM giaovien WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("giaovien_id");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private static String getTeacherNameByUserId(int userId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT ho_ten FROM giaovien WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getString("ho_ten");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getTeacherNameByTeacherId(int teacherId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT ho_ten FROM giaovien WHERE giaovien_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, teacherId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getString("ho_ten");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

	public static void teacherRole(User user) {
		// TODO Auto-generated method stub
		
	}
}
