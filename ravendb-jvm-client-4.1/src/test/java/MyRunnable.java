
import net.ravendb.client.documents.DocumentStore;
import net.ravendb.client.documents.IDocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;
import net.ravendb.client.documents.session.SessionOptions;
import net.ravendb.client.documents.session.TransactionMode;

public class MyRunnable implements Runnable {
    IDocumentSession session;
    IDocumentStore store;
    int i;
    int n_threads;
    
    public MyRunnable(IDocumentSession session, IDocumentStore store) {
        this.session = session;
        this.store = store;
    }
   
    public MyRunnable(int i, int n_threads) {
        this.i = i;
        this.n_threads = n_threads;
    }
  
    public void run(){
     try{

         try (IDocumentStore store = new DocumentStore(new String[]{ 
             "http://127.0.0.1:8080",
             "http://127.0.0.2:8080",
             "http://127.0.0.3:8080"}, "Airbnb"))
         {
             store.initialize();  

             Operacoes op = new Operacoes();
             SessionOptions sessionOptions = new SessionOptions();
             sessionOptions.setTransactionMode(TransactionMode.CLUSTER_WIDE);

             try (IDocumentSession session = store.openSession(sessionOptions))
             {
                 if (this.i <= (n_threads *0.8)){
                     op.select(session);
                     
                 }
                 else{
                     op.update(store, this.i);
                 }
             }        
         }
     } catch (Exception e){}
    };
}


