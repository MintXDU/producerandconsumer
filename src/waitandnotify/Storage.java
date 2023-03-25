package waitandnotify;

import java.util.LinkedList;

public class Storage {
    // 仓库容量
    private final int MAX_SIZE = 10;

    // 仓库存储的载体
    private LinkedList<Object> list = new LinkedList<>();

    /*
    * 生产动作：
    * 1. 当缓冲区满时，生产者线程停止执行，放弃锁，使自己处于等待状态，让其他线程执行。
    * 2. 若缓冲区没满，生产者线程生产一个物品，向其他等待的线程发出可执行的通知，同时放弃锁。
    * */
    public void produce() {
        synchronized (list) {
            while (list.size() + 1 > MAX_SIZE) {
                System.out.println("[Producer" + Thread.currentThread().getName() + "] storage is full.");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.add(new Object());
            System.out.println("[Producer" + Thread.currentThread().getName() + "] make a production, the storage has " + list.size() + " items.");
            list.notifyAll();
        }
    }

    /*
    * 消费动作：
    * 1. 当缓冲区空时，消费者线程停止执行，放弃锁，使自己处于等待状态，让其他线程执行。
    * 2. 若缓冲区不为空，消费者线程消费一个物品，向其他限额还能够发出可执行的通知，同时放弃锁。
    * */
    public void consume() {
        synchronized (list) {
            while (list.size() < 1) {
                System.out.println("[Consumer" + Thread.currentThread().getName() + "] storage is empty.");
                try {
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            list.remove();
            System.out.println("[Consumer" + Thread.currentThread().getName() + "] consume one item, the storage has " + list.size() + " items.");
            list.notifyAll();
        }
    }
}
