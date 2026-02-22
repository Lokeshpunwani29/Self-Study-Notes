package multithreading;

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        Thread t2 = new Thread(new MyTask());
        Thread t3 = new Thread(new MyTask());
//        t2.setPriority(Thread.MAX_PRIORITY);
//        Thread.sleep(2000);
        //t1.join();
        t1.start();
        Thread.sleep(2000);
        t2.start();
        //Thread.sleep(2000);
        t3.start();


    }
}
