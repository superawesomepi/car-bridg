
public class OneLaneBridge extends Bridge {
    private int limit = 0;

    public OneLaneBridge(int limit) {
        this.limit = limit;
    }

    private Object waitlist = new Object(); // condition variable
    public void arrive(Car car) throws InterruptedException {
        synchronized(waitlist) {
            //System.out.println("New car arrived: " + car.toString());
            if(bridge.size() == 0) direction = car.getDirection(); // if the bridge is empty, set the direction to that of the arriving car
            while (car.getDirection() != direction || bridge.size() == limit) {
                waitlist.wait();
                if(bridge.size() == 0) direction = car.getDirection(); // if the bridge is empty, set the direction to that of the arriving car
            }
            car.setEntryTime(currentTime);
            bridge.add(car);
            System.out.println("Bridge (dir=" + direction + "): " + bridge);
            currentTime++;
        }
    }

    public void exit(Car car) throws InterruptedException {
        synchronized(waitlist) {
            while(bridge.indexOf(car) != 0) {
                waitlist.wait(); // if the car is not at the front of the bridge go back to waiting until another car exits
            }
            bridge.remove(car);
            System.out.println("Bridge (dir=" + direction + "): " + bridge);
            waitlist.notifyAll();
        }
    }

}