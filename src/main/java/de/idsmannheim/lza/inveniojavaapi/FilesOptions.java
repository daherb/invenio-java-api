/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.FilesOptionsDeserializer;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 *
 * Files Options (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#files-options)
 *
 *  ___________________________________________________________________
 * | Name            | Type    | Location | Description                |
 * |-----------------+---------+----------+----------------------------|
 * | enabled         | boolean | body     | Required. Should (and can) |
 * |                 |         |          | files be attached to this  |
 * |                 |         |          | record or not.             |
 * | default_preview | string  | body     | Filename of file to be     |
 * |                 |         |          | previewed by default       |
 * | order           | array   | body     | Array of filename strings  |
 * |                 |         |          | in display order.          |
 *  -------------------------------------------------------------------
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonDeserialize(using = FilesOptionsDeserializer.class)
public class FilesOptions {
    
    @JsonProperty("enabled")
    boolean enabled;
    @JsonProperty("default_preview")
    String defaultPreview;
    @JsonProperty("order")
    ArrayList<String> order = new ArrayList<>();
    
    public FilesOptions(boolean enabled, String defaultPreview) {
        this.enabled = enabled;
        this.defaultPreview = defaultPreview;
        
    }
    
    public FilesOptions(boolean enabled) {
        this.enabled = enabled;
    }
    
    public FilesOptions addOrder(ArrayList<String> order) {
        this.order.addAll(order);
        return this;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.enabled ? 1 : 0);
        hash = 89 * hash + Objects.hashCode(this.defaultPreview);
        hash = 89 * hash + Objects.hashCode(this.order);
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
        final FilesOptions other = (FilesOptions) obj;
        if (this.enabled != other.enabled) {
            return false;
        }
        if (!Objects.equals(this.defaultPreview, other.defaultPreview)) {
            return false;
        }
        return Objects.equals(this.order, other.order);
    }

    @Override
    public String toString() {
        return "FilesOptions{" + "enabled=" + enabled + ", defaultPreview=" + defaultPreview + ", order=" + order + '}';
    }
    
    
}