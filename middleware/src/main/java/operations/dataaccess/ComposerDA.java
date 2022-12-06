package operations.dataaccess;

import models.Composer;
import operations.SPARQLOperations;
import operations.queries.Composers;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static operations.dataaccess.EventDA.convertTriplesStringsToIRIs;
import static operations.dataaccess.EventDA.validateInsertForm;


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

    public Composer getDBpediaData(String id) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        Composer composer = new Composer();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getDBpediaData(id));

        if (associatedTriples.size() == 0) {
            throw new Exception(String.format("The composer with id '%s' does not exist in DBPedia.", id));
        }

        composer.setAssociatedTriples(associatedTriples);

        return composer;
    }

    public Map<String, String> deleteComposer(String id) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));

        conn.executeUpdate(queries.deleteComposer(id));

        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Composer deleted successfully.");

        return response;
    }

    public Map<String, String> insertComposer(Map<String, Object> insertForm) throws Exception {
        HashMap<String, String> response = new HashMap<>();

        if (!validateInsertForm(insertForm)) {
            response.put("message", "Invalid body! Please provide the composer URI and the associated triples.");
            return response;
        }

        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));

        String composerURI = (String) insertForm.get("URI");
        ArrayList<HashMap<String, String>> associatedTriples = (ArrayList<HashMap<String, String>>) insertForm.get("associatedTriples");

        ArrayList<UpdateRequest> insertQueries = queries.insertComposer(
                composerURI,
                convertTriplesStringsToIRIs(associatedTriples)
        );

        conn.executeUpdateList(insertQueries);

        response.put("message", "Composer created successfully.");

        return response;
    }
}
