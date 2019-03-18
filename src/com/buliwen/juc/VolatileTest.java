package com.buliwen.juc;

/**
 * 一、volatile 关键字：
 *          当多个线程进行操作共享数据时，可以保证内存中数据的可见性。
 *
 *          相较于synchronized的锁策略是一种较为轻量的同步策略。
 *             **** 注意：
 *              1、volatile不具备"互斥性"
 *              2、volatile不保证变量的"原子性"
 *
 *       自我思考：
 *          当多个线程操作内存中的数据时，实际上数据是维护在自己的缓存中，如果没有线程间通讯的
 *          同步策略，数据是互不可见，不可共享的，共享数据是线程从主存中得到的数据
 *
 *          当加了同步锁 synchronized ，获取到锁的线程会从主存刷新数据，以保证线程间数据的共享，
 *          但是加锁会造成其他线程的阻塞，大大降低cpu的运行效率。
 *
 *          加了volatile 关键字相当于是多个线程都在操作主存的数据，保证了线程的可见性，但是却不会
 *          保证原子性，可能造成数据的脏读脏写
 *
 */
public class VolatileTest {

    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        Thread thread = new Thread(td);
        thread.start();

        while (true){
            //synchronized (td){
                if (td.isFlag()){
                    System.out.println("main get td.flag is true ~");
                    break;
                }
            //}
        }
    }
}

class ThreadDemo implements Runnable{

    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag is change to " + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}