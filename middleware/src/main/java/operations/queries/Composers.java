package operations.queries;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateRequest;
import org.eclipse.rdf4j.sparqlbuilder.constraint.Expressions;
import org.eclipse.rdf4j.sparqlbuilder.core.Prefix;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;
import org.eclipse.rdf4j.sparqlbuilder.core.query.DeleteDataQuery;
import org.eclipse.rdf4j.sparqlbuilder.core.query.InsertDataQuery;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.sparqlbuilder.graphpattern.GraphPatternNotTriples;
import org.eclipse.rdf4j.sparqlbuilder.graphpattern.GraphPatterns;
import org.eclipse.rdf4j.sparqlbuilder.rdf.Iri;
import org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf;
import utils.Constants;

import java.util.ArrayList;
import java.util.Map;

import static org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf.iri;

public class Composers {
    public Composers() {

    }

    /*
    # Equivalent query in SPARQL

    PREFIX type: <http://dbtune.org/classical/resource/type/>

    SELECT ?composer ?predicate ?object
    WHERE {
        ?composer a type:Composer .
        ?composer ?predicate ?object .

        filter (regex(str(?composer), "^http://dbtune.org/classical/resource/composer/uspensky_vladimir$")) .
    }
    */

    public Query getComposer(String id) {
        Variable composer = SparqlBuilder.var("composer");
        Variable predicate = SparqlBuilder.var("predicate");
        Variable object = SparqlBuilder.var("object");

        SelectQuery selectQuery = Queries.SELECT().distinct().prefix(Constants.type).prefix(Constants.rdf).
                select(composer, predicate, object).
                where(
                    composer.has(Constants.rdf.iri("type"), Constants.type.iri("Composer")).
                    andHas(predicate, object).
                    filter(
                        Expressions.regex(
                            Expressions.str(composer),
                            String.format("^http://dbtune.org/classical/resource/composer/%s$", id)
                        )
                    )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public Query getDBpediaData(String id) {
        String selectQuery = String.format("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX void: <http://rdfs.org/ns/void#>\n" +
                "PREFIX DBpedia: <https://www.dbpedia.org/>\n" +
                "\n" +
                "SELECT DISTINCT ?predicate ?predicateLabel ?value ?valueLabel\n" +
                "WHERE {\n" +
                "  DBpedia: void:sparqlEndpoint ?sparqlEndpoint .\n" +
                "  <http://dbtune.org/classical/resource/composer/%s> owl:sameAs ?externalResource .\n" +
                "  filter ( regex(str(?externalResource), \"dbpedia\")) .\n" +
                "  \n" +
                "  SERVICE ?sparqlEndpoint {\n" +
                "    ?externalResource ?predicate ?value .\n" +
                "    \n" +
                "    OPTIONAL { \n" +
                "   \t  ?predicate rdfs:label ?predicateLabel .\n" +
                "      FILTER (lang(?predicateLabel) = \"en\") .\n" +
                "    }\n" +
                "    \n" +
                "    OPTIONAL { \n" +
                "   \t  ?value rdfs:label ?valueLabel .\n" +
                "      FILTER (lang(?valueLabel) = \"en\") .\n" +
                "    }\n" +
                "    \n" +
                "    FILTER ( IF (isLiteral(?value), lang(?value) = \"en\", TRUE) ) .\n" +
                "  }\n" +
                "}", id);

        return QueryFactory.create(selectQuery);
    }
}
