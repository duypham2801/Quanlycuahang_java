package JDBC;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GradeInterface extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JTextField studentIdTextField;
    private JTextField subjectIdTextField;
    private JTextField semesterTextField;
    private JTextArea resultTextArea;

    public GradeInterface() {
        setTitle("Grade Interface");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel studentIdLabel = new JLabel("Student ID:");
        studentIdTextField = new JTextField(10);

        JLabel subjectIdLabel = new JLabel("Course ID:");
        subjectIdTextField = new JTextField(10);

        JLabel semesterLabel = new JLabel("Semester:");
        semesterTextField = new JTextField(10);

        JButton showGradeButton = new JButton("Show Grade");
        showGradeButton.addActionListener(this);

        resultTextArea = new JTextArea(5, 10);

        add(studentIdLabel);
        add(studentIdTextField);
        add(subjectIdLabel);
        add(subjectIdTextField);
        add(semesterLabel);
        add(semesterTextField);
        add(showGradeButton);
        add(resultTextArea);

        setVisible(true);
    }

    public static void main(String[] args) {
        new GradeInterface();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Show Grade")) {
            String studentId = studentIdTextField.getText();
            String subjectId = subjectIdTextField.getText();
            String semester = semesterTextField.getText();

            String url = "jdbc:mysql://localhost:3306/new_schemam";
            String username = "root";
            String password = "280113";

            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                Statement statement = connection.createStatement();


                String query = "SELECT s.first_name, s.last_name, m.mark  " +
                        "FROM student s " +
                        "INNER JOIN mark m ON s.studentId = m.studentId " +
                        "WHERE s.studentId = '" + studentId + "' " +
                        "AND m.subjectId = '" + subjectId + "' " +
                        "AND m.semesterCode = '" + semester + "' " +
                        "GROUP BY s.first_name, s.last_name";

                ResultSet resultSet = statement.executeQuery(query);

                resultTextArea.setText("");
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    double Mark = resultSet.getDouble("mark");

                    resultTextArea.append("Sinh viên: " + firstName + " " + lastName + ", Grade is: " + Mark + "\n");
                }

                resultSet.close();
                statement.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}

