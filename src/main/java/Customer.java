
public class Customer {
    private int customerId;
    private String address;

    public Customer(int customerId, String address) {
        this.customerId = customerId;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
