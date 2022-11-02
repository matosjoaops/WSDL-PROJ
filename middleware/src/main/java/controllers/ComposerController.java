package controllers;

import org.springframework.web.bind.annotation.*;
import services.ComposerService;

import java.util.HashMap;

@RestController
public class ComposerController extends Controller {

    @CrossOrigin
    @GetMapping(value = {"/composer/{id}"})
    public HashMap<String, Object> getComposer(
            @PathVariable(name = "id") String id
    ) {
        String fusekiHost = getFusekiHost("default");
        ComposerService composerService = new ComposerService();

        return composerService.get(fusekiHost, id);
    }
}