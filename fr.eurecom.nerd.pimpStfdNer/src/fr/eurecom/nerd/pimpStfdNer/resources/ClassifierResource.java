package fr.eurecom.nerd.pimpStfdNer.resources;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;


import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.objectbank.ObjectBank;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import fr.eurecom.nerd.pimpStfdNer.model.ClassifierMap;
import fr.eurecom.nerd.pimpStfdNer.model.ClassifierTrained;
import fr.eurecom.nerd.pimpStfdNer.model.Document;
import fr.eurecom.nerd.pimpStfdNer.model.DocumentMap;

public class ClassifierResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;	  
	ClassifierMap cls;


	public ClassifierResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
		this.cls=ClassifierMap.getInstance();
	}
	
	/*
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void uploadFileDebug(
			//@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail
			) {
		System.out.println(fileDetail.toString());
		
	
	}*/

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream
			,@FormDataParam("file") FormDataContentDisposition fileDetail
			) {
			
	//TODO: workaround for fixing dependencies bug:
	/*public Response uploadFile(
			@FormDataParam("file") FormDataBodyPart bodyPart
			) {
		//FormDataContentDisposition fileDetail = bodyPart.getFormDataContentDisposition();
		InputStream uploadedInputStream = bodyPart.getValueAs(InputStream.class);*/
		String new_id;
		ClassifierTrained c = cls.get(this.id);
		if (c == null){
			//TODO: return 404
		}
		new_id=c.add(uploadedInputStream);


		URI created_uri = null;
		try {
			created_uri = new URI(new_id);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Response.created(created_uri).build();


	}



}
