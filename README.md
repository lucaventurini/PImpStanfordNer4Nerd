PImpStanfordNer4Nerd
=======
A Parallel IMPlementation of Stanford NER for NERD.

PImpStanfordNer4Nerd (or PImp, for friends) is a web framework designed to access the APIs of [Stanford NER](http://nlp.stanford.edu/software/CRF-NER.shtml), in order to extract Named Entities from documents and train a model with your own dataset. The server is fully REST compliant.

## Structure of the repository

This repository contains three folders:
* `/fr.eurecom.nerd.pimpStfdNer` is the folder where all the code for PImp is kept;
* `/HelloWorld` contains some simple demos for Stanford NER, which show how to [extract entities](HelloWorld/src/fr/eurecom/nerd/nerdcrf/PImpTask.java) and [train a classifier](https://github.com/lucaventurini/PImpStanfordNer4Nerd/blob/master/HelloWorld/src/fr/eurecom/nerd/nerdcrf/PImpTask.java);
* `/report` contains the paper describing the work done for this project.

## Requirements

The server is built using Jersey, and requires Java. We warmly suggest you to resolve dependencies using Maven. If you want to import manually the libraries needed for the project, have a look at the file [pom.xml](fr.eurecom.nerd.pimpStfdNer/pom.xml). You will need to import Jersey and Stanford NER libraries.

The server was tested on a Tomcat server v7.0 with JDK v1.7.

## Build PImp
Maven developers just need to run `mvn clean install` in the directory /fr.eurecom.nerd.pimpStfdNer. This will create a .war with all the dependencies.

Then run the .war on your server.

## Use case

In the following scenario, an user tries the framework by submitting a document to the classifier with the default model; then, he repeats the experiment with a model self-trained, on a training set based on the first chapter of Jane Austen's Emma, where Named Entities of type Person are labelled.
The client used is `curl`.

1. The document is posted to the server:

	> curl -i -X POST localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/documents -d "text=Emma and Elizabeth shared a dream."

Here it is the output of the command:



	HTTP/1.1 201 Created
	Server: Apache-Coyote/1.1
	Location: http://localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/documents/213
	Content-Length: 0
	Date: Mon, 10 Jun 2013 13:09:46 GMT

We follow the location to check everything is ok:


	> curl -i -X GET localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/documents/213

	HTTP/1.1 200 OK
	Server: Apache-Coyote/1.1
	Content-Type: text/xml
	Content-Length: 133
	Date: Mon, 10 Jun 2013 14:29:39 GMT


	<?xml version="1.0" encoding="UTF-8" standalone="yes"?><document><id>213</id><text>Emma and Elizabeth shared a dream.</text></document>

We notice that the default response is in XML. In the rest of the scenario we will ask for JSON content type, adding a field in the negotiation.
2. We create a new annotation without specifying a model:

	> curl -i -X POST localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/annotations -d "docId=213"

Again, we follow the location of the new resource created and get to the following:

	> curl -i -X GET -H "Accept:application/json" localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/annotations/278

	HTTP/1.1 200 OK
	Server: Apache-Coyote/1.1
	Content-Type: application/json
	Transfer-Encoding: chunked
	Date: Mon, 10 Jun 2013 15:24:33 GMT

	{"token":[{"label":"O","word":"Emma"},{"label":"O","word":"and"},{"label":"PERSON","word":"Elizabeth"},{"label":"O","word":"shared"},{"label":"O","word":"a"},{"label":"O","word":"dream"},{"label":"O","word":"."}]}

We see that the default model does not correctly label "Emma", while Elizabeth is correctly tagged as PERSON.
3. Let us create a new model:

	> curl -i -X POST localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/models

	HTTP/1.1 201 Created
	Server: Apache-Coyote/1.1
	Location: http://localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/models/52
	Content-Length: 0
	Date: Mon, 10 Jun 2013 16:09:46 GMT

	> curl -i -X POST localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/models/52 -F "file=@jane-austen-emma-ch1.tsv"

We have just uploaded a file in the format seen in [this file](HelloWorld/jane-austen-emma-ch1.tsv), where all person names are manually labeled. More than a file can be uploaded to the same model, to improve the model with new labeled sets.
4. Finally, we try the new model created with the same document as before:

	> curl -i -X POST localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/annotations -d "docId=213&model=52"

	> curl -i -X GET -H "Accept:application/json" localhost:8080/fr.eurecom.nerd.pimpStfdNer/pimp/annotations/279

	HTTP/1.1 200 OK
	Server: Apache-Coyote/1.1
	Content-Type: application/json
	Transfer-Encoding: chunked
	Date: Mon, 10 Jun 2013 16:10:14 GMT

	{"token":[{"label":"PERS","word":"Emma"},{"label":"O","word":"and"},{"label":"O","word":"Elizabeth"},{"label":"O","word":"shared"},{"label":"O","word":"a"},{"label":"O","word":"dream"},{"label":"O","word":"."}]}

We see the new model correctly tags Emma as a PERS, but fails to label Elizabeth.

## References

see [nerd.eurecom.fr](http://nerd.eurecom.fr)
