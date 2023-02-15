/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.TombstoneDeserializer;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Tombstone (https://inveniordm.docs.cern.ch/reference/metadata/#tombstone)
 * 
 * A tombstone is created when a record is removed from the system. The tombstone records the reason for the removal, a category for statistics purposes, who removed the record and when.
 * 
 * Subfields:
 *  __________________________________________________________________________
 * | Field      | Cardinality | Description                                   |
 * |------------+-------------+-----------------------------------------------|
 * | reason     | (1)         | Free text, the reason for removal.            |
 * | category   | (1)         | An identifier for a category of reasons. Used |
 * |            |             | for statistics purposes and for extracting    |
 * |            |             | e.g. spam records from the system.            |
 * | removed_by | (1)         | The user who removed the record.              |
 * | timestamp  | (1)         | The UTC timestamp when the record was removed.|
 *  --------------------------------------------------------------------------
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonDeserialize(using = TombstoneDeserializer.class)
public class Tombstone {
    
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = TombstoneDeserializer.UserDeserializer.class)
    public static class User {
        @JsonProperty("user")
        int user;

        public User(int user) {
            this.user = user;
        }

        @Override
        protected Object clone() {
            return new User(user);
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + this.user;
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
            final User other = (User) obj;
            return this.user == other.user;
        }

        @Override
        public String toString() {
            return "User{" + "user=" + user + '}';
        }
        
        
    }
    
    /**
      * Subfields:
      *  __________________________________________________________________________
      * | Field      | Cardinality | Description                                   |
      * |------------+-------------+-----------------------------------------------|
      * | reason     | (1)         | Free text, the reason for removal.            |
      * | category   | (1)         | An identifier for a category of reasons. Used |
      * |            |             | for statistics purposes and for extracting    |
      * |            |             | e.g. spam records from the system.            |
      * | removed_by | (1)         | The user who removed the record.              |
      * | timestamp  | (1)         | The UTC timestamp when the record was removed.|
      *  --------------------------------------------------------------------------
      */
    @JsonProperty("reason")
    String reason;
    @JsonProperty("category")
    String category;
    @JsonProperty("removed_by")
    User removedBy;
    @JsonProperty("timestamp")
    Date timestamp;

    public Tombstone(String reason, String category, User removedBy, Date timestamp) {
        this.reason = reason;
        this.category = category;
        this.removedBy = removedBy;
        this.timestamp = timestamp;
    }

    @Override
    protected Object clone() {
        return new Tombstone(reason, category, (User) removedBy.clone(), (Date) timestamp.clone());
    }

    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.reason);
        hash = 29 * hash + Objects.hashCode(this.category);
        hash = 29 * hash + Objects.hashCode(this.removedBy);
        hash = 29 * hash + Objects.hashCode(this.timestamp);
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
        final Tombstone other = (Tombstone) obj;
        if (!Objects.equals(this.reason, other.reason)) {
            return false;
        }
        if (!Objects.equals(this.category, other.category)) {
            return false;
        }
        if (!Objects.equals(this.removedBy, other.removedBy)) {
            return false;
        }
        // Use string representation to avoid problem with millisecond differences
        return Objects.equals(this.timestamp.toString(), other.timestamp.toString());
    }

    @Override
    public String toString() {
        return "Tombstone{" + "reason=" + reason + ", category=" + category + ", removedBy=" + removedBy + ", timestamp=" + timestamp + '}';
    }
    
    
}
