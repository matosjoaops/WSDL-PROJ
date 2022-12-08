package operations.queries;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.eclipse.rdf4j.sparqlbuilder.constraint.Expressions;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.sparqlbuilder.rdf.Iri;
import utils.Constants;
import static org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf.iri;

public class MyQueries {
    public MyQueries() {}

    public Query getComposerWorks(String composerId) {
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
                                                String.format("^http://dbtune.org/classical/resource/work/%s/", composerId)
                                        )
                                )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public Query getWorksByKey(String key) {
        Variable work = SparqlBuilder.var("work");

        SelectQuery selectQuery = Queries.SELECT()
                .prefix(Constants.rdf)
                .prefix(Constants.rdfs)
                .prefix(Constants.type)
                .prefix(Constants.ns2)
                .select(work)
                .where(
                        work
                                .has(Constants.rdf.iri("type"), Constants.ns2.iri("MusicalWork"))
                                .andHas(Constants.ns2.iri("key"), key)
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public Query getComposersWhoInfluenced(String composerId) {
        Variable composer = SparqlBuilder.var("composer");
        Variable influencedComposer = SparqlBuilder.var("influencedComposer");

        SelectQuery selectQuery = Queries.SELECT()
                .prefix(Constants.rdf)
                .prefix(Constants.rdfs)
                .prefix(Constants.type)
                .prefix(Constants.ns2)
                .prefix(Constants.ns4)
                .select(composer)
                .where(
                        composer
                                .has(Constants.rdf.iri("type"), Constants.type.iri("Composer"))
                                .andHas(Constants.ns4.iri("hasInfluenced"), influencedComposer)
                                .filter(
                                        Expressions.regex(
                                                Expressions.str(influencedComposer),
                                                String.format("^http://dbtune.org/classical/resource/composer/%s$", composerId)
                                        )
                                )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public Query getComposersWhoWereInfluenced(String composerId) {
        Variable composer = SparqlBuilder.var("composer");
        Variable composerWhoInfluenced = SparqlBuilder.var("composerWhoInfluenced");

        SelectQuery selectQuery = Queries.SELECT()
                .prefix(Constants.rdf)
                .prefix(Constants.rdfs)
                .prefix(Constants.type)
                .prefix(Constants.ns2)
                .prefix(Constants.ns4)
                .select(composer)
                .where(
                        composer
                                .has(Constants.rdf.iri("type"), Constants.type.iri("Composer"))
                                .andHas(Constants.ns4.iri("influencedBy"), composerWhoInfluenced)
                                .filter(
                                        Expressions.regex(
                                                Expressions.str(composerWhoInfluenced),
                                                String.format("^http://dbtune.org/classical/resource/composer/%s$", composerId)
                                        )
                                )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public Query getWorkParts(String composerId, String workId) {
        Variable part = SparqlBuilder.var("part");
        Iri workIri = iri(String.format("http://dbtune.org/classical/resource/work/%s/%s", composerId, workId));

        SelectQuery selectQuery = Queries.SELECT()
                .prefix(Constants.rdf)
                .prefix(Constants.rdfs)
                .prefix(Constants.type)
                .prefix(Constants.ns2)
                .prefix(Constants.dc)
                .select(part)
                .where(
                        workIri
                                .has(Constants.dc.iri("hasPart"), part)
                );

        return  QueryFactory.create(selectQuery.getQueryString());
    }
}
