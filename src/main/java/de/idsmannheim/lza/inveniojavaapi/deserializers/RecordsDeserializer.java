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
import de.idsmannheim.lza.inveniojavaapi.Records;
import de.idsmannheim.lza.inveniojavaapi.Record;
import java.io.IOException;
import java.util.ArrayList;
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
            ArrayList<Record> hits = om.readerForListOf(Record.class).readValue(node.get("hits").toString());
            return new Records.Hits(total).addHits(hits);
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
