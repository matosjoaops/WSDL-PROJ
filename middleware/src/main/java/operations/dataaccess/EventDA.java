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

    private boolean validateInsertForm(Map<String, Object> insertForm) {
        if (!insertForm.containsKey("URI") || !insertForm.containsKey("associatedTriples")) {
            return false;
        }

        ArrayList<HashMap<String, String>> associatedTriples = (ArrayList<HashMap<String, String>>) insertForm.get("associatedTriples");

        for (HashMap<String, String> associatedTriple : associatedTriples) {
            if (!associatedTriple.containsKey("predicate") || !associatedTriple.containsKey("object") || associatedTriple.size() != 2) {
                return false;
            }
        }

        return true;
    }

    public Map<String, String> insertEvent(Map<String, Object> insertForm) throws Exception {
        HashMap<String, String> response = new HashMap<>();

        if (!validateInsertForm(insertForm)) {
            response.put("message", "Invalid body! Please provide the event URI and the associated triples.");
            return response;
        }

        SPARQLOperations conn = new SPARQLOperations(this.host);

        String eventURI = (String) insertForm.get("URI");
        ArrayList<HashMap<String, String>> associatedTriples = (ArrayList<HashMap<String, String>>) insertForm.get("associatedTriples");

        conn.executeUpdate(queries.insertEvent(
                eventURI,
                associatedTriples
        ));

        response.put("message", "Event created successfully.");

        return response;
    }
}
