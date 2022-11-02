package services;

import models.Composer;
import operations.dataaccess.ComposerDA;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class ComposerService {

    public ArrayList<HashMap<String, String>> get(String host, String id) {
        ComposerDA composerDA = new ComposerDA(host);
        Composer composer = composerDA.getComposer(id);

        return composer.getAssociatedTriples();
    }
}


