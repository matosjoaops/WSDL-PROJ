package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.ComposerService;
import services.WorkService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/work")
public class WorkController extends Controller {

    @CrossOrigin
    @GetMapping("/{composerId}/{workId}")
    public HashMap<String, Object> getWork(
            @PathVariable("composerId") String composerId, @PathVariable("workId") String workId
    ) {
        String fusekiHost = getFusekiHost("default");
        WorkService workService = new WorkService(fusekiHost);
        HashMap<String, Object> result = workService.get(composerId, workId);
        if (result.get("URI") == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find a work with the provided IDs.");
        return result;
    }
    @CrossOrigin
    @PutMapping("/{composerId}/{workId}")
    public Map<String, String> updateWork(
            @PathVariable("composerId") String composerId,
            @PathVariable("workId") String workId,
            @RequestBody Map<String, Object> workData
    ) {
        String fusekiHost = getFusekiHost("default");
        WorkService workService = new WorkService(fusekiHost);

        try {
            return workService.update(composerId, workId, workData);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                HashMap<String, String> response = new HashMap<>();
                response.put("message", e.getMessage());
                return response;
            } else  {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is something wrong with the data that was provided.");
            }
        }
    }
    @CrossOrigin
    @PostMapping("/")
    public Map<String, String> createWork(
           @RequestBody Map<String, Object> workData
    ) {
        String fusekiHost = getFusekiHost("default");
        WorkService workService = new WorkService(fusekiHost);

        try {
            return workService.create(workData);
        } catch (Exception e) {
            if (e.getMessage() != null) {
                HashMap<String, String> response = new HashMap<>();
                response.put("message", e.getMessage());
                return response;
            } else  {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is something wrong with the data that was provided.");
            }
        }
    }

    @CrossOrigin
    @DeleteMapping("/{composerId}/{workId}")
    public Map<String, String> deleteWork(
            @PathVariable("composerId") String composerId, @PathVariable("workId") String workId
    ) {
        String fusekiHost = getFusekiHost("default");
        WorkService workService = new WorkService(fusekiHost);

        try {
            return workService.delete(composerId, workId);
        }
        catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "The work could not be deleted.");
        }
    }

    @CrossOrigin
    @GetMapping("/search")
    public ArrayList<HashMap<String, String>> searchWork(
            @PathVariable("searchString") String searchString
    ) {
        String fusekiHost = getFusekiHost("default");
        WorkService workService = new WorkService(fusekiHost);
        return workService.searchWork(searchString);
    }
}