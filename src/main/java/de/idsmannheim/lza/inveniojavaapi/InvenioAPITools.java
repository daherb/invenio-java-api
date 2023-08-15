/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;
import javax.xml.bind.DatatypeConverter;
import net.sf.saxon.s9api.SaxonApiException;


/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class InvenioAPITools {
    
    private final InvenioAPI api;
    // private final String url;
    
    // Internal list keeping track of drafts
    private ArrayList<String> draftList = new ArrayList<>();
    
    private static final Logger LOG = Logger.getLogger(InvenioAPITools.class.getName());
    
    /**
     * Default constructor
     * @param api The API object to be used
     * @throws java.io.IOException
     */
    public InvenioAPITools(InvenioAPI api) throws IOException {
        // this.languageIdFactory = new ControlledVocabulary.LanguageIdFactory();
        this.api = api;
//        url = api.protocol + "://" + api.host + "/records/";
    }
    
    /**
     * Lists the id of all of the current users draft records
     * @return the list of draft record ids
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws java.net.URISyntaxException
     * @throws java.security.KeyManagementException
     * @throws java.security.NoSuchAlgorithmException
     */
    public List<String> listDraftRecords() throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        // First list records to get the number of all records
        Records records = api.listUserRecords(Optional.empty(),Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        // Now list all the records
        records = api.listUserRecords(Optional.empty(),Optional.empty(), Optional.of(records.getHits().getTotal()), Optional.empty(), Optional.empty());
        // Get all record ids
        ArrayList<String> ids = new ArrayList<>();
        Records.Hits hits = records.getHits();
        // Only filter unpublished ones
        for (Record hit : hits.getHits()) {
            // Check if it is unpublished -> draft
            if (!hit.isPublished()) {
                ids.add(hit.getId());
            }
        }
        return ids;
    }
    
    /**
     * Delete all unpublished draft records
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws InterruptedException
     */
    public void deleteDraftRecords() throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        for (String id : listDraftRecords()) {
            LOG.log(Level.INFO, "Deleting {0}", id);
            api.deleteDraftRecord(id);
        }
    }
    
    /**
     * Gets the record id for the record matching the title
     * @param recordTitle the record title
     * @return the record id
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     * @throws java.net.URISyntaxException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     */
    public String getRecordIdForTitle(String recordTitle) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        Records matches = api.listUserRecords(Optional.of("title:" + recordTitle), Optional.empty(), Optional.of(1), Optional.empty(), Optional.of(false));
        if (matches.getHits().getHits().size() == 1) {
            return matches.getHits().getHits().get(0).getId();
        }
        else {
            throw new IllegalArgumentException("Title matches no or multiple records: " + recordTitle);
        }
    }
    
        /**
     * Finds a unique record with a given title
     * @param title the record title
     * @return the record id if it exists
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public Optional<String> findRecordByTitle(String title) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        Records matches = api.listUserRecords(Optional.of("metadata.title:\"" +title+ "\""), Optional.empty(), Optional.of(1), Optional.empty(), Optional.empty());
        if (matches.getHits().getHits().size() == 1 && matches.getHits().getHits().get(0).getMetadata().getTitle().equals(title)) {
            return Optional.of(matches.getHits().getHits().get(0).getId());
        }
        else if (matches.getHits().getHits().isEmpty() || !matches.getHits().getHits().get(0).getMetadata().getTitle().equals(title)) {
            return Optional.empty();
        }
        else {
            throw new IllegalArgumentException("More than one record found matching title");
        }
    }
    
    /**
     * Download one specific file from a record to an output path recovering the original path separator
     *
     * @param recordId the record id
     * @param fileEntry the file entry
     * @param outputPath the output path
     * @param separator the temporary path separator used when uploading
     * @return true if the download was successful and false otherwise
     */
    public boolean downloadFile(String recordId, Files.FileEntry fileEntry, Path outputPath, String separator) {
        try {
            String fileName = fileEntry.getKey();
            File outputFile = Path.of(outputPath.toString(), fileName.replaceAll(separator, "/")).toFile();
            // Create directories if necessary
            outputFile.getParentFile().mkdirs();
            // Download file
            api.getRecordFileContent(recordId, fileName).transferTo(new FileOutputStream(outputFile));
            if (validateChecksum(outputFile, fileEntry.getChecksum())) {
                LOG.info("Downloaded file " + outputFile);
                return true;
            }
            else {
                LOG.severe("Failed validate download " + outputFile);
                return false;
            }
        }
        catch (Exception e) {
            LOG.severe("Exception when downloading file " + fileEntry.getKey());
            return false;
        }
    }
    
    /**
     * Computes the checksum for a file and compares it to a given checksum
     * @param file the file
     * @param checksum the checksum of the form `algorithm:checksum`
     * @return if the checksums match
     * @throws NoSuchAlgorithmException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public boolean validateChecksum(File file, String checksum) throws NoSuchAlgorithmException, FileNotFoundException, IOException {
        String[] parts = checksum.split(":");
        // Match the digest algorithm with the one specified in the beginning of the provided checksum
        MessageDigest md = MessageDigest.getInstance(parts[0]);
        md.reset();
        md.update(new FileInputStream(file).readAllBytes());
        String newSum = parts[0]+":" + DatatypeConverter.printHexBinary(md.digest());
        return newSum.equalsIgnoreCase(checksum);
    }
    
    /***
     * Helper to check if an id is a draft based on an internal draft list
     * which can be updated using updateDraftList
     * @param id the id of the record
     * @return if it is a draft
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public boolean isDraft(String id) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        if (draftList.isEmpty()) {
            updateDraftList();
        }
        return draftList.contains(id);
    }
    
    /***
     * Updates the internal draft list used by isDraft
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private void updateDraftList() throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        draftList = new ArrayList<>(listDraftRecords());
    }
    
    /***
     * Upload files to a draft record replacing the path separator to avoid issues with URLs
     * @param id the draft record id
     * @param path the path the files are located in
     * @param fileNames the names of the files to be uploaded
     * @param separator replacement for the path separator
     */
    public void uploadDraftFiles(String id, Path path, ArrayList<String> fileNames, String separator) throws URISyntaxException, NoSuchAlgorithmException, NoSuchAlgorithmException, KeyManagementException, KeyManagementException, JsonProcessingException, IOException, InterruptedException, UnsupportedEncodingException {
        ArrayList<Files.FileEntry> entries = new ArrayList<>();
        HashMap<String,File> fileMap = new HashMap<>();
        for (String filename : fileNames) {
            // Normalize filename and replace path separators
            String updatedName = normalizeFilename(filename, separator);
            Files.FileEntry entry = new Files.FileEntry(updatedName);
            entries.add(entry);
            fileMap.put(updatedName, Path.of(path.toString(),filename).toFile().getAbsoluteFile().getCanonicalFile());
            
        }
        api.startDraftFileUpload(id, entries);
        // For each file
        for (String key : fileMap.keySet()) {
            // Upload file
            LOG.log(Level.INFO, "Uploading {0}", fileMap.get(key));
            // TODO change to using file url
            api.uploadDraftFile(id, key, fileMap.get(key).toURI());
            api.completeDraftFileUpload(id, key);
        }
    }
    
    /***
     * Normalizes a filename before upload i.e. replace the path separator by
     * some other string to avoid problems with URLs
     * @param filename the original filename
     * @param separator the replacement for / as the path separator
     * @return the filename used in Invenio
     */
    public String normalizeFilename(String filename, String separator) {
        // Remove leading ./ (path relative to current location) from the filename
        // and replace path separators by the SEPARATOR string
        return filename.replaceAll("^./","").replaceAll("/", separator);
    }
    
    /**
     * Read the Invenio metadata from a CMDI file
     * @param cmdiFile the CMDI metadata file
     * @return the Invenio metadata
     * @throws IOException
     * @throws JDOMException
     */
    public Metadata readMetadata(File cmdiFile) throws IOException, JDOMException, IllegalArgumentException, SaxonApiException {
        // Read the CMDI file
        return CMDI.convertCmdiMetadata(CMDI.readCmdiFile(cmdiFile));
    }
}
