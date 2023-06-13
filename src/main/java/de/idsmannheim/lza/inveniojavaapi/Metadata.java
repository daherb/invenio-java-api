/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import de.idsmannheim.lza.inveniojavaapi.deserializers.AdditionalDescriptionDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.AdditionalTitleDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.AffiliationDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.AlternateIdentifierDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.ContributorDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.CreatorDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.DateDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.ExtendedDateTimeFormat0Deserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.FundingReferenceDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.LanguageDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.LocalizedStringsDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.LocationDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.MetadataDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.PersonOrOrgDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.ReferenceDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.RelatedIdentifierDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.ResourceTypeDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.RightsDeserializer;
import de.idsmannheim.lza.inveniojavaapi.deserializers.SubjectDeserializer;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
@JsonDeserialize(using = MetadataDeserializer.class)
public class Metadata {
    // The mandatory fields are initialized in the constructor
    @JsonProperty("resource_type")
    final ResourceType resourceType;
    @JsonProperty("creators")
    final ArrayList<Creator> creators;
    @JsonProperty("title")
    String title;
    @JsonProperty("publication_date")
    ExtendedDateTimeFormat0 publicationDate;
    // The rest is initialized with an empty value
    @JsonProperty("additional_titles")
    final ArrayList<AdditionalTitle> additionalTitles = new ArrayList<>();
    @JsonProperty("description")
    Optional<String> description = Optional.empty();
    @JsonProperty("additional_descriptions")
    final ArrayList<AdditionalDescription> additionalDescriptions = new ArrayList<>();
    @JsonProperty("rights")
    final ArrayList<License> rights = new ArrayList<>();
    @JsonProperty("contributors")
    final ArrayList<Contributor> contributors = new ArrayList<>();
    @JsonProperty("subjects")
    final ArrayList<Subject> subjects = new ArrayList<>();
    @JsonProperty("languages")
    final ArrayList<Language> languages = new ArrayList<>();
    @JsonProperty("dates")
    final ArrayList<Date> dates = new ArrayList<>();
    @JsonProperty("version")
    Optional<String> version = Optional.empty();
    @JsonProperty("publisher")
    Optional<String> publisher = Optional.empty();
    @JsonProperty("identifiers")
    final ArrayList<AlternateIdentifier> alternativeIdentifiers = new ArrayList<>();
    @JsonProperty("related_identifiers")
    ArrayList<RelatedIdentifier> relatedIdentifiers = new ArrayList<>();
    @JsonProperty("sizes")
    final ArrayList<String> sizes = new ArrayList<>();
    @JsonProperty("formats")
    final ArrayList<String> formats = new ArrayList<>();
    @JsonProperty("locations")
    Location locations;
    @JsonProperty("funding")
    final ArrayList<FundingReference> fundingReferences = new ArrayList<>();
    @JsonProperty("references")
    final ArrayList<Reference> references = new ArrayList<>();
    
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = ResourceTypeDeserializer.class)
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

        @Override
        protected Object clone() {
            return new ResourceType(id);
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 83 * hash + Objects.hashCode(this.id);
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
            final ResourceType other = (ResourceType) obj;
            return Objects.equals(this.id, other.id);
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
    @JsonDeserialize(using = CreatorDeserializer.class)
    public static class Creator {
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = CreatorDeserializer.CreatorRoleDeserializer.class)
        public static class Role {
            
            @JsonProperty("id")
                    ControlledVocabulary.Role id;
            
            public Role(ControlledVocabulary.Role id) {
                this.id = id;
            }
            
            @Override
            public int hashCode() {
                int hash = 5;
                hash = 13 * hash + Objects.hashCode(this.id);
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
                final Role other = (Role) obj;
                return Objects.equals(this.id, other.id);
            }
            
            @Override
            public String toString() {
                return "Role{" + "id=" + id + '}';
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return new Creator.Role(id);
            }
            
            
        }
        
        @JsonProperty("person_or_org")
        PersonOrOrg personOrOrg;
        @JsonProperty("role")
        Optional<Creator.Role> role = Optional.empty();
        @JsonProperty("affiliations")
        ArrayList<Affiliation> affiliations = new ArrayList<>();
        
        @JsonCreator
        public Creator(PersonOrOrg personOrOrg) {
            this.personOrOrg = personOrOrg;
        }
        
        public Creator setRole(Creator.Role role) {
            this.role = Optional.of(role);
            return this;
        }
        
        public Creator addAffiliations(List<Affiliation> affiliations) {
            this.affiliations.addAll(affiliations);
            return this;
        }

        @Override
        public String toString() {
            return "Creator{" + "personOrOrg=" + personOrOrg + ", role=" + role + ", affiliations=" + affiliations + '}';
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            Creator creator = new Creator((PersonOrOrg) personOrOrg.clone());
            if (role.isPresent())
                creator.setRole((Creator.Role) role.get().clone());
            creator.addAffiliations((List<Affiliation>) affiliations.clone());
            return creator;
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
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
            final Creator other = (Creator) obj;
            if (!Objects.equals(this.personOrOrg, other.personOrOrg)) {
                return false;
            }
            if (!Objects.equals(this.role, other.role)) {
                return false;
            }
            return Objects.equals(this.affiliations, other.affiliations);
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
    @JsonDeserialize(using = PersonOrOrgDeserializer.class)
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
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = PersonOrOrgDeserializer.IdentifierDeserializer.class)
        public static class Identifier {
            @JsonProperty("scheme")
            ControlledVocabulary.PersonOrOrgIdentifierScheme scheme;
            @JsonProperty("identifier")
            String identifier;

            public Identifier(ControlledVocabulary.PersonOrOrgIdentifierScheme scheme, String identifier) {
                this.scheme = scheme;
                this.identifier = identifier;
            }

            @Override
            public String toString() {
                return "Identifier{" + "scheme=" + scheme + ", identifier=" + identifier + '}';
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return new Identifier((ControlledVocabulary.PersonOrOrgIdentifierScheme) scheme.clone(), identifier);
            }

            
            @Override
            public int hashCode() {
                int hash = 7;
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
                final Identifier other = (Identifier) obj;
                if (!Objects.equals(this.identifier, other.identifier)) {
                    return false;
                }
                return Objects.equals(this.scheme, other.scheme);
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
         */
        public PersonOrOrg(String givenName, 
                String familyName) {
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
            }
        /**
         * Constructor for a person with an explicit name parameter
         * @param givenName given name of the person
         * @param familyName family name of the person
         * @param name complete name of the person
         */
        public PersonOrOrg(String givenName, 
                String familyName, String name) {
            this(givenName, familyName);
            this.name = name;
        }
        
        /**
         * Constructor for an organization, automatically setting the type
         * @param name name of the organization
         */
        public PersonOrOrg(String name){
                if (name == null || name.isBlank()) {
                    throw new IllegalArgumentException
                            ("Name has to be present for an organization");
                }
            this.name = name;
            this.type = Type.Organizational;
        }

        public void setType(Type type) {
            this.type = type;
        }

        /**
         * Adds identifiers
         * @param identifiers list of identifiers
         * @return the updated object
         */
        public PersonOrOrg addIdentifiers(ArrayList<Identifier> identifiers) {
            this.identifiers.addAll(identifiers);
            return this;
        }

        
        
        @Override
        public String toString() {
            return "PersonOrOrg{" + "type=" + type + ", givenName=" + givenName + ", familyName=" + familyName + ", name=" + name + ", identifiers=" + identifiers + '}';
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            PersonOrOrg personOrOrg = new PersonOrOrg(givenName, familyName, name);
            personOrOrg.setType(type);
            return personOrOrg;
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
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
            final PersonOrOrg other = (PersonOrOrg) obj;
            if (!Objects.equals(this.givenName, other.givenName)) {
                return false;
            }
            if (!Objects.equals(this.familyName, other.familyName)) {
                return false;
            }
            if (!Objects.equals(this.name, other.name)) {
                return false;
            }
            if (this.type != other.type) {
                return false;
            }
            return Objects.equals(this.identifiers, other.identifiers);
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = AffiliationDeserializer.class)
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

        @Override
        public String toString() {
            return "Affiliation{" + "id=" + id + ", name=" + name + '}';
        }

        @Override
        protected Object clone() {
            return new Affiliation(id, name);
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
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
            final Affiliation other = (Affiliation) obj;
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
            return Objects.equals(this.name, other.name);
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = ExtendedDateTimeFormat0Deserializer.class)
    public static class ExtendedDateTimeFormat0 {
        String startYear;
        Optional<String> startMonth = Optional.empty();
        Optional<String> startDay = Optional.empty();
        Optional<String> endYear = Optional.empty();
        Optional<String> endMonth = Optional.empty();
        Optional<String> endDay = Optional.empty();

        public ExtendedDateTimeFormat0(String startYear) {
            if (!startYear.matches("\\d{4}"))
                throw new IllegalArgumentException("Illegal year: " + startYear);
            this.startYear = startYear;
        }

        public ExtendedDateTimeFormat0 addStartMonth(String startMonth) {
            if (!startMonth.matches("\\d{2}"))
                throw new IllegalArgumentException("Illegal month: " + startMonth);
            this.startMonth = Optional.of(startMonth);
            return this;
        }
        
        public ExtendedDateTimeFormat0 addStartDay(String startDay) {
            if (startMonth.isEmpty())
                throw new IllegalArgumentException
                    ("Start month has to be present before adding start day");
            if (!startDay.matches("\\d{2}"))
                throw new IllegalArgumentException("Illegal day: " + startDay);
            this.startDay = Optional.of(startDay);
            return this;
        }
        
        public ExtendedDateTimeFormat0 addEndYear(String endYear) {
            if (!endYear.matches("\\d{4}") && !endYear.isEmpty())
                throw new IllegalArgumentException("Illegal year: " + endYear);
            this.endYear = Optional.of(endYear);
            return this;
        }
        
        public ExtendedDateTimeFormat0 addEndMonth(String endMonth) {
            if (endYear.isEmpty())
                throw new IllegalArgumentException
                    ("End year has to be present before adding end month");
            if (!endMonth.matches("\\d{2}"))
                throw new IllegalArgumentException("Illegal month: " + endMonth);
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

        @Override
        protected Object clone() {
            ExtendedDateTimeFormat0 extendedDateTimeFormat0 = new ExtendedDateTimeFormat0(startYear);
            if (startMonth.isPresent())
                extendedDateTimeFormat0.addStartMonth(startMonth.get());
            if (startDay.isPresent())
                extendedDateTimeFormat0.addStartDay(startDay.get());
            if (endYear.isPresent())
                extendedDateTimeFormat0.addEndYear(endYear.get());
            if (endMonth.isPresent())
                extendedDateTimeFormat0.addEndMonth(endMonth.get());
            if (endDay.isPresent())
                extendedDateTimeFormat0.addEndDay(endDay.get());
            return extendedDateTimeFormat0;
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 89 * hash + Objects.hashCode(this.startYear);
            hash = 89 * hash + Objects.hashCode(this.startMonth);
            hash = 89 * hash + Objects.hashCode(this.startDay);
            hash = 89 * hash + Objects.hashCode(this.endYear);
            hash = 89 * hash + Objects.hashCode(this.endMonth);
            hash = 89 * hash + Objects.hashCode(this.endDay);
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
            final ExtendedDateTimeFormat0 other = (ExtendedDateTimeFormat0) obj;
            if (!Objects.equals(this.startYear, other.startYear)) {
                return false;
            }
            if (!Objects.equals(this.startMonth, other.startMonth)) {
                return false;
            }
            if (!Objects.equals(this.startDay, other.startDay)) {
                return false;
            }
            if (!Objects.equals(this.endYear, other.endYear)) {
                return false;
            }
            if (!Objects.equals(this.endMonth, other.endMonth)) {
                return false;
            }
            return Objects.equals(this.endDay, other.endDay);
        }

        /**
         * Method to parse a date in ISO format and convert into Extended Date Time Format
         * @param dateString the date or date range as a string
         * @return the parsed ExtendedDateTimeFormat0 object
         * @throws java.lang.IllegalArgumentException
         */
        public static ExtendedDateTimeFormat0 parseDateToExtended(String dateString) throws IllegalArgumentException {
            Pattern p1 = Pattern.compile("(?<startYear>\\d{4})(?:-(?<startMonth>\\d{2}))?(?:-(?<startDay>\\d{2}))?.*(?:/(?<endYear>\\d{4})(?:-(?<endMonth>\\d{2}))?(?:-(?<endDay>\\d{2}))?.*)?");
            Matcher m1 = p1.matcher(dateString);
            Pattern p2 = Pattern.compile("(?:(?<startDay>\\d{2})-)?(?:(?<startMonth>\\d{2})-)?(?<startYear>\\d{4})(?:/(?:(?<endDay>\\d{2})-)?(?:(?<endMonth>\\d{2})-)?(?<endYear>\\d{4}))?");
            Matcher m2 = p2.matcher(dateString);
            if (m1.matches()) {
                ExtendedDateTimeFormat0 date = new ExtendedDateTimeFormat0(m1.group("startYear"));
                if (m1.group("startMonth") != null)
                    date.addStartMonth(m1.group("startMonth"));
                if (m1.group("startDay") != null)
                    date.addStartDay(m1.group("startDay"));
                if (m1.group("endYear") != null)
                    date.addEndYear(m1.group("endYear"));
                if (m1.group("endMonth") != null)
                    date.addEndMonth(m1.group("endMonth"));
                if (m1.group("endDay") != null)
                    date.addEndDay(m1.group("endDay"));
                return date;
            }
            // Try non-iso date format
            else if (m2.matches()) {
                ExtendedDateTimeFormat0 date = new ExtendedDateTimeFormat0(m2.group("startYear"));
                if (m2.group("startMonth") != null)
                    date.addStartMonth(m2.group("startMonth"));
                if (m2.group("startDay") != null)
                    date.addStartDay(m2.group("startDay"));
                if (m2.group("endYear") != null)
                    date.addEndYear(m2.group("endYear"));
                if (m2.group("endMonth") != null)
                    date.addEndMonth(m2.group("endMonth"));
                if (m2.group("endDay") != null)
                    date.addEndDay(m2.group("endDay"));
                return date;
            }
            else {
                throw new IllegalArgumentException("Invalid date format: " + dateString);
            }
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = LanguageDeserializer.class)
    public static class Language {
        
        ControlledVocabulary.LanguageId id;

        public Language(ControlledVocabulary.LanguageId id) {
            this.id = id;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return new Language((ControlledVocabulary.LanguageId) id.clone());
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 43 * hash + Objects.hashCode(this.id);
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
            final Language other = (Language) obj;
            return Objects.equals(this.id, other.id);
        }

        @Override
        public String toString() {
            return id.toString();
            // return "Language{" + "id=" + id + '}';
        }
        
        @JsonProperty("id")
        public String getId() {
            // TODO find a better solution
            return this.id.id2.orElse(this.id.id3.orElse("MISSING_LANG"));
        }
    }
    
    @JsonDeserialize(using = LocalizedStringsDeserializer.class)
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public static class LocalizedStrings {
        
        HashMap<Language, String> localizedStrings = new HashMap<>();
        
        public LocalizedStrings() {
        }

        public LocalizedStrings add(Language lang, String string) {
            this.localizedStrings.put(lang,string);
            return this;
        }
        
        public LocalizedStrings addAll(LocalizedStrings strings) {
            this.localizedStrings.putAll(strings.localizedStrings);
            return this;
        }

        @Override
        protected Object clone() {
            LocalizedStrings newLocalizedStrings = new LocalizedStrings();
            newLocalizedStrings.addAll((LocalizedStrings) localizedStrings.clone());
            return newLocalizedStrings;
        }
        
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 13 * hash + Objects.hashCode(this.localizedStrings);
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
            final LocalizedStrings other = (LocalizedStrings) obj;
            return Objects.equals(this.localizedStrings, other.localizedStrings);
        }

        @Override
        public String toString() {
            return "LocalizedStrings{" + "localizedStrings=" + localizedStrings + '}';
        }

        public boolean isEmpty() {
            return this.localizedStrings.isEmpty();
        }

        //@JsonAnyGetter
        @JsonValue
        public HashMap<Language, String> getMap() {
            return localizedStrings;
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = AdditionalTitleDeserializer.class)
    public static class AdditionalTitle {
        @JsonProperty("title")
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
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = AdditionalTitleDeserializer.TitleTypeDeserializer.class)
        public static class TitleType {
            
            ControlledVocabulary.TitleTypeId id;
            
            LocalizedStrings title = new LocalizedStrings();
            
            public TitleType(ControlledVocabulary.TitleTypeId id, LocalizedStrings titles) {
                this.id = id;
                if (!titles.isEmpty())
                    this.title.addAll(titles);
//                else
//                    throw new IllegalArgumentException("At least one title required");
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return new TitleType((ControlledVocabulary.TitleTypeId) id.clone(), (LocalizedStrings) title.clone());
            }

            
            @Override
            public int hashCode() {
                int hash = 7;
                hash = 37 * hash + Objects.hashCode(this.id);
                hash = 37 * hash + Objects.hashCode(this.title);
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
                final TitleType other = (TitleType) obj;
                if (!Objects.equals(this.id, other.id)) {
                    return false;
                }
                return Objects.equals(this.title, other.title);
            }

            @Override
            public String toString() {
                return "TitleType{" + "id=" + id + ", title=" + title + '}';
            }

            @JsonProperty("id")
            public ControlledVocabulary.TitleTypeId getId() {
                return id;
            }

            @JsonProperty("title")
            public LocalizedStrings getTitle() {
                return title;
            }

            
        }
        
        @JsonProperty("type")
        TitleType type;
        /**
         * The lang field is as follows:
         *  ____________________________________________________
         * | Field | Cardinality | Description                  |
         * |-------+-------------+------------------------------|
         * | id    | (1, CV)     | The ISO-639-3 language code. |
         *  ----------------------------------------------------
         */
        @JsonProperty("lang")
        Optional<Language> lang = Optional.empty();

        @JsonCreator
        public AdditionalTitle(String title, TitleType type) {
            this.title = title;
            this.type = type;
        }

        public AdditionalTitle setLang(Language lang) {
            this.lang = Optional.of(lang);
            return this;
        }

        
        @Override
        public String toString() {
            return "AdditionalTitle{" + "title=" + title + ", type=" + type + ", lang=" + lang + '}';
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            AdditionalTitle additionalTitle = new AdditionalTitle(title, (TitleType) type.clone());
            if (lang.isPresent())
                additionalTitle.setLang(lang.get());
            return additionalTitle;
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.title);
            hash = 97 * hash + Objects.hashCode(this.type);
            hash = 97 * hash + Objects.hashCode(this.lang);
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
            final AdditionalTitle other = (AdditionalTitle) obj;
            if (!Objects.equals(this.title, other.title)) {
                return false;
            }
            if (!Objects.equals(this.type, other.type)) {
                return false;
            }
            return Objects.equals(this.lang, other.lang);
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = AdditionalDescriptionDeserializer.class)
    public static class AdditionalDescription {
        @JsonProperty("description")
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
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = AdditionalDescriptionDeserializer.DescriptionTypeDeserializer.class)
        public static class DescriptionType {
            @JsonProperty("id")
            ControlledVocabulary.DescriptionTypeId id;
            @JsonProperty("title")
            LocalizedStrings title = new LocalizedStrings();

            public DescriptionType(ControlledVocabulary.DescriptionTypeId id, LocalizedStrings title) {
                this.id = id;
                if (!title.isEmpty())
                    this.title.addAll(title);
//                else
//                    throw new IllegalArgumentException("At least one title required");
            }

            @Override
            protected Object clone() {
                return new DescriptionType((ControlledVocabulary.DescriptionTypeId) id.clone(), (LocalizedStrings) title.clone());
            }

            
            @Override
            public int hashCode() {
                int hash = 7;
                hash = 89 * hash + Objects.hashCode(this.id);
                hash = 89 * hash + Objects.hashCode(this.title);
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
                final DescriptionType other = (DescriptionType) obj;
                if (!Objects.equals(this.id, other.id)) {
                    return false;
                }
                return Objects.equals(this.title, other.title);
            }

            @Override
            public String toString() {
                return "DescriptionType{" + "id=" + id + ", title=" + title + '}';
            }
            
            
        }
        @JsonProperty("type")
        DescriptionType type;
        /**
         * The lang field is as follows:
         *  ____________________________________________________
         * | Field | Cardinality | Description                  |
         * |-------+-------------+------------------------------|
         * | id    | (1, CV)     | The ISO-639-3 language code. |
         *  ----------------------------------------------------
         */
        @JsonProperty("lang")
        Optional<Language> lang;

        public AdditionalDescription(String description, DescriptionType type) {
            this.description = description;
            this.type = type;
        }

        public AdditionalDescription setLang(Language lang) {
            this.lang = Optional.of(lang);
            return this;
        }

        
        @Override
        protected Object clone() {
            AdditionalDescription additionalDescription = new AdditionalDescription(description, type);
            if (lang.isPresent())
                additionalDescription.setLang(lang.get());
            return additionalDescription;
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 59 * hash + Objects.hashCode(this.description);
            hash = 59 * hash + Objects.hashCode(this.type);
            hash = 59 * hash + Objects.hashCode(this.lang);
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
            final AdditionalDescription other = (AdditionalDescription) obj;
            if (!Objects.equals(this.description, other.description)) {
                return false;
            }
            if (!Objects.equals(this.type, other.type)) {
                return false;
            }
            return Objects.equals(this.lang, other.lang);
        }

        @Override
        public String toString() {
            return "AdditionalDescription{" + "description=" + description + ", type=" + type + ", lang=" + lang + '}';
        }
        
        
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = RightsDeserializer.class)
    public static class License {
        @JsonProperty("id")
        Optional<String> id = Optional.empty();
        @JsonProperty("title")
        LocalizedStrings title = new LocalizedStrings();
        @JsonProperty("description")
        LocalizedStrings description = new LocalizedStrings();
        @JsonProperty("link")
        Optional<URL> link = Optional.empty();
        // Not part of the specs but occurs in the records list
        @JsonProperty("props")
        HashMap<String,String> props = new HashMap<>();
        
        public License(String id, LocalizedStrings title,
            LocalizedStrings description) {
            this.id = Optional.of(id);
            this.title.addAll(title);
            this.description.addAll(description);
        }
        
        public License(Optional<String> id, LocalizedStrings title,
            LocalizedStrings description) {
            if (id.isEmpty() && title.isEmpty())
                throw new IllegalArgumentException("Either id or title have to be given");
            this.id = id;
            this.title.addAll(title);
            this.description.addAll(description);
        }

        public License setLink(URL link) {
            this.link = Optional.of(link);
            return this;
        }

        public License addProps(HashMap<String,String> props) {
            this.props.putAll(props);
            return this;
        }

        @Override
        protected Object clone() {
            License license = new License(id, (LocalizedStrings) title.clone(), (LocalizedStrings) description.clone());
            if (link.isPresent())
                license.setLink(link.get());
            license.addProps((HashMap<String, String>) props.clone());
            return license;
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 67 * hash + Objects.hashCode(this.id);
            hash = 67 * hash + Objects.hashCode(this.title);
            hash = 67 * hash + Objects.hashCode(this.description);
            hash = 67 * hash + Objects.hashCode(this.link);
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
            final License other = (License) obj;
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
            if (!Objects.equals(this.title, other.title)) {
                return false;
            }
            if (!Objects.equals(this.description, other.description)) {
                return false;
            }
            return Objects.equals(this.link, other.link);
        }

        @Override
        public String toString() {
            return "License{" + "id=" + id + ", title=" + title + ", description=" + description + ", link=" + link + '}';
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
     * The role field is as follows:
     *  ____________________________________________________________________
     * | Field | Cardinality | Description                                  |
     * |-------+-------------+----------------------------------------------|
     * | id    | (1, CV)     | The role's controlled vocabulary identifier. |
     *  --------------------------------------------------------------------
     * 
     * Note that Creators and Contributors may use different controlled
     * vocabularies for the role field.
     */
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = ContributorDeserializer.class)
    public static class Contributor {
        
        @JsonProperty("person_or_org")
        PersonOrOrg personOrOrg;
        @JsonProperty("role")
        ControlledVocabulary.Role role;
        @JsonProperty("affiliations")
        ArrayList<Affiliation> affiliations = new ArrayList<>();

        public Contributor(PersonOrOrg personOrOrg, ControlledVocabulary.Role role) {
            this.personOrOrg = personOrOrg;
            this.role = role;
        }

        public Contributor addAffiliations(List<Affiliation> affiliations) {
            this.affiliations.addAll(affiliations);
            return this;
        }
        
        @Override
        protected Object clone() throws CloneNotSupportedException {
            Contributor contributor = new Contributor((PersonOrOrg) personOrOrg.clone(), (ControlledVocabulary.Role) role.clone());
            contributor.addAffiliations((ArrayList<Affiliation>) affiliations.clone());
            return contributor;
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + Objects.hashCode(this.personOrOrg);
            hash = 97 * hash + Objects.hashCode(this.role);
            hash = 97 * hash + Objects.hashCode(this.affiliations);
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
            final Contributor other = (Contributor) obj;
            if (!Objects.equals(this.personOrOrg, other.personOrOrg)) {
                return false;
            }
            if (!Objects.equals(this.role, other.role)) {
                return false;
            }
            return Objects.equals(this.affiliations, other.affiliations);
        }

        @Override
        public String toString() {
            return "Contributor{" + "personOrOrg=" + personOrOrg + ", role=" + role + ", affiliations=" + affiliations + '}';
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = SubjectDeserializer.class)
    public static class Subject {
        
        @JsonProperty("id")
        Optional<URL> id = Optional.empty();
        @JsonProperty("subject")
        Optional<String> subject = Optional.empty();

        public Subject(URL id) {
            this.id = Optional.of(id);
        }

        public Subject(String subject) {
            this.subject = Optional.of(subject);
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            if (id.isPresent())
                return new Subject(id.get());
            if (subject.isPresent())
                return new Subject(subject.get());
            throw new CloneNotSupportedException("Error cloning subject. Neither id nor subject defined.");
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + Objects.hashCode(this.id);
            hash = 97 * hash + Objects.hashCode(this.subject);
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
            final Subject other = (Subject) obj;
            if (!Objects.equals(this.id, other.id)) {
                return false;
            }
            return Objects.equals(this.subject, other.subject);
        }

        @Override
        public String toString() {
            return "Subject{" + "id=" + id + ", subject=" + subject + '}';
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
    
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = DateDeserializer.class)
    public static class Date {
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
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = DateDeserializer.DateTypeDeserializer.class)
        public static class DateType {
            @JsonProperty("id")
            ControlledVocabulary.DateTypeId id;
            @JsonProperty("title")
            LocalizedStrings title = new LocalizedStrings();

            public DateType(ControlledVocabulary.DateTypeId id, LocalizedStrings title) {
                this.id = id;
                if (!title.isEmpty())
                    this.title.addAll(title);
                else 
                    throw new IllegalArgumentException("At least one title required");
            }

            @Override
            protected Object clone() {
                return new DateType((ControlledVocabulary.DateTypeId) id.clone(), (LocalizedStrings) title.clone());
            }

            
            @Override
            public int hashCode() {
                int hash = 5;
                hash = 89 * hash + Objects.hashCode(this.id);
                hash = 89 * hash + Objects.hashCode(this.title);
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
                final DateType other = (DateType) obj;
                if (!Objects.equals(this.id, other.id)) {
                    return false;
                }
                return Objects.equals(this.title, other.title);
            }

            @Override
            public String toString() {
                return "DateType{" + "id=" + id + ", title=" + title + '}';
            }
            
            
        }
        @JsonProperty("date")
        ExtendedDateTimeFormat0 date;
        @JsonProperty("type")
        DateType type;
        @JsonProperty("description")
        Optional<String> description = Optional.empty();

        public Date(ExtendedDateTimeFormat0 date, DateType type) {
            this.date = date;
            this.type = type;
        }

        public Date setDescription(String description) {
            this.description = Optional.of(description);
            return this;
        }

        
        @Override
        protected Object clone() throws CloneNotSupportedException {
            Date newDate = new Date((ExtendedDateTimeFormat0) date.clone(), (DateType) type.clone());
            if (description.isPresent())
                newDate.setDescription(description.get());
            return newDate;
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 79 * hash + Objects.hashCode(this.date);
            hash = 79 * hash + Objects.hashCode(this.type);
            hash = 79 * hash + Objects.hashCode(this.description);
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
            final Date other = (Date) obj;
            if (!Objects.equals(this.date, other.date)) {
                return false;
            }
            if (!Objects.equals(this.type, other.type)) {
                return false;
            }
            return Objects.equals(this.description, other.description);
        }

        @Override
        public String toString() {
            return "Date{" + "date=" + date + ", type=" + type + ", description=" + description + '}';
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = AlternateIdentifierDeserializer.class)
    public static class AlternateIdentifier {
        @JsonProperty("identifier")
        String identifier;
        @JsonProperty("scheme")
        ControlledVocabulary.RecordIdentifierScheme scheme;

        public AlternateIdentifier(String identifier, 
                ControlledVocabulary.RecordIdentifierScheme scheme) {
            this.identifier = identifier;
            this.scheme = scheme;
        }

        @Override
        protected Object clone() {
            return new AlternateIdentifier(identifier, (ControlledVocabulary.RecordIdentifierScheme) scheme.clone());
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + Objects.hashCode(this.identifier);
            hash = 53 * hash + Objects.hashCode(this.scheme);
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
            final AlternateIdentifier other = (AlternateIdentifier) obj;
            if (!Objects.equals(this.identifier, other.identifier)) {
                return false;
            }
            return Objects.equals(this.scheme, other.scheme);
        }

        @Override
        public String toString() {
            return "AlternateIdentifier{" + "identifier=" + identifier + ", scheme=" + scheme + '}';
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = RelatedIdentifierDeserializer.class)
    public static class RelatedIdentifier {
        
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = RelatedIdentifierDeserializer.RelationTypeDeserializer.class)
        public static class RelationType {
            @JsonProperty("id")
            ControlledVocabulary.RelationTypeId id;
            @JsonProperty("title")
            LocalizedStrings title = new LocalizedStrings();
            
            public RelationType(ControlledVocabulary.RelationTypeId id, 
                    LocalizedStrings titles) {
                this.id = id;
                if (!titles.isEmpty()) {
                    this.title.addAll(titles);
                }
//                else {
//                    throw new IllegalArgumentException("At least one title required: " + titles.toString() + " " + String.valueOf(titles.isEmpty()));
//                }
            }

            @Override
            protected Object clone() {
                return new RelationType(id, (LocalizedStrings) title.clone());
            }

            public ControlledVocabulary.RelationTypeId getId() {
                return id;
            }

            public LocalizedStrings getTitle() {
                return title;
            }

            @Override
            public int hashCode() {
                int hash = 5;
                hash = 67 * hash + Objects.hashCode(this.id);
                hash = 67 * hash + Objects.hashCode(this.title);
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
                final RelationType other = (RelationType) obj;
                if (!Objects.equals(this.id, other.id)) {
                    return false;
                }
                return Objects.equals(this.title, other.title);
            }

            @Override
            public String toString() {
                return "RelationType{" + "id=" + id + ", title=" + title + '}';
            }
            
            
        }
        
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = RelatedIdentifierDeserializer.RelatedResourceTypeDeserializer.class)
        public static class RelatedResourceType {
            @JsonProperty("id")
            ControlledVocabulary.RelatedResourceType id;
            
            @JsonProperty("title")
            LocalizedStrings title = new LocalizedStrings();

            public RelatedResourceType(ControlledVocabulary.RelatedResourceType id, LocalizedStrings title) {
                this.id = id;
                this.title.addAll(title);
            }

            public ControlledVocabulary.RelatedResourceType getId() {
                return id;
            }

            public LocalizedStrings getTitle() {
                return title;
            }

            @Override
            public int hashCode() {
                int hash = 5;
                hash = 89 * hash + Objects.hashCode(this.id);
                hash = 89 * hash + Objects.hashCode(this.title);
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
                final RelatedResourceType other = (RelatedResourceType) obj;
                if (!Objects.equals(this.id, other.id)) {
                    return false;
                }
                return Objects.equals(this.title, other.title);
            }

            @Override
            public String toString() {
                return "RelatedResourceType{" + "id=" + id + ", title=" + title + '}';
            }
            
        }
        
        @JsonProperty("identifier")
        String identifier;
        @JsonProperty("scheme")
        ControlledVocabulary.RelatedRecordIdentifierScheme scheme;
        @JsonProperty("relation_type")
        RelationType relationType;
        @JsonProperty("resource_type")
        Optional<RelatedResourceType> resourceType = Optional.empty();

        public RelatedIdentifier(String identifier, 
                ControlledVocabulary.RelatedRecordIdentifierScheme scheme, 
                RelationType relationType) {
            this.identifier = identifier;
            this.scheme = scheme;
            this.relationType = relationType;
        }

        public RelatedIdentifier setResourceType(RelatedResourceType resourceType) {
            this.resourceType = Optional.of(resourceType);
            return this;
        }

        public String getIdentifier() {
            return identifier;
        }

        public ControlledVocabulary.RelatedRecordIdentifierScheme getScheme() {
            return scheme;
        }

        public RelationType getRelationType() {
            return relationType;
        }

        public Optional<RelatedResourceType> getResourceType() {
            return resourceType;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            RelatedIdentifier relatedIdentifier = new RelatedIdentifier(identifier, (ControlledVocabulary.RelatedRecordIdentifierScheme) scheme.clone(), (RelationType) relationType.clone());
            if (resourceType.isPresent())
                relatedIdentifier.setResourceType(resourceType.get());
            return relatedIdentifier;
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 29 * hash + Objects.hashCode(this.identifier);
            hash = 29 * hash + Objects.hashCode(this.scheme);
            hash = 29 * hash + Objects.hashCode(this.relationType);
            hash = 29 * hash + Objects.hashCode(this.resourceType);
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
            final RelatedIdentifier other = (RelatedIdentifier) obj;
            if (!Objects.equals(this.identifier, other.identifier)) {
                return false;
            }
            if (!Objects.equals(this.scheme, other.scheme)) {
                return false;
            }
            if (!Objects.equals(this.relationType, other.relationType)) {
                return false;
            }
            return Objects.equals(this.resourceType, other.resourceType);
        }

        @Override
        public String toString() {
            return "RelatedIdentifier{" + "identifier=" + identifier + ", scheme=" + scheme + ", relationType=" + relationType + ", resourceType=" + resourceType + '}';
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = LocationDeserializer.class)
    public static class Location {
        
        /**
         *  * Identifier object in identifiers:
         *  _________________________________________________________________
         * | Field      | Cardinality | Description                          |
         * | identifier | (1, CV)     | A globally unique identifier for the |
         * |            |             | location.                            |
         * | scheme     | (1, CV)     | The scheme of the identifier.        |
         *  -----------------------------------------------------------------
         */
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = LocationDeserializer.LocationIdentifierDeserializer.class)
        public static class LocationIdentifier {
            @JsonProperty("identifier")
            String identifier; // This should be a controlled vocabulary but it is not defined
            @JsonProperty("scheme")
            String scheme;     // This should be a controlled vocabulary but it is not defined

            public LocationIdentifier(String identifier, String scheme) {
                this.identifier = identifier;
                this.scheme = scheme;
            }

            @Override
            public int hashCode() {
                int hash = 5;
                hash = 23 * hash + Objects.hashCode(this.identifier);
                hash = 23 * hash + Objects.hashCode(this.scheme);
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
                final LocationIdentifier other = (LocationIdentifier) obj;
                if (!Objects.equals(this.identifier, other.identifier)) {
                    return false;
                }
                return Objects.equals(this.scheme, other.scheme);
            }

            @Override
            public String toString() {
                return "LocationIdentifier{" + "identifier=" + identifier + ", scheme=" + scheme + '}';
            }
            
            
        }
        
        /**
         * See https://www.rfc-editor.org/rfc/rfc7946#section-3.1
         */
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = LocationDeserializer.LocationGeometryDeserializer.class)
        public static class LocationGeometry {
            @JsonProperty("type")
            String type;
            @JsonProperty("coordinates")
            ArrayList<Object> coordinates = new ArrayList<>();

            public LocationGeometry(String type, ArrayList<Object> coordinates) {
                this.type = type;
                this.coordinates.addAll(coordinates);
            }

            @Override
            public int hashCode() {
                int hash = 7;
                hash = 17 * hash + Objects.hashCode(this.type);
                hash = 17 * hash + Objects.hashCode(this.coordinates);
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
                final LocationGeometry other = (LocationGeometry) obj;
                if (!Objects.equals(this.type, other.type)) {
                    return false;
                }
                return Objects.equals(this.coordinates, other.coordinates);
            }

            @Override
            public String toString() {
                return "LocationGeometry{" + "type=" + type + ", coordinates=" + coordinates + '}';
            }
            
        }
        
        /**
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
         */
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = LocationDeserializer.LocationFeatureDeserializer.class)
        public static class LocationFeature {
            @JsonProperty("geometry")
            Optional<LocationGeometry> geometry = Optional.empty();
            @JsonProperty("identifiers")
            List<LocationIdentifier> identifiers;
            @JsonProperty("place")
            Optional<String> place = Optional.empty();
            @JsonProperty("description")
            Optional<String> description = Optional.empty();

            public LocationFeature(Optional<LocationGeometry> geometry, 
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

            @Override
            public int hashCode() {
                int hash = 3;
                hash = 19 * hash + Objects.hashCode(this.geometry);
                hash = 19 * hash + Objects.hashCode(this.identifiers);
                hash = 19 * hash + Objects.hashCode(this.place);
                hash = 19 * hash + Objects.hashCode(this.description);
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
                final LocationFeature other = (LocationFeature) obj;
                if (!Objects.equals(this.geometry, other.geometry)) {
                    return false;
                }
                if (!Objects.equals(this.identifiers, other.identifiers)) {
                    return false;
                }
                if (!Objects.equals(this.place, other.place)) {
                    return false;
                }
                return Objects.equals(this.description, other.description);
            }

            @Override
            public String toString() {
                return "LocationFeature{" + "geometry=" + geometry + ", identifiers=" + identifiers + ", place=" + place + ", description=" + description + '}';
            }
            
            
        }
        
        @JsonProperty("features")
        ArrayList<LocationFeature> features = new ArrayList<>();

        public Location(List<LocationFeature> features) {
            if (!features.isEmpty())
                this.features.addAll(features);
            else
                throw new IllegalArgumentException("At least one location feature required");
        }

        @Override
        protected Object clone() {
            return new Location((ArrayList<LocationFeature>) features.clone());
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 41 * hash + Objects.hashCode(this.features);
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
            final Location other = (Location) obj;
            return Objects.equals(this.features, other.features);
        }

        @Override
        public String toString() {
            return "Location{" + "features=" + features + '}';
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = FundingReferenceDeserializer.class)
    public static class FundingReference {
        /**
         * The funder subfields:
         *  _____________________________________________________________________
         * | Field | Cardinality | Description                                   |
         * | id    | (0-1, CV)   | The funder id from the controlled vocabulary. |
         * | name  | (0-1)       | The name of the funder.                       |
         *  ---------------------------------------------------------------------
         *
         * One of id OR name must be given. It's recommended to use name if there
         * is no matching id in the controlled vocabulary.
         */
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = FundingReferenceDeserializer.FunderDeserializer.class)
        public static class Funder {
            @JsonProperty("id")
            Optional<ControlledVocabulary.FunderId> id = Optional.empty();
            @JsonProperty("name")
            Optional<String> name = Optional.empty();

            public Funder(Optional<ControlledVocabulary.FunderId> id, Optional<String> name) {
                if (id.isPresent() || name.isPresent()) {
                    this.id = id;
                    this.name = name;
                }
                else
                    throw new IllegalArgumentException("Either id or name have to be given");
            }

            @Override
            protected Object clone() {
                return new Funder(id, name);
            }

            
            @Override
            public int hashCode() {
                int hash = 7;
                hash = 29 * hash + Objects.hashCode(this.id);
                hash = 29 * hash + Objects.hashCode(this.name);
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
                final Funder other = (Funder) obj;
                if (!Objects.equals(this.id, other.id)) {
                    return false;
                }
                return Objects.equals(this.name, other.name);
            }

            @Override
            public String toString() {
                return "Funder{" + "id=" + id + ", name=" + name + '}';
            }
            
            
        }
        /**
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
        @JsonInclude(JsonInclude.Include.NON_DEFAULT)
        @JsonDeserialize(using = FundingReferenceDeserializer.AwardDeserializer.class)
        public static class Award {
            @JsonProperty("id")
            Optional<ControlledVocabulary.AwardId> id = Optional.empty();
            @JsonProperty("title")
            LocalizedStrings title = new LocalizedStrings();
            @JsonProperty("number")
            Optional<String> number = Optional.empty();
            @JsonProperty("identifiers")
            ArrayList<AlternateIdentifier> identifiers = new ArrayList<>();

            public Award(Optional<ControlledVocabulary.AwardId> id, 
                    LocalizedStrings title, Optional<String> number,
                    ArrayList<AlternateIdentifier> identifiers) {
                if (id.isPresent() || (!title.isEmpty()&& number.isPresent())) {
                    this.id = id;
                    this.title.addAll(title);
                    this.number = number;
                    this.identifiers.addAll(identifiers);
                }
                else
                    throw new IllegalArgumentException("Either id or both title and number have to be given");
            }

            @Override
            protected Object clone() {
                return new Award(id, (LocalizedStrings) title.clone(), number, (ArrayList<AlternateIdentifier>) identifiers.clone());
            }

            
            @Override
            public int hashCode() {
                int hash = 5;
                hash = 19 * hash + Objects.hashCode(this.id);
                hash = 19 * hash + Objects.hashCode(this.title);
                hash = 19 * hash + Objects.hashCode(this.number);
                hash = 19 * hash + Objects.hashCode(this.identifiers);
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
                final Award other = (Award) obj;
                if (!Objects.equals(this.id, other.id)) {
                    return false;
                }
                if (!Objects.equals(this.title, other.title)) {
                    return false;
                }
                if (!Objects.equals(this.number, other.number)) {
                    return false;
                }
                return Objects.equals(this.identifiers, other.identifiers);
            }

            @Override
            public String toString() {
                return "Award{" + "id=" + id + ", title=" + title + ", number=" + number + ", identifiers=" + identifiers + '}';
            }
            
            
        }
        
        @JsonProperty("funder")
        Funder funder;
        @JsonProperty("award")
        Optional<Award> award;

        public FundingReference(Funder funder) {
            this.funder = funder;
        }

        public FundingReference setAward(Award award) {
            this.award = Optional.of(award);
            return this;
        }

        
        @Override
        protected Object clone() {
            FundingReference fundingReference = new FundingReference((Funder) this.funder.clone());
            if (this.award.isPresent())
                fundingReference.setAward(this.award.get());
            return fundingReference;
            
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 41 * hash + Objects.hashCode(this.funder);
            hash = 41 * hash + Objects.hashCode(this.award);
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
            final FundingReference other = (FundingReference) obj;
            if (!Objects.equals(this.funder, other.funder)) {
                return false;
            }
            return Objects.equals(this.award, other.award);
        }

        @Override
        public String toString() {
            return "FundingReference{" + "funder=" + funder + ", award=" + award + '}';
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
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    @JsonDeserialize(using = ReferenceDeserializer.class)
    public static class Reference {
        @JsonProperty("reference")
        String reference;
        @JsonProperty("scheme")
        Optional<ControlledVocabulary.ReferenceScheme> scheme = Optional.empty();
        @JsonProperty("identifier")
        Optional<String> identifier = Optional.empty();

        public Reference(String reference) {
            this.reference = reference;
        }

        public Reference setScheme(ControlledVocabulary.ReferenceScheme scheme) {
            this.scheme = Optional.of(scheme);
            return this;
        }

        public Reference setIdentifier(String identifier) {
            this.identifier = Optional.of(identifier);
            return this;
        }

        @Override
        protected Object clone() {
            Reference newReference = new Reference(this.reference);
            if (scheme.isPresent())
                newReference.setScheme(scheme.get());
            if (identifier.isPresent()) 
                newReference.setIdentifier(identifier.get());
            return newReference;
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + Objects.hashCode(this.reference);
            hash = 23 * hash + Objects.hashCode(this.scheme);
            hash = 23 * hash + Objects.hashCode(this.identifier);
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
            final Reference other = (Reference) obj;
            if (!Objects.equals(this.reference, other.reference)) {
                return false;
            }
            if (!Objects.equals(this.scheme, other.scheme)) {
                return false;
            }
            return Objects.equals(this.identifier, other.identifier);
        }

        @Override
        public String toString() {
            return "Reference{" + "reference=" + reference + ", schema=" + scheme + ", identifier=" + identifier + '}';
        }
    }
    
    /**
     * Constructor for all mandatory fields
     * @param resourceType the resource type
     * @param creators the list of creators
     * @param title the title of the resource
     * @param publicationDate the publication date
     */
    @JsonCreator
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
            ArrayList<Creator> creators,
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

    /**
     * Overwrites the title
     * @param title the new title
     * @return the updated metadata
     */
    @JsonIgnore
    public Metadata setTitle(String title) {
        this.title = title;
        return this;
    }

    /**
     * Overwrites the publication date
     * @param newPublicationDate the new publication date
     * @return the updated metadata
     */
    @JsonIgnore
    public Metadata setPublicationDate(ExtendedDateTimeFormat0 newPublicationDate) {
        this.publicationDate = newPublicationDate;
        return this;
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
            List<Date> dates) {
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
    
    public Metadata setRelatedIdentifiers(
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
        this.relatedIdentifiers = new ArrayList<>(relatedIdentifiers);
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
    
    public Metadata setLocations(
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
            Location locations) {
        this.locations = locations;
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

    public ResourceType getResourceType() {
        return resourceType;
    }

    public ArrayList<Creator> getCreators() {
        return creators;
    }

    public String getTitle() {
        return title;
    }

    public ExtendedDateTimeFormat0 getPublicationDate() {
        return publicationDate;
    }

    public List<AdditionalTitle> getAdditionalTitles() {
        return additionalTitles;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public List<AdditionalDescription> getAdditionalDescriptions() {
        return additionalDescriptions;
    }

    public List<License> getRights() {
        return rights;
    }

    public List<Contributor> getContributors() {
        return contributors;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public List<Date> getDates() {
        return dates;
    }

    public Optional<String> getVersion() {
        return version;
    }

    public Optional<String> getPublisher() {
        return publisher;
    }

    public List<AlternateIdentifier> getAlternativeIdentifiers() {
        return alternativeIdentifiers;
    }

    public List<RelatedIdentifier> getRelatedIdentifiers() {
        return relatedIdentifiers;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public List<String> getFormats() {
        return formats;
    }

    public Location getLocations() {
        return locations;
    }

    public List<FundingReference> getFundingReferences() {
        return fundingReferences;
    }

    public List<Reference> getReferences() {
        return references;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Metadata newMetadata = new Metadata((ResourceType) resourceType.clone(), (ArrayList < Creator >) creators.clone(), title, (ExtendedDateTimeFormat0) publicationDate.clone());
        newMetadata.addAdditionalTitles((ArrayList<AdditionalTitle>) additionalTitles.clone());
        if (description.isPresent())
            newMetadata.setDescription(description.get());
        newMetadata.addAdditionalDescriptions((List<AdditionalDescription>) additionalDescriptions.clone());
        newMetadata.addRights((List<License>) rights.clone());
        newMetadata.addContributors((List<Contributor>) contributors.clone());
        newMetadata.addSubjects((List<Subject>) subjects.clone());
        newMetadata.addLanguages((List<Language>) languages.clone());
        newMetadata.addDates((List<Date>) dates.clone());
        if (version.isPresent())
            newMetadata.setVersion(version.get());
        if (publisher.isPresent())
            newMetadata.setPublisher(publisher.get());
        newMetadata.addAlternativeIdentifiers((List<AlternateIdentifier>) alternativeIdentifiers.clone());
        newMetadata.addRelatedIdentifiers((List<RelatedIdentifier>) relatedIdentifiers.clone());
        newMetadata.addSizes((List<String>) sizes.clone());
        newMetadata.addFormats((List<String>) formats.clone());
        if (locations != null)
            newMetadata.setLocations((Location) locations.clone());
        newMetadata.addFundingReferences((List<FundingReference>) fundingReferences.clone());
        newMetadata.addReferences((List<Reference>) references.clone());
        return newMetadata;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.resourceType);
        hash = 53 * hash + Objects.hashCode(this.creators);
        hash = 53 * hash + Objects.hashCode(this.title);
        hash = 53 * hash + Objects.hashCode(this.publicationDate);
        hash = 53 * hash + Objects.hashCode(this.additionalTitles);
        hash = 53 * hash + Objects.hashCode(this.description);
        hash = 53 * hash + Objects.hashCode(this.additionalDescriptions);
        hash = 53 * hash + Objects.hashCode(this.rights);
        hash = 53 * hash + Objects.hashCode(this.contributors);
        hash = 53 * hash + Objects.hashCode(this.subjects);
        hash = 53 * hash + Objects.hashCode(this.languages);
        hash = 53 * hash + Objects.hashCode(this.dates);
        hash = 53 * hash + Objects.hashCode(this.version);
        hash = 53 * hash + Objects.hashCode(this.publisher);
        hash = 53 * hash + Objects.hashCode(this.alternativeIdentifiers);
        hash = 53 * hash + Objects.hashCode(this.relatedIdentifiers);
        hash = 53 * hash + Objects.hashCode(this.sizes);
        hash = 53 * hash + Objects.hashCode(this.formats);
        hash = 53 * hash + Objects.hashCode(this.locations);
        hash = 53 * hash + Objects.hashCode(this.fundingReferences);
        hash = 53 * hash + Objects.hashCode(this.references);
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
        final Metadata other = (Metadata) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.resourceType, other.resourceType)) {
            return false;
        }
        if (!Objects.equals(this.creators, other.creators)) {
            return false;
        }
        if (!Objects.equals(this.publicationDate, other.publicationDate)) {
            return false;
        }
        if (!Objects.equals(this.additionalTitles, other.additionalTitles)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.additionalDescriptions, other.additionalDescriptions)) {
            return false;
        }
        if (!Objects.equals(this.rights, other.rights)) {
            return false;
        }
        if (!Objects.equals(this.contributors, other.contributors)) {
            return false;
        }
        if (!Objects.equals(this.subjects, other.subjects)) {
            return false;
        }
        if (!Objects.equals(this.languages, other.languages)) {
            return false;
        }
        if (!Objects.equals(this.dates, other.dates)) {
            return false;
        }
        if (!Objects.equals(this.version, other.version)) {
            return false;
        }
        if (!Objects.equals(this.publisher, other.publisher)) {
            return false;
        }
        if (!Objects.equals(this.alternativeIdentifiers, other.alternativeIdentifiers)) {
            return false;
        }
        if (!Objects.equals(this.relatedIdentifiers, other.relatedIdentifiers)) {
            return false;
        }
        if (!Objects.equals(this.sizes, other.sizes)) {
            return false;
        }
        if (!Objects.equals(this.formats, other.formats)) {
            return false;
        }
        if (!Objects.equals(this.locations, other.locations)) {
            return false;
        }
        if (!Objects.equals(this.fundingReferences, other.fundingReferences)) {
            return false;
        }
        return Objects.equals(this.references, other.references);
    }

    @Override
    public String toString() {
        return "Metadata{" + "resourceType=" + resourceType + ", creators=" + creators + ", title=" + title + ", publicationDate=" + publicationDate + ", additionalTitles=" + additionalTitles + ", description=" + description + ", additionalDescriptions=" + additionalDescriptions + ", rights=" + rights + ", contributors=" + contributors + ", subjects=" + subjects + ", languages=" + languages + ", dates=" + dates + ", version=" + version + ", publisher=" + publisher + ", alternativeIdentifiers=" + alternativeIdentifiers + ", relatedIdentifiers=" + relatedIdentifiers + ", sizes=" + sizes + ", formats=" + formats + ", locations=" + locations + ", fundingReferences=" + fundingReferences + ", references=" + references + '}';
    }
    

}
