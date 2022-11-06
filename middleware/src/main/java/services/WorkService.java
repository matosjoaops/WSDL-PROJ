package services;

import models.Composer;
import models.Work;
import operations.dataaccess.ComposerDA;
import operations.dataaccess.WorkDA;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class WorkService {
    public HashMap<String, Object> get(String host, String composerId, String workId) {
        WorkDA workDA = new WorkDA(host);
        Work work = workDA.getWork(composerId, workId);

        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", work.getURI());
        result.put("associatedTriples", work.getAssociatedTriples());

        return result;
    }
}
