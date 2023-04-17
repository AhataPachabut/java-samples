import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.Test;

public class Task2Test {

    private Collection<Integer> collection = new ArrayList<>();

    @Test
    void test() {
        Random random = new Random();
        Executor executor = Executors.newFixedThreadPool(3);

        executor.execute(() -> {
            while (true) {
                add(random.nextInt());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        });
        executor.execute(() -> {
            while (true) {
                printSum();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        });
        executor.execute(() -> {
            while (true) {
                printSquareRoot();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        });

        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
        }
    }

    private synchronized void add(Integer value) {
        collection.add(value);
    }

    private synchronized void printSum() {
        System.out.println("Sum=" +
            collection.stream().reduce((integer, integer2) -> integer + integer2).get());
    }

    private synchronized void printSquareRoot() {
        System.out.println("Square root=" +
            Math.sqrt(
                collection.stream()
                    .map(i -> Math.pow(i, 2))
                    .reduce((aDouble, aDouble2) -> aDouble + aDouble2)
                    .get()
            )
        );
    }


}
