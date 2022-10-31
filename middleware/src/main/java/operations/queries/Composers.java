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

import java.util.ArrayList;
import java.util.Map;

import static org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf.iri;

public class Composers {

    private final Prefix owl = SparqlBuilder.prefix("owl", iri("http://www.w3.org/2002/07/owl#"));
    private final Prefix rdf = SparqlBuilder.prefix("rdf", iri("http://www.w3.org/1999/02/22-rdf-syntax-ns#"));
    private final Prefix rdfs = SparqlBuilder.prefix("rdfs", iri("http://www.w3.org/2000/01/rdf-schema#"));
    private final Prefix foaf = SparqlBuilder.prefix("foaf", iri("http://xmlns.com/foaf/0.1/"));

    private final Prefix composer = SparqlBuilder.prefix("composer", iri("http://dbtune.org/classical/resource/composer/"));
    private final Prefix type = SparqlBuilder.prefix("type", iri("http://dbtune.org/classical/resource/type/"));

    public Composers() {

    }

    public Query getComposer(String uuid) {
        Variable composerVar = SparqlBuilder.var("composer");

        SelectQuery selectQuery =  Queries.SELECT().distinct().prefix(foaf).prefix(composer).prefix(type).
                select(composerVar).
                where(
                        composerVar.has(rdf.iri("type"), type.iri("Composer")).
                                andHas(foaf.iri("name"), "Beethoven, Ludwig van")
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }
}
