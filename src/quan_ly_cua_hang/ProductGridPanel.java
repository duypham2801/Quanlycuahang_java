package quan_ly_cua_hang;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProductGridPanel extends JPanel {
    private JLabel imageLabel;
    private JTextField nameTextField;
    private JTextField categoryTextField;
    private JTextField producerTextField;
    private JTextField descriptionTextField;
    private JTextField priceTextField;
    private JTextField quantityTextField;

    public ProductGridPanel() {
        setLayout(new BorderLayout());

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(100, 100)); 
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        nameTextField = new JTextField();
        categoryTextField = new JTextField();
        producerTextField = new JTextField();
        descriptionTextField = new JTextField();
        priceTextField = new JTextField();
        quantityTextField = new JTextField();

        nameTextField.setEditable(false);
        categoryTextField.setEditable(false);
        producerTextField.setEditable(false);
        descriptionTextField.setEditable(false);
        priceTextField.setEditable(false);
        quantityTextField.setEditable(false);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(6, 1)); 
        infoPanel.add(nameTextField);
        infoPanel.add(categoryTextField);
        infoPanel.add(producerTextField);
        infoPanel.add(descriptionTextField);
        infoPanel.add(priceTextField);
        infoPanel.add(quantityTextField);
        add(infoPanel, BorderLayout.SOUTH);
    }

    public void setProductInfo(BufferedImage image, String name, String category, String producer, String description, double price, int quantity) {
        if (image != null) {
            imageLabel.setIcon(new ImageIcon(image.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        } else {
            imageLabel.setIcon(null);
        }

        nameTextField.setText("Tên sản phẩm: " + name);
        categoryTextField.setText("Danh mục: " + category);
        producerTextField.setText("Nhà sản xuất: " + producer);
        descriptionTextField.setText("Mô tả: " + description);
        priceTextField.setText("Giá: " + price);
        quantityTextField.setText("Số lượng: " + quantity);
    }
}
