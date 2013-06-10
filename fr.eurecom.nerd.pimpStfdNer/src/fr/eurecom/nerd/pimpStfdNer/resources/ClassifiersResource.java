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






import fr.eurecom.nerd.pimpStfdNer.model.ClassifierMap;
import fr.eurecom.nerd.pimpStfdNer.model.DocumentMap;

@Path("/models")
public class ClassifiersResource {

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	ClassifierMap cls = ClassifierMap.getInstance();



	@POST
	//@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newDocument(@Context HttpServletResponse servletResponse) throws IOException {
		String new_id = cls.put();
		URI created_uri = null;
		try {
			created_uri = new URI(new_id);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.created(created_uri).build();
	}

	@Path("{cl_id}")
	public ClassifierResource getDocument(@PathParam("cl_id") String id) {
		return new ClassifierResource(uriInfo, request, id);
	}

}
