package quan_ly_cua_hang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationScreen extends JFrame {
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JTextField nameTextField;
    private JTextField cccdTextField;
    private JTextField emailTextField;
    private JTextField addressTextField;
    private JTextField phoneTextField;

    public RegistrationScreen() {
        setTitle("Đăng ký tài khoản");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 2));

        mainPanel.add(new JLabel("Tên đăng nhập:"));
        usernameTextField = new JTextField();
        mainPanel.add(usernameTextField);

        mainPanel.add(new JLabel("Mật khẩu:"));
        passwordField = new JPasswordField();
        mainPanel.add(passwordField);

        mainPanel.add(new JLabel("Họ và tên:"));
        nameTextField = new JTextField();
        mainPanel.add(nameTextField);

        mainPanel.add(new JLabel("CCCD:"));
        cccdTextField = new JTextField();
        mainPanel.add(cccdTextField);

        mainPanel.add(new JLabel("Email:"));
        emailTextField = new JTextField();
        mainPanel.add(emailTextField);

        mainPanel.add(new JLabel("Địa chỉ:"));
        addressTextField = new JTextField();
        mainPanel.add(addressTextField);

        mainPanel.add(new JLabel("Số điện thoại:"));
        phoneTextField = new JTextField();
        mainPanel.add(phoneTextField);

        JButton registerButton = new JButton("Đăng ký");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomerDAO customerDAO = new CustomerDAO();
                Customer newCustomer = new Customer();
                newCustomer.setLoginCustomer(usernameTextField.getText());
                newCustomer.setPasswordCustomer(new String(passwordField.getPassword()));
                newCustomer.setNameCustomer(nameTextField.getText());
                newCustomer.setCccdCustomer(cccdTextField.getText());
                newCustomer.setEmailCustomer(emailTextField.getText());
                newCustomer.setAddressCustomer(addressTextField.getText());
                newCustomer.setPhoneCustomer(phoneTextField.getText());
                customerDAO.addCustomer(newCustomer);

                JOptionPane.showMessageDialog(RegistrationScreen.this, "Đăng ký tài khoản thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                dispose(); 
            }
        });
        mainPanel.add(registerButton);

        add(mainPanel);
        setVisible(true);
    }
}
