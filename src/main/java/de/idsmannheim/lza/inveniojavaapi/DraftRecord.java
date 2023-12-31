/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.DraftRecordDeserializer;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * A class combining Access, FilesOptions and Metadata as well as other information
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonDeserialize(using = DraftRecordDeserializer.class)
public class DraftRecord {
    @JsonProperty("access")
    Access access;
    // Annotation at String getter
    Optional<Date> created = Optional.empty();
    // Annotation at String getter
    Optional<Date> expiresAt = Optional.empty();
    @JsonProperty("files")
    FilesOptions files;
    @JsonProperty("id")
    Optional<String> id = Optional.empty();
    @JsonProperty("is_published")
    Optional<Boolean> isPublished = Optional.empty();
    @JsonProperty("links")
    HashMap<String,String> links = new HashMap<>();
    @JsonProperty("metadata")
    Metadata metadata;
    @JsonProperty("parent")
    Optional<Parent> parent = Optional.empty();
    @JsonProperty("pids")
    HashMap<String,ExternalPid> pids = new HashMap<>();
    @JsonProperty("revision_id")
    Optional<Integer> revisionId = Optional.empty();
    // Annotation at String getter
    Optional<Date> updated = Optional.empty();
    @JsonProperty("versions")
    Optional<Record.Versions> versions = Optional.empty();
    
    public DraftRecord(Access access, FilesOptions files, Metadata metadata) {
        this.access = access;
        this.files = files;
        this.metadata = metadata;
    }

    public DraftRecord setCreated(Date created) {
        this.created = Optional.of(created);
        return this;
    }

    public DraftRecord setExpiresAt(Date expiresAt) {
        this.expiresAt = Optional.of(expiresAt);
        return this;
    }

    public DraftRecord setId(String id) {
        this.id = Optional.of(id);
        return this;
    }

    public DraftRecord setIsPublished(Boolean isPublished) {
        this.isPublished = Optional.of(isPublished);
        return this;
    }

    public DraftRecord addLinks(HashMap<String, String> links) {
        this.links.putAll(links);
        return this;
    }

    public DraftRecord setParent(Parent parent) {
        this.parent = Optional.of(parent);
        return this;
    }

    public DraftRecord addPids(HashMap<String,ExternalPid> pids) {
        this.pids.putAll(pids);
        return this;
    }
    
    public DraftRecord setRevisionId(Integer revisionId) {
        this.revisionId = Optional.of(revisionId);
        return this;
    }

    public DraftRecord setUpdated(Date updated) {
        this.updated = Optional.of(updated);
        return this;
    }
    
    public DraftRecord setVersions(Record.Versions versions) {
        this.versions = Optional.of(versions);
        return this;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    
    public Access getAccess() {
        return access;
    }

    public Optional<Date> getCreated() {
        return created;
    }
    
    @JsonProperty("created")
    public Optional<String> getCreatedAsString() {
        return created.map(DateFormater.getInstance()::format);
    }

    public Optional<Date> getExpiresAt() {
        return expiresAt;
    }

    @JsonProperty("expires_at")
    public Optional<String> getExpiresAtAsString() {
        return expiresAt.map(DateFormater.getInstance()::format);
    }
    
    public FilesOptions getFiles() {
        return files;
    }

    public Optional<String> getId() {
        return id;
    }

    public Optional<Boolean> getIsPublished() {
        return isPublished;
    }

    public HashMap<String, String> getLinks() {
        return links;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public Optional<Parent> getParent() {
        return parent;
    }

    public HashMap<String, ExternalPid> getPids() {
        return pids;
    }

    public Optional<Integer> getRevisionId() {
        return revisionId;
    }

    public Optional<Date> getUpdated() {
        return updated;
    }
    
    @JsonProperty("updated")
    public Optional<String> getUpdatedAsString() {
        return updated.map(DateFormater.getInstance()::format);
    }

    public Optional<Record.Versions> getVersions() {
        return versions;
    }

    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        DraftRecord draftRecord = new DraftRecord((Access) access.clone(), (FilesOptions) files.clone(), (Metadata) metadata.clone());
        if (created.isPresent())
            draftRecord.setCreated((Date) created.get().clone());
        if (expiresAt.isPresent())
            draftRecord.setExpiresAt((Date) expiresAt.get().clone());
        if (id.isPresent())
            draftRecord.setId(id.get());
        if (isPublished.isPresent())
            draftRecord.setIsPublished(isPublished.get());
        draftRecord.addLinks((HashMap<String, String>) links.clone());
        if (parent.isPresent())
            draftRecord.setParent((Parent) parent.get().clone());
        draftRecord.addPids((HashMap<String, ExternalPid>) pids.clone());
        if (revisionId.isPresent())
            draftRecord.setRevisionId(revisionId.get());
        if (updated.isPresent())
            draftRecord.setUpdated((Date) updated.get().clone());
        if (versions.isPresent())
            draftRecord.setVersions((Record.Versions) versions.get().clone());
        return draftRecord;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.access);
        hash = 19 * hash + Objects.hashCode(this.created);
        hash = 19 * hash + Objects.hashCode(this.expiresAt);
        hash = 19 * hash + Objects.hashCode(this.files);
        hash = 19 * hash + Objects.hashCode(this.id);
        hash = 19 * hash + Objects.hashCode(this.isPublished);
        hash = 19 * hash + Objects.hashCode(this.links);
        hash = 19 * hash + Objects.hashCode(this.metadata);
        hash = 19 * hash + Objects.hashCode(this.parent);
        hash = 19 * hash + Objects.hashCode(this.pids);
        hash = 19 * hash + Objects.hashCode(this.revisionId);
        hash = 19 * hash + Objects.hashCode(this.updated);
        hash = 19 * hash + Objects.hashCode(this.versions);
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
        final DraftRecord other = (DraftRecord) obj;
        if (!Objects.equals(this.access, other.access)) {
            return false;
        }
        if (!Objects.equals(this.created, other.created)) {
            return false;
        }
        if (!Objects.equals(this.expiresAt, other.expiresAt)) {
            return false;
        }
        if (!Objects.equals(this.files, other.files)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.isPublished, other.isPublished)) {
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
        if (!Objects.equals(this.revisionId, other.revisionId)) {
            return false;
        }
        if (!Objects.equals(this.updated, other.updated)) {
            return false;
        }
        return Objects.equals(this.versions, other.versions);
    }

    

    @Override
    public String toString() {
        return "DraftRecord{" + "access=" + access + ", created=" + created + ", expiresAt=" + expiresAt + ", files=" + files + ", id=" + id + ", isPublished=" + isPublished + ", links=" + links + ", metadata=" + metadata + ", parent=" + parent + ", pids=" + pids + ", revisionId=" + revisionId + ", updated=" + updated + ", versions=" + versions + '}';
    }



    
    
    
}
