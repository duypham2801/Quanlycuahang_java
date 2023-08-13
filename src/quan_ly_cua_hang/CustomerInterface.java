package quan_ly_cua_hang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerInterface extends JFrame {
    private JTextField searchTextField;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton searchButton;
    private JComboBox<String> categoryComboBox; 
    private JComboBox<String> producerComboBox; 
    private JButton filterButton;
    private JButton buyButton;

    public CustomerInterface(String username) {
        setTitle("Giao diện khách hàng");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchTextField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        buyButton = new JButton("Mua hàng");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchTextField.getText();
                searchProducts(keyword);
            }
        });
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBuyProductInterface();
                dispose();
            }
        });
        searchPanel.add(searchTextField);
        searchPanel.add(searchButton);
        searchPanel.add(buyButton);
        mainPanel.add(searchPanel, BorderLayout.NORTH);

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

    public void showAllProducts() {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        displayProductTable(products);
    }

    public void searchProducts(String keyword) {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getProductsByName(keyword);
        displayProductTable(products);
    }

    public void updateProductList() {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();
        displayProductTable(products);
        updateCategoryComboBox(); 
        updateProducerComboBox();
    }

    public void displayProductTable(List<Product> products) {
        tableModel.setRowCount(0);

        for (Product product : products) {
            Object[] rowData = {product.getIdProduct(), product.getNameProduct(), product.getNameList(),
                    product.getNameProducer(), product.getDescription(), product.getCostProduct(),
                    product.getAvailProduct()};
            tableModel.addRow(rowData);
        }
    }

    public void updateCategoryComboBox() {
        ProductDAO productDAO = new ProductDAO();
        List<String> categories = productDAO.getAllCategories();
        categoryComboBox.removeAllItems();
        categoryComboBox.addItem("Tất cả danh mục");
        for (String category : categories) {
            categoryComboBox.addItem(category);
        }
    }

    public void updateProducerComboBox() {
        ProductDAO productDAO = new ProductDAO();
        List<String> producers = productDAO.getAllProducers();
        producerComboBox.removeAllItems();
        producerComboBox.addItem("Tất cả nhà sản xuất");
        for (String producer : producers) {
            producerComboBox.addItem(producer);
        }
    }

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
    
    private void openBuyProductInterface() {
        ArrayList<Product> cart = new ArrayList<>(); // Tạo một giỏ hàng mới
        CustomerInterface customerInterface = new CustomerInterface("username");
        BuyProductInterface buyProductInterface = new BuyProductInterface(cart, customerInterface);
    }
    
    public double getProductPriceByName(String productName) {
        ProductDAO productDAO = new ProductDAO();
        List<Product> products = productDAO.getAllProducts();

        for (Product product : products) {
            if (product.getNameProduct().equalsIgnoreCase(productName)) {
                return product.getCostProduct();
            }
        }

        return 0.0; // Trả về 0.0 nếu không tìm thấy giá sản phẩm
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerInterface("username");
            }
        });
    }
}
