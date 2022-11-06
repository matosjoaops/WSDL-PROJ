package operations.dataaccess;

import models.Composer;
import models.Work;
import operations.SPARQLOperations;
import operations.queries.Composers;
import operations.queries.Works;
import org.apache.jena.rdf.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class WorkDA {

    public HashMap<String, String> hosts = new HashMap<>();
    Works queries = new Works();
    public Model model;

    public WorkDA(String host) {
        this.hosts.put("sparql", host + "sparql");
        this.hosts.put("data", host + "data");
        this.hosts.put("update", host + "update");
        this.hosts.put("default", host);
    }
    public Work getWork(String composerId, String workId) {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        Work work = new Work();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getWork(composerId, workId));

        if (associatedTriples.size() == 0) {
            return work;
        }

        String composerURI = associatedTriples.get(0).get("composer");

        work.setURI(composerURI);

        for (HashMap<String, String> associatedTriple : associatedTriples) {
            associatedTriple.remove("composer");
        }

        work.setAssociatedTriples(associatedTriples);

        return work;
    }
}
