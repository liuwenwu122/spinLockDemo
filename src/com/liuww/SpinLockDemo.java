package com.liuww;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Date: 2019/10/13
 * @Author: WenWu.Liu
 * @Desc: 自旋锁小demo
 */
public class SpinLockDemo {

    private static AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();


    public void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t invoked my myLock");

        while (!atomicReference.compareAndSet(null, thread)) {

        }


    }

    public void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoked my myUnLock");

    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();

        // 线程A调用
        new Thread(() -> {
            // 线程A调用加锁
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 线程A调用解锁
            spinLockDemo.myUnLock();
        }, "A").start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 线程B调用
        new Thread(() -> {
            // 线程B调用加锁
            spinLockDemo.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 线程A调用解锁
            spinLockDemo.myUnLock();
        }, "B").start();
    }
}




