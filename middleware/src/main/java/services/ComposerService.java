package services;

import models.Composer;
import operations.dataaccess.ComposerDA;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ComposerService {

    public HashMap<String, HashMap<String, ArrayList<HashMap<String, String>>>> get(String host, String uuid) {
        ComposerDA composerDA = new ComposerDA(host);
        Composer composer = composerDA.getComposer(uuid);

        HashMap<String, HashMap<String, ArrayList<HashMap<String, String>>>> response = new HashMap<>();
        response.put("DOC_IDENTITY", composer.getDOC_IDENTITY());

        return response;
    }
}


