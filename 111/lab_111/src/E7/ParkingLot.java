package E7;


import java.util.Map;
import java.util.function.Predicate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ParkingLot { //cars pays at the end of the stay
    private SizeableVehicleStorage<Vehicle, Double> storage;
    private double PricePerHour;
    private double balance;

    public ParkingLot(int size, double pricePerHour){
        storage = new SizeableVehicleStorage<Vehicle, Double>(size);
        this.PricePerHour = pricePerHour;
    }

    public boolean enter(Vehicle v, double expectedStay){
        try {
            this.storage.put(v, expectedStay);
        }
        catch(StorageError e){
            return false;
        }
        return true;
    }

    public boolean exit(Vehicle v){
        if (storage.containsKey(v)){
            double hoursStayed = storage.remove(v);
            this.balance += hoursStayed * this.PricePerHour;
            return true;
        }
        return false;
    }

    public boolean exitByHours(Vehicle v, double hours){
        if (storage.containsKey(v)){
            double hoursStayed = storage.remove(v);
            this.balance += hours * this.PricePerHour;
            return true;
        }
        return false;
    }

    public double getBalance(){
        return this.balance;
    }

    public double getExpectedRevenue(){
        double revenue = 0;
        for (Map.Entry<Vehicle, Double> entry: this.storage.entrySet()){
            revenue += entry.getValue() * this.PricePerHour;
        }
        return revenue + this.balance;
    }

    public long countBikes(){
        return this.storage.entrySet().stream().filter(((item) -> item.getKey() instanceof Motorcycle)).count();
    }

    public long countWheeledVehicle(){
        return this.storage.entrySet().stream().filter(((item) -> item.getKey() instanceof WheeledVehicle)).count();
    }

    public long countSpaceShip(){
        return this.storage.entrySet().stream().filter(((item) -> item.getKey() instanceof SpaceShip)).count();
    }

    public long customCount(Predicate pred){
        return this.storage.entrySet().stream().map(entry -> entry.getKey()).filter(pred).count();
    }

    public int getSize(){
        return this.storage.size();
    }

    public int getMaxSize(){
        return this.storage.getMaxSize();
    }

    public boolean isInLot(Vehicle v){
        return this.storage.containsKey(v);
    }

    public double getExpectedTime(Vehicle v){
        return this.storage.get(v);
    }


    public static void main(String args[]){
        ParkingLot parkingLot = new ParkingLot(2,5);
        Car c = new Car(8, "WV", "Orange", 40);
        Motorcycle m = new Motorcycle(5, "AR", "Black", 10);
        Truck t = new Truck(20, "BMW", "Gray", 60);
        parkingLot.enter(c, 1);
        System.out.println("1. ExpectedRev: " +  parkingLot.getExpectedRevenue());
        System.out.println("2. balance: " +  parkingLot.getBalance());
        parkingLot.enter(m, 2);
        parkingLot.enter(t, 3);
        parkingLot.enter(t, 3);
        parkingLot.exit(c);
        parkingLot.exit(c);
        System.out.println("3. ExpectedRev: " +  parkingLot.getExpectedRevenue());
        System.out.println("4. balance: " +  parkingLot.getBalance());
        System.out.println("5. bikes: " +  parkingLot.countBikes());
        Motorcycle m2 = new Motorcycle(5, "AR", "Black", 10);
        parkingLot.enter(m2, 2);
        System.out.println("6. bikes: " +  parkingLot.countBikes());
        System.out.println("7. ExpectedRev: " +  parkingLot.getExpectedRevenue());
        System.out.println("8. balance: " +  parkingLot.getBalance());
    }
}
