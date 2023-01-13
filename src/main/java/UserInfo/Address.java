package UserInfo;

public class Address {
    /*UserInfo.Address
    id - цяло число, уникално за всяка инстанцияа, Не може да се променя(константа)
    country - текст
    city - текст
    street - текст
    userId - връзка към юзъра на който принадлежи този адрес*/
    private final int userId;
    private static int id = 0;
    private final String country, city, street;

    public Address(final String country, final String city, final String street, final int userId) throws Exception
    {
        if(userId < 0)
        {
            throw new RuntimeException( "not valid id");
        }

        if(country.isEmpty())
        {
            throw new RuntimeException( "not valid country");
        }

        if(city.isEmpty())
        {
            throw new RuntimeException( "not valid city");
        }

        if(street.isEmpty())
        {
            throw new RuntimeException( "not valid street");
        }
        id++;
        this.country = country;
        this.city = city;
        this.street = street;
        this.userId = userId;
    }

    public int getId() { return id; }
    public int getUserId() {return this.userId; }
    public String getCountry() { return this.country; }
    public String getCity() { return this.city; }
    public String getStreet() { return this.street; }

}
