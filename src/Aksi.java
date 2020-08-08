public class Aksi implements Runnable {

    private Buku<Proses> pool;
    private int threadNo;

    public Aksi(Buku<Proses> pool, int threadNo){
        this.pool = pool;
        this.threadNo = threadNo;
    }

    public void run() {
        Proses exportingProcess = pool.pinjamBuku();
        System.out.println("Buku di rak-"
                + exportingProcess.getNoProses() + " di kembalikan ");

        pool.kembaliBuku(exportingProcess);

        System.out.println("Buku di rak-"
                + exportingProcess.getNoProses() + " di pinjam");
    }
}

