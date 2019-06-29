import net.ravendb.client.documents.IDocumentStore;
import net.ravendb.client.documents.session.IDocumentSession;

public class MyRunnable implements Runnable {
    IDocumentSession session;
    IDocumentStore store;
    
   public MyRunnable(IDocumentSession session, IDocumentStore store) {
       this.session = session;
       this.store = store;
   }

   public void run() {
        try{
            
            Operacoes op = new Operacoes();
            
            op.select(this.session);
            op.update(this.store);
            
        } catch (Exception e){
            System.out.println(e);
        }
   }
}
