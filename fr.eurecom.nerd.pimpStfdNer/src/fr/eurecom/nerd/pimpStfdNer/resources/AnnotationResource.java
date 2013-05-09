package fr.eurecom.nerd.pimpStfdNer.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;


import fr.eurecom.nerd.pimpStfdNer.model.Annotation;
import fr.eurecom.nerd.pimpStfdNer.model.AnnotationMap;
import fr.eurecom.nerd.pimpStfdNer.model.Document;
import fr.eurecom.nerd.pimpStfdNer.model.DocumentMap;

public class AnnotationResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;	  
	AnnotationMap annotations;

	public AnnotationResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		this.annotations=AnnotationMap.getInstance();
	}


	//Application integration     
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Annotation getDocument() {
		Annotation a = annotations.get(id);
		if(a==null)
			throw new RuntimeException("Get: Annotation with " + id +  " not found");
		return a;
	}

	// For the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public Annotation getDocumentHTML() {
		Annotation a = annotations.get(id);
		if(a==null)
			throw new RuntimeException("Get: Annotation with " + id +  " not found");
		return a;
	}

}
