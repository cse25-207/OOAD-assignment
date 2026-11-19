
public class CompanyCustomer extends Customer {

    private String companyName;
    private String registrationNumber;

    public CompanyCustomer(int customerId, String address,
                           String username, String password,
                           String companyName, String registrationNumber) {

        super(customerId, address, username, password);

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