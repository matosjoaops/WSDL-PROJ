package operations.dataaccess;

import models.Event;
import operations.SPARQLOperations;
import operations.queries.Events;
import org.apache.jena.rdf.model.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class EventDA {
    public HashMap<String, String> hosts = new HashMap<>();
    Events queries = new Events();
    public Model model;

    public EventDA(String host) {
        this.hosts.put("sparql", host + "sparql");
        this.hosts.put("data", host + "data");
        this.hosts.put("update", host + "update");
        this.hosts.put("default", host);
    }
    public Event getEvent(String id) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("default"));
        Event event = new Event();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getEvent(id));

        if (associatedTriples.size() == 0) {
            throw new Exception(String.format("The event with id '%s' does not exist.", id));
        }

        String eventURI = associatedTriples.get(0).get("event");

        event.setURI(eventURI);

        for (HashMap<String, String> associatedTriple : associatedTriples) {
            associatedTriple.remove("event");
        }

        event.setAssociatedTriples(associatedTriples);

        return event;
    }

    public void deleteEvent(String id) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.hosts.get("update"));

        conn.executeUpdate(queries.deleteEvent(id));
    }
}
