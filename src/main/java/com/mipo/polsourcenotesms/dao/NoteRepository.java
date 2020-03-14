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

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

    public void addNote(Note note) {
        EntityManager manager = getEntityManager();
        manager.getTransaction().begin();
        manager.persist(note);
        manager.getTransaction().commit();
        manager.close();
    }

    public void removeNote(long id) {
        EntityManager manager = getEntityManager();
        manager.getTransaction().begin();
        manager.remove(manager.find(Note.class, id));
        manager.getTransaction().commit();
        manager.close();
    }

    public Note findNote(long id) {
        return getEntityManager().find(Note.class, id);
    }
    
    public Note findNote(String title) {
        return getEntityManager().find(Note.class, title);
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