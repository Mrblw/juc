package com.buliwen.juc;

import java.util.Random;

/**
 *
 */
public class CASModel {

    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                int value = cas.getValue();
                int destValue = new Random().nextInt(100);
                //System.out.println(Thread.currentThread().getName() + "  dest is "+ destValue);
                boolean b = cas.compareAndSet(value, destValue);
                System.out.println(b + "  =========    " + Thread.currentThread().getName());
            }).start();
        }
    }
}

class CompareAndSwap{

    private int value;

    /**
     * 获取内存值
     */
    public synchronized int getValue(){
        return value;
    }

    /**
     * 比较
     */
    public synchronized int compareAndSwap(int expectedValue,int newValue){
        int oldValue = this.value;
        System.out.println(Thread.currentThread().getName() + " ==================== old is " + oldValue + " , expect is " + expectedValue);
        if (oldValue == expectedValue){
            this.value = newValue;
        }
        return oldValue;
    }

    /**
     * 设置值
     */
    public synchronized boolean compareAndSet(int expectedValue,int newValue){
        return expectedValue == compareAndSwap(expectedValue,newValue);
    }
}
