package UserInfo;

public class UserDetails {
    /*
    UserInfo.UserDetails
    id - цяло число, уникално за всяка инстанция. Не може да се променя(констант)
    firstName - текст
    lastName - текст
    telephone - само цифри
    userid - връзка към UserInfo.User-a по id за когото са детаилит
     */
    private final int userId;
    private static int id = 0;
    private final String firstName, lastName;
    private final long phoneNumber;

    public UserDetails(final int userId, final String firstName, final String lastName, final long phoneNumber) {
        {
            if(userId < 0)
            {
                throw new RuntimeException( "not valid id!");
            }

            if(firstName.isEmpty())
            {
                throw new RuntimeException( "not valid first name!");
            }

            if(lastName.isEmpty())
            {
                throw new RuntimeException( "not valid last name!");
            }

            if(phoneNumber >= (100L * 100000000) || phoneNumber < 1000000000)
            {
                throw new RuntimeException( "not valid phone number!");
            }
            id++;
            this.userId = userId;
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
        }
    }

    public int getId() { return id; }
    public int getUserId() { return this.userId; }
    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public Long getPhoneNumber() { return this.phoneNumber; }
}
