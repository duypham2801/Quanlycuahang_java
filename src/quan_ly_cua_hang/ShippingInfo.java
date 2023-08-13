package quan_ly_cua_hang;

public class ShippingInfo {
    private String idTransport;
    private String methodTransport;
    private String addressTransport;

    // Các phương thức getter và setter cho từng thuộc tính
    public String getIdTransport() {
        return idTransport;
    }

    public void setIdTransport(String idTransport) {
        this.idTransport = idTransport;
    }

    public String getMethodTransport() {
        return methodTransport;
    }

    public void setMethodTransport(String methodTransport) {
        this.methodTransport = methodTransport;
    }

    public String getAddressTransport() {
        return addressTransport;
    }

    public void setAddressTransport(String addressTransport) {
        this.addressTransport = addressTransport;
    }
}

