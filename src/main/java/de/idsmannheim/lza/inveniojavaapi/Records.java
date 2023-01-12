/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.RecordsDeserializer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonDeserialize(using = RecordsDeserializer.class)
public class Records {
    
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = RecordsDeserializer.AggregationDeserializer.class)
    public static class Aggregation {
        @JsonProperty("buckets")
        ArrayList<Bucket> buckets = new ArrayList<>();
        @JsonProperty("label")
        Optional<String> label = Optional.empty();

        public Aggregation() {
        }

        public Aggregation addBuckets(ArrayList<Bucket> buckets) {
            this.buckets.addAll(buckets);
            return this;
        }

        public Aggregation setLabel(String label) {
            this.label = Optional.of(label);
            return this;
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.buckets);
            hash = 97 * hash + Objects.hashCode(this.label);
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
            final Aggregation other = (Aggregation) obj;
            if (!Objects.equals(this.buckets, other.buckets)) {
                return false;
            }
            return Objects.equals(this.label, other.label);
        }

        @Override
        public String toString() {
            return "Aggregation{" + "buckets=" + buckets + ", label=" + label + '}';
        }
        
        
    }
    
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonDeserialize(using = RecordsDeserializer.BucketDeserializer.class)
    public static class Bucket {
        @JsonProperty("doc_count")
        int docCount;
        @JsonProperty("is_selected")
        boolean isSelected;
        @JsonProperty("inner")
        Optional<Aggregation> inner = Optional.empty();
        @JsonProperty("key")
        String key;
        @JsonProperty("label")
        String label;

        public Bucket(int docCount, boolean isSelected, String key, String label) {
            this.docCount = docCount;
            this.isSelected = isSelected;
            this.key = key;
            this.label = label;
        }

        public Bucket setInner(Aggregation inner) {
            this.inner = Optional.of(inner);
            return this;
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 31 * hash + this.docCount;
            hash = 31 * hash + (this.isSelected ? 1 : 0);
            hash = 31 * hash + Objects.hashCode(this.inner);
            hash = 31 * hash + Objects.hashCode(this.key);
            hash = 31 * hash + Objects.hashCode(this.label);
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
            final Bucket other = (Bucket) obj;
            if (this.docCount != other.docCount) {
                return false;
            }
            if (this.isSelected != other.isSelected) {
                return false;
            }
            if (!Objects.equals(this.key, other.key)) {
                return false;
            }
            if (!Objects.equals(this.label, other.label)) {
                return false;
            }
            return Objects.equals(this.inner, other.inner);
        }

        @Override
        public String toString() {
            return "Bucket{" + "docCount=" + docCount + ", isSelected=" + isSelected + ", inner=" + inner + ", key=" + key + ", label=" + label + '}';
        }
        
        
    }
    
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = RecordsDeserializer.HitsDeserializer.class)
    public static class Hits {
        @JsonProperty("hits")
        ArrayList<Hit> hits = new ArrayList<>();
        @JsonProperty("total")
        int total;

        public Hits(int total) {
            this.total = total;
        }

        public Hits addHits(ArrayList<Hit> hits) {
            this.hits.addAll(hits);
            return this;
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + Objects.hashCode(this.hits);
            hash = 59 * hash + this.total;
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
            final Hits other = (Hits) obj;
            if (this.total != other.total) {
                return false;
            }
            return Objects.equals(this.hits, other.hits);
        }

        @Override
        public String toString() {
            return "Hits{" + "hits=" + hits + ", total=" + total + '}';
        }
        
        
    }
    
    @JsonInclude(JsonInclude.Include.NON_ABSENT)
    @JsonDeserialize(using = RecordsDeserializer.HitDeserializer.class)
    public static class Hit {
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
        Records.Parent parent;
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

        public Hit(Access access, Date created, FilesOptions files, String id, boolean isDraft, boolean isPublished, Metadata metadata, Parent parent, int revisionId, String status, Date updated, Versions versions) {
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

        public Hit addCustomFields(HashMap<String, Object> customFields) {
            this.customFields.putAll(customFields);
            return this;
        }

        public Hit addLinks(HashMap<String, String> links) {
            this.links.putAll(links);
            return this;
        }

        public Hit addPids(HashMap<String, ExternalPid> pids) {
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
            final Hit other = (Hit) obj;
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
    
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = RecordsDeserializer.ParentDeserializer.class)
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
    @JsonDeserialize(using = RecordsDeserializer.VersionsDeserializer.class)
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
    @JsonProperty("aggregations")
    HashMap<String,Aggregation> aggregations = new HashMap<>();
    @JsonProperty("hits")
    Hits hits;
    @JsonProperty("links")
    HashMap<String,String> links = new HashMap<>();
    @JsonProperty("sortBy")
    String sortBy;

    public Records(Hits hits, String sortBy) {
        this.hits = hits;
        this.sortBy = sortBy;
    }

    public Records addAggregations(HashMap<String, Aggregation> aggregations) {
        this.aggregations.putAll(aggregations);
        return this;
    }

    
    public Records addLinks(HashMap<String, String> links) {
        this.links.putAll(links);
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.aggregations);
        hash = 97 * hash + Objects.hashCode(this.hits);
        hash = 97 * hash + Objects.hashCode(this.links);
        hash = 97 * hash + Objects.hashCode(this.sortBy);
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
        final Records other = (Records) obj;
        if (!Objects.equals(this.sortBy, other.sortBy)) {
            return false;
        }
        if (!Objects.equals(this.aggregations, other.aggregations)) {
            return false;
        }
        if (!Objects.equals(this.hits, other.hits)) {
            return false;
        }
        return Objects.equals(this.links, other.links);
    }

    @Override
    public String toString() {
        return "Records{" + "aggregations=" + aggregations + ", hits=" + hits + ", links=" + links + ", sortBy=" + sortBy + '}';
    }
    
    
}
