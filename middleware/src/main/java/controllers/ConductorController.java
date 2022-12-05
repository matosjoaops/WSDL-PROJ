package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import services.ConductorService;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ConductorController extends Controller {
    
    @CrossOrigin
    @GetMapping(value = {"/conductor/{id}"})
    public HashMap<String, Object> getConductor(
            @PathVariable(name = "id") String id
    ) {
        ConductorService conductorService = new ConductorService(getFusekiHost("default"));

        try {
            return conductorService.get(id);
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
    @DeleteMapping(value = {"/conductor/{id}"})
    public Map<String, String> deleteConductor(
            @PathVariable(name = "id") String id
    ) {
        ConductorService conductorService = new ConductorService(getFusekiHost("default"));

        try {
            return conductorService.delete(id);
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
