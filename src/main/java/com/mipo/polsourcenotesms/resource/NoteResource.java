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
import com.mipo.polsourcenotesms.pojo.DeleteNoteRequestPojo;
import com.mipo.polsourcenotesms.pojo.ReadNoteRequestPojo;
import com.mipo.polsourcenotesms.pojo.UpdateNoteRequestPojo;
import com.mipo.polsourcenotesms.pojo.UpdateNoteResponsePojo;

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
    @Path("/readnote/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNoteById(@PathParam("id") long id) {
        Note note = notes.findNotebyId(id);
        return Response.status(Response.Status.OK).entity(note).build();
    }
    
    @POST
    @Path("/readnote")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNoteByTitle(ReadNoteRequestPojo request) {
        Note note = notes.findNote(request);
        return Response.status(Response.Status.ACCEPTED).entity(note).build();
    }
    
    @GET
    @Path("/allnotes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllNotes() {
        List<Note> allNotess = notes.findAllNotes();
        return Response.status(Response.Status.OK).entity(allNotess).build();
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createNote(CreateNoteRequestPojo request) { 
        CreateNoteResponsePojo response = new CreateNoteResponsePojo();
        try{
            Note note = new Note();
            note.setTitle(request.getTitle());
            note.setContent(request.getContent());
            notes.createNote(note);
            response.setStatus("Note Created");
            response.setStatusCode("00");
            return Response.status(Response.Status.CREATED).build();
        }
        catch(Exception e)
        {
            response.setStatus(e.getMessage());
            response.setStatusCode("01");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();            
        }    
    }
    
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateNote(UpdateNoteRequestPojo request) { 
        UpdateNoteResponsePojo response = new UpdateNoteResponsePojo();
        try{
            notes.updateNote(request);
            response.setStatus("Note Updated");
            response.setStatusCode("00");
            return Response.status(Response.Status.OK).build();
        }
        catch(Exception e)
        {
            response.setStatus(e.getMessage());
            response.setStatusCode("01");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE    
    @Path("/delete")    
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteNote(DeleteNoteRequestPojo request) {
        notes.removeNote(request.getTitle());
        return Response.status(Response.Status.OK).build();
    }


}
