/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.ParentDeserializer;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Parent (https://inveniordm.docs.cern.ch/reference/metadata/#parent)
 * 
 * Information related to the record as a concept is recorded under the top-level
 * parent key. Every record has a parent and that parent connects all versions 
 * of a record together. The goal here is to connect these records and store 
 * shared information - ownership for now.
 * 
 * Subfields:
 *  __________________________________________________________________
 * | Field  | Cardinality | Description                               |
 * |--------+-------------+-------------------------------------------|
 * | id     | (1)         | The identifier of the parent record.      |
 * | access | (1)         | Access details for the record as a whole. |
 *  ------------------------------------------------------------------
 * 
 * The access is described with the following subfields:
 *  ______________________________________________
 * | Field    | Cardinality | Description         |
 * |----------+-------------+---------------------|
 * | owned_by | (1-n)       | An array of owners. |
 *  ----------------------------------------------
 * 
 * Owners are defined as:
 *  ________________________________________________________________________
 * | Field | Cardinality | Description                                      |
 * |-------+-------------+--------------------------------------------------|
 * | user  | (1)         | The id of the user owning the record as a whole. |
 *  ------------------------------------------------------------------------
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonDeserialize(using = ParentDeserializer.class)
public class Parent {
    
    /**
     *  ______________________________________________
     * | Field    | Cardinality | Description         |
     * |----------+-------------+---------------------|
     * | owned_by | (1-n)       | An array of owners. |
     *  ----------------------------------------------
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = ParentDeserializer.AccessDeserializer.class)
    public static class Access {
        @JsonProperty("owned_by")
        ArrayList<Owner> ownedBy = new ArrayList<>();

        public Access(ArrayList<Owner> ownedBy) {
            if (ownedBy.isEmpty())
                throw new IllegalArgumentException("At least one owner has to be given");
            this.ownedBy.addAll(ownedBy);
        }

        @Override
        protected Object clone() {
            return new Access((ArrayList<Owner>) ownedBy.clone());
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 67 * hash + Objects.hashCode(this.ownedBy);
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
            return Objects.equals(this.ownedBy, other.ownedBy);
        }

        @Override
        public String toString() {
            return "Access{" + "ownedBy=" + ownedBy + '}';
        }
        
        
    }
    /**
     *  ________________________________________________________________________
     * | Field | Cardinality | Description                                      |
     * |-------+-------------+--------------------------------------------------|
     * | user  | (1)         | The id of the user owning the record as a whole. |
     *  ------------------------------------------------------------------------
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = ParentDeserializer.OwnerDeserializer.class)
    public static class Owner {
        @JsonProperty("user")
        int user;

        public Owner(int user) {
            this.user = user;
        }

        @Override
        protected Object clone() {
            return new Owner(user);
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + this.user;
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
            final Owner other = (Owner) obj;
            return this.user == other.user;
        }

        @Override
        public String toString() {
            return "Owner{" + "user=" + user + '}';
        }
        
        
    }
    
    /**
     *  __________________________________________________________________
     * | Field  | Cardinality | Description                               |
     * |--------+-------------+-------------------------------------------|
     * | id     | (1)         | The identifier of the parent record.      |
     * | access | (1)         | Access details for the record as a whole. |
     *  ------------------------------------------------------------------
     */
    @JsonProperty("id")
    String id;
    @JsonProperty("access")
    Access access;

    public Parent(String id, Access access) {
        this.id = id;
        this.access = access;
    }

    @Override
    protected Object clone() {
        return new Parent(id, (Access) access.clone());
    }

    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.access);
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
        return Objects.equals(this.access, other.access);
    }

    @Override
    public String toString() {
        return "Parent{" + "id=" + id + ", access=" + access + '}';
    }
    
    
}
