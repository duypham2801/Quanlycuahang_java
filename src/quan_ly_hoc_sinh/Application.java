package quan_ly_hoc_sinh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loggedIn = false;

        while (!loggedIn) {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = authenticate(username, password);
            if (user != null) {
                loggedIn = true;
                System.out.println("Welcome, " + user.getUsername() + "!");

                if ("hoc_sinh".equals(user.getRole())) {
//                    StudentManager.getStudentById(user);
                } else if ("giao_vien_chu_nhiem".equals(user.getRole())) {
                    TeacherManager.teacherRole(user);
                } else if ("giao_vien_bo_mon".equals(user.getRole())) {
                    SubjectManager.subjectTeacherRole(user);
                }
            } else {
                System.out.println("Invalid username or password. Try again.");
            }
        }

        scanner.close();
    }

    private static User authenticate(String username, String password) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int id = result.getInt("user_id");
                String role = result.getString("role");
                User user = new User(id, username, password, role);
                return user;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
