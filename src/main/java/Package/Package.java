package Package;
public class Package {

    private final int id, size;
    private int price = 0;
    private final Size multiplier;
    boolean isSalable = false;

    public Package(int id, int size) throws Exception {
        if(size < 1 || size > 9)
        {
            throw new RuntimeException("Invalid size");
        }

        if(size <= 3)
        {
            multiplier = Size.SMALL;
        }
        else if(size <= 6)
        {
            multiplier = Size.MEDIUM;
        }
        else
        {
            multiplier = Size.LARGE;
        }

        if(id < 0)
        {
            throw new RuntimeException("Invalid id");
        }
        this.id = id;
        this.size = size;
    }

    public Package(int id, int size, boolean isSalable, int price) throws Exception {
        if(size < 1 || size > 9)
        {
            throw new RuntimeException("Invalid size");
        }

        if(size <= 3)
        {
            multiplier = Size.SMALL;
        }

        else if(size <= 6)
        {
            multiplier = Size.MEDIUM;
        }

        else
        {
            multiplier = Size.LARGE;
        }

        if(id < 0)
        {
            throw new RuntimeException("Invalid id");
        }
        this.id = id;
        this.size = size;
        this.isSalable = true;
        this.price = price;
    }

    public int getId() {return this.id; }
    public int getSize() { return this.size; }
    public double getDeliveryPrice()
    {
        int price = 0;
        switch(multiplier)
        {
            case SMALL -> { price = size * 3; }
            case MEDIUM -> { price = size * 4; }
            case LARGE -> { price = size * 5; }
        }
        if(isSalable) {
            price += this.price * 0.1;
        }
        return price;
    }
}
