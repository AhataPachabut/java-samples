import java.util.Random;
import org.junit.jupiter.api.Test;

//Guarded Blocks
public class Task4Test {

    @Test
    void test() {
        var random = new Random();

        var pool = new BlockingObjectPool(5);

        var thread1 = new Thread(() -> {
            while (true) {
                pool.get();
            }
        });
        var thread2 = new Thread(() -> {
            while (true) {
                pool.put(random.nextInt());
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {

        }
    }

    /**
     * Pool that block when it has not any items or it full
     */
    public class BlockingObjectPool {

        private Object[] collection;
        private int count;

        /**
         * Creates filled pool of passed size
         *
         * @param size of pool
         */
        public BlockingObjectPool(int size) {
            collection = new Object[size];
            count = 0;
        }

        /**
         * Gets object from pool or blocks if pool is empty
         *
         * @return object from pool
         */
        public synchronized Object get() {
            while (count == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }
            var result = collection[count - 1];
            collection[count - 1] = null;
            count--;
            System.out.println("Collection after get -> " + count);
            notifyAll();
            return result;
        }

        /**
         * Puts object to pool or blocks if pool is full
         *
         * @param object to be taken back to pool
         */
        public synchronized void put(Object object) {
            while (count == collection.length) {
                try {
                    wait();
                } catch (InterruptedException e) {

                }
            }
            count++;
            collection[count - 1] = object;
            System.out.println("Collection after put -> " + count);
            notifyAll();
        }
    }
}
