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
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Class representing list of records as a result of a search operation
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
        ArrayList<Record> hits = new ArrayList<>();
        @JsonProperty("total")
        int total;

        public Hits(int total) {
            this.total = total;
        }

        public Hits addHits(ArrayList<Record> hits) {
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
