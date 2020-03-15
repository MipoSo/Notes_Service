/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package com.mipo.polsourcenotesms.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.mipo.polsourcenotesms.dao.NoteRepository;
import com.mipo.polsourcenotesms.model.Note;
import com.mipo.polsourcenotesms.pojo.CreateNoteRequestPojo;
import com.mipo.polsourcenotesms.pojo.CreateNoteResponsePojo;
import com.mipo.polsourcenotesms.pojo.ReadNoteRequestPojo;
import com.mipo.polsourcenotesms.pojo.ReadNoteResponsePojo;
import com.mipo.polsourcenotesms.pojo.UpdateNoteRequestPojo;
import com.mipo.polsourcenotesms.pojo.UpdateNoteResponsePojo;
import com.mipo.polsourcenotesms.pojo.DeleteNoteRequestPojo;
import com.mipo.polsourcenotesms.pojo.DeleteNoteResponsePojo;
import com.mipo.polsourcenotesms.pojo.NotePojo;
import com.mipo.polsourcenotesms.pojo.ReadAllNoteResponsePojo;
import com.mipo.polsourcenotesms.pojo.ReadNoteHistoryResponsePojo;

import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Annotated Resource class.
 */

@Component
@Path("/notes") //service name(/service)
public class NoteResource {//class name

    @Autowired
    private NoteRepository notes;
    
    @GET
    @Path("/history/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNoteHistory(@PathParam("id") long id) {
        ReadNoteHistoryResponsePojo response = new ReadNoteHistoryResponsePojo();
        try{
            List<Note> allNotess = notes.findAllHistory(id);
            response.setStatus("Success");
            response.setStatusCode("00");
            response.setNote(allNotess);
            return Response.status(Response.Status.OK).entity(response).build();
        }
        catch(Exception e){
            response.setStatus(e.getMessage());
            response.setStatusCode("01");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();             
        }
    }
    
    @GET
    @Path("/allnotes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNotes() {
        ReadAllNoteResponsePojo response = new ReadAllNoteResponsePojo();
        try{
            List<Note> allNotess = notes.findAllNotes();
            response.setStatus("Success");
            response.setStatusCode("00");
            response.setNote(allNotess);
            return Response.status(Response.Status.OK).entity(response).build();
        }
        catch(Exception e){
            response.setStatus(e.getMessage());
            response.setStatusCode("01");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();             
        }
    }

    @GET
    @Path("/readnote/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNoteById(@PathParam("id") long id) {
        ReadNoteResponsePojo response = new ReadNoteResponsePojo();
        try{
            Note note = notes.findNotebyId(id);
            NotePojo notep = new NotePojo();
            notep.setTitle(note.getTitle());
            notep.setContent(note.getContent());
            response.setStatus("Success");
            response.setStatusCode("00");
            response.setNote(notep);
            return Response.status(Response.Status.OK).entity(response).build();            
        }
        catch(Exception e){
            response.setStatus(e.getMessage());
            response.setStatusCode("01");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();            
        }
    }
    
    @POST
    @Path("/readnote")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNoteByTitle(ReadNoteRequestPojo request) {
        ReadNoteResponsePojo response = new ReadNoteResponsePojo();
        try{
            Note note = notes.findNote(request);
            NotePojo notep = new NotePojo();
            notep.setTitle(note.getTitle());
            notep.setContent(note.getContent());
            response.setStatus("Success");
            response.setStatusCode("00");
            response.setNote(notep);
            return Response.status(Response.Status.OK).entity(response).build();            
        }
        catch(Exception e){
            response.setStatus(e.getMessage());
            response.setStatusCode("01");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();            
        }
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)    
    @Produces(MediaType.APPLICATION_JSON)
    public Response createNote(CreateNoteRequestPojo request) { 
        CreateNoteResponsePojo response = new CreateNoteResponsePojo();
        try{
            Note note = new Note();
            note.setTitle(request.getTitle());
            note.setContent(request.getContent());
            notes.createNote(note);
            response.setStatus("Note Created");
            response.setStatusCode("00");
            return Response.status(Response.Status.CREATED).entity(response).build();
        }
        catch(Exception e)
        {
            response.setStatus(e.getMessage());
            response.setStatusCode("01");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();            
        }    
    }
    
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateNote(UpdateNoteRequestPojo request) { 
        UpdateNoteResponsePojo response = new UpdateNoteResponsePojo();
        try{
            notes.updateNote(request);
            response.setStatus("Note Updated");
            response.setStatusCode("00");
            return Response.status(Response.Status.OK).entity(response).build();
        }
        catch(Exception e)
        {
            response.setStatus(e.getMessage());
            response.setStatusCode("01");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();
        }
    }

    @POST    
    @Path("/delete")    
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteNote(DeleteNoteRequestPojo request) {
        DeleteNoteResponsePojo response = new DeleteNoteResponsePojo();
        try{          
            notes.removeNote(request);
            response.setStatus("Deleted");
            response.setStatusCode("00");
            return Response.status(Response.Status.OK).entity(response).build();
        }
        catch(Exception e){
            response.setStatus(e.getMessage());
            response.setStatusCode("01");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(response).build();            
        }
    }


}
