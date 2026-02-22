package multithreading;

class MyThread extends Thread{
    public void run(){
        Counter.decrement();
        System.out.println("Thread Running t1 : " + Thread.currentThread().getName() + "\n Count is " + Counter.count);
    }
}
