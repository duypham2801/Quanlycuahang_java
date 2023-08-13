package quan_ly_hoc_sinh;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentManager {
    private static User currentUser;
    private static JFrame frame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowLoginGUI());
    }

    private static void createAndShowLoginGUI() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(4, 2, 10, 10));
        frame.setLocationRelativeTo(null);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        frame.add(usernameLabel);
        frame.add(usernameField);
        frame.add(passwordLabel);
        frame.add(passwordField);
        frame.add(new JLabel()); // Empty label for layout
        frame.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Call authenticate method and handle login logic here
                currentUser = authenticate(username, password);
                if (currentUser != null) {
                    frame.dispose(); // Close the login window
                    showAppropriateInterface(currentUser); // Show the appropriate interface based on the user's role
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password. Try again.",
                            "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

                // Clear the password field for security
                passwordField.setText("");
            }
        });

        frame.setVisible(true);
    }

    private static User authenticate(String username, String password) {
		return currentUser;
        // Implement your authentication logic here (same as in the previous code)
        // Return the authenticated User object or null if authentication fails
    }

    private static void showAppropriateInterface(User user) {
        frame = new JFrame("School Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);

        // Create a main panel to hold the components
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create a welcome label to display the user's name
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + "!", SwingConstants.CENTER);
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Create a tabbed pane to switch between student, teacher, and subject interfaces
        JTabbedPane tabbedPane = new JTabbedPane();
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        if ("hoc_sinh".equals(user.getRole())) {
            // Create the student interface and add it to the tabbed pane
            JPanel studentPanel = createStudentInterface(user.getId());
            tabbedPane.addTab("Student Interface", studentPanel);
        } else if ("giao_vien_chu_nhiem".equals(user.getRole())) {
            // Create the teacher interface and add it to the tabbed pane
            JPanel teacherPanel = createTeacherInterface(user.getId());
            tabbedPane.addTab("Teacher Interface", teacherPanel);
        } else if ("giao_vien_bo_mon".equals(user.getRole())) {
            // Create the subject teacher interface and add it to the tabbed pane
            JPanel subjectTeacherPanel = createSubjectTeacherInterface(user.getId());
            tabbedPane.addTab("Subject Teacher Interface", subjectTeacherPanel);
        }

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static JPanel createStudentInterface(int studentId) {
        JPanel panel = new JPanel(new BorderLayout());

        // Create a label to display student information
        JLabel studentLabel = new JLabel("Student Information:", SwingConstants.CENTER);
        panel.add(studentLabel, BorderLayout.NORTH);

        // Create a text area to display student marks
        JTextArea marksTextArea = new JTextArea();
        marksTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(marksTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Get student information and marks from the database
        String studentInfo = StudentManager.getStudentById(studentId);
        String studentMarks = StudentManager.getStudentById(studentId);

        // Display student information and marks
        marksTextArea.setText(studentInfo + "\n\n" + studentMarks);

        return panel;
    }

    static String getStudentById(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}

	private static JPanel createTeacherInterface(int teacherId) {
        JPanel panel = new JPanel(new BorderLayout());

        // Create a label to display teacher information
        JLabel teacherLabel = new JLabel("Teacher Information:", SwingConstants.CENTER);
        panel.add(teacherLabel, BorderLayout.NORTH);

        // Create a text area to display list of students
        JTextArea studentListTextArea = new JTextArea();
        studentListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(studentListTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Get teacher information and list of students from the database
        User teacherInfo = TeacherManager.getTeacherById(teacherId);
        String studentList = TeacherManager.getTeacherStudents(teacherId);

        // Display teacher information and list of students
        studentListTextArea.setText(teacherInfo + "\n\n" + studentList);

        return panel;
    }

    private static JPanel createSubjectTeacherInterface(int subjectTeacherId) {
        JPanel panel = new JPanel(new BorderLayout());

        // Create a label to display subject teacher information
        JLabel subjectTeacherLabel = new JLabel("Subject Teacher Information:", SwingConstants.CENTER);
        panel.add(subjectTeacherLabel, BorderLayout.NORTH);

        // Create a text area to display list of subjects
        JTextArea subjectListTextArea = new JTextArea();
        subjectListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(subjectListTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Get subject teacher information and list of subjects from the database
        String subjectTeacherInfo = SubjectManager.getSubjectById(subjectTeacherId);
        String subjectList = SubjectManager.getSubjectById(subjectTeacherId);

        // Display subject teacher information and list of subjects
        subjectListTextArea.setText(subjectTeacherInfo + "\n\n" + subjectList);

        return panel;
    }

	public static String getStudentNameByStudentId(int studentId) {
		// TODO Auto-generated method stub
		return null;
	}
}
