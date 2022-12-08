package operations.queries;

import org.apache.jena.ext.xerces.impl.dv.ValidatedInfo;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.eclipse.rdf4j.sparqlbuilder.constraint.Expressions;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;
import org.eclipse.rdf4j.sparqlbuilder.core.Variable;
import org.eclipse.rdf4j.sparqlbuilder.core.query.Queries;
import org.eclipse.rdf4j.sparqlbuilder.core.query.SelectQuery;
import org.eclipse.rdf4j.sparqlbuilder.rdf.Iri;
import org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf;
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

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public Query getCompositionsByYear(String year) {
        Variable composition = SparqlBuilder.var("composition");
        Variable date = SparqlBuilder.var("date");

        SelectQuery selectQuery = Queries.SELECT()
                .prefix(Constants.rdf)
                .prefix(Constants.rdfs)
                .prefix(Constants.type)
                .prefix(Constants.ns2)
                .prefix(Constants.ns5)
                .prefix(Constants.dc)
                .select(composition)
                .where(
                        composition
                                .has(Constants.rdf.iri("type"), Constants.ns2.iri("Composition"))
                                .andHas(Constants.ns5.iri("date"), date)
                                .filter(
                                        Expressions.equals(
                                                Expressions.str(date),
                                                Rdf.literalOf(year)
                                        )
                                )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public Query getCompositionsByTimeRange(int year1, int year2) {
        Variable composition = SparqlBuilder.var("composition");
        Variable date = SparqlBuilder.var("date");

        SelectQuery selectQuery = Queries.SELECT()
                .prefix(Constants.rdf)
                .prefix(Constants.rdfs)
                .prefix(Constants.type)
                .prefix(Constants.ns2)
                .prefix(Constants.ns5)
                .prefix(Constants.dc)
                .select(composition)
                .where(
                        composition
                                .has(Constants.rdf.iri("type"), Constants.ns2.iri("Composition"))
                                .andHas(Constants.ns5.iri("date"), date)
                                .filter(
                                        Expressions.and(
                                                Expressions.gt(date, year1),
                                                Expressions.lt(date, year2)
                                        )
                                )
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public Query getCompositionsByPlace(String place) {
        Variable composition = SparqlBuilder.var("composition");

        SelectQuery selectQuery = Queries.SELECT()
                .prefix(Constants.rdf)
                .prefix(Constants.rdfs)
                .prefix(Constants.type)
                .prefix(Constants.ns2)
                .prefix(Constants.ns5)
                .prefix(Constants.dc)
                .select(composition)
                .where(
                        composition
                                .has(Constants.rdf.iri("type"), Constants.ns2.iri("Composition"))
                                .andHas(Constants.ns5.iri("place"), place)
                );

        return QueryFactory.create(selectQuery.getQueryString());
    }

    public Query getComposerLocations(String composerId) {
        String queryString = String.format("" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                "PREFIX void: <http://rdfs.org/ns/void#>\n" +
                "PREFIX DBpedia: <https://www.dbpedia.org/>\n" +
                "PREFIX georss: <http://www.georss.org/georss/>\n" +
                "\n" +
                "SELECT DISTINCT ?predicate ?predicateLabel ?value ?valueLabel ?coordinates\n" +
                "WHERE {\n" +
                "  DBpedia: void:sparqlEndpoint ?sparqlEndpoint .\n" +
                "  <http://dbtune.org/classical/resource/composer/%s> owl:sameAs ?externalResource .\n" +
                "  filter ( regex(str(?externalResource), \"dbpedia\")) .\n" +
                "  \n" +
                "  SERVICE ?sparqlEndpoint {\n" +
                "    ?externalResource ?predicate ?value .\n" +
                "    \n" +
                "    OPTIONAL { \n" +
                "      ?predicate rdfs:label ?predicateLabel .\n" +
                "      FILTER (lang(?predicateLabel) = \"en\") .\n" +
                "    }\n" +
                "    \n" +
                "    FILTER (regex(?predicateLabel, \"birth place\") || regex(?predicateLabel, \"death place\")) .\n" +
                "    \n" +
                "    OPTIONAL { \n" +
                "      ?value rdfs:label ?valueLabel .\n" +
                "      FILTER (lang(?valueLabel) = \"en\") .\n" +
                "    }\n" +
                "    \n" +
                "    FILTER ( IF (isLiteral(?value), lang(?value) = \"en\", TRUE) ) .\n" +
                "    \n" +
                "    ?value georss:point ?coordinates .\n" +
                "  }\n" +
                "}", composerId);
        return QueryFactory.create(queryString);
    }
}
