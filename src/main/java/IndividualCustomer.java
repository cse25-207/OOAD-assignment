public class IndividualCustomer extends Customer {

    private String firstName;
    private String surname;
    private String email;
    private String phone;

    public IndividualCustomer(int customerId, String address,
                              String firstName, String surname,
                              String email, String phone) {
        super(customerId, address);
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}