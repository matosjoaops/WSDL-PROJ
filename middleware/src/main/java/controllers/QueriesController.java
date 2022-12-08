package controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import services.QueryService;
import services.WorkService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/queries")
public class QueriesController extends Controller {

    @CrossOrigin
    @GetMapping("/composerWorks")
    public Map<String, Map<String, Object>> getComposerWorks(
             @RequestParam("composerId") String composerId
    ) {
        String fusekiHost = getFusekiHost("default");
        QueryService service = new QueryService(fusekiHost);
        Map<String, Map<String, Object>> works = service.getComposerWorks(composerId);
        if (works.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find any works with the composer ID that was provided.");
        return works;
    }

    @CrossOrigin
    @GetMapping("/workKeys")
    public List<String> getWorkByKey(
            @RequestParam("key") String key
    ) {
        String fusekiHost = getFusekiHost("default");
        QueryService service = new QueryService(fusekiHost);
        List<String> works = service.getWorkByKey(key);
        if (works.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find any works with the key that was provided.");
        return works;
    }


}