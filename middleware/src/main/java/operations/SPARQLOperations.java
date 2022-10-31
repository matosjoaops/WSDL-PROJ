package operations;

import operations.queries.*;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.update.UpdateRequest;

import java.util.*;

public class SPARQLOperations {
    public Composers queries = new Composers();
    public String sparqlHost;
    public String dataHost;
    public String updateHost;

    public SPARQLOperations(String defaultHost) {
        this.sparqlHost = defaultHost + "sparql";
        this.dataHost = defaultHost + "data";
        this.updateHost = defaultHost + "update";
    }

    public void importOWL(String[] fileNames) {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create()
                .destination(dataHost);

        try (RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            // load ontologies
            conn.load("datasets/classical.ttl");

            // load datasets
            for (String fileName : fileNames) {
                System.out.println(fileName);
                conn.load(fileName);
            }
        }
    }

    public ArrayList<HashMap<String, String>> executeQueryAndAddContent(Query query,
            HashMap<String, ArrayList<HashMap<String, String>>> myObject, String key) {
        return executeQueryAndAddContentOpt(query, myObject, key, false);
    }

    public ArrayList<HashMap<String, String>> executeQueryAndAddRawContent(Query query,
            HashMap<String, ArrayList<HashMap<String, String>>> myObject, String key) {
        return executeQueryAndAddContentOpt(query, myObject, key, true);
    }

    public ArrayList<HashMap<String, String>> executeQueryAndAddContentOpt(Query query,
            HashMap<String, ArrayList<HashMap<String, String>>> myObject, String key, boolean raw) {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create()
                .destination(sparqlHost);

        ArrayList<HashMap<String, String>> myArrayList = new ArrayList<>();
        if (myObject != null && key != null) {
            myObject.put(key, myArrayList);
        }

        try (RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            QueryExecution qExec = conn.query(query);
            ResultSet rs = qExec.execSelect();
            QuerySolution stmt;

            while (rs.hasNext()) {

                stmt = rs.next();
                Iterator<String> b = stmt.varNames();
                HashMap<String, String> result = new HashMap<>();

                while (b.hasNext()) {
                    String current = b.next();
                    RDFNode res = stmt.get(current);

                    if (raw) {
                        result.put(current, res.toString());
                    }
                }
                if (result.size() != 0) {
                    myArrayList.add(result);
                }
            }
            conn.close();
            qExec.close();
        }
        return myArrayList;
    }
}