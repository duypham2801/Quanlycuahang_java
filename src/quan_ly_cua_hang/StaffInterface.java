package quan_ly_cua_hang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StaffInterface extends ProductInterface {
    private JButton addButton; 
    private JPanel mainPanel; 
    private JButton resetButton;
    public StaffInterface(String staffName) {
        super("Giao diện nhân viên");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAllProducts();
                categoryComboBox.setSelectedIndex(0);
                producerComboBox.setSelectedIndex(0);
            }
        });
        searchPanel.add(resetButton);

        addButton = new JButton("Thêm sản phẩm");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddProductInterface();
            }
        });
        searchPanel.add(addButton);

        searchTextField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchTextField.getText();
                searchProducts(keyword);
            }
        });
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

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

        JPanel filterPanel = new JPanel();
        categoryComboBox = new JComboBox<>();
        producerComboBox = new JComboBox<>();
        filterPanel.add(new JLabel("Danh mục:"));
        filterPanel.add(categoryComboBox);
        filterPanel.add(new JLabel("Nhà sản xuất:"));
        filterPanel.add(producerComboBox);
        filterButton = new JButton("Lọc");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                String selectedProducer = (String) producerComboBox.getSelectedItem();
                filterProducts(selectedCategory, selectedProducer);
            }
        });
        filterPanel.add(filterButton);
        mainPanel.add(filterPanel, BorderLayout.SOUTH);

        setMainPanel(mainPanel);

        showAllProducts();
        updateCategoryComboBox();
        updateProducerComboBox();
    }

    @Override
    public void showAllProducts() {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        displayProductTable(products);
    }

    @Override
    public void searchProducts(String keyword) {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getProductsByName(keyword);
        displayProductTable(products);
    }

    @Override
    public void updateProductList() {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        displayProductTable(products);
        updateCategoryComboBox(); 
        updateProducerComboBox();
    }

    @Override
    public void displayProductTable(List<Product> products) {
        tableModel.setRowCount(0);

        for (Product product : products) {
            Object[] rowData = {product.getIdProduct(), product.getNameProduct(), product.getNameList(),
                    product.getNameProducer(), product.getDescription(), product.getCostProduct(),
                    product.getAvailProduct()};
            tableModel.addRow(rowData);
        }
    }

    @Override
    public void updateCategoryComboBox() {
        ProductDAO productDAO = new ProductDAO();
        List<String> categories = productDAO.getAllCategories();
        categoryComboBox.removeAllItems();
        categoryComboBox.addItem("Tất cả danh mục");
        for (String category : categories) {
            categoryComboBox.addItem(category);
        }
    }

    @Override
    public void updateProducerComboBox() {
        ProductDAO productDAO = new ProductDAO();
        List<String> producers = productDAO.getAllProducers();
        producerComboBox.removeAllItems();
        producerComboBox.addItem("Tất cả nhà sản xuất");
        for (String producer : producers) {
            producerComboBox.addItem(producer);
        }
    }

    @Override
    public void filterProducts(String category, String producer) {
        ProductDAO productDAO = new ProductDAO();
        if ("Tất cả danh mục".equals(category) && "Tất cả nhà sản xuất".equals(producer)) {
            
            showAllProducts();
        } else if ("Tất cả danh mục".equals(category)) {
            
            List<Product> products = productDAO.getProductsByProducer(producer);
            displayProductTable(products);
        } else if ("Tất cả nhà sản xuất".equals(producer)) {
            
            List<Product> products = productDAO.getProductsByCategory(category);
            displayProductTable(products);
        } else {
            List<Product> products = productDAO.getProductsByCategoryAndProducer(category, producer);
            displayProductTable(products);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StaffInterface("Tên nhân viên");
            }
        });
    }
}
