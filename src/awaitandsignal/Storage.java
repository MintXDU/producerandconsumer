package awaitandsignal;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Storage {
    // 仓库容量
    private final int MAX_SIZE = 10;

    // 仓库存储的载体
    private LinkedList<Object> list = new LinkedList<>();

    // 锁
    private final Lock lock = new ReentrantLock();

    // 仓库满的条件变量
    private final Condition full = lock.newCondition();

    // 仓库空的条件变量
    private final Condition empty = lock.newCondition();

    /*
    * 生产动作：
    * 1. 当缓冲区满时，生产者线程停止执行，放弃锁，使自己处于等待状态，让其他线程执行。
    * 2. 若缓冲区没满，生产者线程生产一个物品，向其他等待的线程发出可执行的通知，同时放弃锁。
    * */
    public void produce() {
        // 获得锁
        lock.lock();
        while (list.size() + 1 > MAX_SIZE) {
            System.out.println("[Producer" + Thread.currentThread().getName() + "] storage is full.");
            try {
                full.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.add(new Object());
        System.out.println("[Producer" + Thread.currentThread().getName() + "] make a production, the storage has " + list.size() + " items.");
        empty.signalAll();
        lock.unlock();
    }

    /*
    * 消费动作：
    * 1. 当缓冲区空时，消费者线程停止执行，放弃锁，使自己处于等待状态，让其他线程执行。
    * 2. 若缓冲区不为空，消费者线程消费一个物品，向其他限额还能够发出可执行的通知，同时放弃锁。
    * */
    public void consume() {
        lock.lock();
        while (list.size() < 1) {
            System.out.println("[Consumer" + Thread.currentThread().getName() + "] storage is empty.");
            try {
                empty.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.remove();
        System.out.println("[Consumer" + Thread.currentThread().getName() + "] consume one item, the storage has " + list.size() + " items.");
        full.signalAll();
        lock.unlock();
    }
}
