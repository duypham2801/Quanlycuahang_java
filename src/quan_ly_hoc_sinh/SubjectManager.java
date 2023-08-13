package quan_ly_hoc_sinh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectManager {
    // Lấy thông tin môn học dựa vào subjectId
    public static String getSubjectById(int subjectId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM monhoc WHERE monhoc_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, subjectId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getString("ten_monhoc");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Lấy thông tin môn học dựa vào subjectName
    public static int getSubjectByName(int monhocId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM monhoc WHERE ten_monhoc = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, monhocId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("monhoc_id");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Lấy thông tin điểm của tất cả học sinh trong môn học dựa vào subjectId
    public static void getSubjectMarks(int subjectId) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM diem WHERE monhoc_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, subjectId);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                System.out.println("Subject Information:");
                System.out.println("ID: " + subjectId);
                System.out.println("Name: " + getSubjectById(subjectId));

                System.out.println("Student Marks:");
                System.out.println("-------------------------");
                do {
                    int studentId = result.getInt("hocsinh_id");
                    float mark = result.getFloat("diem");
                    String hoTen = StudentManager.getStudentNameByStudentId(studentId);
                    System.out.println("Student ID: " + studentId + "\t\tName: " + hoTen + "\t\tMark: " + mark);
                } while (result.next());
            } else {
                System.out.println("No marks found for this subject!");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Thêm môn học mới
    public static void addNewSubject(String subjectName) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "INSERT INTO monhoc (ten_monhoc) VALUES (?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, subjectName);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("New subject added successfully.");
            } else {
                System.out.println("Failed to add new subject.");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public static void subjectTeacherRole(User user) {
		// TODO Auto-generated method stub
		
	}
}
