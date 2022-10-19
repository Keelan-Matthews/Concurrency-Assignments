import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Intersection {
    int id;
    ReentrantLock enqLock, deqLock;
    Condition notFull, notEmpty;
    AtomicInteger size;
    int capacity;
    volatile Node head, tail;
    TrafficLight light;

    public Intersection(int _id) {
        this.id = _id;
        enqLock = new ReentrantLock();
        deqLock = new ReentrantLock();
        notFull = enqLock.newCondition();
        notEmpty = deqLock.newCondition();
        size = new AtomicInteger(0);
        capacity = 2;
        head = new Node(null);
        tail = head;
    }

    public void setAdjIntersections(int i1, int i2) {
        light = new TrafficLight(i1, i2);
        light.start();
    }

    public void stopLight() {
        light.stopLight();
    }

    public String getEdgeState(int edge) {
        TrafficLight.State state = light.getLightState();
        if (edge == 1) {
            return state.e1;
        } else {
            return state.e2;
        }
    }

    public void enq(String vehicleName) {
        boolean mustWakeDequeuers = false;
        enqLock.lock();
        try {
            while (size.get() == capacity) {
                notFull.await();
            }
            tail.next = new Node(vehicleName);
            tail = tail.next;
            mustWakeDequeuers = (size.getAndIncrement() == 0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            printQueue();
            enqLock.unlock();
        }
        if (mustWakeDequeuers) {
            deqLock.lock();
            try {
                notEmpty.signalAll();
            } finally {
                deqLock.unlock();
            }
        }
    }

    public String deq() {
        String vehicleName = null;
        boolean mustWakeEnqueuers = false;
        deqLock.lock();
        try {
            while (size.get() == 0) {
                notEmpty.await();
            }
            vehicleName = head.next.vehicleName;
            head = head.next;
            mustWakeEnqueuers = (size.getAndDecrement() == capacity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            printQueue();
            deqLock.unlock();
        }
        if (mustWakeEnqueuers) {
            enqLock.lock();
            try {
                notFull.signalAll();
            } finally {
                enqLock.unlock();
            }
        }
        return vehicleName;
    }

    private void printQueue() {
        if (size.get() > 0) {
            enqLock.lock();
            deqLock.lock();
            try {
                Node curr = head.next;
                System.out.println("Current cars on intersection " + id + ":\n");
                System.out.print("|");
                while (curr != null) {
                    System.out.print(curr.vehicleName);
                    curr = curr.next;
                    if (curr != null) {
                        System.out.print(" -> ");
                    }
                }
                System.out.print("|");
                System.out.println("");
            } finally {
                enqLock.unlock();
                deqLock.unlock();
            }
        }
    }

    public class Node {
        String vehicleName;
        volatile Node next;

        public Node(String _vehicleName) {
            this.vehicleName = _vehicleName;
            this.next = null;
        }
    }
}
