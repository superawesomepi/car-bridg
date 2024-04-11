
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
            if(car.getDirection() != direction || bridge.size() == limit) {
                waitlist.wait();
            }
            car.setEntryTime(currentTime);
            bridge.add(car);
            System.out.println("Bridge (dir=" + direction + "): " + bridge);
            currentTime++;
        }
    }

    public void exit(Car car) throws InterruptedException {
        synchronized(waitlist) {
            bridge.remove(car);
            System.out.println("Bridge (dir=" + direction + "): " + bridge);
            waitlist.notifyAll();
        }
    }

}