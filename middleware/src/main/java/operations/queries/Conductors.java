package operations.queries;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.eclipse.rdf4j.sparqlbuilder.constraint.Expressions;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery;
import org.eclipse.rdf4j.sparqlbuilder.core.query.ModifyQuery;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.sparqlbuilder.rdf.Iri;
import utils.Constants;

import static org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf.iri;

public class Conductors {

    public Conductors() {

    }

    public Query getConductor(String id) {
        Variable conductor = SparqlBuilder.var("conductor");
        Variable predicate = SparqlBuilder.var("predicate");
        Variable object = SparqlBuilder.var("object");

        SelectQuery selectQuery = Queries.SELECT().distinct().prefix(Constants.type).prefix(Constants.rdf).
                select(conductor, predicate, object).
                where(
                    conductor.has(Constants.rdf.iri("type"), Constants.type.iri("Conductor")).
                    andHas(predicate, object).
                    filter(
                        Expressions.regex(
                            Expressions.str(conductor),
                            String.format("^http://dbtune.org/classical/resource/conductor/%s$", id)
                        )
                    )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public UpdateRequest deleteConductor(String id) {
        Variable conductor = SparqlBuilder.var("conductor");
        Variable predicate = SparqlBuilder.var("predicate");
        Variable object = SparqlBuilder.var("object");

        ModifyQuery deleteQuery = Queries.DELETE(conductor.has(predicate, object)).
                where(
                        conductor.has(predicate, object).
                                    filter(Expressions.or(
                                        Expressions.regex(
                                                Expressions.str(conductor),
                                                String.format("^http://dbtune.org/classical/resource/conductor/%s$", id)
                                        ),
                                        Expressions.regex(
                                                Expressions.str(object),
                                                String.format("^http://dbtune.org/classical/resource/conductor/%s$", id)
                                        )
                                ))
                );

        return UpdateFactory.create(deleteQuery.getQueryString());
    }

    public ArrayList<UpdateRequest> insertConductor(String conductorURIString, ArrayList<HashMap<String, Iri>> associatedTriples) {
        Iri conductorURI = iri(conductorURIString);

        ArrayList<UpdateRequest> insertQueries = new ArrayList<>();

        for (HashMap<String, Iri> triple: associatedTriples) {
            InsertDataQuery insertDataQuery = Queries.INSERT_DATA()
                    .insertData(
                            conductorURI.has(triple.get("predicate"), triple.get("object"))
                    );
            insertQueries.add(UpdateFactory.create(insertDataQuery.getQueryString()));
        }

        return insertQueries;
    }

    public Query searchConductor(String searchString) {
        Variable conductor = SparqlBuilder.var("conductor");
        Variable predicate = SparqlBuilder.var("predicate");
        Variable object = SparqlBuilder.var("object");

        SelectQuery selectQuery = Queries.SELECT().distinct().prefix(Constants.type).prefix(Constants.rdf).
                select(conductor, predicate, object).
                where(
                        conductor.has(Constants.rdf.iri("type"), Constants.type.iri("Conductor")).
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