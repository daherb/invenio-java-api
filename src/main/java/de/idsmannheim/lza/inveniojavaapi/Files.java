/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.FilesDeserializer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import io.vavr.control.Either;
import java.text.SimpleDateFormat;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Files (https://inveniordm.docs.cern.ch/reference/metadata/#files)
 * 
 * Records may have associated digital files. A record is not meant to be associated with a high number of files, as the files are stored inside the record and thus increase the overall size of the JSON document.
 * 
 * All of the fields below are under the "files" key.
 * 
 *  ______________________________________________________________________________
 * | Fields          | Cardinality | Description                                  |
 * |-----------------+-------------+----------------------------------------------|
 * | Enabled         | (1)         | The enabled field determines if the record   |
 * |                 |             | is a metadata-only record or if files are    |
 * |                 |             | associated.                                  |
 * | Entries         | (0-n)       | The entries field lists the associated       |
 * |                 |             | digital files for the resource described     |
 * |                 |             | by the record                                |
 * | Default preview | (0-1)       | The default preview field names the          |
 * |                 |             | filename of the file which should by         |
 * |                 |             | default be shown on the record landing page. |
 *  ------------------------------------------------------------------------------
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonDeserialize(using = FilesDeserializer.class)
public class Files {
    
    /**
     * Entries (https://inveniordm.docs.cern.ch/reference/metadata/#entries-0-n)
     * 
     * The entries field lists the associated digital files for the resource 
     * described by the record. The files must all be registered in 
     * Invenio-Files-REST store independently. This is to ensure that files can
     * be tracked and fixity can be checked.
     * 
     * The key (paper.pdf below) represents a file path.
     * Subfields:
     *  ______________________________________________________________________________
     * | Field         | Cardinality | Description                                    |
     * | bucket_id     | (1)         | The bucket identifier.                         |
     * | checksum      | (1)         | The checksum of the file in the form           |
     * |               |             | <algorithm>:<value>.                           |
     * | created       | (1)         | Date of creation (init) of the file record.    |
     * | file_id       | (1)         | The digital file instance identifier           |
     * |               |             | (references a file on storage).                |
     * | key           | (1)         | The filepath of the file.                      |
     * | link          | (1)         | Links to the file (self, content, iiif_canvas, |
     * |               |             | iiif_base, iiif_info, iiif_api, etc.)          |
     * | metadata      | (1)         | Dictionary of free key-value pairs with meta   |
     * |               |             | information about the file (e.g. description). |
     * | mimetype      | (1)         | The mimetype of the file.                      |
     * | size          | (1)         | The size in bytes of the file.                 |
     * | status        | (1)         | The current status of the file ingestion       |
     * |               |             | (completed or pending).                        |
     * | storage_class | (1)         | The backend for the file (e.g. local or        |
     * |               |             | external storage).                             |
     * | updated       | (1)         | Date of latest update of the file record       |
     * |               |             | metadata or file.                              |
     * | version_id    | (1)         | The logical object identifier.                 |
     *  ------------------------------------------------------------------------------
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = FilesDeserializer.FileEntryDeserializer.class)
    public static class FileEntry {
        @JsonProperty("bucket_id")
        String bucketId;
        @JsonProperty("checksum")
        String checksum;
        Date created = new Date();
        @JsonProperty("file_id")
        String fileId;
        @JsonProperty("key")
        String key;
        @JsonProperty("links")
        HashMap<String,String> links = new HashMap<>();
        @JsonProperty("metadata")
        HashMap<String,Object> metadata = new HashMap<>();
        @JsonProperty("mimetype")
        String mimetype;
        @JsonProperty("size")
        int size;
        // TODO: Enum?
        @JsonProperty("status")
        String status;
        // TODO: Enum?
        @JsonProperty("storage_class")
        String storageClass;
        Date updated = new Date();
        @JsonProperty("version_id")
        String versionId;

        /**
         * Default constructor for file entry in metadata
         * 
         * @param bucketId The bucket identifier.
         * @param checksum The checksum of the file in the form <algorithm>:<value>.
         * @param created Date of creation (init) of the file record.
         * @param fileId The digital file instance identifier (references a file on storage).
         * @param key The filepath of the file.
         * @param mimetype The mimetype of the file.
         * @param size The size in bytes of the file.
         * @param status The current status of the file ingestion (completed or pending).
         * @param storageClass The backend for the file (e.g. local or external storage).
         * @param updated Date of latest update of the file record metadata or file.
         * @param versionId The logical object identifier.
         */
        public FileEntry(String bucketId, String checksum, Date created, String fileId, String key, String mimetype, int size, String status, String storageClass, Date updated, String versionId) {
            this.bucketId = bucketId;
            this.checksum = checksum;
            this.created = created;
            this.fileId = fileId;
            this.key = key;
            this.mimetype = mimetype;
            this.size = size;
            this.status = status;
            this.storageClass = storageClass;
            this.updated = updated;
            this.versionId = versionId;
        }

        /**
         * Minimal constructor for file entries used in draft file upload
         * @param key 
         */
        public FileEntry(String key) {
            this.key = key;
        }
        public FileEntry addMetadata(HashMap<String,Object> metadata) {
            this.metadata.putAll(metadata);
            return this;
        }
        
        public FileEntry addLinks(HashMap<String,String> link) {
            this.links.putAll(link);
            return this;
        }
        
        @JsonProperty("created")
        public String getCreatedAsString() {
            if (created != null)
                return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(created);
            else
                return null;
        }
        
        @JsonProperty("updated")
        public String getUpdatedAsString() {
            if (updated != null)
               return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(updated);
            else
                return null;
        }

        public String getBucketId() {
            return bucketId;
        }

        public String getChecksum() {
            return checksum;
        }

        public String getFileId() {
            return fileId;
        }

        public String getKey() {
            return key;
        }

        public HashMap<String, String> getLinks() {
            return links;
        }

        public HashMap<String, Object> getMetadata() {
            return metadata;
        }

        public String getMimetype() {
            return mimetype;
        }

        public int getSize() {
            return size;
        }

        public String getStatus() {
            return status;
        }

        public String getStorageClass() {
            return storageClass;
        }

        public String getVersionId() {
            return versionId;
        }

        @Override
        protected Object clone() {
            FileEntry fileEntry = new FileEntry(bucketId, checksum, (Date) created.clone(), fileId, key, mimetype, size, status, storageClass, (Date) updated.clone(), versionId);
            fileEntry.addLinks((HashMap<String, String>) links.clone());
            fileEntry.addMetadata((HashMap<String, Object>) metadata.clone());
            return fileEntry;
        }
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 67 * hash + Objects.hashCode(this.bucketId);
            hash = 67 * hash + Objects.hashCode(this.checksum);
            hash = 67 * hash + Objects.hashCode(this.created);
            hash = 67 * hash + Objects.hashCode(this.fileId);
            hash = 67 * hash + Objects.hashCode(this.key);
            hash = 67 * hash + Objects.hashCode(this.links);
            hash = 67 * hash + Objects.hashCode(this.metadata);
            hash = 67 * hash + Objects.hashCode(this.mimetype);
            hash = 67 * hash + Objects.hashCode(this.size);
            hash = 67 * hash + Objects.hashCode(this.status);
            hash = 67 * hash + Objects.hashCode(this.storageClass);
            hash = 67 * hash + Objects.hashCode(this.updated);
            hash = 67 * hash + Objects.hashCode(this.versionId);
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
            final FileEntry other = (FileEntry) obj;
            if (!Objects.equals(this.bucketId, other.bucketId)) {
                return false;
            }
            if (!Objects.equals(this.checksum, other.checksum)) {
                return false;
            }
            if (!Objects.equals(this.fileId, other.fileId)) {
                return false;
            }
            if (!Objects.equals(this.key, other.key)) {
                return false;
            }
            if (!Objects.equals(this.mimetype, other.mimetype)) {
                return false;
            }
            if (!Objects.equals(this.size, other.size)) {
                return false;
            }
            if (!Objects.equals(this.status, other.status)) {
                return false;
            }
            if (!Objects.equals(this.storageClass, other.storageClass)) {
                return false;
            }
            if (!Objects.equals(this.versionId, other.versionId)) {
                return false;
            }
            if (!Objects.equals(this.created, other.created)) {
                return false;
            }
            if (!Objects.equals(this.links, other.links)) {
                return false;
            }
            if (!Objects.equals(this.metadata, other.metadata)) {
                return false;
            }
            return Objects.equals(this.updated, other.updated);
        }

        @Override
        public String toString() {
            return "FileEntry{" + "bucketId=" + bucketId + ", checksum=" + checksum + ", created=" + created + ", fileId=" + fileId + ", key=" + key + ", link=" + links + ", metadata=" + metadata + ", mimetype=" + mimetype + ", size=" + size + ", status=" + status + ", storageClass=" + storageClass + ", updated=" + updated + ", versionId=" + versionId + '}';
        }
        
        
    }
    
    /**
     *  ______________________________________________________________________________
     * | Fields          | Cardinality | Description                                  |
     * |-----------------+-------------+----------------------------------------------|
     * | Enabled         | (1)         | The enabled field determines if the record   |
     * |                 |             | is a metadata-only record or if files are    |
     * |                 |             | associated.                                  |
     * | Entries         | (0-n)       | The entries field lists the associated       |
     * |                 |             | digital files for the resource described     |
     * |                 |             | by the record                                |
     * | Default preview | (0-1)       | The default preview field names the          |
     * |                 |             | filename of the file which should by         |
     * |                 |             | default be shown on the record landing page. |
     *  ------------------------------------------------------------------------------
    */
    @JsonProperty("enabled")
    boolean enabled;
    // Antries can either be a list (e.g. when listing draft files) or a map from file name to file entry (e.g. in metadata)
    // JSON property is defined on the getter
    // Either<ArrayList<FileEntry>,HashMap<String,FileEntry>> entries;
    ArrayList<FileEntry> entriesList = new ArrayList<>();
    HashMap<String, FileEntry> entriesMap = new HashMap<String, FileEntry>();
    // HashMap<String, FileEntry> entries = new HashMap<>();
    @JsonProperty("default_preview")
    Optional<String> defaultPreview = Optional.empty();

    public Files(boolean enabled) {
        this.enabled = enabled;
    }
    
    public Files addEntriesMap(HashMap<String, FileEntry> entries) {
//        if (this.entries == null)
//            this.entries = Either.right(entries);
//        else
//            this.entries.get().putAll(entries);
        this.entriesMap.putAll(entries);
        return this;
    }
    
    public Files addEntriesList(ArrayList<FileEntry> entries) {
//        if (this.entries == null)
//            this.entries = Either.left(entries);
//        else
//            this.entries.getLeft().addAll(entries);
        this.entriesList.addAll(entries);
        return this;
    }
    
    public Files setDefaultPreview(String defaultPreview) {
        this.defaultPreview = Optional.of(defaultPreview);
        return this;
    }

    @JsonProperty("entries")
    public Object getEntries() {
//        if (this.entries.isLeft())
//            return this.entries.getLeft();
//        else
//            return this.entries.get();
        if (!this.entriesMap.isEmpty()) {
            return this.entriesMap;
        }
        else {
            return this.entriesList;
        }
    }

    @Override
    protected Object clone() {
        Files files = new Files(enabled);
        files.addEntriesMap(entriesMap);
        files.addEntriesList(entriesList);
//        if (entries.isLeft())
//            files.addEntries((ArrayList<FileEntry>) entries.getLeft().clone());
//        else
//            files.addEntries((HashMap<String, FileEntry>) entries.get().clone());
        if (defaultPreview.isPresent())
            files.setDefaultPreview(defaultPreview.get());
        return files;
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.enabled ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.entriesMap);
        hash = 89 * hash + Objects.hashCode(this.entriesList);
        hash = 89 * hash + Objects.hashCode(this.defaultPreview);
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
        final Files other = (Files) obj;
        if (this.enabled != other.enabled) {
            return false;
        }
        if (!Objects.equals(this.entriesMap, other.entriesMap)) {
            return false;
        }
        if (!Objects.equals(this.entriesList, other.entriesList)) {
            return false;
        }
        return Objects.equals(this.defaultPreview, other.defaultPreview);
    }

    @Override
    public String toString() {
        return "Files{" + "enabled=" + enabled + ", entriesMap=" + entriesMap + ", entriesList=" + entriesList + ", defaultPreview=" + defaultPreview + '}';
    }
    
    
}
