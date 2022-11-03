package operations.dataaccess;

import models.Event;
import operations.SPARQLOperations;
import operations.queries.Events;
import org.apache.jena.rdf.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventDA {
    public String host;
    Events queries = new Events();
    public Model model;

    public EventDA(String host) {
        this.host = host;
    }
    public Event getEvent(String id) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.host);
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

    public Map<String, String> deleteEvent(String id) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.host);

        conn.executeUpdate(queries.deleteEvent(id));

        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Event deleted successfully.");

        return response;
    }

    public Map<String, String> insertEvent(Map<String, Object> insertForm) throws Exception {
        SPARQLOperations conn = new SPARQLOperations(this.host);

        String eventURI = (String) insertForm.get("URI");
        ArrayList<HashMap<String, String>> associatedTriples = (ArrayList<HashMap<String, String>>) insertForm.get("associatedTriples");

        conn.executeUpdate(queries.insertEvent(
                eventURI,
                associatedTriples
        ));

        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Event created successfully.");

        return response;
    }
}
