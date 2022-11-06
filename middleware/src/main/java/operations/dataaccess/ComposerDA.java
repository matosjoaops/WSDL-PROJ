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
    public Composer getComposer(String id) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        Composer composer = new Composer();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getComposer(id));

        if (associatedTriples.size() == 0) {
            throw new Exception(String.format("The composer with id '%s' does not exist.", id));
        }

        String composerURI = associatedTriples.get(0).get("composer");

        composer.setURI(composerURI);

        for (HashMap<String, String> associatedTriple : associatedTriples) {
            associatedTriple.remove("composer");
        }

        composer.setAssociatedTriples(associatedTriples);

        return composer;
    }
}
