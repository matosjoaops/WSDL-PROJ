package operations.dataaccess;


import models.Work;
import operations.SPARQLOperations;
import operations.queries.MyQueries;
import org.apache.jena.rdf.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QueryDA {

    public HashMap<String, String> hosts = new HashMap<>();

    MyQueries queries = new MyQueries();
    public Model model;

    public QueryDA(String host) {
        this.hosts.put("sparql", host + "sparql");
        this.hosts.put("data", host + "data");
        this.hosts.put("update", host + "update");
        this.hosts.put("default", host);
    }

    public List<Work> getComposerWorks(String composerId) {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        List<Work> works = new ArrayList<>();
        String lastURI = "";

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getComposerWorks(composerId));

        if (associatedTriples.size() == 0) {
            return works;
        }

        for (HashMap<String, String> triple : associatedTriples) {
            String URI = triple.get("work");
            triple.remove("work");
            Work work  = new Work();
            if (URI.equals(lastURI)) {
                work = works.get(works.size() - 1);
                work.addTriple(triple);
            }
            else {
                work.setURI(URI);
                work.addTriple(triple);
                works.add(work);
            }
            lastURI = URI;
        }

        return works;
    }
}
