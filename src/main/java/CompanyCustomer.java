
public class CompanyCustomer extends Customer {

    private String companyName;
    private String registrationNumber;

    public CompanyCustomer(int customerId, String address,
                           String companyName, String registrationNumber) {
        super(customerId, address);
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }
}
