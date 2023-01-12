package UserInfo;

public class User {
    /*UserInfo.User
id - цяло число, уникално за всеки потребител. Не може да се променя(константа)
username - потребителско име. Не може да има два user-a с едно и също username!
password - парола
role - роля
Role
enum
ADMINISTRATOR, CUSTOMER, DRIVER*/
    private static int id = 0;
    private final String username, password;
    private final Role role;

    public User(final String username, final String password, Role role)
    {
        if(username.isEmpty())
        {
            throw new RuntimeException( "not valid username");
        }

        if(password.isEmpty())
        {
            throw new RuntimeException( "not valid password");
        }
        id++;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() { return id; }
    public String getUserName() { return this.username; }
    public String getPassword() { return this.password; }

    public Role getRole() { return this.role; }
}
