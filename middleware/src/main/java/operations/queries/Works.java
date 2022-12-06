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

public class Works {

    public Works() {

    }

    public Query getWork(String composerId, String workId) {
        Variable work = SparqlBuilder.var("work");
        Variable predicate = SparqlBuilder.var("predicate");
        Variable object = SparqlBuilder.var("object");

        SelectQuery selectQuery = Queries.SELECT().distinct().prefix(Constants.rdf).prefix(Constants.type).prefix(Constants.ns2).
                select(work, predicate, object).
                where(
                        work.has(Constants.rdf.iri("type"), Constants.ns2.iri("MusicalWork")).
                                andHas(predicate, object).
                                filter(
                                        Expressions.regex(
                                                Expressions.str(work),
                                                String.format("^http://dbtune.org/classical/resource/work/%s/%s$", composerId, workId)
                                        )
                                )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public UpdateRequest deleteWork(String composerId, String workId) {
        Variable work = SparqlBuilder.var("work");
        Variable predicate = SparqlBuilder.var("predicate");
        Variable object = SparqlBuilder.var("object");


        ModifyQuery deleteQuery = Queries.DELETE(work.has(predicate, object))
                .where(
                        work.has(predicate, object).
                                filter(Expressions.or(
                                        Expressions.regex(
                                                Expressions.str(work),
                                                String.format("^http://dbtune.org/classical/resource/work/%s/%s$", composerId, workId)
                                        ),
                                        Expressions.regex(
                                                Expressions.str(object),
                                                String.format("^http://dbtune.org/classical/resource/work/%s/%s$", composerId, workId)
                                        )
                                        )
                                )
                );
        return UpdateFactory.create(deleteQuery.getQueryString());
    }

    public ArrayList<UpdateRequest> createWork(String workURIString, ArrayList<HashMap<String, Iri>> associatedTriples) {
        Iri workURI = iri(workURIString);

        ArrayList<UpdateRequest> insertQueries = new ArrayList<>();

        for (HashMap<String, Iri> triple: associatedTriples) {
            InsertDataQuery insertDataQuery = Queries.INSERT_DATA()
                    .insertData(
                            workURI.has(triple.get("predicate"), triple.get("object"))
                    );
            insertQueries.add(UpdateFactory.create(insertDataQuery.getQueryString()));
        }

        return insertQueries;
    }

    public Query searchWork(String searchString) {
        Variable work = SparqlBuilder.var("work");
        Variable predicate = SparqlBuilder.var("predicate");
        Variable object = SparqlBuilder.var("object");

        SelectQuery selectQuery = Queries.SELECT().distinct().prefix(Constants.ns2).prefix(Constants.rdf).
                select(work, predicate, object).
                where(
                        work.has(Constants.rdf.iri("type"), Constants.ns2.iri("MusicalWork")).
                                andHas(predicate, object).
                                filter(
                                        Expressions.regex(
                                                Expressions.str(object),
                                                searchString,
                                                "i"
                                        )
                                )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }
}
