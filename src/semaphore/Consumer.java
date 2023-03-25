package semaphore;

public class Consumer implements Runnable{
    private Storage storage;

    public Consumer() {}

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        // while 循环使进程一直运行下去，如果没有 while 循环，则一个进程只会消费 1 次。
        while (true) {
            try {
                // 每次消费前睡眠 3 秒钟，而每次生产前线程睡眠 1 秒钟，这保证了生产比消费更快。
                Thread.sleep(3000);
                storage.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
