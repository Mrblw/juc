package com.buliwen.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一、i++的原子性问题，i++操作不具备原子性，其内存实现方式实际为三个步骤：
 *          1、从内存中得到i的数值  i = 10;  ----------->  读
 *          2、将数值赋值给temp   temp = i； 并且 temp = i+1； --------> 写
 *          3、将temp的值再赋给i  i= temp； ----------> 改
 *
 * 二、原子变量：jdk1.5之后的java.util.concurrent.atomic包下提供了常用的原子变量：
 *      其中实现：
 *          1、使用volatile关键字来保证内存的可见性
 *          2、CAS（compare and swap）算法保证数据的原子性
 *              CAS 算法是硬件对于并发操作共享数据的支持
 *              CAS 包含了三个操作数：
 *              内存值：V
 *              预估值：A
 *              更新值：B
 *
 *              当且仅当：V == A 时，V = B，否则则不做任何操作（这部分当更新失败会有重新尝试的操作）
 *
 *
 *              public final int getAndIncrement() {
 *                  return unsafe.getAndAddInt(this, valueOffset, 1);
 *              }
 *
 *              public final int getAndAddInt(Object var1, long var2, int var4) {
 *                  int var5;
 *                  do {
 *                      var5 = this.getIntVolatile(var1, var2);
 *                  } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
 *
 *                  return var5;
 *              }
 *
 *              public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
 */
public class AtomicTest {

    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }
}

class AtomicDemo implements Runnable {

    //private int i = 0;

    private AtomicInteger i = new AtomicInteger(0);

    @Override
    public void run() {
        try {
            Thread.sleep(2000L);
            System.out.println(getI());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public int getI() {
        return i.getAndIncrement();
    }
}
