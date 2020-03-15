/*
 * Copyright 2020 WSO2.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mipo.polsourcenotesms.pojo;

import com.mipo.polsourcenotesms.model.Note;
import java.util.List;

/**
 *
 * @author mipo
 */
public class ReadAllNoteResponsePojo {
    private String status;

    private String statusCode;
    
    private List<Note> note;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<Note> getNote() {
        return note;
    }

    public void setNote(List<Note> note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "ReadAllNoteResponsePojo{" + "status=" + status + ", statusCode=" + statusCode + ", note=" + note + '}';
    }

    
    
}
