package utils;

import org.eclipse.rdf4j.sparqlbuilder.core.Prefix;
import org.eclipse.rdf4j.sparqlbuilder.core.SparqlBuilder;

import static org.eclipse.rdf4j.sparqlbuilder.rdf.Rdf.iri;

public final class Constants {
    public static final Prefix owl = SparqlBuilder.prefix("owl", iri("http://www.w3.org/2002/07/owl#"));
    public static final Prefix rdf = SparqlBuilder.prefix("rdf", iri("http://www.w3.org/1999/02/22-rdf-syntax-ns#"));
    public static final Prefix rdfs = SparqlBuilder.prefix("rdfs", iri("http://www.w3.org/2000/01/rdf-schema#"));
    public static final Prefix foaf = SparqlBuilder.prefix("foaf", iri("http://xmlns.com/foaf/0.1/"));
    public static final Prefix composer = SparqlBuilder.prefix("composer", iri("http://dbtune.org/classical/resource/composer/"));
    public static final Prefix type = SparqlBuilder.prefix("type", iri("http://dbtune.org/classical/resource/type/"));
    public static final Prefix ns2 = SparqlBuilder.prefix("ns2", iri("http://purl.org/ontology/mo/"));
    public static final Prefix ns4 = SparqlBuilder.prefix("ns4", iri("http://purl.org/ontology/classicalmusicnav#"));
    public static final Prefix dc = SparqlBuilder.prefix("dc", iri("http://purl.org/dc/elements/1.1/"));
    public static final Prefix ns5 = SparqlBuilder.prefix("ns5", iri("http://purl.org/vocab/bio/0.1/"));
}
