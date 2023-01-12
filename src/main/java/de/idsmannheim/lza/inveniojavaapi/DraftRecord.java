/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class DraftRecord {
    
    @JsonProperty("access")
    Access access;
    @JsonProperty("files")
    FilesOptions files;
    @JsonProperty("metadata")
    Metadata metada;

    public DraftRecord(Access access, FilesOptions files, Metadata metada) {
        this.access = access;
        this.files = files;
        this.metada = metada;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.access);
        hash = 17 * hash + Objects.hashCode(this.files);
        hash = 17 * hash + Objects.hashCode(this.metada);
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
        if (!Objects.equals(this.files, other.files)) {
            return false;
        }
        return Objects.equals(this.metada, other.metada);
    }

    @Override
    public String toString() {
        return "DraftRecord{" + "access=" + access + ", files=" + files + ", metada=" + metada + '}';
    }
    
    
}
