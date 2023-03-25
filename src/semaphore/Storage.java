package semaphore;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/*
* Semaphore 是一种基于计数的信号量。它可以设定一个阈值，基于此，多个线程竞争获取许可信号，做完自己的申请后归还，超过阈值后，线程申请许可信号将会被阻塞。
* */
public class Storage {
    // 仓库存储的载体
    private LinkedList<Object> list = new LinkedList<>();

    // 仓库的最大容量
    final Semaphore notFull = new Semaphore(10);

    // 将线程挂起，等待其他触发
    final Semaphore notEmpty = new Semaphore(0);

    // 互斥锁
    final Semaphore mutex = new Semaphore(1);

    public void produce() {
        try {
            notFull.acquire();
            mutex.acquire();
            list.add(new Object());
            System.out.println("[Producer" + Thread.currentThread().getName() + "] make a production, the storage has " + list.size() + " items.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            notEmpty.release();
        }
    }

    public void consume() {
        try {
            notEmpty.acquire();
            mutex.acquire();
            list.remove();
            System.out.println("[Consumer" + Thread.currentThread().getName() + "] consume one item, the storage has " + list.size() + " items.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            notFull.release();
        }
    }
}
