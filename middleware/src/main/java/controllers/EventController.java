package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.EventService;

import java.util.HashMap;

@RestController
public class EventController extends Controller {

    @CrossOrigin
    @GetMapping(value = {"/event/{id}"})
    public HashMap<String, Object> getEvent(
            @PathVariable(name = "id") String id
    ) {
        EventService eventService = new EventService(getFusekiHost("default"));

        try {
            return eventService.get(id);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some error occurred.");
            }
        }
    }

    @CrossOrigin
    @DeleteMapping(value = {"event/{id}"})
    public void deleteEvent(
            @PathVariable(name = "id") String id
    ) {
        EventService eventService = new EventService(getFusekiHost("default"));

        try {
            eventService.delete(id);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some error occurred.");
            }
        }
    }
}