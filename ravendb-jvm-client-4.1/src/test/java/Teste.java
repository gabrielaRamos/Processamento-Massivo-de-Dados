
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Teste {
    static int n_threads = 10;
    static int i;
    
    public static void main(String[] args) throws InterruptedException {
        long start_time = System.nanoTime();
        ExecutorService es = Executors.newCachedThreadPool();
        
        for (i=0;i <=n_threads;i++){
            es.execute(new MyRunnable(i, n_threads));    
        }   
        es.shutdown();
        
        boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        long end_time = System.nanoTime();
        double difference = (end_time - start_time) / 1e9;
        
        System.out.println("Tempo de processamento:" + difference + " segundos ");
    }      
}
