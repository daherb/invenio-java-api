/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package de.idsmannheim.lza.inveniojavaapi;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class Records {
    
    /**
     * Access (https://inveniordm.docs.cern.ch/reference/metadata/#access)
     * 
     * The access field denotes record-specific read (visibility) options.
     * The access field has this structure:
     *  ____________________________________________________________
     * |Field    | Cardinality | Description                        |
     * |---------+-------------+------------------------------------|
     * | record  | (1)         | "public" or "restricted".          |
     * |         |             |Read access to the record.          |
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
    public static class Access {
        
        enum AccessType { RESTRICTED, PUBLIC };
        
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
        public static class Embargo {
            
            boolean active;
            Optional<Date> until;
            Optional<String> reason;
            public Embargo(boolean active, Optional<Date> until, Optional<String> reason) {
                this.active = active;
                this.until = until;
                this.reason = reason;
            }
        }
        
        AccessType record;
        AccessType files;
        Optional<Embargo> embargo;
        
        public Access(AccessType record, AccessType files, Optional<Embargo> embargo) {
            this.record = record;
            this.files = files;
            this.embargo = embargo;
        }
    }
    
    /**
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
    public static class FileOption {
        
        boolean enabled;
        String default_preview;
        List<String> order;
        
        public FileOption(boolean enabled, String default_preview, List<String> order) {
            this.enabled = enabled;
            this.default_preview = default_preview;
            this.order.addAll(order);
        }
    }
    
    public void createDraft(Access access, FileOption files, Metadata metada) {
    }
}
