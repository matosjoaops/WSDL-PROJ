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

    public Map<String, String> update(String composerId, String workId, Map<String, Object> workData) throws Exception {
        delete(composerId, workId);
        workData.put("URI", "http://dbtune.org/classical/resource/work/" + composerId + "/" + workId);
        Map<String, String> result = create(workData);
        result.put("message", "Work was updated successfully.");
        return result;
    }

    public Map<String, String> create(Map<String, Object> workData) throws Exception {
       return workDA.createWork(workData);
    }

    public Map<String, String> delete(String composerId, String workId) throws Exception {
        return workDA.deleteWork(composerId, workId);
    }
}
