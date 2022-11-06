package operations.queries;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.eclipse.rdf4j.sparqlbuilder.constraint.Expressions;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import utils.Constants;

public class Works {

    public Works() {

    }

    // Equivalent query in SPARQL

    /*PREFIX type: <http://dbtune.org/classical/resource/type/>

    SELECT ?composer ?predicate ?object
    WHERE {
        ?composer a type:Composer .
        ?composer ?predicate ?object .

        filter (regex(str(?composer), "^http://dbtune.org/classical/resource/composer/uspensky_vladimir$")) .
    }*/

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
}
