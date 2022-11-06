package operations.dataaccess;

import models.Work;
import operations.SPARQLOperations;
import operations.queries.Works;
import org.apache.jena.rdf.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

        String workURI = associatedTriples.get(0).get("work");

        work.setURI(workURI);

        for (HashMap<String, String> associatedTriple : associatedTriples) {
            associatedTriple.remove("work");
        }

        work.setAssociatedTriples(associatedTriples);

        return work;
    }
    /*
    public Map<String, String> updateWork(String composerId, String workId, Map<String, Object> workData) {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        HashMap<String, String> response = new HashMap<>();

        conn.executeUpdate(queries.updateWork());

        return response;
    }

    public Map<String, String> createWork(Map<String, Object> workData) {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        HashMap<String, String> response = new HashMap<>();

        conn.executeUpdate(queries.createWork());

        return response;
    }*/

    public Map<String, String> deleteWork(String composerId, String workId) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        conn.executeUpdate(queries.deleteWork(composerId, workId));

        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Event was deleted successfully.");

        return response;
    }
}
