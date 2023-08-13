package quan_ly_cua_hang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.border.EmptyBorder;

public class BuyProductInterface extends JFrame {
    private JTextField productNameTextField;
    private JTextField productPriceTextField; // Thêm trường giá sản phẩm
    private JSpinner quantitySpinner;
    private JButton addToCartButton;
    private CustomerInterface customerInterface;
    private ArrayList<Product> cart;

    public BuyProductInterface(ArrayList<Product> cart, CustomerInterface customerInterface) {
        this.cart = cart;
        this.customerInterface = customerInterface;

        setTitle("Mua sản phẩm");
        setSize(400, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel productNameLabel = new JLabel("Tên sản phẩm:");
        productNameTextField = new JTextField(20);


        JLabel quantityLabel = new JLabel("Số lượng:");
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        quantitySpinner = new JSpinner(spinnerModel);

        addToCartButton = new JButton("Thêm vào giỏ");
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameTextField.getText();
                double productPrice = customerInterface.getProductPriceByName(productName); // Lấy giá sản phẩm từ CustomerInterface
                int quantity = (int) quantitySpinner.getValue();
                
                Product purchasedProduct = new Product();
                purchasedProduct.setNameProduct(productName);
                purchasedProduct.setCostProduct(productPrice); // Thiết lập giá sản phẩm từ CustomerInterface
                purchasedProduct.setAvailProduct(quantity);

                cart.add(purchasedProduct);

                openCartInterface();

                dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));
        mainPanel.add(productNameLabel);
        mainPanel.add(productNameTextField);
        mainPanel.add(quantityLabel);
        mainPanel.add(quantitySpinner);
        mainPanel.add(addToCartButton);

        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void openCartInterface() {
        CartInterface cartInterface = new CartInterface(cart, this);
    }

    public static void main(String[] args) {
        ArrayList<Product> cart = new ArrayList<>();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                CustomerInterface customerInterface = new CustomerInterface("username");
                BuyProductInterface buyProductInterface = new BuyProductInterface(cart, customerInterface);
            }
        });
    }
}

class CartInterface extends JFrame {
    private ArrayList<Product> cart;
    private BuyProductInterface previousFrame;

    public CartInterface(ArrayList<Product> cart, BuyProductInterface previousFrame) {
        this.cart = cart;
        this.previousFrame = previousFrame;

        setTitle("Giỏ hàng");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTextArea cartTextArea = new JTextArea(10, 30);
        cartTextArea.setEditable(false);

        for (Product product : cart) {
            cartTextArea.append(product.getNameProduct() + " - Giá: " + product.getCostProduct() + " - Số lượng: " + product.getAvailProduct() + "\n");
        }

        //JButton viewCartButton = new JButton("Xem giỏ hàng");
        JButton checkoutButton = new JButton("Đặt hàng");
        JButton backButton = new JButton("Quay lại mua hàng");

        //viewCartButton.addActionListener(new ActionListener() {
            //@Override
            //public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, cartTextArea, "Danh sách sản phẩm trong giỏ hàng", JOptionPane.INFORMATION_MESSAGE);
            //}
        //});

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPaymentInterface();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousFrame.setVisible(true);
                dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(new JScrollPane(cartTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        //buttonPanel.add(viewCartButton);
        buttonPanel.add(checkoutButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setVisible(true);
    }

    private void openPaymentInterface() {
        PaymentInterface paymentInterface = new PaymentInterface(cart, this);
    }
}

class PaymentInterface extends JFrame {
    private ArrayList<Product> cart;
    private CartInterface previousFrame;


    public PaymentInterface(ArrayList<Product> cart, CartInterface previousFrame) {
        this.cart = cart;
        this.previousFrame = previousFrame;

        setTitle("Đặt hàng");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel paymentPanel = new JPanel(new GridLayout(8, 2, 10, 10));

        double totalAmount = 0;

        JTextArea productInfoTextArea = new JTextArea(10, 30);
        productInfoTextArea.setEditable(false);
        
        double subTotal = 0;
        for (Product product : cart) {
            subTotal = product.getCostProduct() * product.getAvailProduct();
            productInfoTextArea.append("Sản phẩm: " + product.getNameProduct() + " - Giá: " + product.getCostProduct() + " - Số lượng: " + product.getAvailProduct() +"\n"
            );

            subTotal += subTotal;
        }

        double shippingFee = 35000; // Phí ship cố định
        totalAmount = shippingFee + subTotal;

        JLabel productInfoLabel = new JLabel("Thông tin sản phẩm:");
        paymentPanel.add(productInfoLabel);
        paymentPanel.add(productInfoTextArea);

        JLabel subTotalPriceLabel = new JLabel("Tạm tính:");
        JLabel subTotalLabel = new JLabel(Double.toString(subTotal));
        JLabel shippingFeeLabel = new JLabel("Phí ship:");
        JLabel shippingFeeAmountLabel = new JLabel(Double.toString(shippingFee));
        JLabel totalPriceLabel = new JLabel("Tổng cộng:");
        JLabel totalAmountLabel = new JLabel(Double.toString(totalAmount));
        JLabel fullNameLabel = new JLabel("Họ tên:");
        JTextField fullNameTextField = new JTextField(20);
        JLabel phoneNumberLabel = new JLabel("Số điện thoại:");
        JTextField phoneNumberTextField = new JTextField(20);
        JLabel addressLabel = new JLabel("Địa chỉ:");
        JTextField addressTextField = new JTextField(20);

        paymentPanel.add(subTotalPriceLabel);
        paymentPanel.add(subTotalLabel);
        paymentPanel.add(shippingFeeLabel);
        paymentPanel.add(shippingFeeAmountLabel);
        paymentPanel.add(totalPriceLabel);
        paymentPanel.add(totalAmountLabel);
        paymentPanel.add(fullNameLabel);
        paymentPanel.add(fullNameTextField);
        paymentPanel.add(phoneNumberLabel);
        paymentPanel.add(phoneNumberTextField);
        paymentPanel.add(addressLabel);
        paymentPanel.add(addressTextField);

        final double finalTotalAmount = totalAmount;
        JButton payButton = new JButton("Đặt hàng");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = fullNameTextField.getText();
                String phoneNumber = phoneNumberTextField.getText();
                String address = addressTextField.getText();

                // Thực hiện chức năng thanh toán ở đây
                // Có thể hiển thị thông báo thanh toán thành công và/hoặc lưu thông tin đơn hàng
                // Sau khi thanh toán, bạn cũng có thể muốn xóa sản phẩm từ giỏ hàng

                JOptionPane.showMessageDialog(
                    null, 
                    "Đặt hàng thành công!\n\n" +
                    "Họ tên: " + fullName + "\n" +
                    "Số điện thoại: " + phoneNumber + "\n" +
                    "Địa chỉ: " + address + "\n" +
                    "Tổng cộng: " + finalTotalAmount,
                    "Đặt hàng thành công",
                    JOptionPane.INFORMATION_MESSAGE
                );

                cart.clear(); // Xóa giỏ hàng sau khi đặt hàng thành công
                previousFrame.setVisible(true);
                dispose();
            }
        });

        JButton backButton = new JButton("Quay lại giỏ hàng");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                previousFrame.setVisible(true);
                dispose();
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(paymentPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(payButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        pack();
        setVisible(true);
    }
}