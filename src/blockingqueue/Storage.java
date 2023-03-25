package blockingqueue;

import java.util.concurrent.LinkedBlockingQueue;

/*
* BlockingQueue 是一个已经在内部实现了同步的队列，实现方法是 ReentrantLock。它可以在生成对象时指定容量大小，用于阻塞操作的是 put() 和 take() 方法。
* put() 方法：类似于生产者，容量达到最大时，自动阻塞。
* take() 方法：类似于消费者，容量为 0 时，自动阻塞。
* */
public class Storage {
    // 仓库存储的载体
    private LinkedBlockingQueue<Object> list = new LinkedBlockingQueue<>(10);

    /*
    * 生产动作：
    * 只管 put, 阻塞队列自己会判断是否已满。
    * */
    public void produce() {
        try {
            list.put(new Object());
            System.out.println("[Producer" + Thread.currentThread().getName() + "] make a production, the storage has " + list.size() + " items.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /*
    * 消费动作：
    * 只管 take ，阻塞队列自己会判断是否为空。
    * */
    public void consume() {
        try {
            list.take();
            System.out.println("[Consumer" + Thread.currentThread().getName() + "] consume one item, the storage has " + list.size() + " items.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
