package zhanj.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

    private final int MAX_THREADS;

    private final LinkedBlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    private final List<Worker> workers;

    private volatile int nThreads = 0;

    public ThreadPool(int n) {
        MAX_THREADS = n;
        this.workers = new ArrayList<>(n);
    }

    public void execute(Runnable task) throws InterruptedException {
        if (nThreads < MAX_THREADS) {
            synchronized (tasks) {
                // add new worker thread
                if (nThreads < MAX_THREADS) {
                    ++nThreads;
                    Worker worker = new Worker(tasks);
                    worker.start();
                    workers.add(worker);
                }
            }
        }
        tasks.put(task);
    }

    private final class Worker extends Thread{

        private final LinkedBlockingQueue<Runnable> tasks;

        public Worker(LinkedBlockingQueue<Runnable> tasks) {
            this.tasks = tasks;
        }

        @Override
        public void run() {
            try {
                System.out.printf("new thread %s started. waiting for tasks ...%n", Thread.currentThread().getName());
                while (true) {
                    Runnable task = tasks.take();
                    task.run();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
