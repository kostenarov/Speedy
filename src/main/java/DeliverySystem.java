import java.util.*;
import UserInfo.*;
import Package.Package;

/*DeliverySystem
Полета
users - колекция с потребители
orders - колекция с поръчки
addresses - колекция с адреси
currentUser - текущ потребител който ползва системата
Операции (методи).
Всички операции могат да бъдат изпълнявани само ако има текущо логнат потребител
Винаги трябва да има един административен потребител с username: admin и password: admin123
Работа с файлове:
Всички модели, които се създават по време на работа на системата да се записват в отделни файлове под формата на csv.
Програмата да може да се стартира с параметър път до папка, в която да има csv файлове, които системата да използва,
за да зареди първоначални данни.
(Все едно има база данни, но пишете и четете от файлове във формат на csv)
Работа със системата:
Напишете main с безкрайно четене от конзолата, което да позволява интеракция на потребителя със системата използвайки команди.
Да се поддържат всички операции.
login - V
logout - V
register - V
addAddress
addOrder
addPackage
getOrderToDeliver
getOrderToDeliver
Програмата да изписва информативни съобщения когато настъпи грешка и да не прекратява изпълнението си.
*/
public class DeliverySystem {
    private Set<User> users = new HashSet<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private ArrayList<Address> addresses = new ArrayList<>();
    private User currentUser;

    public DeliverySystem(User user, UserDetails details)
    {
        if(user.getRole() != Role.ADMINISTRATOR)
        {
            throw new RuntimeException("No Admin in the system");
        }
        this.users.add(user);
    }

//registerUser(String username, String password) - регистрира user с подадените полета.
// Ако вече съществува потребител с такъв username да се хвърли грешка. Само потребител,
// който е админ може да регистрира нови потребители. Ако текущия потребител не е админ, да се хвърля грешка.
    public void register(String username, String password)
    {
        if(currentUser.getRole() != Role.ADMINISTRATOR)
        {
            throw new SecurityException("Current user is not an admin");
        }

        if(username.isEmpty())
        {
            throw new RuntimeException("Username is empty");
        }

        if(password.isEmpty())
        {
            throw new RuntimeException("Password is empty");
        }

        for(User user : users)
        {
            if(user.getUserName().equals(username))
            {
                throw new SecurityException("UserInfo.User with this username already exists");
            }
        }

        users.add(new User(username, password, Role.CUSTOMER));
    }

