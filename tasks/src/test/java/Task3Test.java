import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

public class Task3Test {

    private volatile Queue<String> queue = new ArrayDeque<>();

    @Test
    void test() {
        Runnable producer = () -> {
            for (var i = 1; ; i++) {
                var value = Thread.currentThread().getId() + "-" + i;
                queue.add(value);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        };

        Runnable consumer = () -> {
            while (true) {
                if (queue.size() > 0) {
                    var result = queue.poll();
                    System.out.println("Pool " + result);
                    System.out.println("Queue " + queue.toString());
                }

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        };

        Executor executor = Executors.newFixedThreadPool(10);
        for (var i = 0; i < 5; i++) {
            executor.execute(producer);
        }
        for (var i = 0; i < 2; i++) {
            executor.execute(consumer);
        }

        try {
            Thread.currentThread().join();
        } catch (
            InterruptedException e) {
        }
    }
}
