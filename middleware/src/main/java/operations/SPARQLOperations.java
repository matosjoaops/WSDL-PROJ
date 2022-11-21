package operations;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
import org.apache.jena.update.UpdateRequest;

import java.util.*;

public class SPARQLOperations {
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
            conn.load("datasets/void.ttl");

            // load datasets
            for (String fileName : fileNames) {
                conn.load(fileName);
            }
        }
    }

    public ArrayList<HashMap<String, String>> executeQuery(Query query) {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination(sparqlHost);

        ArrayList<HashMap<String, String>> myArrayList = new ArrayList<>();

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

                    result.put(current, res.toString());

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

    public void executeUpdate(UpdateRequest update) throws Exception {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination(updateHost);

        try (RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            conn.update(update);
        } catch (Exception e) {
            throw new Exception("Update failed!");
        }
    }

    public void executeUpdateList(List<UpdateRequest> updateList) throws Exception {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create()
                .destination(updateHost);

        try (RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            try {
                conn.begin(ReadWrite.WRITE);
                for (UpdateRequest update : updateList) {
                    conn.update(update);
                }
                conn.commit();
            } catch (Exception e) {
                conn.abort();
                e.printStackTrace();
                throw new Exception("Update failed!");
            } finally {
                conn.end();
            }
        }
    }
}