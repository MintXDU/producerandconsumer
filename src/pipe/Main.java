package pipe;

import java.io.IOException;

/*
* 一个线程发送数据到输出管道，另一个线程从输入管道中读取数据。
* 这种方法只适用于两个线程之间的通信，不适合多个线程之间的通信。
* */
public class Main {
    public static void main(String[] args) {
        Producer p = new Producer();
        Consumer c = new Consumer();
        Thread t1 = new Thread(p);
        Thread t2 = new Thread(c);

        try {
            p.getPipedOutputStream().connect(c.getPipedInputStream());
            t2.start();
            t1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
