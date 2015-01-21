package ua.com.amicablesoft.commons.ofu.sample;

import ua.com.amicablesoft.commons.ofu.Updater;

/**
 * Created by Tymoshenko Bogdan <bogdan.tymoshenko@gmail.com> on 1/21/15.
 */
public class Main {

    public static void main(String[] args) {
        Employee employee1 = new Employee();
        employee1.setName("Vasia");
        employee1.setAge(22);

        Car car1 = new Car();
        car1.setVendor("Opel");
        car1.setModel("Kadet");
        car1.setYear(1987);
        employee1.setCar(car1);

        Address address1 = new Address();
        address1.setStreet("Melnykova st.");
        address1.setHouse("18");
        employee1.setAddress(address1);

        Employee employee2 = new Employee();
        employee2.setName("Petia");
        employee2.setAge(26);

        Car car2 = new Car();
        car2.setVendor("Ford");
        car2.setModel("Scorpio");
        car2.setYear(1989);
        employee2.setCar(car2);

        Address address2 = new Address();
        address2.setStreet("Lisova st.");
        address2.setHouse("64");
        employee2.setAddress(address2);

        Updater.update(employee1, employee2);
    }
}
