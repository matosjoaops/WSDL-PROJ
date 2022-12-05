package operations.dataaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.update.UpdateRequest;

import models.Conductor;
import operations.SPARQLOperations;
import operations.queries.Conductors;

import static operations.dataaccess.EventDA.convertTriplesStringsToIRIs;
import static operations.dataaccess.EventDA.validateInsertForm;

public class ConductorDA {
    public HashMap<String, String> hosts = new HashMap<>();
    Conductors queries = new Conductors();
    public Model model;

    public ConductorDA(String host) {
        this.hosts.put("sparql", host + "sparql");
        this.hosts.put("data", host + "data");
        this.hosts.put("update", host + "update");
        this.hosts.put("default", host);
    }
    public Conductor getConductor(String id) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        Conductor conductor = new Conductor();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getConductor(id));
    
        if (associatedTriples.size() == 0) {
            throw new Exception(String.format("The conductor with id '%s' does not exist.", id));
        }

        String conductorURI = associatedTriples.get(0).get("conductor");

        conductor.setURI(conductorURI);

        for (HashMap<String, String> associatedTriple : associatedTriples) {
            associatedTriple.remove("conductor");
        }

        conductor.setAssociatedTriples(associatedTriples);

        return conductor;
    }

    public Map<String, String> deleteConductor(String id) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));

        conn.executeUpdate(queries.deleteConductor(id));

        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Conductor deleted successfully.");

        return response;
    }

    public Map<String, String> insertConductor(Map<String, Object> insertForm) throws Exception {
        HashMap<String, String> response = new HashMap<>();

        if (!validateInsertForm(insertForm)) {
            response.put("message", "Invalid body! Please provide the conductor URI and the associated triples.");
            return response;
        }

        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));

        String conductorURI = (String) insertForm.get("URI");
        ArrayList<HashMap<String, String>> associatedTriples = (ArrayList<HashMap<String, String>>) insertForm.get("associatedTriples");

        ArrayList<UpdateRequest> insertQueries = queries.insertConductor(
                conductorURI,
                convertTriplesStringsToIRIs(associatedTriples)
        );

        conn.executeUpdateList(insertQueries);

        response.put("message", "Conductor created successfully.");

        return response;
    }
}
