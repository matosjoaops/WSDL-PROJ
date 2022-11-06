package services;

import models.Composer;
import models.Work;
import operations.dataaccess.ComposerDA;
import operations.dataaccess.WorkDA;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WorkService {

    WorkDA workDA;

    public WorkService(String host) {
        this.workDA = new WorkDA(host);
    }

    public HashMap<String, Object> get(String composerId, String workId) {
        Work work = workDA.getWork(composerId, workId);
        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", work.getURI());
        result.put("associatedTriples", work.getAssociatedTriples());

        return result;
    }
    /*
    public HashMap<String, Object> update(String composerId, String workId, Map<String, Object> workData) {
        Work work = workDA.updateWork(composerId, workId, workData);
        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", work.getURI());
        result.put("associatedTriples", work.getAssociatedTriples());

        return result;
    }

    public HashMap<String, Object> create(Map<String, Object> workData) {
        Work work = workDA.createWork(workData);
        HashMap<String, Object> result = new HashMap<>();

        result.put("URI", work.getURI());
        result.put("associatedTriples", work.getAssociatedTriples());

        return result;
    }*/

    public Map<String, String> delete(String composerId, String workId) throws Exception {
        return workDA.deleteWork(composerId, workId);
    }
}
