package zhanj.threadpool;

public class App {

    public static void main(String[] args) throws InterruptedException {
        ThreadPool pool = new ThreadPool(3);
        for (int i = 0; i < 10; ++i) {
            final int j = i;
            pool.execute(() -> {
                System.out.printf("%s executes task-%d%n", Thread.currentThread().getName(), j);
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s finished task-%d%n", Thread.currentThread().getName(), j);
            });
        }
    }
}
