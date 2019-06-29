
import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.IDocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;
import net.ravendb.client.documents.session.SessionOptions;
import net.ravendb.client.documents.session.TransactionMode;

public class Teste1 {
    static int n_threads = 10;
  
    public static void main(String[] args) {
        try (IDocumentStore store = new DocumentStore(new String[]{ 
            "http://127.0.0.1:8080",
            "http://127.0.0.2:8080",
            "http://127.0.0.3:8080"}, "Airbnb")) {

            store.initialize();  

            SessionOptions sessionOptions = new SessionOptions();
            sessionOptions.setTransactionMode(TransactionMode.CLUSTER_WIDE);
            
            try (IDocumentSession session = store.openSession(sessionOptions)) {
                int i;
                for (i=0;i<n_threads;i++){
                    Runnable r = new MyRunnable(session, store);
                    new Thread(r).start();
                }
            }
        }
    }
 
}
