/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * Class providing higher-level abstraction helpers based on API calls
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class APITools {
    
    public static final String SEPARATOR = "-0-0-";
    API api;
    
    private static final Logger LOG = Logger.getLogger(APITools.class.getName());

    
    /** 
     * Default constructor
     * @param api The API object to be used
     */
    public APITools(API api) {
        this.api = api;
    }
    
    /**
     * Upload a sip from a local path as a new draft
     * @param sipPath the path to the SIP
     * @return the id of the new draft record
     * @throws IOException
     * @throws JDOMException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws JsonProcessingException
     * @throws UnsupportedEncodingException 
     */
    public String uploadDraftSip(Path sipPath) throws IOException, JDOMException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException, FileNotFoundException, JsonProcessingException, UnsupportedEncodingException {
            ObjectMapper om = new ObjectMapper();
            // add a trailing slash if missing
            if (!sipPath.endsWith("/"))
                sipPath = Path.of(sipPath.toString(), "/");
            om.findAndRegisterModules();
            om.enable(SerializationFeature.INDENT_OUTPUT);
            ArrayList<File> cmdiFiles = new ArrayList<>(
                    Arrays.asList(Path.of(sipPath.toString(), "metadata").toFile()
                            .listFiles()).stream()
                            .filter((f) -> f.toString().toLowerCase().endsWith(".cmdi"))
                            .collect(Collectors.toList()));
            if (cmdiFiles.size() != 1) {
                throw new IOException("Wrong number of cmid files. Should be 1 but was " + String.valueOf(cmdiFiles.size()));
            }
            Metadata metadata = CMDI.readCmdiMetadata(new SAXBuilder().build(cmdiFiles.get(0)));
            HashMap<String,File> sipFiles = new HashMap<>();
            for (File f : FileUtils.listFiles(sipPath.toFile(), FileFileFilter.FILE, DirectoryFileFilter.DIRECTORY)) {
                String fn = f.getAbsolutePath().replace(sipPath.toAbsolutePath().toString(), "").replaceAll("/", SEPARATOR);
                sipFiles.put(fn, f);
            }
            DraftRecord draftRecord = new DraftRecord(
                    new Access(Access.AccessType.Public, Access.AccessType.Public),
                    new FilesOptions(true),
                    metadata);
            Record created = api.createDraftRecord(draftRecord);
            //LOG.info(om.writeValueAsString(created));
            ArrayList<Files.FileEntry> entries = new ArrayList();
            for (String fn :sipFiles.keySet()) {
                entries.add(new Files.FileEntry(URLEncoder.encode(fn, StandardCharsets.UTF_8.toString())));
            }
            Files files = api.startDraftFileUpload(created.id, entries);
            //LOG.info(om.writeValueAsString(files));
            for (HashMap.Entry<String, File> sipFile : sipFiles.entrySet()) {
                // LOG.info(sipFile.getKey());
                Files.FileEntry entry = api.uploadDraftFile(created.id, sipFile.getKey(), sipFile.getValue());
                //LOG.info(om.writeValueAsString(entry));
                Files.FileEntry completed = api.completeDraftFileUpload(created.id, sipFile.getKey());
                //LOG.info(om.writeValueAsString(completed));
            }
            LOG.log(Level.INFO, "New record id: {0}", created.id);
            return created.id;
        }
        
        /**
         * Uploads all sips (i.e.subfolders) in a certain path
         * @param sipPath The path to the folder containing the sips
         * @throws java.io.IOException
         * @throws org.jdom2.JDOMException
         * @throws java.net.URISyntaxException
         * @throws java.security.NoSuchAlgorithmException
         * @throws java.io.UnsupportedEncodingException
         * @throws java.lang.InterruptedException
         * @throws java.io.FileNotFoundException
         * @throws com.fasterxml.jackson.core.JsonProcessingException
         * @throws java.security.KeyManagementException
         */
        public void uploadDraftSips(Path sipPath) throws IOException, JDOMException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException, FileNotFoundException, JsonProcessingException, UnsupportedEncodingException {
            for (File sip : sipPath.toFile().listFiles()) {
                if (sip.isDirectory()) {
                    LOG.log(Level.INFO, "Uploading {0}", sip.toString());
                    uploadDraftSip(sip.toPath());
                }
            }
        }
        
        /**
         * Downloads a draft SIP to a destination 
         * @param id The Invenio id
         * @param destinationPath the path where the sip will be stored
         * @throws java.io.IOException
         * @throws org.jdom2.JDOMException
         * @throws java.net.URISyntaxException
         * @throws java.security.NoSuchAlgorithmException
         * @throws java.io.UnsupportedEncodingException
         * @throws java.lang.InterruptedException
         * @throws java.io.FileNotFoundException
         * @throws com.fasterxml.jackson.core.JsonProcessingException
         * @throws java.security.KeyManagementException
         */
        public void downloadDraftSip(String id, Path destinationPath) throws IOException, JDOMException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException, FileNotFoundException, JsonProcessingException, UnsupportedEncodingException {
            Files files = api.listDraftFiles(id);
            if (files.entries.isLeft()) {
                for (Files.FileEntry fe : files.entries.getLeft()) {
                    downloadSipFile(id,fe.key,destinationPath);
                }
            }
            else {
                for (String key : files.entries.get().keySet())
                    downloadSipFile(id, key, destinationPath);
            }
        }
        
        /**
         * Helper to download a sip file
         * @param id the draft id
         * @param name the file name
         * @param destination the download destination
         * @throws UnsupportedEncodingException
         * @throws IOException
         * @throws URISyntaxException
         * @throws NoSuchAlgorithmException
         * @throws KeyManagementException
         * @throws JsonProcessingException
         * @throws InterruptedException 
         */
        private void downloadSipFile(String id, String name, Path destination) throws UnsupportedEncodingException, IOException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, JsonProcessingException, InterruptedException {
            String decodedName = URLDecoder.decode(name, StandardCharsets.UTF_8.toString());
            File file = Path.of(Path.of(destination.toString(),id).toString(),
                            decodedName.split(SEPARATOR)).toFile();
            FileUtils.forceMkdirParent(file);
            LOG.log(Level.INFO, "Downloading file {0}", file);
            api.getDraftFileContent(id,decodedName).transferTo(new FileOutputStream(file));
        }
        
        /**
         * Deletes a draft sip and all associated files
         * @param id the draft id
         * @throws java.io.IOException
         * @throws org.jdom2.JDOMException
         * @throws java.net.URISyntaxException
         * @throws java.security.NoSuchAlgorithmException
         * @throws java.io.UnsupportedEncodingException
         * @throws java.lang.InterruptedException
         * @throws java.io.FileNotFoundException
         * @throws com.fasterxml.jackson.core.JsonProcessingException
         * @throws java.security.KeyManagementException
         */
        public void deleteDraftSip(String id) throws IOException, JDOMException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException, FileNotFoundException, JsonProcessingException, UnsupportedEncodingException {
            Files files = api.listDraftFiles(id);
            if (files.entries.isLeft()) {
                for (Files.FileEntry fe : files.entries.getLeft()) {
                    api.deleteDraftFile(id, fe.key);
                }
            }
            else {
                for (String key : files.entries.get().keySet()) {
                    api.deleteDraftFile(id, key);
                }
            }
            //api.deleteDraftRecord(id);
        }
        
        /**
         * Publish a draft SIP
         * @param id the draft id
         * @throws java.io.IOException
         * @throws org.jdom2.JDOMException
         * @throws java.net.URISyntaxException
         * @throws java.security.NoSuchAlgorithmException
         * @throws java.io.UnsupportedEncodingException
         * @throws java.lang.InterruptedException
         * @throws java.io.FileNotFoundException
         * @throws com.fasterxml.jackson.core.JsonProcessingException
         * @throws java.security.KeyManagementException
         */
        public void publishDraftSip(String id) throws IOException, JDOMException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException, FileNotFoundException, JsonProcessingException, UnsupportedEncodingException {
            api.publishDraftRecord(id);
        }
        
        /**
         * Delete all draft sips
         * @throws java.io.IOException
         * @throws org.jdom2.JDOMException
         * @throws java.net.URISyntaxException
         * @throws java.security.NoSuchAlgorithmException
         * @throws java.io.UnsupportedEncodingException
         * @throws java.lang.InterruptedException
         * @throws java.io.FileNotFoundException
         * @throws com.fasterxml.jackson.core.JsonProcessingException
         * @throws java.security.KeyManagementException
         */
        public void deleteAllDrafts() throws IOException, JDOMException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException, FileNotFoundException, JsonProcessingException, UnsupportedEncodingException {
            Records userRecords = api.listUserRecords(); // api.searchRecords(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
            for (Record r : userRecords.hits.hits) {
                if (!r.isPublished) { // Tried to use isDraft but doesn't seem to be working
                    LOG.log(Level.INFO, "Deleting {0} - {1} - {2}", new Object[]{r.id, r.metadata.title, r.isDraft});
                    api.deleteDraftRecord(r.id);
                }
                    
            }
        }
}
