package fr.eurecom.nerd.pimpStfdNer.resources;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
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






import fr.eurecom.nerd.pimpStfdNer.model.DocumentMap;

@Path("/documents")
public class DocumentsResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	DocumentMap docs = DocumentMap.getInstance();



	@POST
	//@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newDocument(@FormParam("text") String text,
			@Context HttpServletResponse servletResponse) throws IOException {
		String new_id = docs.put(text);
		URI created_uri = null;
		try {
			created_uri = new URI(new_id);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.created(created_uri).build();
	}

	@Path("{doc_id}")
	public DocumentResource getDocument(@PathParam("doc_id") String id) {
		return new DocumentResource(uriInfo, request, id);
	}

}
