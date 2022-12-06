package services;

import models.Composer;
import operations.dataaccess.ComposerDA;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class ComposerService {
    ComposerDA composerDA;

    public ComposerService(String host) {
        composerDA = new ComposerDA(host);
    }

    public HashMap<String, Object> get(String id) throws Exception {
        Composer composer = composerDA.getComposer(id);

        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", composer.getURI());
        result.put("associatedTriples", composer.getAssociatedTriples());

        return result;
    }

    public HashMap<String, Object> getDBpediaData(String id) throws Exception {
        Composer composer = composerDA.getDBpediaData(id);

        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", composer.getURI());
        result.put("associatedTriples", composer.getAssociatedTriples());

        return result;
    }

    public Map<String, String> delete(String id) throws Exception {
        return composerDA.deleteComposer(id);
    }
}


