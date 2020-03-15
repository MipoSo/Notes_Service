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

package com.mipo.polsourcenotesms.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mipo.polsourcenotesms.model.Note;
import com.mipo.polsourcenotesms.pojo.ReadNoteRequestPojo;
import com.mipo.polsourcenotesms.pojo.UpdateNoteRequestPojo;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

/**
 * UserRepository class which is extended from AbstractRepository class.
 */

@Transactional
@Repository
public class NoteRepository {

    @Autowired
    private EntityManagerFactory emf;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void createNote(Note note) {
        EntityManager manager = getEntityManager();
        manager.getTransaction().begin();
        manager.persist(note);
        manager.getTransaction().commit();
        manager.close();
    }
    
    public void updateNote(UpdateNoteRequestPojo request) {
        EntityManager manager = getEntityManager();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Note> cq = cb.createQuery(Note.class);
        Root<Note> rootEntry = cq.from(Note.class);
        //CriteriaQuery<Note> note = cu.select(rootEntry).where(cb.equal(rootEntry.get("title"), request.getTitle()));
        cq.select(rootEntry).where(cb.equal(rootEntry.get("title"), request.getOldTitle()));
        Note noteresult = manager.createQuery(cq).getSingleResult();
        noteresult = manager.find(Note.class, noteresult.getId());
        manager.getTransaction().begin();
        noteresult.setContent(request.getContent()); 
        noteresult.setTitle(request.getNewTitle());
        manager.persist(noteresult);
        manager.getTransaction().commit();
        manager.close();
    }
    
    public void removeNote(String title) {    
        EntityManager manager = getEntityManager();    
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaDelete<Note> cd = cb.createCriteriaDelete(Note.class);
        Root<Note> rootEntry = cd.from(Note.class);
        cd.where(cb.equal(rootEntry.get("title"), title));
        manager.getTransaction().begin();
        manager.createQuery(cd).executeUpdate();
        manager.getTransaction().commit();
        manager.close();
    }

    public Note findNotebyId(long id) {
        return getEntityManager().find(Note.class, id);
    }
    
    public Note findNote(ReadNoteRequestPojo request) {
        EntityManager manager = getEntityManager();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Note> cq = cb.createQuery(Note.class);
        Root<Note> rootEntry = cq.from(Note.class);
        cq.select(rootEntry).where(cb.equal(rootEntry.get("title"), request.getTitle()));
        Note noteresult = manager.createQuery(cq).getSingleResult();
        noteresult = manager.find(Note.class, noteresult.getId());
        return noteresult;
    }
    
    public List<Note> findAllNotes() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Note> cq = cb.createQuery(Note.class);
        Root<Note> rootEntry = cq.from(Note.class);
        CriteriaQuery<Note> all = cq.select(rootEntry);
        TypedQuery<Note> allQuery = getEntityManager().createQuery(all);
        return allQuery.getResultList();
    }
}
//createRegistration