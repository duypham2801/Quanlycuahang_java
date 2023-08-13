package quan_ly_cua_hang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public abstract class ProductInterface extends JFrame {
    protected JTextField searchTextField;
    protected JTable productTable;
    protected DefaultTableModel tableModel;
    protected JButton searchButton;
    protected JComboBox<String> categoryComboBox;
    protected JComboBox<String> producerComboBox;
    protected JButton filterButton;
    protected JPanel mainPanel; 

    public ProductInterface(String title) {
        setTitle(title);
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel();
        searchTextField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        searchButton.addActionListener(e -> {
            String keyword = searchTextField.getText();
            searchProducts(keyword);
        });
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);
        topPanel.add(searchPanel, BorderLayout.CENTER);

        JPanel filterPanel = new JPanel();
        categoryComboBox = new JComboBox<>();
        producerComboBox = new JComboBox<>();
        filterPanel.add(new JLabel("Danh mục:"));
        filterPanel.add(categoryComboBox);
        filterPanel.add(new JLabel("Nhà sản xuất:"));
        filterPanel.add(producerComboBox);
        filterButton = new JButton("Lọc");
        filterButton.addActionListener(e -> {
            String selectedCategory = (String) categoryComboBox.getSelectedItem();
            String selectedProducer = (String) producerComboBox.getSelectedItem();
            filterProducts(selectedCategory, selectedProducer);
        });
        filterPanel.add(filterButton);
        topPanel.add(filterPanel, BorderLayout.SOUTH);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel();
        productTable = new JTable(tableModel);
        tableModel.addColumn("Mã sản phẩm");
        tableModel.addColumn("Tên sản phẩm");
        tableModel.addColumn("Danh mục");
        tableModel.addColumn("Nhà sản xuất");
        tableModel.addColumn("Mô tả");
        tableModel.addColumn("Giá");
        tableModel.addColumn("Số lượng");

        JScrollPane scrollPane = new JScrollPane(productTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        showAllProducts();
        updateCategoryComboBox();
        updateProducerComboBox();
    }

    public abstract void showAllProducts();

    public abstract void searchProducts(String keyword);

    public abstract void updateProductList();

    public void displayProductTable(List<Product> products) {
        tableModel.setRowCount(0);

        for (Product product : products) {
            Object[] rowData = {
                product.getIdProduct(), product.getNameProduct(), product.getNameList(),
                product.getNameProducer(), product.getDescription(), product.getCostProduct(),
                product.getAvailProduct()
            };
            tableModel.addRow(rowData);
        }
    }

    public abstract void updateCategoryComboBox();

    public abstract void updateProducerComboBox();

    public abstract void filterProducts(String category, String producer);
    
    protected void setMainPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}

