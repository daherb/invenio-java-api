/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.AccessDeserializer;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Access (https://inveniordm.docs.cern.ch/reference/metadata/#access)
 *
 * The access field denotes record-specific read (visibility) options.
 * The access field has this structure:
 *  ____________________________________________________________
 * |Field    | Cardinality | Description                        |
 * |---------+-------------+------------------------------------|
 * | record  | (1)         | "public" or "restricted".          |
 * |         |             | Read access to the record.         |
 * | files   | (1)         | "public" or "restricted".          |
 * |         |             | Read access to the record's files. |
 * | embargo | (0-1)       | Embargo options for the record.    |
 *  ------------------------------------------------------------
 *
 * "public" means anyone can see the record/files.
 * "restricted" means only the owner(s) or specific users can see the
 * record/files. Only in the cases of "record": "restricted" or "files":
 * "restricted" can an embargo be provided as input. However, once an
 * embargo is lifted, the embargo section is kept for transparency.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonDeserialize(using = AccessDeserializer.class)
public class Access {
    
    public static enum AccessType { Restricted, Public };
    
    /**
     * Embargo (https://inveniordm.docs.cern.ch/reference/metadata/#embargo)
     *
     * The embargo field denotes when an embargo must be lifted, at which
     * point the record is made publicly accessible. The embargo field has
     * this structure:
     *
     *  ______________________________________________________
     * | Field  | Cardinality | Description                   |
     * |--------+-------------+-------------------------------|
     * | active |(1)          | boolean. Is the record under  |
     * |        |             | embargo or not.               |
     * | until  | (0-1)       | Required if active true. ISO  |
     * |        |             | date string. When to lift the |
     * |        |             | embargo. e.g., "2100-10-01"   |
     * | reason | (0-1)       | string. Explanation for the   |
     * |        |             | embargo.                      |
     *  ------------------------------------------------------
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonDeserialize(using = AccessDeserializer.EmbargoDeserializer.class)
    public static class Embargo {
        
        @JsonProperty("active")
        boolean active; 
        @JsonProperty("until")
        Optional<Date> until = Optional.empty();
        @JsonProperty("reason")
        Optional<String> reason = Optional.empty();
        
        /**
         * Constructor for inactive embargo
         */
        public Embargo() {
            this.active = false;
        }
        
        /**
         * Constructor for active embargo
         * @param until End of embargo
         * @param reason Reason for embargo
         */
        public Embargo(Date until, Optional<String> reason) {
            this.active = true;
            this.until = Optional.of(until);
            this.reason = reason;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 37 * hash + (this.active ? 1 : 0);
            hash = 37 * hash + Objects.hashCode(this.until);
            hash = 37 * hash + Objects.hashCode(this.reason);
            return hash;
        }

        @Override
        protected Object clone() {
            if (this.active) {
                return new Embargo(until.get(), reason);
            }
            else {
                return new Embargo();
            }
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
            final Embargo other = (Embargo) obj;
            if (this.active != other.active) {
                return false;
            }
            if (!Objects.equals(this.until, other.until)) {
                return false;
            }
            return Objects.equals(this.reason, other.reason);
        }

        @Override
        public String toString() {
            return "Embargo{" + "active=" + active + ", until=" + until + ", reason=" + reason + '}';
        }
        
        
    }
    // Use separate getters instead of JsonProperty for record and files
    AccessType record;
    AccessType files;
    // Status is not in the specification but part of the record list
    @JsonProperty("status")
    Optional<String> status = Optional.empty();
    @JsonProperty("embargo")
    Optional<Embargo> embargo;
    
    public Access(AccessType record, AccessType files) {
        this.record = record;
        this.files = files;
    }

    public Access setStatus(String status) {
        this.status = Optional.of(status);
        return this;
    }

    public Access setEmbargo(Embargo embargo) {
        this.embargo = Optional.of(embargo);
        return this;
    }
    
    @JsonProperty("record")
    public String getRecordAccessAsString() {
        return this.record.toString().toLowerCase();
    }
    
    @JsonProperty("files")
    public String getFileAccessAsString() {
        return this.files.toString().toLowerCase();
    }

    @Override
    protected Object clone() {
        return new Access(record, files);
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.record);
        hash = 41 * hash + Objects.hashCode(this.files);
        hash = 41 * hash + Objects.hashCode(this.status);
        hash = 41 * hash + Objects.hashCode(this.embargo);
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
        final Access other = (Access) obj;
        if (this.record != other.record) {
            return false;
        }
        if (this.files != other.files) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return Objects.equals(this.embargo, other.embargo);
    }

    @Override
    public String toString() {
        return "Access{" + "record=" + record + ", files=" + files + ", status=" + status + ", embargo=" + embargo + '}';
    }
    
    
}

