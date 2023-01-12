/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import de.idsmannheim.lza.inveniojavaapi.Access;
import de.idsmannheim.lza.inveniojavaapi.FilesOptions;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import de.idsmannheim.lza.inveniojavaapi.Records;
import de.idsmannheim.lza.inveniojavaapi.ExternalPid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class RecordsDeserializer extends StdDeserializer<Records> {

    public static class AggregationDeserializer extends StdDeserializer<Records.Aggregation> {
        
        public AggregationDeserializer() {
            this(null);
        }
        
        public AggregationDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Records.Aggregation deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
            ArrayList<Records.Bucket> buckets = new ArrayList<>();
            Records.Aggregation aggregation = new Records.Aggregation();
            if (node.has("buckets")) {
                buckets.addAll(om.readerForListOf(Records.Bucket.class).readValue(node.get("buckets").toString()));
                aggregation.addBuckets(buckets);
            }
            if (node.has("label")) {
                aggregation.setLabel(node.get("label").asText());
            }
            return aggregation;
        }
    }
    
    public static class BucketDeserializer extends StdDeserializer<Records.Bucket> {
        
        public BucketDeserializer() {
            this(null);
        }
        
        public BucketDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Records.Bucket deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
            int docCount = node.get("doc_count").asInt();
            boolean isSelected = node.get("is_selected").asBoolean();
            String key = node.get("key").asText();
            String label = node.get("label").asText();
            Records.Bucket bucket = new Records.Bucket(docCount, isSelected, key, label);
            if (node.has("inner")) {
                Records.Aggregation inner = om.readValue(node.get("inner").toString(), Records.Aggregation.class);
                bucket.setInner(inner);
            }
            return bucket;
        }
    }
    
    public static class HitsDeserializer extends StdDeserializer<Records.Hits> {
        
        public HitsDeserializer() {
            this(null);
        }
        
        public HitsDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Records.Hits deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
            int total = node.get("total").asInt();
            ArrayList<Records.Hit> hits = om.readerForListOf(Records.Hit.class).readValue(node.get("hits").toString());
            return new Records.Hits(total).addHits(hits);
        }
    }
    
    public static class HitDeserializer extends StdDeserializer<Records.Hit> {
        
        public HitDeserializer() {
            this(null);
        }
        
        public HitDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Records.Hit deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
            Access access = om.readValue(node.get("access").toString(), Access.class);
            Date created = om.readValue(node.get("created").toString(), Date.class);
            HashMap<String,Object> customFields = om.readerForMapOf(Object.class).readValue(node.get("custom_fields").toString());
            FilesOptions files = om.readValue(node.get("files").toString(), FilesOptions.class);
            String id = node.get("id").asText();
            boolean isDraft = node.get("is_draft").asBoolean();
            boolean isPublished = node.get("is_published").asBoolean();
            HashMap<String,String> links = om.readerForMapOf(String.class).readValue(node.get("links").toString());
            Metadata metadata = om.readValue(node.get("metadata").toString(), Metadata.class);
            Records.Parent parent = om.readValue(node.get("parent").toString(), Records.Parent.class);
            HashMap<String,ExternalPid> pids = om.readerForMapOf(ExternalPid.class).readValue(node.get("pids").toString());
            int revisionId = node.get("revision_id").asInt();
            String status = node.get("status").asText();
            Date updated = om.readValue(node.get("updated").toString(), Date.class);
            Records.Versions versions = om.readValue(node.get("versions").toString(), Records.Versions.class);
            return new Records.Hit(access, created, files, id, isDraft, isPublished, metadata, parent, revisionId, status, updated, versions)
                    .addCustomFields(customFields)
                    .addLinks(links)
                    .addPids(pids);
        }
    }
    
    public static class ParentDeserializer extends StdDeserializer<Records.Parent> {
        
        public ParentDeserializer() {
            this(null);
        }
        
        public ParentDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Records.Parent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
            String id = node.get("id").asText();
            Records.Parent parent = new Records.Parent(id);
            if (node.has("communities")) {
                parent.addCommunities(om.readerForMapOf(Object.class).readValue(node.get("communities").toString()));
            }
            return parent;
        }
    }
    
    public static class VersionsDeserializer extends StdDeserializer<Records.Versions> {
        
        public VersionsDeserializer() {
            this(null);
        }
        
        public VersionsDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Records.Versions deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            int index = node.get("index").asInt();
            boolean latest = node.get("is_latest").asBoolean();
            return new Records.Versions(index, latest);
        }
    }
    
    public RecordsDeserializer() {
        this(null);
    }
    
    public RecordsDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public Records deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        HashMap<String,Records.Aggregation> aggregations = om.readerForMapOf(Records.Aggregation.class).readValue(node.get("aggregations").toString());
        Records.Hits hits = om.readValue(node.get("hits").toString(), Records.Hits.class);
        HashMap<String,String> links = om.readerForMapOf(String.class).readValue(node.get("links").toString());
        String sortBy = node.get("sortBy").asText();
        return new Records(hits, sortBy).addAggregations(aggregations).addLinks(links);
    }
    
}
