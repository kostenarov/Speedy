import UserInfo.*;
import UserInfo.Role;
import UserInfo.UserDetails;
import Package.Package;

import java.util.ArrayList;
import java.util.Scanner;
//addAddress - V
//addOrder - V
//addPackage - V
//getOrderToDeliver
//getOrderToDeliver
public class Main {
    public static void Choicer(int choice, DeliverySystem Speedy) throws Exception {
        switch(choice)
        {
            case 1 -> {
                if(Speedy.isLoggedIn())
                {
                    Speedy.logout();
                }
                else {
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Please input login credentials: \nUsername: ");
                    String username = scanner.next();
                    System.out.println("Password: ");
                    String password = scanner.next();
                    try {
                        Speedy.login(username, password);
                    } catch (SecurityException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }//Login

            case 2 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Do you want the user to have a role? 1(Yes)/0(No)");
                int decision = scanner.nextInt();
                System.out.println();
                System.out.println("Please input register credentials: \nUsername: ");
                String username = scanner.next();
                System.out.println("Password: ");
                String password = scanner.next();
                Role role = Role.CUSTOMER;
                System.out.println("Please choose a role:\n1. Administrator\n2. Driver\n3. Customer");
                decision = scanner.nextInt();

                switch(decision)
                {
                    case 1 -> role = Role.ADMINISTRATOR;
                    case 2 -> role = Role.DRIVER;
                }

                try
                {
                    {
                        Speedy.register(username, password, role);
                        System.out.println("User " + username + " was successfully registered with " + role.toString() + " role");
                    }
                }

                catch(SecurityException e)
                {
                    System.out.println(e.getMessage());
                }
            }//Register

            case 3 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please input Address credentials: \nCountry: ");
                String country = scanner.next();
                System.out.println("City: ");
                String city = scanner.next();
                System.out.println("Street:");
                String street = scanner.next();
                System.out.println("User id");
                int id = scanner.nextInt();
                try
                {
                    Speedy.addAddress(new Address(country, city, street, id));
                }
                catch(SecurityException e)
                {
                    System.out.println(e.getMessage());
                }
            }//add Address

            case 4 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please input address Id:");
                int addressId = scanner.nextInt();
                ArrayList<Package> packets = new ArrayList<>();
                int packetChoice = 1;
                while(packetChoice != 0)
                {
                    if(packets.size() == 0)
                    {
                        int salableChecker = 0;
                        System.out.println("Do you want to package to be salable?(yes - 1 / no - 0)");
                        salableChecker = scanner.nextInt();
                        if(salableChecker == 1)
                        {
                            System.out.println("Please input the size of the package(1 - 9):");
                            int size = 0, price = 0;
                            size = scanner.nextInt();
                            System.out.println("Please input the price of the package(1 - 10):");
                            price = scanner.nextInt();
                            try{
                                packets.add(new Package(0, size, true, price));
                            }
                            catch(RuntimeException e)
                            {
                                System.out.println(e.getMessage());
                            }
                        }
                        else if(salableChecker == 0){
                            System.out.println("Please input the size of the package(1 - 9):");
                            int size = 0, price = 0;
                            size = scanner.nextInt();
                            try{
                                packets.add(new Package(0, size, true, price));
                            }
                            catch(RuntimeException e)
                            {
                                System.out.println(e.getMessage());
                            }
                        }
                    }

                    else
                    {
                        System.out.println("Do you want to add a package(yes - 1 / no - 0): ");
                        packetChoice = scanner.nextInt();
                        if(packetChoice == 1)
                        {
                            int salableChecker = 0;
                            System.out.println("Do you want to package to be salable?(yes - 1 / no - 0)");
                            salableChecker = scanner.nextInt();
                            if(salableChecker == 1)
                            {
                                System.out.println("Please input the size of the package(1 - 9):");
                                int size = 0, price = 0;
                                size = scanner.nextInt();
                                System.out.println("Please input the price of the package(1 - 10):");
                                price = scanner.nextInt();
                                try{
                                    packets.add(new Package(packets.size(), size, true, price));
                                }
                                catch(RuntimeException e)
                                {
                                    System.out.println(e.getMessage());
                                }
                            }
                            else if(salableChecker == 0){
                                System.out.println("Please input the size of the package(1 - 9):");
                                int size = 0, price = 0;
                                size = scanner.nextInt();
                                try{
                                    packets.add(new Package(packets.size(), size, true, price));
                                }
                                catch(RuntimeException e)
                                {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    }
                }
                System.out.println("Please input address Id:");
                try
                {
                    Speedy.addOrder(new Order(addressId, Speedy.getTotalOrders(), packets));
                }
                catch(RuntimeException e)
                {
                    System.out.println(e.getMessage());
                }
            }//add Order

            case 5 -> {
                Scanner scanner = new Scanner(System.in);
                int salableChecker = 0;
                System.out.println("Do you want to package to be salable?(yes - 1 / no - 0)");
                salableChecker = scanner.nextInt();
                if(salableChecker == 1)
                {
                    int orderId = 0;
                    System.out.println("Please input the id of the order:");
                    orderId = scanner.nextInt();
                    System.out.println("Please input the size of the package(1 - 9):");
                    int size = 0, price = 0;
                    size = scanner.nextInt();
                    System.out.println("Please input the price of the package(1 - 10):");
                    price = scanner.nextInt();
                    try{
                        Speedy.addPackage(new Package(0, size, true, price), orderId);//TODO fix
                    }
                    catch(RuntimeException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                else if(salableChecker == 0){
                    int orderId = 0;
                    System.out.println("Please input the id of the order:");
                    orderId = scanner.nextInt();
                    System.out.println("Please input the size of the package(1 - 9):");
                    int size = 0, price = 0;
                    size = scanner.nextInt();
                    try{
                       Speedy.addPackage(new Package(0, size, true, price), orderId);//TODO fix
                    }
                    catch(RuntimeException e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }//add Package

            case 6 -> {
                Order order = Speedy.getOrderToDeliver();
                System.out.println(order.getAddressId());
                for(Address temp : Speedy.getAddresses())
                {
                    if(temp.getId() == order.getAddressId())
                    {
                        System.out.println("Country - " + temp.getCountry() +
                                "\n City -" + temp.getCity()+
                                "\n Street -" + temp.getStreet());
                    }
                }
            }//get order random

            case 7 -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please input the id of the order you want to get:");
                int orderId = scanner.nextInt();
                Order order = Speedy.getOrderToDeliver(orderId);
                System.out.println(order.getAddressId());
                for(Address temp : Speedy.getAddresses())
                {
                    if(temp.getId() == order.getAddressId())
                    {
                        System.out.println("Country - " + temp.getCountry() +
                                "\n City -" + temp.getCity()+
                                "\n Street -" + temp.getStreet());
                    }
                }
            }//get order not random
        }
    }

    public static void main(String[] args) throws Exception {
        int choice = 1;
        Scanner scanner = new Scanner(System.in);
        DeliverySystem Speedy = new DeliverySystem(new User("Dabber", "Penis", Role.ADMINISTRATOR),
                new UserDetails(0, "Dabber", "Dabberov", 1111111134));

        while(choice != 0)
        {
            System.out.print("Please input choice: ");
            choice = scanner.nextInt();
            Choicer(choice, Speedy);
        }
    }
}
/*Система за спедиторска фирма
Работа с файлове:
Всички модели, които се създават по време на работа на системата да се записват в отделни файлове под формата на csv.
Програмата да може да се стартира с параметър път до папка, в която да има csv файлове, които системата да използва, за да зареди първоначални данни.
(Все едно има база данни, но пишете и четете от файлове във формат на csv)
Работа със системата:
Напишете main с безкрайно четене от конзолата, което да позволява интеракция на потребителя със системата използвайки команди.

Програмата да изписва информативни съобщения когато настъпи грешка и да не прекратява изпълнението си.
Тестове:
Да се напишат unit test-ове за всички методи в DeliverySystem класа и за пакетите.
Други:
Хвърлените грешки да са смислени и говорещи какво се е случило.
Спазвайте принципите на ООП.
Пишете разбираем и лесен за четене код.

Може да добавяте класове, методи и полета по ваше усмотрение.
*/