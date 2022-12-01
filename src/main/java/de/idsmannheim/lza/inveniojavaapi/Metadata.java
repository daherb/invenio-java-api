/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * Metadata (https://inveniordm.docs.cern.ch/reference/metadata/#metadata)
 *
 * The fields are listed below in the combined order of "required" and
 * "appearance on the deposit page". So required fields are listed first
 * and then other fields are listed in the order of their appearance on the
 * deposit page.
 *
 * The cardinality of each field is expressed in between parenthesis on the
 * title of each field's section. Cardinality here indicates if the field
 * is required by the REST API.
 *
 * The abbreviation CV stands for Controlled Vocabulary.
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Metadata {
    // The mandatory fields are initialized in the constructor
    @JsonProperty("resource_type")
    private final ResourceType resourceType;
    @JsonProperty("creators")
    private final List<Creator> creators;
    @JsonProperty("title")
    private final String title;
    @JsonProperty("publication_date")
    private final ExtendedDateTimeFormat0 publicationDate;
    // The rest is initialized with an empty value
    @JsonProperty("additional_titles")
    private final List<AdditionalTitle> additionalTitles = new ArrayList<>();
    @JsonProperty("description")
    private Optional<String> description = Optional.empty();
    @JsonProperty("additional_descriptions")
    private final List<AdditionalDescription> additionalDescriptions = new ArrayList<>();
    @JsonProperty("rights")
    private final List<License> rights = new ArrayList<>();
    @JsonProperty("contributors")
    private final List<Contributor> contributors = new ArrayList<>();
    @JsonProperty("subjects")
    private final List<Subject> subjects = new ArrayList<>();
    @JsonProperty("languages")
    private final List<Language> languages = new ArrayList<>();
    @JsonProperty("dates")
    private final List<RecordDate> dates = new ArrayList<>();
    @JsonProperty("version")
    private Optional<String> version = Optional.empty();
    @JsonProperty("publisher")
    private Optional<String> publisher = Optional.empty();
    @JsonProperty("alternative_identifiers")
    private final List<AlternateIdentifier> alternativeIdentifiers = new ArrayList<>();
    @JsonProperty("related_identifiers")
    private final List<RelatedIdentifier> relatedIdentifiers = new ArrayList<>();
    @JsonProperty("sizes")
    private final List<String> sizes = new ArrayList<>();
    @JsonProperty("formats")
    private final List<String> formats = new ArrayList<>();
    @JsonProperty("locations")
    private final List<Location> locations = new ArrayList<>();
    @JsonProperty("funding")
    private final List<FundingReference> fundingReferences = new ArrayList<>();
    @JsonProperty("references")
    private final List<Reference> references = new ArrayList<>();
    
    /**
     * Resource type (1)¶
     *
     * The type of the resource described by the record. The resource type
     * must be selected from a controlled vocabulary which can be
     * customized by each InvenioRDM instance.
     *
     * When interfacing with Datacite, this field is converted to a format
     * compatible with 10. Resource Type (i.e. type and subtype). DataCite
     * allows free text for the subtype, however InvenioRDM requires this
     * to come from a customizable controlled vocabulary.
     *
     * The resource type vocabulary also defines mappings to other
     * vocabularies than Datacite such as Schema.org, Citation Style
     * Language, BibTeX, and OpenAIRE. These mappings are very important
     * for the correct generation of citations due to how different types
     * are being interpreted by reference management systems.
     *
     * Subfields:
     *  ____________________________________________________
     * |Field | Cardinality | Description                   |
     * |------+-------------+-------------------------------|
     * | id   | (1, CV)     | The resource type id from the |
     * |      |             | controlled vocabulary.        |
     *  ----------------------------------------------------
     */
    public static class ResourceType {
        @JsonProperty("id")
        ControlledVocabulary.ResourceType id;
        
        @JsonCreator
        public ResourceType(ControlledVocabulary.ResourceType id) {
            this. id = id;
        }

        public ControlledVocabulary.ResourceType getId() {
            return id;
        }

        
        @Override
        public String toString() {
            return "ResourceType{" + "id=" + id + '}';
        }
        
        
    }
    
    /**
     * Creators (1-n) (https://inveniordm.docs.cern.ch/reference/metadata/#creators-1-n)
     *
     * The creators field registers those persons or organisations that
     * should be credited for the resource described by the record. The list
     * of persons or organisations in the creators field is used for
     * generating citations, while the persons or organisations listed in
     * the contributors field are not included in the generated citations.
     *
     * The field is compatible with 2. Creator in DataCite. In addition we
     * are adding the possiblity of associating a role (like for contributors).
     * This is specifically for cases where e.g. an editor needs to be
     * credited for the work, while authors of individual articles will be
     * listed under contributors.
     *
     * Subfields:
     *  __________________________________________________________________
     * |Field          | Cardinality | Description                        |
     * |---------------+-------------+------------------------------------|
     * | person_or_org | (1)         | The person or organization.        |
     * | role          | (0-1, CV)   | The role of the person or          |
     * |               |             | organisation selected from a       |
     * |               |             | customizable controlled vocabulary.|
     * | affiliations  | (0-n)       | Affilations if person_or_org.type  |
     * |               |             | is personal.                       |
     *  ------------------------------------------------------------------
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class Creator {
        
        @JsonProperty("person_or_org")
        PersonOrOrg personOrOrg;
        @JsonProperty("roles")
        Optional<ControlledVocabulary.Role> role = Optional.empty();
        @JsonProperty("affiliations")
        ArrayList<Affiliation> affiliations = new ArrayList<>();
        
        public Creator(PersonOrOrg personOrOrg, Optional<ControlledVocabulary.Role> role, List<Affiliation> affiliations) {
            this.personOrOrg = personOrOrg;
            this.role = role;
            this.affiliations.addAll(affiliations);
        }
    }
    
    /**
     * A person_or_org is described with the following subfields:
     *  ___________________________________________________________________
     * | Field       | Cardinality     | Description                       |
     * |-------------+-----------------+-----------------------------------|
     * | type        | (1)             | The type of name. Either personal |
     * |             |                 | or organizational.                |
     * | given_name  |(1 if type is    |Given name(s).                     |
     * |             | personal / 0 if |                                   |
     * |             | type is         |                                   |
     * |             | organizational) |                                   |
     * | family_name | (1 if type is   | Family name.                      |
     * |             | personal / 0 if |                                   |
     * |             | type is         |                                   |
     * |             | organizational) |                                   |
     * | name        | (0 if type is   | The full name of the organisation.|
     * |             | personal / 1 if | For a person, this field is       |
     * |             | type is         | generated from given_name and     |
     * |             | organizational) | family_name                       |
     * | identifiers | (0-n)           | Person or organisation            |
     * |             |                 | identifiers.                      |
     *  -------------------------------------------------------------------
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class PersonOrOrg {
        public enum Type { Personal, Organizational };

        /**
         * Identifiers are described with the following subfields (note, we only support one identifier per scheme):
         *  ____________________________________________________________
         * | Term       | Cardinality | Description                     |
         * |------------+-------------+---------------------------------|
         * | scheme     | (1, CV)     | The identifier scheme.          |
         * | identifier | (1)         | Actual value of the identifier. |
         *  ------------------------------------------------------------
         */
        public static class Identifier {
            @JsonProperty("scheme")
            ControlledVocabulary.PersonOrOrgIdentifierScheme scheme;
            @JsonProperty("id")
            String identifier;

            public Identifier(ControlledVocabulary.PersonOrOrgIdentifierScheme scheme, String identifier) {
                this.scheme = scheme;
                this.identifier = identifier;
            }
            
            
        }
        
        PersonOrOrg.Type type;
        @JsonProperty("given_name")
        String givenName;
        @JsonProperty("family_name")
        String familyName;
        @JsonProperty("name")
        String name;
        @JsonProperty("identifiers")
        ArrayList<Identifier> identifiers = new ArrayList<>();
        
        @JsonProperty("type")
        public String getType() {
            return type.toString().toLowerCase();
        }
        /**
         * Constructor for a person, automatically sets the type to personal and concatenates names in name field
         * @param givenName given name of the person
         * @param familyName family name of the person
         * @param identifiers list of identifiers
         */
        public PersonOrOrg(String givenName, 
                String familyName, List<Identifier> identifiers) {
            this.type = Type.Personal;
            if (givenName == null || givenName.isBlank() || 
                        familyName == null || familyName.isBlank()) {
                    // That's a bad idea but we didn't make the rules
                    throw new IllegalArgumentException
                            ("Given and Family name have to be present for a person");
                }
                this.givenName = givenName;
                this.familyName = familyName;
                this.name = familyName + ", " + givenName;
                this.identifiers.addAll(identifiers);
            }
        /**
         * Constructor for a person with an explicit name parameter
         * @param givenName given name of the person
         * @param familyName family name of the person
         * @param name complete name of the person
         * @param identifiers list of identifiers
         */
        public PersonOrOrg(String givenName, 
                String familyName, String name, List<Identifier> identifiers) {
            this(givenName, familyName, identifiers);
            this.name = name;
        }
        
        public PersonOrOrg(String name, List<Identifier> identifiers){
                if (name == null || name.isBlank()) {
                    throw new IllegalArgumentException
                            ("Name has to be present for an organization");
                }
            this.name = name;
            this.identifiers.addAll(identifiers);
        }
        
    }
    
    /**
     * The affiliations field consists of objects with the following subfields:
     *  ____________________________________________________________________
     * | Field | Cardinality | Description                                  |
     * |-------+-------------+----------------------------------------------|
     * | id    | (0-1, CV)   | The organizational or institutional id       |
     * |       |             | from the controlled vocabulary.              |
     * | name  | (0-1)       | The name of the organisation or institution. |
     *  --------------------------------------------------------------------
     * 
     * One of id or name must be given. It's recommended to use name if there 
     * is no matching id in the controlled vocabulary.
     */
    public static class Affiliation {
        @JsonProperty("id")
        Optional<ControlledVocabulary.OrganizationalOrInstitutionalId> id = Optional.empty();
        @JsonProperty("name")
        Optional<String> name = Optional.empty();

        public Affiliation(Optional<ControlledVocabulary.OrganizationalOrInstitutionalId> id, Optional<String> name) {
            if (id.isEmpty() && name.isEmpty()) {
                throw new IllegalArgumentException
                        ("Either id or name have to be present for an affiliation");
            }
            this.id = id;
            this.name = name;
        }
    }
    /**
     * The string must be be formatted according to Extended Date Time Format
     * (EDTF) Level 0. Only "Date" and "Date Interval" are supported. "Date and
     * Time" is not supported. The following are examples of valid values:
     * 
     * Date:
     * - 2020-11-10 - a complete ISO8601 date.
     * - 2020-11 - a date with month precision
     * - 2020 - a date with year precision
    Date Interval:
    * - 1939/1945 a date interval with year precision, beginning sometime in 1939 and ending sometime in 1945.
    * - 1939-09-01/1945-09 a date interval with day and month precision, beginning September 1st, 1939 and ending sometime in September 1945.
    * 
    * The localization (L10N) of EDTF dates is based on the skeletons defined by
    * the Unicode Common Locale Data Repository (CLDR).
     */
    public static class ExtendedDateTimeFormat0 {
        String startYear;
        Optional<String> startMonth = Optional.empty();
        Optional<String> startDay = Optional.empty();
        Optional<String> endYear = Optional.empty();
        Optional<String> endMonth = Optional.empty();
        Optional<String> endDay = Optional.empty();

        public ExtendedDateTimeFormat0(String startYear) {
            this.startYear = startYear;
        }

        public ExtendedDateTimeFormat0 addStartMonth(String startMonth) {
            this.startMonth = Optional.of(startMonth);
            return this;
        }
        
        public ExtendedDateTimeFormat0 addStartDay(String startDay) {
            if (startMonth.isEmpty())
                throw new IllegalArgumentException
                    ("Start month has to be present before adding start day");
            this.startDay = Optional.of(startDay);
            return this;
        }
        
        public ExtendedDateTimeFormat0 addEndYear(String endYear) {
            this.endYear = Optional.of(endYear);
            return this;
        }
        
        public ExtendedDateTimeFormat0 addEndMonth(String endMonth) {
            if (endYear.isEmpty())
                throw new IllegalArgumentException
                    ("End year has to be present before adding end month");
            this.endMonth = Optional.of(endMonth);
            return this;
        }
        
        public ExtendedDateTimeFormat0 addEndDay(String endDay) {
            if (endYear.isEmpty())
                throw new IllegalArgumentException
                    ("End year has to be present before adding end day");
            if (endMonth.isEmpty())
                throw new IllegalArgumentException
                    ("End month has to be present before adding end day");
            this.endDay = Optional.of(endDay);
            return this;
        }

        @JsonValue
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(startYear);
            if (startMonth.isPresent()) {
                sb.append("-");
                sb.append(startMonth.get());
            }
            if (startDay.isPresent()) {
                sb.append("-");
                sb.append(startDay.get());
            }
            if (endYear.isPresent()) {
                sb.append("/");
                sb.append(endYear.get());
            }
            if (endMonth.isPresent()) {
                sb.append("-");
                sb.append(endMonth.get());
            }
            if (endDay.isPresent()) {
                sb.append("-");
                sb.append(endDay.get());
            }
            return sb.toString();
        }
    }
    
        
    /**
     * The lang field is as follows:
     *  ____________________________________________________
     * | Field | Cardinality | Description                  |
     * |-------+-------------+------------------------------|
     * | id    | (1, CV)     | The ISO-639-3 language code. |
     *  ----------------------------------------------------
     */
    public static class Language {
        
        ControlledVocabulary.Language id;

        public Language(ControlledVocabulary.Language id) {
            this.id = id;
        }
    }
    
    public static class LocalizedString {
        Language lang;
        String string;

        public LocalizedString(Language lang, String string) {
            this.lang = lang;
            this.string = string;
        }
        
    }
    /**
     * Additional titles (0-n)¶
     * 
     * Additional names or titles by which a resource is known.
     * 
     * The field is compatible with 3. Title in DataCite. Compared to DataCite, InvenioRDM splits 3. Title into two separate fields title and additional_titles. This is to ensure consistent usage of a record's title throughout the entire software stack for display purposes.
     * 
     * Subfields:
     *  __________________________________________________
     * | Field | Cardinality | Description                |
     * |-------+-------------+----------------------------|
     * | title | (1)         | The additional title.      |
     * | type  | (1)         | The type of the title.     |
     * | lang  | (0-1, CV)   | The language of the title. |
     *  --------------------------------------------------
     */
    public static class AdditionalTitle {
        
        String title;

        /**
         * The type field is as follows:
         *  __________________________________________________________________
         * | Field | Cardinality | Description                                |
         * |-------+-------------+--------------------------------------------|
         * | id    | (1, CV)     | Title type id from the controlled          |
         * |       |             | vocabulary. By default one of:             |
         * |       |             | alternative-title, subtitle,               |
         * |       |             | translated-title or other.                 |
         * | title | (1)         | The corresponding localized human readable |
         * |       |             | label. e.g. {"en": "Alternative title"}    |
         *  ------------------------------------------------------------------
         *
         * Note that multiple localized titles are supported e.g. {"en":
         * "Alternative title", "fr": "Titre alternatif"}. Use ISO 639-1 codes
         * (2 letter locales) as keys. Only id needs to be passed on the REST
         * API.
         */
        public static class TitleType {
            ControlledVocabulary.TitleTypeId id;
            List<LocalizedString> title = new ArrayList<>();
            
            public TitleType(ControlledVocabulary.TitleTypeId id, List<LocalizedString> titles) throws IllegalAccessException {
                this.id = id;
                if (!titles.isEmpty())
                    this.title.addAll(titles);
                else
                    throw new IllegalAccessException("At least one title required");
            }
        }
        
        TitleType type;
        /**
         * The lang field is as follows:
         *  ____________________________________________________
         * | Field | Cardinality | Description                  |
         * |-------+-------------+------------------------------|
         * | id    | (1, CV)     | The ISO-639-3 language code. |
         *  ----------------------------------------------------
         */
        Optional<Language> lang = Optional.empty();

        public AdditionalTitle(String title, TitleType type, Optional<Language> lang) {
            this.title = title;
            this.type = type;
            this.lang = lang;
        }
    }
    
    /**
     * Additional descriptions (0-n)¶
     * 
     * Additional descriptions in addition to the primary description (e.g. 
     * abstracts in other languages), methods or further notes.
     * 
     * The field is compatible with 17. Description in DataCite.
     * 
     * Subfields:
     *  ______________________________________________________________
     * | Field       | Cardinality | Description                      |
     * |-------------+-------------+----------------------------------|
     * | description | (1)         | Free text.                       |
     * | type        | (1)         | The type of the description.     |
     * | lang        | (0-1)       | The language of the description. |
     *  --------------------------------------------------------------
     */
    public static class AdditionalDescription {
        String description;
        /**
         * The type field is as follows:
         *  __________________________________________________________________
         * | Field | Cardinality | Description                                |
         * |-------+-------------+--------------------------------------------|
         * | id    | (1, CV)     | Description type id from the controlled    |
         * |       |             | vocabulary. By default one of: abstract,   |
         * |       |             | methods, series-information,               |
         * |       |             | table-of-contents, technical-info, other.  |
         * | title | (1)         | The corresponding localized human readable |
         * |       |             | label. e.g. {"en": "Abstract"}             |
         *  ------------------------------------------------------------------
         * 
         * Note that multiple localized titles are supported e.g. 
         * {"en": "Table of contents", "fr": "Table des matières"}. 
         * Use ISO 639-1 codes (2 letter locales) as keys. Only id needs to be
         * passed on the REST API.
         */
        public static class DescriptionType {
            ControlledVocabulary.DescriptionTypeId id;
            List<LocalizedString> title = new ArrayList<>();

            public DescriptionType(ControlledVocabulary.DescriptionTypeId id, List<LocalizedString> title) {
                this.id = id;
                if (!title.isEmpty())
                    this.title.addAll(title);
                else
                    throw new IllegalArgumentException("At least one title required");
            }
            
            
        }
        DescriptionType type;
        /**
         * The lang field is as follows:
         *  ____________________________________________________
         * | Field | Cardinality | Description                  |
         * |-------+-------------+------------------------------|
         * | id    | (1, CV)     | The ISO-639-3 language code. |
         *  ----------------------------------------------------
         */
        Language lang;
    }
    
    /**
     * Rights (Licenses) (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#rights-licenses-0-n)
     *
     * Rights management statement for the resource.
     *
     * When interfacing with Datacite, this field is converted to be
     * compatible with 16. Rights.
     *
     * The rights field is intended to primarily be linked to a
     * customizable vocabulary of licenses (defaults SPDX). It should
     * however also be possible to provide custom rights statements.
     *
     * The rights statements does not have any impact on access control
     * to the resource.
     *
     * Subfields:
     *  ________________________________________________________________
     * | Field       | Cardinality | Description                        |
     * |-------------+-------------+------------------------------------|
     * | id          | (0-1)       | Identifier value                   |
     * | title       | (0-1)       | Localized human readable title     |
     * |             |             | e.g., {"en": "The ACME Corporation |
     * |             |             |License."}.                         |
     * | description | (0-1)       | Localized license description text |
     * |             |             | e.g., {"en": "This license..."}.   |
     * | link        | (0-1)       | Link to full license.              |
     *  ----------------------------------------------------------------
     * 
     * REST API:
     * 
     * Either id or title must be passed, but not both.
     */
    public static class License {
        Optional<String> id = Optional.empty();
        Optional<LocalizedString> title = Optional.empty();
        Optional<LocalizedString> description = Optional.empty();
        Optional<URL> link = Optional.empty();
        
        public License(String id, Optional<LocalizedString> title,
            Optional<LocalizedString> description, Optional<URL> link) {
            this.id = Optional.of(id);
            this.title = title;
            this.description = description;
            this.link = link;
        }
        
        public License(Optional<String> id, LocalizedString title,
            Optional<LocalizedString> description, Optional<URL> link) {
            this.id = id;
            this.title = Optional.of(title);
            this.description = description;
            this.link = link;
        }
    }
    
    /**
     * Contributors (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#contributors-0-n)
     *
     * The organisations or persons responsible for collecting, managing,
     * distributing, or otherwise contributing to the development of the
     * resource.
     *
     * This field is compatible with 7. Contributor in DataCite.
     *
     * The structure is identical to the Creators field. The "creators"
     * field records those persons or organisations that should be
     * credited for the resource described by the record (e.g. for
     * citation purposes). The "contributors" field records all other
     * persons or organisations that have contributed, but which should
     * not be credited for citation purposes.
     * 
     * Subfields:
     *  _______________________________________________________________________
     * | Field         | Cardinality | Description                             |
     * |---------------+-------------+-----------------------------------------|
     * | person_or_org | (1)         | The person or organization.             |
     * | role          | (1, CV)     | The role of the person or organisation  |
     * |               |             | selected from a customizable controlled |
     * |               |             | vocabulary.                             |
     * | affiliations  | (0-n)       | Affilations if person_or_org.type is    |
     * |               |             | personal.                               |
     *  -----------------------------------------------------------------------
   
    * 
    * Note that Creators and Contributors may use different controlled
    * vocabularies for the role field.
     */
    public static class Contributor {
        
        PersonOrOrg personOrOrg;
        ControlledVocabulary.Role role;
        List<Affiliation> affiliations = new ArrayList<>();

        public Contributor(PersonOrOrg personOrOrg, ControlledVocabulary.Role role, List<Affiliation> affiliations) {
            this.personOrOrg = personOrOrg;
            this.role = role;
            if (personOrOrg.type == personOrOrg.type.Personal) {
                this.affiliations.addAll(affiliations);
            }
        }
        
    }
    
    /**
     * Subjects (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#subjects-0-n)
     * 
     * Subject, keyword, classification code, or key phrase describing the resource.
     * 
     * This field is compatible with 6. Subject in DataCite.
     * 
     * Subfields:
     *  ________________________________________________________________
     * | Field   | Cardinality | Description                            |
     * | id      | (0-1, CV)   | The identifier of the subject from the |
     * |         |             | controlled vocabulary.                 |
     * | subject | (0-1)       | A custom keyword.                      |
     *  ----------------------------------------------------------------
     * 
     * Either id or subject must be passed.
     */
    public static class Subject {
        Optional<URL> id = Optional.empty();
        Optional<String> subject = Optional.empty();

        public Subject(URL id) {
            this.id = Optional.of(id);
        }

        public Subject(String subject) {
            this.subject = Optional.of(subject);
        }
    }
    /**
     * Dates (0-n)¶
     * 
     * Different dates relevant to the resource.
     * 
     * The field is compatible with 8. Date in DataCite.
     * 
     * Subfields:
     * 
     *  _______________________________________________________________________
     * | Field       | Cardinality | Description                               |
     * | date        | (1)         | A date or time interval according to      |
     * |             |             | Extended Date Time Format Level 0.        |
     * | type        | (1)         | The type of date.                         |
     * | description | (0-1)       | free text, specific information about the |
     * |             |             | date.                                     |
     *  -----------------------------------------------------------------------
     * 
     * Example:
     * {
     *   "dates": [{
     *       "date": "1939/1945",
     *       "type": {
     *       "id": "other",
     *       "title": {
     *         "en": "Other"
     *       }
     *     },
     *     "description": "A date"
     *   }],
     * }
     */
    
    public static class RecordDate {
        /**
        * The type field is as follows:
        *  _______________________________________________________________________
        * | Field | Cardinality | Description                                     |
        * | id    | (1, CV)     | Date type id from the controlled vocabulary. By |
        * |       |             | default one of: accepted, available, collected, |
        * |       |             | copyrighted, created, issued, other, submitted, |
        * |       |             | updated, valid, withdrawn.                      |
        * | title | (1)         | The corresponding localized human readable      |
        * |       |             | label. e.g. {"en": "Accepted"}                  |
        *  -----------------------------------------------------------------------
        * 
        * Note that multiple localized titles are supported e.g. 
        * {"en": "Available", "fr": "Disponible"}. Use ISO 639-1 codes (2 letter
        * locales) as keys. Only id needs to be passed on the REST API.
        * 
        */
        public static class DateType {
          ControlledVocabulary.DateTypeId id;
          List<LocalizedString> title = new ArrayList<>();

            public DateType(ControlledVocabulary.DateTypeId id, List<LocalizedString> title) {
                this.id = id;
                if (!title.isEmpty())
                    this.title.addAll(title);
                else 
                    throw new IllegalArgumentException("At least one title required");
            }
          
        }
        ExtendedDateTimeFormat0 date;
        DateType type;
        Optional<String> description = Optional.empty();

        public RecordDate(ExtendedDateTimeFormat0 date, DateType type, Optional<String> description) {
            this.date = date;
            this.type = type;
            this.description = description;
        }
    }
    /**
             * Alternate identifiers (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#alternate-identifiers-0-n)
             * 
             * Persistent identifiers for the resource other than the ones 
             * registered as system-managed internal or external persistent 
             * identifiers.
             * 
             * This field is compatible with 11. Alternate Identifiers in 
             * DataCite.
             * 
             * The main difference between the system-managed identifiers and 
             * this field, is that system-managed identifiers are fully 
             * controlled and managed by InvenioRDM, while identifiers listed
             * here are used solely for display purposes. For instance, a DOI
             * registered in the system-managed identifiers will prevent
             * another record with the same DOI from being created. A DOI
             * included in this field, will not prevent another record from
             * including the same DOI in this field.
             * 
             * Subfields:
             *  ________________________________________________________
             * |Field      | Cardinality | Description                  |
             * |-----------+-------------+------------------------------|
             * |identifier | (1)         | identifier value             |
             * |scheme     |(1, CV)      | The scheme of the identifier |
             *  --------------------------------------------------------
             * 
             * Supported identifier schemes:
             * 
             * ARK, arXiv, Bibcode, DOI, EAN13, EISSN, Handle, IGSN, ISBN, ISSN,
             * ISTC, LISSN, LSID, PubMed ID, PURL, UPC, URL, URN, W3ID. 
             * See RDM_RECORDS_IDENTIFIERS_SCHEMES in invenio-rdm-records.
             * 
             * Note that those are passed lowercased e.g., arXiv is arxiv.
             */
    public static class AlternateIdentifier {
        
        String identifier;
        ControlledVocabulary.RecordIdentifierScheme scheme;

        public AlternateIdentifier(String identifier, 
                ControlledVocabulary.RecordIdentifierScheme scheme) {
            this.identifier = identifier;
            this.scheme = scheme;
        }
    }
    
    /**
     * Related identifiers/works (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#related-identifiersworks-0-n)
     *
     * Identifiers of related resources.
     *
     * This field is compatible with 12. Related Identifiers in DataCite.
     * The field however does not support the subfields
     * 12.c relatedMetadataScheme, 12.d schemeURI and 12.e schemeType
     * used for linking to additional metadata.
     * 
     * Subfields:
     *  _____________________________________________________________________
     * | Field         | Cardinality | Description                           |
     * |---------------+-------------+---------------------------------------|
     * | identifier    | (1, CV)     | A global unique persistent identifier |
     * |               |             | for a related resource.               |
     * | scheme        | (1, CV)     | The scheme of the identifier.         |
     * | relation_type | (1)         | The relation of the record to this    |
     * |               |             | related resource.                     |
     * | resource_type |(0-1)        | The resource type of the related      |
     * |               |             | resource. Can be different from the   |
     * |               |             | Resource type field.                  |
     *  ---------------------------------------------------------------------
     * 
     * Supported identifier schemes:
     * 
     * ISBN10, ISBN13, ISSN, ISTC, DOI, Handle, EAN8, EAN13, ISNI ORCID, ARK,
     * PURL, LSID, URN, Bibcode, arXiv, PubMed ID, PubMed Central ID, GND, SRA,
     * BioProject, BioSample, Ensembl, UniProt, RefSeq, Genome Assembly.
     * 
     * The field relation_type is of this shape:
     *  ________________________________________________________________________
     * | Field | Cardinality | Description                                      |
     * |-------+-------------+--------------------------------------------------|
     * | id    |(1, CV)      | Relation type id from the controlled vocabulary. |
     * |       |             | The default list is here.                        |
     * | title |(1)          | The corresponding localized human readable       |
     * |       |             | label. e.g. {"en": "Is cited by"}                |
     *  ------------------------------------------------------------------------
     * 
     * The field resource_type is of this shape:
     *  ____________________________________________________________________
     * | Field | Cardinality |Description                                   |
     * |-------+-------------+----------------------------------------------|
     * | id    | (1, CV)     | Date type id from the controlled vocabulary. |
     * |       |             | The default list is here.                    |
     * | title | (1)         | The corresponding localized human readable   |
     * |       |             | label. e.g. {"en": "Annotation collection"}  |
     *  --------------------------------------------------------------------
     * 
     * For both relation_type.title and resource_type.title multiple localized 
     * titles are supported e.g. {"en": "Cites", "fr": "Cite"}. Use ISO 639-1 
     * codes (2 letter locales) as keys. In both cases, only id needs to be 
     * passed on the REST API.
     */
    public static class RelatedIdentifier {
        
        public static class RelationType {
            ControlledVocabulary.RelationTypeId id;
            List<LocalizedString> title = new ArrayList<>();
            
            public RelationType(ControlledVocabulary.RelationTypeId id, 
                    List<LocalizedString> titles) {
                this.id = id;
                if (!title.isEmpty())
                    this.title.addAll(title);
                else
                    throw new IllegalArgumentException("At least one title required");
            }
        }
        String identifier;
        ControlledVocabulary.RelatedRecordIdentifierScheme scheme;
        RelationType relationType;
        // Currently ignoring that it can this resource type can be different from ResourceType
        Optional<ResourceType> resourceType = Optional.empty();

        public RelatedIdentifier(String identifier, 
                ControlledVocabulary.RelatedRecordIdentifierScheme scheme, 
                RelationType relationType, 
                Optional<ResourceType> resourceType) {
            this.identifier = identifier;
            this.scheme = scheme;
            this.relationType = relationType;
            this.resourceType = resourceType;
        }
        
    }
    
    /**
     * Locations (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#locations-0-n)
     * 
     * ⚠️ Not part of the deposit page yet:
     * Although available via the API, this field may see changes when added to
     * the deposit page.
     * 
     * Spatial region or named place where the data was gathered or about which
     * the data is focused.
     * 
     * The field is compatible with 18. GeoLocation in DataCite Metadata Schema. 
     * The field however has important differences:
     * - The field allows associating geographical identifiers with a location.
     * - The field allows associating a free text description to the location.
     * - The field uses the GeoJSON specification for describing geospatial 
     *   coordinates instead of the individual fields used by DataCite.
     * 
     * The location fields is modelled over GeoJSON FeatureCollection and 
     * Feature objects using foreign members (for identifiers, place and 
     * description). We do not store the GeoJSON type information 
     * (e.g. "type": "FeatureCollection" and "type": "Feature") because this 
     * information is implicit. The JSON served on the REST API (external JSON),
     * will include the type information as well as "properties": null in order
     * to make it valid GeoJSON.
     * 
     * Subfields:
     *  ________________________________________________________________________
     * | Field    | Cardinality | Description                                   |
     * | features | (1)         | A list of GeoJSON feature objects. The reason |
     * |          |             | for this keyword is to align with GeoJSON and |
     * |          |             | allow for later expansion with other          |
     * |          |             | subfields such as bounding box (bbox) from    |
     * |          |             | the GeoJSON spec.                             |
     *  ------------------------------------------------------------------------
     * 
     * Subfields of items in features:
     *  ________________________________________________________________________
     * | Field       | Cardinality | Description                                |
     * |-------------+-------------+--------------------------------------------|
     * | geometry    | (0-1)       | A GeoJSON Geometry Object according to     |
     * |             |             | RFC 7946. Note, GeoJSON's coordinate       |
     * |             |             | reference system is WGS84.                 |
     * | identifiers | (0-1)       | A list of geographic location identifiers. |
     * |             |             | This could for instance be from GeoNames   |
     * |             |             | or Getty Thesaurus of Geographic Names     |
     * |             |             | (TGN) which would allow linking to         |
     * |             |             | historic places.                           |
     * | place       | (0-1)       | Free text, used to describe a geographical |
     * |             |             | location.                                  |
     * | description | (0-1)       | Free text, used for any extra information  |
     * |             |             | related to the location.                   |
     *  ------------------------------------------------------------------------
     * 
     * Identifier object in identifiers:
     *  _________________________________________________________________
     * | Field      | Cardinality | Description                          |
     * | identifier | (1, CV)     | A globally unique identifier for the |
     * |            |             | location.                            |
     * | scheme     | (1, CV)     | The scheme of the identifier.        |
     *  -----------------------------------------------------------------
     * 
     * Notes:
     * - Indexing: The geometry field is indexed as a geo_shape field type in 
     *   Elasticsearch which allows advanced geospatial querying. In addition,
     *   for each geometry object, we calculate the centroid using the Shapely
     *   library and index it as a geo_point field type in Elasticsearch which
     *   supports more specialised queries for points.
     * - Initially only Point objects will be supported in the upload form and
     *   landing page. This is primarily due to need for a user-friendly field.

     */
    public static class Location {
        
        public static class LocationIdentifier {
            String identifier; // This should be a controlled vocabulary but it is not defined
            String scheme;     // This should be a controlled vocabulary but it is not defined

            public LocationIdentifier(String identifier, String scheme) {
                this.identifier = identifier;
                this.scheme = scheme;
            }
        }
        public static class LocationFeature {
            Optional<String> geometry = Optional.empty();
            List<LocationIdentifier> identifiers;
            Optional<String> place = Optional.empty();
            Optional<String> description = Optional.empty();

            public LocationFeature(Optional<String> geometry, 
                    List<LocationIdentifier> identifiers, 
                    Optional<String> place,
                    Optional<String> description) {
                if (geometry.isPresent() || !identifiers.isEmpty() || 
                        place.isPresent() || description.isPresent()) {
                    this.geometry = geometry;
                    this.identifiers = identifiers;
                    this.place = place;
                    this.description = description;
                }
                else
                    throw new IllegalArgumentException("At least one location feature required");
            }
            
            
        }
        List<LocationFeature> features = new ArrayList<>();

        public Location(List<LocationFeature> features) {
            if (!features.isEmpty())
                this.features.addAll(features);
            else
                throw new IllegalArgumentException("At least one location feature required");
        }
        
    }
    
    /**
     * Funding references (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#funding-references-0-n)
     * 
     * Information about financial support (funding) for the resource being 
     * registered.
     * 
     * This field is compatible with 19. Funding Reference in DataCite.
     * 
     * The funder subfield is intended to be linked to a customizable 
     * vocabulary from Open Funder Registry or ROR. The award subfield is 
     * intended to be either linked to a customizable vocabulary sourced from 
     * the OpenAIRE grant database, or specified explicitly to allow linking 
     * to grants not provided by the grant database.
     * 
     * Subfields:
     *  __________________________________________________________________
     * | Field  | Cardinality | Description                               |
     * |--------+-------------+-------------------------------------------|
     * | funder | (1)         | The organisation of the funding provider. |
     * | award  | (0-1)       |The award (grant) sponsored by the funder. |
     *  ------------------------------------------------------------------
     * 
     * The funder subfields:
     *  _____________________________________________________________________
     * | Field | Cardinality | Description                                   |
     * | id    | (0-1, CV)   | The funder id from the controlled vocabulary. |
     * | name  | (0-1)       | The name of the funder.                       |
     *  ---------------------------------------------------------------------
     * 
     * One of id OR name must be given. It's recommended to use name if there 
     * is no matching id in the controlled vocabulary.
     * 
     * The award subfields:
     *  _________________________________________________________________________
     * | Field       |Cardinality |Description                                   |
     * | id          | (0-1, CV)  | The award id from the controlled vocabulary. |
     * | title       | (0-1)      | The localized title of the award             |
     * |             |            | (e.g., {"en": "Nobel Prize in Physics"})     |
     * | number      | (0-1)      | The code assigned by the funder to a         |
     * |             |            | sponsored award (grant).                     |
     * | identifiers | (0-N)      | Identifiers for the award.                   |
     *  -------------------------------------------------------------------------
     * 
     * One of id OR (title and number) must be given. It's recommended to use
     * title and number if there is no matching id in the controlled vocabulary.
     */
    public static class FundingReference {
        
        public FundingReference() {
        }
    }
    
    /**
     * References (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#references-0-n)
     * ⚠️ Not part of the deposit page yet:
     * Although available via the API, this field may see changes when added to
     * the deposit page.
     * 
     * A list of reference strings
     * 
     * Subfields:
     *  _________________________________________________________________
     * | Field      | Cardinality | Description                          |
     * | reference  | (1)         | free text, the full reference string |
     * | scheme     | (0-1)       | the scheme of the identifier.        |
     * | identifier | (0-1)       | the identifier if known.             |
     *  -----------------------------------------------------------------
     * 
     * Supported schemes:
     * - CrossRef Funder ID
     * - GRID
     * - ISNI
     * - Other
     * 
     * Note that those are passed lowercased with spaces removed e.g., 
     * CrossRef Funder ID is crossreffunderid.
     */
    public static class Reference {
        
        String reference;
        Optional<ControlledVocabulary.ReferenceScheme> schema = Optional.empty();
        Optional<String> identifier = Optional.empty();

        public Reference(String reference,
                Optional<ControlledVocabulary.ReferenceScheme> schema,
                Optional<String> identifier) {
            this.reference = reference;
            this.schema = schema;
            this.identifier = identifier;
        }
        
    }
    
    /**
     * Constructor for all mandatory fields
     * @param resourceType the resource type
     * @param creators the list of creators
     * @param title the title of the resource
     * @param publicationDate the publication date
     */
    public Metadata(
            /**
             * Resource type (1) (https://inveniordm.docs.cern.ch/reference/metadata/#resource-type-1)
             *
             * The type of the resource described by the record. The resource
             * type must be selected from a controlled vocabulary which can be
             * customized by each InvenioRDM instance.
             *
             * When interfacing with Datacite, this field is converted to a
             * format compatible with 10. Resource Type (i.e. type and subtype).
             * DataCite allows free text for the subtype, however InvenioRDM
             * requires this to come from a customizable controlled vocabulary.
             *
             * The resource type vocabulary also defines mappings to other
             * vocabularies than Datacite such as Schema.org, Citation Style
             * Language, BibTeX, and OpenAIRE. These mappings are very important
             * for the correct generation of citations due to how different
             * types are being interpreted by reference management systems.
             */
            ResourceType resourceType,
            /**
             * Creators (1-n) (https://inveniordm.docs.cern.ch/reference/metadata/#creators-1-n)
             *
             * The creators field registers those persons or organisations that
             * should be credited for the resource described by the record. The
             * list of persons or organisations in the creators field is used
             * for generating citations, while the persons or organisations
             * listed in the contributors field are not included in the
             * generated citations.
             *
             * The field is compatible with 2. Creator in DataCite. In addition
             * we are adding the possiblity of associating a role (like for
             * contributors). This is specifically for cases where e.g. an
             * editor needs to be credited for the work, while authors of
             * individual articles will be listed under contributors.
             */
            List<Creator> creators,
            /**
             * Title (1) (https://inveniordm.docs.cern.ch/reference/metadata/#title-1)
             *
             * A primary name or primary title by which a resource is known.
             * May be the title of a dataset or the name of a piece of software.
             * The primary title is used for display purposes throughout
             * InvenioRDM.
             *
             * The fields is compatible with 3. Title in DataCite. Compared to
             * DataCite, the field does not support specifying the language of
             * the title.
             */
            String title,
            /**
             * Publication date (1)¶
             *
             * The date when the resource was or will be made publicly available.
             *
             * The field is compatible 5. PublicationYear in DataCite. In case
             * of time intervals, the earliest date is used for DataCite.
             *
             * Format:
             *
             * The string must be be formatted according to Extended Date Time
             * Format (EDTF) Level 0. Only "Date" and "Date Interval" are
             * supported. "Date and Time" is not supported. The following are
             * examples of valid values:
             *
             * Date:
             * - 2020-11-10 - a complete ISO8601 date.
             * - 2020-11 - a date with month precision
             * - 2020 - a date with year precision
             *
             * Date Interval:
             * - 1939/1945 a date interval with year precision, beginning sometime
             *   in 1939 and ending sometime in 1945.
             * - 1939-09-01/1945-09 a date interval with day and month precision,
             *   beginning September 1st, 1939 and ending sometime in September
             *   1945.
             *
             * The localization (L10N) of EDTF dates is based on the skeletons
             * defined by the Unicode Common Locale Data Repository (CLDR).
             */
            ExtendedDateTimeFormat0 publicationDate) {
        this.resourceType = resourceType;
        this.creators = creators;
        this.title = title;
        this.publicationDate = publicationDate;
    }
    
    public Metadata addAdditionalTitles(
            /**
             * Additional titles (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#additional-titles-0-n)
             * 
             * Additional names or titles by which a resource is known.
             * 
             * The field is compatible with 3. Title in DataCite. Compared to
             * DataCite, InvenioRDM splits 3. Title into two separate fields
             * title and additional_titles. This is to ensure consistent usage
             * of a record's title throughout the entire software stack for
             * display purposes.
             */
            List<AdditionalTitle> additionalTitles) {
        this.additionalTitles.addAll(additionalTitles);
        return this;
    }
    
    public Metadata setDescription(
            /**
             * Description (0-1) (https://inveniordm.docs.cern.ch/reference/metadata/#description-0-1)
             * 
             * The description of a record.
             * 
             * The fields is compatible with 17. Description in DataCite. 
             * Compared to DataCite the field does not support specifying the 
             * language of the description.
             * 
             * The description may use a whitelist of HTML tags and attributes 
             * to style the text.
             */
            String description) {
        this.description = Optional.of(description);
        return this;
    }
    
    public Metadata addAdditionalDescriptions(
            /**
             * Additional descriptions (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#additional-descriptions-0-n)
             * 
             * Additional descriptions in addition to the primary description 
             * (e.g. abstracts in other languages), methods or further notes.
             * 
             * The field is compatible with 17. Description in DataCite.
             */
            List<AdditionalDescription> additionalDescriptions) {
        this.additionalDescriptions.addAll(additionalDescriptions);
        return this;
    }
    
    public Metadata addRights(
            /**
             * Rights (Licenses) (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#rights-licenses-0-n)
             * 
             * Rights management statement for the resource.
             * 
             * When interfacing with Datacite, this field is converted to be 
             * compatible with 16. Rights.
             * 
             * The rights field is intended to primarily be linked to a 
             * customizable vocabulary of licenses (defaults SPDX). It should 
             * however also be possible to provide custom rights statements.
             * 
             * The rights statements does not have any impact on access control
             * to the resource.
             */
            List<License> rights) {
        this.rights.addAll(rights);
        return this;
    }
    public Metadata addContributors(
            /**
             * Contributors (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#contributors-0-n)
             * 
             * The organisations or persons responsible for collecting, managing,
             * distributing, or otherwise contributing to the development of the
             * resource.
             * 
             * This field is compatible with 7. Contributor in DataCite.
             * 
             * The structure is identical to the Creators field. The "creators"
             * field records those persons or organisations that should be 
             * credited for the resource described by the record (e.g. for 
             * citation purposes). The "contributors" field records all other 
             * persons or organisations that have contributed, but which should
             * not be credited for citation purposes.
             */
            List<Contributor> contributors) {
        this.contributors.addAll(contributors);
        return this;
    }
    
    public Metadata addSubjects(
            /**
             * Subjects (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#subjects-0-n)
             * 
             * Subject, keyword, classification code, or key phrase describing the resource.
             * 
             * This field is compatible with 6. Subject in DataCite.
             */
            List<Subject> subjects) {
        this.subjects.addAll(subjects);
        return this;
    }
    
    public Metadata addLanguages(
            /**
             * Languages (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#languages-0-n)
             * 
             * The languages of the resource.
             * 
             * This field is compatible with 9. Language in DataCite. DataCite
             * however only supports one primary language, while this field
             * supports multiple languages.
             */
            List<Language> languages) {
        this.languages.addAll(languages);
        return this;
    }
    
    public Metadata addDates(
            /**
             * Dates (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#dates-0-n)
             * 
             * Different dates relevant to the resource.
             * 
             * The field is compatible with 8. Date in DataCite.
             */
            List<RecordDate> dates) {
        this.dates.addAll(dates);
        return this;
    }
    
    public Metadata setVersion(
            /**
             * Version (0-1) (https://inveniordm.docs.cern.ch/reference/metadata/#version-0-1)
             * 
             * The version number of the resource. Suggested practice is to use
             * semantic versioning. If the version number is provided, it's used
             * for display purposes on search results and landing pages.
             * 
             * This field is compatible with 15. Version in DataCite.
             */
            String version) {
        this.version = Optional.of(version);
        return this;
    }
    
    public Metadata setPublisher(
            /**
             * Publisher (0-1) (https://inveniordm.docs.cern.ch/reference/metadata/#publisher-0-1)
             * 
             * The name of the entity that holds, archives, publishes, prints,
             * distributes, releases, issues, or produces the resource. This
             * property will be used to formulate the citation, so consider the
             * prominence of the role. For software, use Publisher for the code
             * repository. If there is an entity other than a code repository,
             * that "holds, archives, publishes, prints, distributes, releases,
             * issues, or produces" the code, use the property Contributor field
             * for the code repository.
             * 
             * Defaults to the name of the repository.
             */
            String publisher) {
        this.publisher = Optional.of(publisher);
        return this;
    }
    
    public Metadata addAlternativeIdentifiers(
            /**
             * Alternate identifiers (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#alternate-identifiers-0-n)
             * 
             * Persistent identifiers for the resource other than the ones 
             * registered as system-managed internal or external persistent 
             * identifiers.
             * 
             * This field is compatible with 11. Alternate Identifiers in 
             * DataCite.
             * 
             * The main difference between the system-managed identifiers and 
             * this field, is that system-managed identifiers are fully 
             * controlled and managed by InvenioRDM, while identifiers listed
             * here are used solely for display purposes. For instance, a DOI
             * registered in the system-managed identifiers will prevent
             * another record with the same DOI from being created. A DOI
             * included in this field, will not prevent another record from
             * including the same DOI in this field.
             */
            List<AlternateIdentifier> alternateIdentifiers) {
        this.alternativeIdentifiers.addAll(alternateIdentifiers);
        return this;
    }
    
    public Metadata addRelatedIdentifiers(
            /**
            * Related identifiers/works (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#related-identifiersworks-0-n)
            * 
            * Identifiers of related resources.
            * 
            * This field is compatible with 12. Related Identifiers in DataCite.
            * The field however does not support the subfields 
            * 12.c relatedMetadataScheme, 12.d schemeURI and 12.e schemeType 
            * used for linking to additional metadata.
            */
            List<RelatedIdentifier> relatedIdentifiers) {
        this.relatedIdentifiers.addAll(relatedIdentifiers);
        return this;
    }
    
    public Metadata addSizes(
            /**
             * Sizes (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#sizes-0-n)
             * 
             * ⚠️ Not part of the deposit page yet:
             * Although available via the API, this field may see changes when
             * added to the deposit page.
             * 
             * Size (e.g. bytes, pages, inches, etc.) or duration (extent), e.g.
             * hours, minutes, days, etc., of a resource.
             * 
             * This field is compatible with 13. Size in DataCite.
             */
            List<String> sizes) {
        this.sizes.addAll(sizes);
        return this;
    }
    
    public Metadata addFormats(
            /**
             * Formats (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#formats-0-n)
             *
             * ⚠️ Not part of the deposit page yet:
             * Although available via the API, this field may see changes when added to the deposit page.
             * 
             * Technical format of the resource.
             * 
             * This field is compatible with 14. Format in Datacite.
             */
            List<String> formats) {
        this.formats.addAll(formats);
        return this;
    }
    
    public Metadata addLocations(
            /**
             * Locations (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#locations-0-n)
             *
             * ⚠️ Not part of the deposit page yet:
             * Although available via the API, this field may see changes when added to
             * the deposit page.
             *
             * Spatial region or named place where the data was gathered or about which
             * the data is focused.
             *
             * The field is compatible with 18. GeoLocation in DataCite Metadata Schema.
             * The field however has important differences:
             * - The field allows associating geographical identifiers with a location.
             * - The field allows associating a free text description to the location.
             * - The field uses the GeoJSON specification for describing geospatial
             *   coordinates instead of the individual fields used by DataCite.
             *
             * The location fields is modelled over GeoJSON FeatureCollection and
             * Feature objects using foreign members (for identifiers, place and
             * description). We do not store the GeoJSON type information
             * (e.g. "type": "FeatureCollection" and "type": "Feature") because this
             * information is implicit. The JSON served on the REST API (external JSON),
             * will include the type information as well as "properties": null in order
             * to make it valid GeoJSON.
             */
            List<Location> locations) {
        this.locations.addAll(locations);
        return this;
    }
    
    public Metadata addFundingReferences(
            /**
             * Funding references (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#funding-references-0-n)
             *
             * Information about financial support (funding) for the resource being
             * registered.
             *
             * This field is compatible with 19. Funding Reference in DataCite.
             *
             * The funder subfield is intended to be linked to a customizable
             * vocabulary from Open Funder Registry or ROR. The award subfield is
             * intended to be either linked to a customizable vocabulary sourced from
             * the OpenAIRE grant database, or specified explicitly to allow linking
             * to grants not provided by the grant database.
             */
            List<FundingReference> fundingReferences) {
        this.fundingReferences.addAll(fundingReferences);
        return this;
    }
    
    public Metadata addReferences(
            /**
             * References (0-n) (https://inveniordm.docs.cern.ch/reference/metadata/#references-0-n)
             * ⚠️ Not part of the deposit page yet:
             * Although available via the API, this field may see changes when added to
             * the deposit page.
             *
             * A list of reference strings
             *
             */
            List<Reference> references) {
        this.references.addAll(references);
        return this;
    }
    
}
