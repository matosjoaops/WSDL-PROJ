package services;

import models.Event;
import operations.dataaccess.EventDA;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EventService {

    EventDA eventDA;

    public EventService(String host) {
        eventDA = new EventDA(host);
    }

    public HashMap<String, Object> get(String id) throws Exception {
        Event event = eventDA.getEvent(id);

        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", event.getURI());
        result.put("associatedTriples", event.getAssociatedTriples());

        return result;
    }

    public void delete(String id) throws Exception {
        eventDA.deleteEvent(id);
    }
}


