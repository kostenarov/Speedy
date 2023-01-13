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
    private static int counter = 0;
    private final int id;
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
        counter++;
        this.id = counter;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public User(final int id, final String username, final String password, Role role)
    {
        if(username.isEmpty())
        {
            throw new RuntimeException( "not valid username");
        }

        if(password.isEmpty())
        {
            throw new RuntimeException( "not valid password");
        }
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public int getId() { return id; }
    public String getUserName() { return this.username; }
    public String getPassword() { return this.password; }

    public Role getRole() { return this.role; }
}
