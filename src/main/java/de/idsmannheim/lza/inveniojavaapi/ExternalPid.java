/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.ExternalPidDeserializer;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * External Pids (https://inveniordm.docs.cern.ch/reference/metadata/#external-pids)
 * 
 * External PIDs are persistent identifiers managed via Invenio-PIDStore that
 * may require integration with external registration services.
 * 
 * Persistent identifiers are globally unique in the system, thus you cannot
 * have two records with the same system-managed persistent identifier
 * (see also Metadata > Identifiers).
 * 
 * You can add a DOI that is not managed by InvenioRDM by using the provider
 * external. You are not able to add external DOIs that have a prefix that is
 * configured as part of a different PID provider.
 * 
 * Only one identifier can be registered per system-defined scheme. Each
 * identifier has the following subfields:
 *  ___________________________________________________________________________
 * | Field      | Cardinality | Description                                    |
 * | identifier | (1)         | The identifier value.                          |
 * | provider   | (1)         | The provider identifier used internally by the |
 * |            |             | system.                                        |
 * | client     | (0-1)       | The client identifier used for connecting with |
 * |            |             | an external registration service.              |
 *  ---------------------------------------------------------------------------
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonDeserialize(using = ExternalPidDeserializer.class)
public class ExternalPid {
    @JsonProperty("identifier")
    String identifier;
    @JsonProperty("provider")
    String provider;
    @JsonProperty("client")
    Optional<String> client = Optional.empty();

    public ExternalPid(String identifier, String provider) {
        this.identifier = identifier;
        this.provider = provider;
    }

    public ExternalPid setClient(String client) {
        this.client = Optional.of(client);
        return this;
    }

    @Override
    protected Object clone() {
        ExternalPid externalPid = new ExternalPid(identifier, provider);
        if (client.isPresent())
            externalPid.setClient(client.get());
        return externalPid;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.identifier);
        hash = 23 * hash + Objects.hashCode(this.provider);
        hash = 23 * hash + Objects.hashCode(this.client);
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
        final ExternalPid other = (ExternalPid) obj;
        if (!Objects.equals(this.identifier, other.identifier)) {
            return false;
        }
        if (!Objects.equals(this.provider, other.provider)) {
            return false;
        }
        return Objects.equals(this.client, other.client);
    }

    @Override
    public String toString() {
        return "ExternalPid{" + "identifier=" + identifier + ", provider=" + provider + ", client=" + client + '}';
    }
    
    
}
