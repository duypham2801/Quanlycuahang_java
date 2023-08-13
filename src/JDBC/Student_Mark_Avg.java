package JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Student_Mark_Avg {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_schemam", "root", "280113");
            System.out.println("MySQL Connected");

            Statement statement = connection.createStatement();

            String query = "SELECT s.studentId, s.first_name, s.last_name, " + "SUM(m.mark * su.credits) / SUM(su.credits) AS avg_mark " +
                    		"FROM student s " +
		                    "INNER JOIN mark m ON s.studentId = m.studentId " +
		                    "INNER JOIN subject su ON m.subjectId = su.subjectId " +
		                    "WHERE m.semesterCode = '20222' " +
		                    "GROUP BY s.studentId, s.first_name, s.last_name";

            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String studentId = resultSet.getString("studentId");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                double avgMark = resultSet.getDouble("avg_mark");

                System.out.println("Sinh viên: " + firstName + " " + lastName + ", Mã sinh viên: " + studentId + ", Điểm trung bình: " + avgMark);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


