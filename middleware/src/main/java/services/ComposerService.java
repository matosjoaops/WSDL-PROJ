package services;

import models.Composer;
import operations.dataaccess.ComposerDA;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ComposerService {

    public HashMap<String, Object> get(String host, String id) {
        ComposerDA composerDA = new ComposerDA(host);
        Composer composer = composerDA.getComposer(id);

        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", composer.getURI());
        result.put("associatedTriples", composer.getAssociatedTriples());

        return result;
    }
}


