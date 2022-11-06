package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.ComposerService;
import services.WorkService;

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
/*
    @CrossOrigin
    @PutMapping("/{composerId}/{workId}")
    public HashMap<String, Object> updateWork(
            @PathVariable("composerId") String composerId, @PathVariable("workId") String workId, @RequestBody Map<String, Object> workData
    ) {
        String fusekiHost = getFusekiHost("default");
        WorkService workService = new WorkService(fusekiHost);
        return workService.update(composerId, workId, workData);
    }

    @CrossOrigin
    @PostMapping("/")
    public HashMap<String, Object> createWork(
           @RequestBody Map<String, Object> workData
    ) {
        String fusekiHost = getFusekiHost("default");
        WorkService workService = new WorkService(fusekiHost);
        return workService.create(workData);
    }*/

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
}