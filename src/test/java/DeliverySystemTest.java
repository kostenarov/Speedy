import DeliverySystem.DeliverySystem;
import Order.Order;
import UserInfo.Address;
import UserInfo.Role;
import Package.Package;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class DeliverySystemTest {
    private DeliverySystem Speedy;

    @BeforeEach
    void setUp()
    {
        Speedy = new DeliverySystem();
        Speedy.login("Admin", "Admin123");
        Speedy.register("Client", "Client", Role.CUSTOMER);
        Speedy.logout();
    }
    @Test
    void registerSuccess() {
        Speedy.login("Admin", "Admin123");
        assertDoesNotThrow(() -> Speedy.register("Pedal", "Gey", Role.CUSTOMER));
    }
    @Test
    void registerUnsuccessfulNotValidRole() {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertThrows(SecurityException.class, () -> Speedy.register("Pedal1", "Gey", Role.CUSTOMER));
    }
    @Test
    void registerUnsuccessfulInvalidUsername() {
        Speedy.login("Admin", "Admin123");
        assertThrows(SecurityException.class, () -> Speedy.register("", "Gey", Role.CUSTOMER));
    }
    @Test
    void registerUnsuccessfulInvalidPassword() {
        Speedy.login("Admin", "Admin123");
        assertThrows(SecurityException.class, () -> Speedy.register("Pedal", "", Role.CUSTOMER));
    }
    @Test
    void registerUnsuccessfulUserExists() {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        assertThrows(SecurityException.class, () -> Speedy.register("Pedal", "Gey", Role.CUSTOMER));
    }

    @Test
    void login() {
        assertDoesNotThrow(() -> Speedy.login("Admin", "Admin123"));
    }

    @Test
    void loginUnsuccessfulLoggedIn()
    {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        assertThrows(SecurityException.class, () -> Speedy.login("Pedal", "Gey"));
    }
    @Test
    void loginUnsuccessfulIncorrectPassword()
    {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        assertThrows(SecurityException.class, () -> Speedy.login("Pedal", "Gabe"));
    }
    @Test
    void loginUnsuccessfulUserDoesntExist()
    {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        assertThrows(SecurityException.class, () -> Speedy.login("Pedo", "Gey"));

    }

    @Test
    void logoutSuccessful() {
        Speedy.login("Admin", "Admin123");
        assertDoesNotThrow(() -> Speedy.logout());
    }

    @Test
    void logoutUnsuccessful(){
        Speedy.login("Admin", "Admin123");
        Speedy.logout();
        assertThrows(RuntimeException.class, () -> Speedy.logout());

    }

    @Test
    void getRandomOrderToDeliverSuccessful() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.DRIVER);
        Speedy.register("Pedal1", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal1", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(1, 1, new Package(2, 2)));
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertDoesNotThrow(() -> Speedy.getOrderToDeliver());
    }

    @Test
    void getRandomOrderToDeliverUnsuccessfulUserNotDriver() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.register("Pedal1", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal1", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(1, 1, new Package(2, 2)));
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertThrows(RuntimeException.class, () -> Speedy.getOrderToDeliver());
    }

    @Test
    void GetOrderToDeliverSuccessful() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.DRIVER);
        Speedy.register("Pedal1", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal1", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(1, 1, new Package(2, 2)));
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertDoesNotThrow(() -> Speedy.getOrderToDeliver(1));
    }

    @Test
    void GetOrderToDeliverUnsuccessfulUserNotDriver() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.DRIVER);
        Speedy.register("Pedal1", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal1", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(1, 1, new Package(2, 2)));
        assertThrows(RuntimeException.class, () -> Speedy.getOrderToDeliver(1));
    }

    @Test
    void GetOrderToDeliverUnsuccessfulInvalidId() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.DRIVER);
        Speedy.register("Pedal1", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal1", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(1, 1, new Package(2, 2)));
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertThrows(RuntimeException.class, () -> Speedy.getOrderToDeliver(-4));
    }

    @Test
    void addOrderSuccessful() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        assertDoesNotThrow(() -> Speedy.addOrder(new Order(1, 1, new Package(2, 2))));
    }

    @Test
    void addOrderUnsuccessfulUserNotClient() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.register("Pedal1", "Gey", Role.DRIVER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.logout();
        Speedy.login("Pedal1", "Gey");
        assertThrows(SecurityException.class, () -> Speedy.addOrder(new Order(1, 1, new Package(2, 2))));
    }

    @Test
    void addOrderUnsuccessfulAddressNotForCurrentUser() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.register("Pedal1", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal1", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertThrows(RuntimeException.class, () -> Speedy.addOrder(new Order(4, 1, new Package(2, 2))));
    }

    @Test
    void addOrderUnsuccessfulAddressDoesNotExist() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        assertThrows(SecurityException.class, () -> Speedy.addOrder(new Order(4, 1, new Package(2, 2))));
    }

    @Test
    void addAddressSuccessful() {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertDoesNotThrow(() -> Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId())));
    }

    @Test
    void addAddressUnsuccessfulUserNotClient() {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.DRIVER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertThrows(SecurityException.class, () -> Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", 1)));
    }

    @Test
    void addAddressUnsuccessfulAddressIdNotMatch() {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertThrows(SecurityException.class, () -> Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", 3)));
    }

    @Test
    void addPackageSuccessful() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(1, 1, new Package(2, 2)));
        assertDoesNotThrow(() -> Speedy.addPackage(new Package(2, 2), 1));
    }
    @Test
    void addPackageUnsuccessfulUserNotClient() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal1", "Gey", Role.DRIVER);
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(1, 1, new Package(2, 2)));
        Speedy.logout();
        Speedy.login("Pedal1", "Gey");
        assertThrows(RuntimeException.class, () -> Speedy.addPackage(new Package(2, 2), 1));
    }

    @Test
    void addPackageUnsuccessfulAddressNotMatch() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal1", "Gey", Role.CUSTOMER);
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(1, 1, new Package(2, 2)));
        Speedy.logout();
        Speedy.login("Pedal1", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(3, 2, new Package(2, 2)));
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        assertThrows(SecurityException.class, () -> Speedy.addPackage(new Package(2, 2), 2));
    }

    @Test
    void addPackageUnsuccessfulOrderDoesNotExist() throws Exception {
        Speedy.login("Admin", "Admin123");
        Speedy.register("Pedal", "Gey", Role.CUSTOMER);
        Speedy.logout();
        Speedy.login("Pedal", "Gey");
        Speedy.addAddress(new Address("Johnny", "Sins", "4ft D", Speedy.getCurrentUser().getId()));
        Speedy.addOrder(new Order(1, 1, new Package(2, 2)));
        assertThrows(RuntimeException.class, () -> Speedy.addPackage(new Package(2, 2), 3));
    }
}