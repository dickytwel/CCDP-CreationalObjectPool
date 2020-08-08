import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ObjectPoolDemo{
    private Buku<Proses> pool;
    private AtomicLong processNo=new AtomicLong(0);
    public void setUp() {

        pool = new Buku<Proses>(2, 4, 5) {
            protected Proses createBuku() {
                return new Proses( processNo.incrementAndGet());
            }
        };
    }
    public void tearDown() {
        pool.shutdown();
    }
    public void testBuku() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(new Aksi(pool, 1));
        executor.execute(new Aksi(pool, 2));
        executor.execute(new Aksi(pool, 3));
        executor.execute(new Aksi(pool, 4));

        executor.shutdown();
        try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[])  {
        ObjectPoolDemo op=new ObjectPoolDemo();
        op.setUp();
        op.tearDown();
        op.testBuku();
    }
}