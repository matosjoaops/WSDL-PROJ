package controllers;

import org.springframework.web.bind.annotation.*;
import services.ComposerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ComposerController extends Controller {

    @CrossOrigin
    @GetMapping(value = {"/composer"})
    public Map<String, HashMap<String, ArrayList<HashMap<String, String>>>> getDocument(
            @RequestParam(value = "uuid", defaultValue = "") String uuid
    ) {
        String fusekiHost = getFusekiHost("default");
        ComposerService documentService = new ComposerService();

        return documentService.get(fusekiHost, uuid);
    }
}