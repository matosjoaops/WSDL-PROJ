package operations.queries;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.eclipse.rdf4j.sparqlbuilder.constraint.Expressions;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;
import org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery;
import org.eclipse.rdf4j.sparqlbuilder.core.query.ModifyQuery;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.sparqlbuilder.rdf.Iri;
import utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import static org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf.iri;

public class Events {
    public Events() {

    }

    /*
    # Equivalent query in SPARQL

    PREFIX type: <http://dbtune.org/classical/resource/type/>

    SELECT ?event ?predicate ?object
    WHERE {
        ?event ?predicate ?object .

        filter (regex(str(?event), "^http://dbtune.org/classical/resource/event/36d17b442f61$")) .
    }
    */

    public Query getEvent(String id) {
        Variable event = SparqlBuilder.var("event");
        Variable predicate = SparqlBuilder.var("predicate");
        Variable object = SparqlBuilder.var("object");

        SelectQuery selectQuery = Queries.SELECT().distinct().prefix(Constants.type).prefix(Constants.rdf).
                select(event, predicate, object).
                where(
                    event.has(predicate, object).
                    filter(
                        Expressions.regex(
                            Expressions.str(event),
                            String.format("^http://dbtune.org/classical/resource/event/%s$", id)
                        )
                    )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    /*
    # Equivalent query in SPARQL

    DELETE { ?event ?predicate ?object }
    WHERE {
        ?event ?predicate ?object .

        filter (
            regex(str(?event), "^http://dbtune.org/classical/resource/event/36d17b442f61$") ||
            regex(str(?object), "^http://dbtune.org/classical/resource/event/36d17b442f61$")
        ) .
    }
    */

    public UpdateRequest deleteEvent(String id) {
        Variable event = SparqlBuilder.var("event");
        Variable predicate = SparqlBuilder.var("predicate");
        Variable object = SparqlBuilder.var("object");

        ModifyQuery deleteQuery = Queries.DELETE(event.has(predicate, object)).
                where(
                        event.has(predicate, object).
                                filter(Expressions.or(
                                        Expressions.regex(
                                                Expressions.str(event),
                                                String.format("^http://dbtune.org/classical/resource/event/%s$", id)
                                        ),
                                        Expressions.regex(
                                                Expressions.str(object),
                                                String.format("^http://dbtune.org/classical/resource/event/%s$", id)
                                        )
                                ))
                );

        return UpdateFactory.create(deleteQuery.getQueryString());
    }

    /*
    # Equivalent query in SPARQL

    INSERT DATA {
        <event-URI> <predicate1> <object1> .
        <event-URI> <predicate2> <object2> .
        <event-URI> <predicate3> <object3> .
    }
    */

    public ArrayList<UpdateRequest> insertEvent(String eventURIString, ArrayList<HashMap<String, Iri>> associatedTriples) {
        Iri eventURI = iri(eventURIString);

        ArrayList<UpdateRequest> insertQueries = new ArrayList<>();

        for (HashMap<String, Iri> triple: associatedTriples) {
            InsertDataQuery insertDataQuery = Queries.INSERT_DATA().
                    insertData(
                            eventURI.has(triple.get("predicate"), triple.get("object"))
                    );
            insertQueries.add(UpdateFactory.create(insertDataQuery.getQueryString()));
        }

        return insertQueries;
    }
}
