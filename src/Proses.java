public class Proses {
    private long noProses;

    public Proses(long noProses)  {
        this.noProses = noProses;

        System.out.println("Buku di rak-" + noProses + " ada");
    }

    public long getNoProses() {
        return noProses;
    }
}