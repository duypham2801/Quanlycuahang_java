package quan_ly_cua_hang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddProductInterface extends JFrame {
    private JTextField productNameTextField;
    private JTextField categoryTextField;
    private JTextField producerTextField;
    private JTextArea descriptionTextArea;
    private JTextField costTextField;
    private JTextField availTextField;
    private JButton saveButton;

    public AddProductInterface() {
        setTitle("Thêm sản phẩm mới");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel productNameLabel = new JLabel("Tên sản phẩm:");
        productNameTextField = new JTextField(20);

        JLabel categoryLabel = new JLabel("Danh mục:");
        categoryTextField = new JTextField(20);

        JLabel producerLabel = new JLabel("Nhà sản xuất:");
        producerTextField = new JTextField(20);

        JLabel descriptionLabel = new JLabel("Mô tả:");
        descriptionTextArea = new JTextArea(5, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);

        JLabel costLabel = new JLabel("Giá:");
        costTextField = new JTextField(20);

        JLabel availLabel = new JLabel("Số lượng:");
        availTextField = new JTextField(20);

        saveButton = new JButton("Lưu sản phẩm");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameTextField.getText();
                String category = categoryTextField.getText();
                String producer = producerTextField.getText();
                String description = descriptionTextArea.getText();
                double cost = Double.parseDouble(costTextField.getText());
                int avail = Integer.parseInt(availTextField.getText());

                Product newProduct = new Product();
                newProduct.setNameProduct(productName);
                newProduct.setNameList(category);
                newProduct.setNameProducer(producer);
                newProduct.setDescription(description);
                newProduct.setCostProduct(cost);
                newProduct.setAvailProduct(avail);

                ProductDAO productDAO = new ProductDAO();
                productDAO.addProduct(newProduct);
                dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(7, 2, 10, 10));
        mainPanel.add(productNameLabel);
        mainPanel.add(productNameTextField);
        mainPanel.add(categoryLabel);
        mainPanel.add(categoryTextField);
        mainPanel.add(producerLabel);
        mainPanel.add(producerTextField);
        mainPanel.add(descriptionLabel);
        mainPanel.add(descriptionScrollPane);
        mainPanel.add(costLabel);
        mainPanel.add(costTextField);
        mainPanel.add(availLabel);
        mainPanel.add(availTextField);
        mainPanel.add(saveButton);

        add(mainPanel);
        pack();
        setVisible(true);
    }
}
