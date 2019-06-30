
import java.util.List;
import net.ravendb.client.documents.IDocumentStore;
import net.ravendb.client.documents.conventions.DocumentConventions;
import net.ravendb.client.documents.operations.CollectionStatistics;
import net.ravendb.client.documents.operations.GetCollectionStatisticsOperation;
import net.ravendb.client.documents.operations.Operation;
import net.ravendb.client.documents.operations.PatchByQueryOperation;
import net.ravendb.client.documents.queries.IndexQuery;
import net.ravendb.client.documents.session.IDocumentSession;
import net.ravendb.client.documents.session.QueryStatistics;
import net.ravendb.client.primitives.Reference;

public class Operacoes {
    public void select (IDocumentSession session){
      
    Reference<QueryStatistics> stats = new Reference<>();
    try{    
    List<Reviews> results = session.advanced().
        rawQuery(Reviews.class, "from Reviews_summary where search(comments , 'canceled')")
        .statistics(stats)
        .toList();  
    
    long durationInMs = stats.value.getDurationInMs();
    int totalResults = stats.value.getTotalResults();
    
    System.out.println("Select - Duracao: " + durationInMs + " Milissegundos");
    System.out.println("Total: " + totalResults);
    }catch (Exception e){System.out.println(e);}
    
    
    };
    
    public void update (IDocumentStore store, int i){
        String  id_reviews = "0000000000008701264-A";
        int id = 8701264 + (i-2);
        id_reviews = "000000000000" + Integer.toString(id) + "-A";
      
        long start_time = System.nanoTime();
        
        Operation operation = store
            .operations()
            .sendAsync(new PatchByQueryOperation(new IndexQuery("" +
                " from Reviews_summary as n " +
                " where id() in ( 'Reviews_summary/" + id_reviews + "')" +
                " update {" +
                "  n.reviewer_name = 'Michael L'" +
                "}")));
        operation.waitForCompletion();
        long end_time = System.nanoTime();
        double difference = (end_time - start_time) / 1e9;
        CollectionStatistics stats = store.maintenance().send(new GetCollectionStatisticsOperation());
        System.out.println("Conflitos: " + stats.getCountOfConflicts());
        System.out.println("Update - Duracacao: " + difference + " segunos");

    };
}
