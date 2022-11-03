package services;

import models.Event;
import operations.dataaccess.EventDA;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EventService {

    public HashMap<String, Object> get(String host, String id) {
        EventDA eventDA = new EventDA(host);
        Event event = eventDA.getEvent(id);

        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", event.getURI());
        result.put("associatedTriples", event.getAssociatedTriples());

        return result;
    }
}


