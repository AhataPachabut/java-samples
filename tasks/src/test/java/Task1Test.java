import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Test;

public class Task1Test {

    //ConcurrentModificationException is thrown
    @Test
    void test() {
        Map<Integer, Integer> map = new HashMap<>();
        fillAndSumMap(map);
    }

    //wrapper
    //ConcurrentModificationException is thrown
    @Test
    void test1() {
        Map<Integer, Integer> map = Collections.synchronizedMap(new HashMap<>());
        fillAndSumMap(map);
    }

    @Test
    void test2() {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        fillAndSumMap(map);
    }

    //ThreadSaveMap
    @Test
    void test3() {
        Map<Integer, Integer> map = new ThreadSaveMap<>();
        fillAndSumMap(map);
    }

    private void fillAndSumMap(Map<Integer, Integer> map) {
        var start = System.currentTimeMillis();

        Random random = new Random();
        Thread t1 = new Thread(() -> {
            for (var i = 0; i < 1000; i++) {
                var r = random.nextInt();
                map.put(i, r);
                System.out.println("Put " + r);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
            }
        });

        Thread t2 = new Thread(() -> {
            int summ;
            while (t1.isAlive()) {
                summ = map.values().stream().reduce(Integer::sum).get();
                System.out.println("Sum=" + summ);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }
        });

        t1.start();
        t2.start();

        try {
            t2.join();
        } catch (InterruptedException ignored) {
        }

        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
    }

    //volatile + synchronized on write operations
    public class ThreadSaveMap<K, V> extends AbstractMap<K, V> {

        private volatile Map<K, V> map = new HashMap<>();

        @Override
        public Set<Entry<K, V>> entrySet() {
            return map.entrySet();
        }

        @Override
        public synchronized V put(K key, V value) {
            return map.put(key, value);
        }

        @Override
        public synchronized V remove(Object key) {
            return map.remove(key);
        }

        @Override
        public synchronized void putAll(Map<? extends K, ? extends V> m) {
            map.putAll(m);
        }

        @Override
        public synchronized void clear() {
            map.clear();
        }
    }
}
