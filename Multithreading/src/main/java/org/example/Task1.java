package org.example;

import java.util.*;

public class Task1 {
    public static void main(String[] args) {
        ThreadSafeMap<Integer, Integer> map = new ThreadSafeMap();
        int limit = 100;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < limit; i++) {
                map.put(i, i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("#1#");
                System.out.println(Thread.currentThread().getName() + " " + map);
                System.out.println("#1#");
            }
        });
        Thread thread2 = new Thread(() -> {
            do {
                for (int i = 0; i < map.size(); i++) {
                    map.put(i, map.get(i) + i);
                }
                System.out.println("#2#");
                System.out.println(Thread.currentThread().getName() + " " + map);
                System.out.println("#2#");
            } while (map.size() != limit);
        });
        Thread mapThread = new Thread(() -> {
            do {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("###");
                System.out.println(Thread.currentThread().getName() + " " + map);
                System.out.println("###");
            } while (map.size() != limit);
        });
        mapThread.start();
        thread1.start();
        thread2.start();

    }
}

class ThreadSafeMap<K, V> {
    private final Map<K, V> map;

    public ThreadSafeMap() {
        map = new HashMap<K, V>();
    }

    synchronized void put(K key, V value) {
        map.put(key, value);
    }

    synchronized V get(K key) {
        return map.get(key);
    }

    synchronized V remove(K key) {
        return map.remove(key);
    }

    synchronized int size() {
        return map.size();
    }

    @Override
    synchronized public String toString() {
        return "ThreadSafeMap{" +
                "map=" + map +
                '}';
    }
}