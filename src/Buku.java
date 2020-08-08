import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class Buku<T> {

    private ConcurrentLinkedQueue<T> pool;

    private ScheduledExecutorService executorService;

    public Buku(final int minBuku) {
        initialize(minBuku);

    }

    public Buku(final int minBuku, final int maxBuku, final long validationInterval) {
        initialize(minBuku);
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                int size = pool.size();

                if (size < minBuku) {
                    int sizeToBeAdded = minBuku + size;
                    for (int i = 0; i < sizeToBeAdded; i++) {
                        pool.add(createBuku());
                    }
                } else if (size > maxBuku) {
                    int sizeToBeRemoved = size - maxBuku;
                    for (int i = 0; i < sizeToBeRemoved; i++) {
                        pool.poll();
                    }
                }
            }
        }, validationInterval, validationInterval, TimeUnit.SECONDS);
    }

    public T pinjamBuku() {
        T object;
        if ((object = pool.poll()) == null) {
            object = createBuku();
        }
        return object;
    }

    public void kembaliBuku(T object) {
        if (object == null) {
            return;
        }
        this.pool.offer(object);
    }

    public void shutdown(){
        if (executorService != null){
            executorService.shutdown();
        }
    }

    protected abstract T createBuku();

    private void initialize(final int minBuku)  {
        pool = new ConcurrentLinkedQueue<T>();
        for (int i = 0; i < minBuku; i++) {
            pool.add(createBuku());
        }
    }
}