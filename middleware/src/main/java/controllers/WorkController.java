package controllers;

import org.springframework.web.bind.annotation.*;
import services.ComposerService;
import services.WorkService;

import java.util.HashMap;

@RestController
@RequestMapping("/work")
public class WorkController extends Controller {
    private final WorkService workService;

    public WorkController(WorkService workService) {
        this.workService = workService;
    }

    @CrossOrigin
    @GetMapping("/{composerId}/{workId}")
    public HashMap<String, Object> getWork(
            @PathVariable("composerId") String composerId, @PathVariable("workId") String workId
    ) {
        String fusekiHost = getFusekiHost("default");

        return workService.get(fusekiHost, composerId, workId);
    }
}