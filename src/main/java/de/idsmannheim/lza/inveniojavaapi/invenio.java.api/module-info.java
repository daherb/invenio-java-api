module invenio.java.api {
    requires java.logging;
    requires java.net.http;
    requires java.xml;
    
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires org.apache.commons.lang3;
    requires org.jdom2;
    requires spring.boot.autoconfigure;
    requires commons.vfs2;
    requires Saxon.HE;
//    requires java.xml.bind;

    exports de.idsmannheim.lza.inveniojavaapi;
    exports de.idsmannheim.lza.inveniojavaapi.cmdi;
    exports de.idsmannheim.lza.inveniojavaapi.deserializers;

}
