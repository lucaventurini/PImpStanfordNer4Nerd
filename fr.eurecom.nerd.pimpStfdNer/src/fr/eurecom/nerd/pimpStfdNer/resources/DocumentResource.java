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


import fr.eurecom.nerd.pimpStfdNer.model.Document;
import fr.eurecom.nerd.pimpStfdNer.model.DocumentMap;

public class DocumentResource {
	 @Context
	  UriInfo uriInfo;
	  @Context
	  Request request;
	  String id;	  
	  DocumentMap docs;
	  
	  public DocumentResource(UriInfo uriInfo, Request request, String id) {
	    this.uriInfo = uriInfo;
	    this.request = request;
	    this.id = id;
	    this.docs=DocumentMap.getInstance();
	  }
	  
	  //Application integration     
	  @GET
	  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	  public Document getDocument() {
	    Document d = docs.get(id);
	    if(d==null)
	      throw new RuntimeException("Get: Document with " + id +  " not found");
	    return d;
	  }
	  
	  // For the browser
	  @GET
	  @Produces(MediaType.TEXT_XML)
	  public Document getDocumentHTML() {
		    Document d = docs.get(id);
		    if(d==null)
		      throw new RuntimeException("Get: Document with " + id +  " not found");
		    return d;
	  }

}
