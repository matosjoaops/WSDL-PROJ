package operations.dataaccess;


import models.Work;
import operations.SPARQLOperations;
import operations.queries.MyQueries;
import org.apache.jena.rdf.model.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryDA {

    public String host;

    MyQueries queries = new MyQueries();
    public Model model;

    public QueryDA(String host) {
        this.host = host;
    }

    public List<Work> getComposerWorks(String composerId) {
        SPARQLOperations conn = new SPARQLOperations(this.host);
        List<Work> works = new ArrayList<>();
        String lastURI = "";

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getComposerWorks(composerId));

        if (associatedTriples.size() == 0) {
            return works;
        }

        for (HashMap<String, String> triple : associatedTriples) {
            String URI = triple.get("work");
            triple.remove("work");
            Work work  = new Work();
            if (URI.equals(lastURI)) {
                work = works.get(works.size() - 1);
                work.addTriple(triple);
            }
            else {
                work.setURI(URI);
                work.addTriple(triple);
                works.add(work);
            }
            lastURI = URI;
        }

        return works;
    }

    public List<String> getWorkByKey(String key) {
        SPARQLOperations conn = new SPARQLOperations(this.host);
        List<String> works = new ArrayList<>();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getWorksByKey(key));
        associatedTriples.forEach((triple) -> works.add(triple.get("work")));

        return works;
    }

    public List<String> getComposersWhoInfluenced(String composerId) {
        SPARQLOperations conn = new SPARQLOperations(this.host);
        List<String> composers = new ArrayList<>();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getComposersWhoInfluenced(composerId));
        associatedTriples.forEach((triple) -> composers.add(triple.get("composer")));

        return composers;
    }

    public List<String> getComposersWhoWereInfluenced(String composerId) {
        SPARQLOperations conn = new SPARQLOperations(this.host);
        List<String> composers = new ArrayList<>();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getComposersWhoWereInfluenced(composerId));
        associatedTriples.forEach((triple) -> composers.add(triple.get("composer")));

        return composers;
    }

    public List<String> getPartsOfWork(String composerId, String workId) {
        SPARQLOperations conn = new SPARQLOperations(this.host);
        List<String> parts = new ArrayList<>();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getWorkParts(composerId, workId));
        associatedTriples.forEach((triple) -> parts.add(triple.get("part")));

        return parts;
    }

    public List<String> getCompositionsByYear(String year) {
        SPARQLOperations conn = new SPARQLOperations(this.host);
        List<String> compositions = new ArrayList<>();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getCompositionsByYear(year));
        associatedTriples.forEach((triple) -> compositions.add(triple.get("composition")));

        return compositions;
    }

    public List<String> getCompositionsByTimeRange(int year1, int year2) {
        SPARQLOperations conn = new SPARQLOperations(this.host);
        List<String> compositions = new ArrayList<>();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getCompositionsByTimeRange(year1, year2));
        associatedTriples.forEach((triple) -> compositions.add(triple.get("composition")));

        return compositions;
    }

    public List<String> getCompositionsByPlace(String place) {
        SPARQLOperations conn = new SPARQLOperations(this.host);
        List<String> compositions = new ArrayList<>();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getCompositionsByPlace(place));
        associatedTriples.forEach((triple) -> compositions.add(triple.get("composition")));

        return compositions;
    }

    public Map<String, Object> getComposerLocations(String composerId) {
        SPARQLOperations conn = new SPARQLOperations(this.host);
        Map<String, Object> result = new HashMap<>();

        ArrayList<HashMap<String, String>> associatedTriples = conn.executeQuery(queries.getComposerLocations(composerId));
        associatedTriples.forEach((triple) -> {
            String predicate = triple.get("predicate");
            String key = predicate.substring(predicate.lastIndexOf('/') + 1);
            Map<String, String> data = new HashMap<>();
            data.put("coordinates", triple.get("coordinates"));
            data.put("placeURI", triple.get("value"));
            result.put(key, data);
        });

        return result;
    }
}
