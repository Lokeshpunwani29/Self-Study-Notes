package multithreading;

public class MyTask implements Runnable{
    @Override
    public void run(){
        Counter.increment();
        System.out.println("My Task t2 is running using thread: " + Thread.currentThread().getName() + " \n Count is " + Counter.count);
    }
}
