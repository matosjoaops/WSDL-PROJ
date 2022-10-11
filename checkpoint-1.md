# Checkpoint 1

## Please identify your group (A, B, C, ...)

Group A.

## What is the name of your project?
DBTune Classical.

## Please describe in short sentences the topic of your work.

Our work is about classical music data. The project's goal is to design and implement a REST API that uses the RDF DBTune classical dataset (https://old.datahub.io/dataset/dbtune-classical).

## What was the main motivation to select this topic?

We chose this topic because some of us are interested in classical music, and it was relatively easy to find an RDF dataset with information regarding this subject.

## Which data and knowledge sources did you already identified to be used in this project?

For the moment, we found a dataset (http://dbtune.org/classical/) representing a collection of knowledge regarding western classical music. It was aggregated by Chris Cannam at Queen Mary University of London and provides interlinking to the respective resources in other knowledge graphs, namely DBpedia and BBCMusic.

## Which main obstacules to the development of the project did you already identify?

The main obstacle will be the lack of experience in this field and the technologies commonly used in this environment.

## Please describe shortly the development phases and planning for the project.

1. Selection of technological stack
    - Apache Jena Fuseki, for the triple store
    - Eclipse RDF4J, for processing and handling RDF data
    - Java Spring Boot, for the REST API
2. Project configuration and setup
3. Load the dataset
4. Find new datasets to link to the base dataset
5. Implement a REST API
    - Implement interesting queries
    - Give the possibility to add, update and remove information
    - Give the possibility to manually link external resources (for example, letting the user state that a piano in our application is the same thing as a piano in DBpedia
    - Make federated queries, taking advantage of the externally linked resources (for example, if we know that a composer in our application is the same as a composer in DBPedia, we can query DBpedia to know more information about the composer that was not previously available for the users of our API)
