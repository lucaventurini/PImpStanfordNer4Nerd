package fr.eurecom.nerd.pimpStfdNer.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;






import fr.eurecom.nerd.pimpStfdNer.model.AnnotationMap;
import fr.eurecom.nerd.pimpStfdNer.model.ClassifierMap;
import fr.eurecom.nerd.pimpStfdNer.model.DocumentMap;

@Path("/annotations")
public class AnnotationsResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	DocumentMap docs;
	AnnotationMap annotations;
	ClassifierMap cls;

	//AbstractSequenceClassifier<CoreLabel> cl1;

	public AnnotationsResource(){
		docs = DocumentMap.getInstance();
		annotations = AnnotationMap.getInstance();

		//TODO: implement an object pool of classifiers

		cls = ClassifierMap.getInstance();

	}



	@POST
	//@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newDocument(@FormParam("docId") String docId,
			@DefaultValue("default") @FormParam("classifier") String cl_id,
			@Context HttpServletResponse servletResponse) throws IOException {
		//TODO: load classifier with docId
		AbstractSequenceClassifier<CoreLabel> cl;
		
		/*
		if(cl_id != null){
			cl = cls.getClassifier(cl_id);
		}else{
			cl = cls.getClassifier("default");

		}*/
		cl = cls.getClassifier(cl_id);
		
		String new_id=annotations.put(cl, docs.get(docId).getText());
		URI created_uri = null;
		try {
			created_uri = new URI(new_id);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.created(created_uri).build();
	}

	@Path("{ann_id}")
	public AnnotationResource getDocument(@PathParam("ann_id") String id) {
		return new AnnotationResource(uriInfo, request, id);
	}

}
