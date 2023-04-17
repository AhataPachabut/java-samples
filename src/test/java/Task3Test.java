import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

//todo wait, notify
public class Task3Test {

    Queue<String> queue = new PriorityQueue<>();

    @Test
    void test() {
        Runnable producer = () -> {
            for (var i = 1; ; i++) {
                synchronized (queue) {
                    var value = Thread.currentThread().getName() + "    " + i;
                    queue.add(value);
                    System.out.println("Add " + value);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        };

        Runnable consumer = () -> {
            while (true) {
                synchronized (queue) {
                    System.out.println("Pool " + queue.poll());
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        };

        Executor executor = Executors.newFixedThreadPool(10);
        for (
            var i = 0;
            i < 5; i++) {
            executor.execute(producer);
        }

        for (
            var i = 0;
            i < 1; i++) {
            executor.execute(consumer);
        }

        try {
            Thread.currentThread().join();
        } catch (
            InterruptedException e) {
        }
    }
}