    public void register(String username, String password, Role role)
    {
        if(currentUser.getRole() != Role.ADMINISTRATOR)
        {
            throw new SecurityException("Current user is not an admin");
        }

        if(username.isEmpty())
        {
            throw new SecurityException("Username is empty");
        }

        if(password.isEmpty())
        {
            throw new SecurityException("Password is empty");
        }

        for(User user : users)
        {
            if(user.getUserName().equals(username))
            {
                throw new SecurityException("UserInfo.User with this username already exists");
            }
        }

        users.add(new User(username, password, role));
    }

//login(String username, String password) - променя текущия потребител на потребителя който се опитва да се впише
// ако username и password съответстват. Ако не се хвърля грешка.
    public void login(String username, String password)
    {
        boolean loggedIn = false;
        if(currentUser != null)
        {
            throw new SecurityException("There is already a logged-in user!");
        }
        for(User user : users)
        {
            if(Objects.equals(user.getUserName(), username))
            {
                if(Objects.equals(user.getPassword(), password))
                {
                    currentUser = user;
                    loggedIn = true;
                }
                else
                {
                    throw new SecurityException("Incorrect password");
                }
            }
        }
        if(!loggedIn)
        {
            throw new SecurityException("UserInfo.User doesn't exist");
        }
    }

//logout() - текущия потребител се изчиства.
    public void logout()
    {
        if(this.currentUser == null)
        {
            throw new RuntimeException("No user logged in");
        }
        this.currentUser = null;
    }

//deliverOrder(int id) - отбелязва че поръчка с посоченото id е доставена. Ако не съществува поръчка с такова id,
// да се хвърли грешка. Ако поръчката с това id, не е в статус DELIVERING, да се хвърли грешка.
// Ако текущия потребител не е шофьор, да се хвърли грешка.
    public Order getOrderToDeliver(final int id)
    {
        if(currentUser.getRole() != Role.DRIVER)
        {
            throw new RuntimeException("UserInfo.User is not a driver");
        }
        if(id < 1)
        {
            throw new RuntimeException("Invalid id");
        }
        for(Order order : orders)
        {
            if((order.getAddressId() == id) && (order.getStatus() == Order.Status.DELIVERING))
            {
                order.setStatus(Order.Status.DELIVERED);
                return order;
            }
            else{
                throw new RuntimeException("Invalid order for delivery");
            }
        }
        return null;
    }

//getOrderToDeliver() - взима се поръчка на произволен принцип за доставка от шофьор.
// Само поръчки които са в статус CREATED могат да бъдат вземани за доставка. Когато поръчка се взема за доставка,
// нейния статус се променя на DELIVERING. Само шофьори могат да вземат поръчки за доставка.
// Ако текущия потребител не е шофьор, да се хвърли грешка. Ако няма налични поръчки за доставка, да се хвърли грешка.
    public Order getOrderToDeliver()
    {
        Random random = new Random();
        int id = random.nextInt(orders.size());
        if(currentUser.getRole() != Role.DRIVER)
        {
            throw new RuntimeException("UserInfo.User is not a driver");
        }
        for(Order order : orders)
        {
            if((order.getAddressId() == id) && (order.getStatus() == Order.Status.DELIVERING))
            {
                order.setStatus(Order.Status.DELIVERED);
                return order;
            }
            else{
                throw new RuntimeException("Invalid order for delivery");
            }
        }
        return null;
    }

//addOrder(Order order) - добавя поръчка към системата със статус CREATED.
// Ако поръчката не е за текущия потребител(адреса на поръчката не е за текущия потребител) да се хвърля грешка.
// Ако текущия потребител не е клиент, да се хвърля грешка. Да се валидира коректността на данните в поръчката.
// (има съществуващ адрес, има поне един пакет).
    void addOrder(Order order)
    {
        boolean exists = false;
        if(currentUser.getRole() != Role.CUSTOMER)
        {
            throw new SecurityException("Current user is not a customer");
        }
        for(Address address : addresses)
        {
            if(address.getId() == order.getAddressId())
            {
                exists = true;
                if(currentUser.getId() == address.getUserId())
                {
                    orders.add(order);
                }
                else {
                    throw new RuntimeException("UserInfo.Address is not for the current user!");
                }
            }
        }
        if(!exists)
        {
            throw new RuntimeException("UserInfo.Address doesn't exists");
        }
    }

//addAddress(UserInfo.Address address) - добавя се нов адрес в системата. Само клиенти могат да добавят адреси.
// Ако текущия потребител и потребителя за когото е адреса се различават, да се хвърля грешка.
// Ако текущия потребител не е клиент, да се хвърля грешка.
    void addAddress(Address address)
    {
        if(currentUser.getRole() != Role.CUSTOMER)
        {
            throw new SecurityException("User is not a client");
        }

        if(address.getUserId() != currentUser.getId())
        {
            throw new SecurityException("User and client id doesn't match");
        }

        addresses.add(address);
    }

//addPackage(Package.Package package, int orderId) добавя пакет към посочената поръчка с даденото id.
// Ако не съществува такава поръчка, да се хвърли грешка. Ако текущия потребител не е клиент, да се хвърля грешка.
// Ако посочената поръчка не е за текущия потребител (адреса на поръчката не е за текущия потребител), да се хвърли грешка.
    void addPackage(Package packet, int orderId)
    {
        boolean orderExists = false, orderAddressExists = false;
        if(currentUser.getRole() != Role.CUSTOMER)
        {
            throw new RuntimeException("UserInfo.User is not a client");
        }
        for(Order order : orders)
        {
            if(order.getId() == orderId)
            {
                orderExists = true;
                for(Address address : addresses)
                {
                    if(order.getAddressId() == address.getId())
                    {
                        orderAddressExists = true;
                        if(address.getUserId() == currentUser.getId())
                        {
                            order.addPackage(packet);
                        }
                        else {
                            throw new SecurityException("The address doesn't match the user's address");
                        }
                    }
                }
            }
            if(!orderExists)
            {
                throw new RuntimeException("Order doesn't exist");
            }

            if(!orderAddressExists)
            {
                throw new RuntimeException("The order address doesn't exist");
            }
        }
    }

    boolean isLoggedIn()
    {
        if(currentUser != null)
            return true;
        return false;
    }

    int getTotalOrders()
    {
        return orders.size();
    }

    ArrayList<Address> getAddresses()
    {
        return addresses;
    }
}
