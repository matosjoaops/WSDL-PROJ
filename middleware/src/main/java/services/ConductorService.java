package services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import models.Conductor;
import operations.dataaccess.ConductorDA;

@Component
public class ConductorService {
    ConductorDA conductorDA;

    public ConductorService(String host) {
        conductorDA = new ConductorDA(host);
    }

    public HashMap<String, Object> get(String id) throws Exception {
        Conductor conductor = conductorDA.getConductor(id);

        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", conductor.getURI());
        result.put("associatedTriples", conductor.getAssociatedTriples());

        return result;
    }

    public Map<String, String> delete(String id) throws Exception {
        return conductorDA.deleteConductor(id);
    }

    public Map<String, String> insert(Map<String, Object> insertForm) throws Exception {
        return conductorDA.insertConductor(insertForm);
    }
    
}
