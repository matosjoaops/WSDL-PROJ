package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.ComposerService;

import java.util.HashMap;

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
}