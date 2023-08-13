package quan_ly_cua_hang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;

    public LoginScreen() {
        setTitle("Đăng nhập");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 2));

        mainPanel.add(new JLabel("Tên đăng nhập:"));
        usernameTextField = new JTextField();
        mainPanel.add(usernameTextField);

        mainPanel.add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        mainPanel.add(new JLabel("Vai trò:"));
        String[] roles = {"Khách hàng", "Nhân viên"};
        roleComboBox = new JComboBox<>(roles);
        mainPanel.add(roleComboBox);

        JButton loginButton = new JButton("Đăng nhập");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                String password = new String(passwordField.getPassword());
                String selectedRole = (String) roleComboBox.getSelectedItem();

                if ("Khách hàng".equals(selectedRole)) {
                    LoginManager loginManager = new LoginManager(null);
                    Customer customer = loginManager.authenticateCustomer(username, password);
                    if (customer != null) {
                        loginSuccessCustomer(customer);
                    } else {
                        JOptionPane.showMessageDialog(LoginScreen.this, "Tên đăng nhập hoặc mật khẩu không đúng!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
                    }
                }

                if ("Nhân viên".equals(selectedRole)) {
                    LoginManager loginManager = new LoginManager(null);
                    Staff staff = loginManager.authenticateStaff(username, password);
                    if (staff != null) {
                        StaffInterface staffInterface = new StaffInterface(staff.getNameStaff());
                        dispose(); 
                    } else {
                        JOptionPane.showMessageDialog(LoginScreen.this, "Tên đăng nhập hoặc mật khẩu không đúng!", "Lỗi đăng nhập", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        mainPanel.add(loginButton);

        JButton registerButton = new JButton("Đăng ký");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrationScreen registrationScreen = new RegistrationScreen();
            }
        });
        mainPanel.add(registerButton);

        add(mainPanel);
        setVisible(true);
    }

    private void loginSuccessCustomer(Customer customer) {
        CustomerInterface customerInterface = new CustomerInterface(customer.getNameCustomer());
        customerInterface.updateProductList(); 
        dispose(); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen();
            }
        });
    }
}
