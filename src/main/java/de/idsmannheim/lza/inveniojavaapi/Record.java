/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.RecordDeserializer;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
@JsonInclude(JsonInclude.Include.NON_ABSENT)
@JsonDeserialize(using = RecordDeserializer.class)
public class Record {
    
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = RecordDeserializer.ParentDeserializer.class)
    public static class Parent {
        @JsonProperty("id")
        String id ;
        // TODO this is a very generic type because it is empty at the moment
        @JsonProperty("communities")
        HashMap<String, Object> communities = new HashMap<>();

        public Parent(String id) {
            this.id = id;
        }

        public Parent addCommunities(HashMap<String, String> communities) {
            this.communities.putAll(communities);
            return this;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 13 * hash + Objects.hashCode(this.id);
            hash = 13 * hash + Objects.hashCode(this.communities);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Parent other = (Parent) obj;
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
            return Objects.equals(this.communities, other.communities);
        }

        @Override
        public String toString() {
            return "Parent{" + "id=" + id + ", communities=" + communities + '}';
        }
        
        
    }
    
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonDeserialize(using = RecordDeserializer.VersionsDeserializer.class)
    public static class Versions {
        @JsonProperty("index")
        int index;
        @JsonProperty("is_latest")
        boolean latest;

        public Versions(int index, boolean latest) {
            this.index = index;
            this.latest = latest;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 41 * hash + this.index;
            hash = 41 * hash + (this.latest ? 1 : 0);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Versions other = (Versions) obj;
            if (this.index != other.index) {
                return false;
            }
            return this.latest == other.latest;
        }

        @Override
        public String toString() {
            return "Versions{" + "index=" + index + ", latest=" + latest + '}';
        }
        
        
    }
    
        @JsonProperty("access")
        Access access;
        @JsonProperty("created")
        Date created;
        @JsonProperty("custom_fields")
        HashMap<String,Object> customFields = new HashMap<>();
        @JsonProperty("files")
        FilesOptions files;
        @JsonProperty("id")
        String id;
        @JsonProperty("is_draft")
        boolean isDraft;
        @JsonProperty("is_published")
        boolean isPublished;
        @JsonProperty("links")
        HashMap<String,String> links = new HashMap<>();
        @JsonProperty("metadata")
        Metadata metadata;
        @JsonProperty("parent")
        Record.Parent parent;
        @JsonProperty("pids")
        HashMap<String,ExternalPid> pids = new HashMap<>();
        @JsonProperty("revision_id")
        int revisionId;
        @JsonProperty("status")
        String status;
        @JsonProperty("updated")
        Date updated;
        @JsonProperty("versions")
        Versions versions;

        public Record(Access access, Date created, FilesOptions files, String id, boolean isDraft, boolean isPublished, Metadata metadata, Parent parent, int revisionId, String status, Date updated, Versions versions) {
            this.access = access;
            this.created = created;
            this.files = files;
            this.id = id;
            this.isDraft = isDraft;
            this.isPublished = isPublished;
            this.metadata = metadata;
            this.parent = parent;
            this.revisionId = revisionId;
            this.status = status;
            this.updated = updated;
            this.versions = versions;
        }

        public Record addCustomFields(HashMap<String, Object> customFields) {
            this.customFields.putAll(customFields);
            return this;
        }

        public Record addLinks(HashMap<String, String> links) {
            this.links.putAll(links);
            return this;
        }

        public Record addPids(HashMap<String, ExternalPid> pids) {
            this.pids.putAll(pids);
            return this;
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 79 * hash + Objects.hashCode(this.access);
            hash = 79 * hash + Objects.hashCode(this.created);
            hash = 79 * hash + Objects.hashCode(this.customFields);
            hash = 79 * hash + Objects.hashCode(this.files);
            hash = 79 * hash + Objects.hashCode(this.id);
            hash = 79 * hash + (this.isDraft ? 1 : 0);
            hash = 79 * hash + (this.isPublished ? 1 : 0);
            hash = 79 * hash + Objects.hashCode(this.links);
            hash = 79 * hash + Objects.hashCode(this.metadata);
            hash = 79 * hash + Objects.hashCode(this.parent);
            hash = 79 * hash + Objects.hashCode(this.pids);
            hash = 79 * hash + this.revisionId;
            hash = 79 * hash + Objects.hashCode(this.status);
            hash = 79 * hash + Objects.hashCode(this.updated);
            hash = 79 * hash + Objects.hashCode(this.versions);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Record other = (Record) obj;
            if (this.isDraft != other.isDraft) {
                return false;
            }
            if (this.isPublished != other.isPublished) {
                return false;
            }
            if (this.revisionId != other.revisionId) {
                return false;
            }
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
            if (!Objects.equals(this.status, other.status)) {
                return false;
            }
            if (!Objects.equals(this.access, other.access)) {
                return false;
            }
            if (!Objects.equals(this.created.toString(), other.created.toString())) {
                return false;
            }
            if (!Objects.equals(this.customFields, other.customFields)) {
                return false;
            }
            if (!Objects.equals(this.files, other.files)) {
                return false;
            }
            if (!Objects.equals(this.links, other.links)) {
                return false;
            }
            if (!Objects.equals(this.metadata, other.metadata)) {
                return false;
            }
            if (!Objects.equals(this.parent, other.parent)) {
                return false;
            }
            if (!Objects.equals(this.pids, other.pids)) {
                return false;
            }
            if (!Objects.equals(this.updated.toString(), other.updated.toString())) {
                return false;
            }
            return Objects.equals(this.versions, other.versions);
        }

        @Override
        public String toString() {
            return "Hit{" + "access=" + access + ", created=" + created + ", customFields=" + customFields + ", files=" + files + ", id=" + id + ", isDraft=" + isDraft + ", isPublished=" + isPublished + ", links=" + links + ", metadata=" + metadata + ", parent=" + parent + ", pids=" + pids + ", revisionId=" + revisionId + ", status=" + status + ", updated=" + updated + ", versions=" + versions + '}';
        }
    }

