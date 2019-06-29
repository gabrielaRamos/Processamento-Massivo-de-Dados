
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
    System.out.println(durationInMs);
    System.out.println(totalResults);
    }catch (Exception e){System.out.println(e);}
    
    
    };
    
    public void update (IDocumentStore store){
    System.out.println("OIeeeee");
    Operation operation = store
        .operations()
        .sendAsync(new PatchByQueryOperation(new IndexQuery("" +
            " from Neighbourhoods as n " +
            " where id() in ( 'Neighbourhoods/0000000000008299275-A')" +
            " update {" +
            "  n.neighbourhood = 'teste'" +
            "}")));
    operation.waitForCompletion();
     CollectionStatistics stats = store.maintenance().send(new GetCollectionStatisticsOperation());
     System.out.print(stats.getCountOfConflicts());
    };
}
