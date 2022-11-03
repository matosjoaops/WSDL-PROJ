package controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import services.EventService;

import java.util.HashMap;

@RestController
public class EventController extends Controller {

    @CrossOrigin
    @GetMapping(value = {"/event/{id}"})
    public HashMap<String, Object> getEvent(
            @PathVariable(name = "id") String id
    ) {
        String fusekiHost = getFusekiHost("default");
        EventService eventService = new EventService();

        return eventService.get(fusekiHost, id);
    }
}