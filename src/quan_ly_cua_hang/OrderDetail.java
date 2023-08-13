package quan_ly_cua_hang;

public class OrderDetail {
    private String idOrder;
    private String idProduct;
    private int countOrder;

    // Các phương thức getter và setter cho từng thuộc tính
    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getCountOrder() {
        return countOrder;
    }

    public void setCountOrder(int countOrder) {
        this.countOrder = countOrder;
    }
}

