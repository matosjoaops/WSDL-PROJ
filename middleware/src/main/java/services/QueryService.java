package services;

import models.Work;
import operations.dataaccess.QueryDA;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryService {
    QueryDA queryDA;

    public QueryService(String host) {
        this.queryDA = new QueryDA(host);
    }

    public Map<String, Map<String, Object>> getComposerWorks(String composerId) {
        List<Work> works = queryDA.getComposerWorks(composerId);
        Map<String, Map<String, Object>> result = new HashMap<>();

        for (Work work : works) {
            Map<String, Object> currentWork = new HashMap<>();
            currentWork.put("URI", work.getURI());
            currentWork.put("associatedTriples", work.getAssociatedTriples());
            result.put(work.getURI(), currentWork);
        }

        return result;
    }

    public List<String> getWorkByKey(String key) {
        return queryDA.getWorkByKey(key);
    }

    public List<String> getComposersWhoInfluenced(String composerId) {
        return queryDA.getComposersWhoInfluenced(composerId);
    }

    public List<String> getComposersWhoWereInfluenced(String composerId) {
        return queryDA.getComposersWhoWereInfluenced(composerId);
    }

    public List<String> getPartsOfWork(String composerId, String workId) {
        return queryDA.getPartsOfWork(composerId, workId);
    }

    public List<String> getCompositionsByYear(String year) {
        return queryDA.getCompositionsByYear(year);
    }
    public List<String> getCompositionsByTimeRange(int year1, int year2) {
        return queryDA.getCompositionsByTimeRange(year1, year2);
    }
}
