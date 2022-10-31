package operations.dataaccess;

import models.Composer;
import operations.SPARQLOperations;
import operations.queries.Composers;
import org.apache.jena.rdf.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class ComposerDA {
    public HashMap<String, String> hosts = new HashMap<>();
    Composers queries = new Composers();
    public Model model;

    public ComposerDA(String host) {
        this.hosts.put("sparql", host + "sparql");
        this.hosts.put("data", host + "data");
        this.hosts.put("update", host + "update");
        this.hosts.put("default", host);
    }
    public Composer getComposer(String uuid) {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        Composer composer = new Composer();

        HashMap<String, ArrayList<HashMap<String, String>>> DOC_IDENTITY = new HashMap<>();
        conn.executeQueryAndAddRawContent(queries.getDocId(uuid), DOC_IDENTITY, "uri");
        composer.setDOC_IDENTITY(DOC_IDENTITY);

        return composer;
    }
}
