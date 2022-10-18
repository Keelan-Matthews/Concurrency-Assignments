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

    public class Node {
        String vehicleName;
        volatile Node next;

        public Node(String _vehicleName) {
            this.vehicleName = _vehicleName;
            this.next = null;
        }
    }
}
