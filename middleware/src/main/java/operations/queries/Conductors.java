package operations.queries;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.eclipse.rdf4j.sparqlbuilder.constraint.Expressions;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import utils.Constants;

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
}