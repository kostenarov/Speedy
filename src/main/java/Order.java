import java.util.ArrayList;
import Package.Package;

/*Order
id -  цяло число, уникално за всяка инстанцияа, Не може да се променя(константа)
packages - колекция с пакети за доставка (един или повече)
addressId - връзка към адреса където ще бъде доставена поръчката
status - enum: CREATED, DELIVERING, DELIVERED
getDeliveryPrice() - връща цялата сума за доставка на поръчката. Получава се като сума на цените за доставка на пакетите в поръчката.
*/
public class Order {
    private ArrayList<Package> packets = new ArrayList<Package>();
    final private int addressId;
    final private int Id;

    public Order() {

    }

    enum Status{CREATED, DELIVERING, DELIVERED}
    Status status;

    public Order(int addressId, int id, Package packet) throws RuntimeException
    {
        if(id < 1)
        {
            throw new RuntimeException("Invalid order id");
        }
        if(addressId < 1)
        {
            throw new RuntimeException("Invalid address id");
        }
        if(packet == null)
        {
            throw new RuntimeException("Invalid package in order");
        }
        this.Id = id;
        this.addressId = addressId;
        addPackage(packet);
        this.status = Status.CREATED;
    }

    public Order(int addressId, int id, ArrayList<Package> packets) throws RuntimeException
    {
        if(id < 1)
        {
            throw new RuntimeException("Invalid order id");
        }
        if(addressId < 1)
        {
            throw new RuntimeException("Invalid address id");
        }
        if(packets.size() == 0)
        {
            throw new RuntimeException("Invalid package in order");
        }
        this.Id = id;
        this.addressId = addressId;
        addPackage(packets);
        this.status = Status.CREATED;
    }

    public void addPackage(Package packet)
    {
        if(packet == null)
        {
            throw new RuntimeException("invalid Package");
        }
        packets.add(packet);
    }

    public void addPackage(ArrayList<Package> packets)
    {
        if(packets == null)
        {
            throw new RuntimeException("invalid Packages");
        }
        this.packets.addAll(packets);
    }

    void changeStatus(Status status)
    {
        if(status == this.status)
        {
            throw new RuntimeException("Status is the same");
        }

        this.status = status;
    }

    public int getDeliveryPrice()
    {
        double price = 0;
        for(Package packet : packets)
        {
            price += packet.getDeliveryPrice();
        }
        return (int)price;
    }

    int getId() { return this.Id; }
    int getAddressId() { return this.addressId; }
    Status getStatus() { return this.status; }
    void setStatus(Status status) { this.status = status; }
}
