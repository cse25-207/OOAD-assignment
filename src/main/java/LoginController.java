
public class LoginController {

        private AuthenticationService authenticationService;

        public LoginController(AuthenticationService authenticationService) {
            this.authenticationService = authenticationService;
        }

        public boolean login(Customer customer, String username, String password) {
            return authenticationService.login(customer, username, password);
        }

        public void logout() {
            authenticationService.logout();
        }

        public Customer getLoggedInCustomer() {
            return authenticationService.getLoggedInCustomer();
        }

        public boolean isLoggedIn() {
            return authenticationService.isLoggedIn();
        }
    }

