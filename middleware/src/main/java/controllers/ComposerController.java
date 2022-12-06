package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.ComposerService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ComposerController extends Controller {

    @CrossOrigin
    @GetMapping(value = {"/composer/{id}"})
    public HashMap<String, Object> getComposer(
            @PathVariable(name = "id") String id
    ) {
        ComposerService composerService = new ComposerService(getFusekiHost("default"));

        try {
            return composerService.get(id);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                HashMap<String, Object> response = new HashMap<>();
                response.put("message", e.getMessage());
                return response;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some error occurred.");
            }
        }
    }

    @CrossOrigin
    @GetMapping(value = {"/composer/dbpedia-federation/{id}"})
    public HashMap<String, Object> getDBpediaData(
            @PathVariable(name = "id") String id
    ) {
        ComposerService composerService = new ComposerService(getFusekiHost("default"));

        try {
            return composerService.getDBpediaData(id);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                HashMap<String, Object> response = new HashMap<>();
                response.put("message", e.getMessage());
                return response;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some error occurred.");
            }
        }
    }

    @CrossOrigin
    @DeleteMapping(value = {"/composer/{id}"})
    public Map<String, String> deleteComposer(
            @PathVariable(name = "id") String id
    ) {
        ComposerService composerService = new ComposerService(getFusekiHost("default"));

        try {
            return composerService.delete(id);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                HashMap<String, String> response = new HashMap<>();
                response.put("message", e.getMessage());
                return response;
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some error occurred.");
            }
        }
    }

}