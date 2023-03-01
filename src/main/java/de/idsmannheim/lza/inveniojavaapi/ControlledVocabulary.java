/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.tuple.ImmutablePair;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class ControlledVocabulary {
    
    /**
     * See https://raw.githubusercontent.com/inveniosoftware/invenio-rdm-records/bc1c38991e602cd3a495f50cba7b1d4f868df07f/invenio_rdm_records/fixtures/data/vocabularies/resource_types.yaml
     */
    public static class ResourceType {
        
        public static enum EResourceType {
            Publication, PublicationAnnotationCollection, PublicationBook, 
            PublicationSection, PublicationConferencePaper, 
            PublicationDataManagementPlan, PublicationArticle, PublicationPatent, 
            PublicationPreprint, PublicationDeliverable, PublicationMilestone, 
            PublicationProposal, PublicationReport, 
            PublicationSoftwareDocumentation, PublicationTaxonomicTreatment, 
            PublicationTechnicalNote, PublicationThesis, PublicationWorkingPaper,
            PublicationOther, 
            Poster, Presentation, Dataset,
            Image, ImageFigure, ImagePlot, ImageDrawing, ImageDiagram, ImagePhoto,
            ImageOther,
            Video, Software, Lesson, Other
            
        }; 
        HashMap<ResourceType.EResourceType,String> types = null;
        
        List typeList = 
                List.of(
        new ImmutablePair<>(EResourceType.Publication, "publication"),
        new ImmutablePair<>(EResourceType.PublicationAnnotationCollection, "publication-annotationcollection"),
        new ImmutablePair<>(EResourceType.PublicationBook,"publication-book"),
        new ImmutablePair<>(EResourceType.PublicationSection, "publication-section"),
        new ImmutablePair<>(EResourceType.PublicationConferencePaper, "publication-conferencepaper"),
        new ImmutablePair<>(EResourceType.PublicationDataManagementPlan, "publication-datamanagementplan"),
        new ImmutablePair<>(EResourceType.PublicationArticle, "publication-article"),
        new ImmutablePair<>(EResourceType.PublicationPatent, "publication-patent"),
        new ImmutablePair<>(EResourceType.PublicationPreprint, "publication-preprint"),
        new ImmutablePair<>(EResourceType.PublicationDeliverable, "publication-deliverable"),
        new ImmutablePair<>(EResourceType.PublicationMilestone, "publication-milestone"),
        new ImmutablePair<>(EResourceType.PublicationProposal, "publication-proposal"),
        new ImmutablePair<>(EResourceType.PublicationReport, "publication-report"),
        new ImmutablePair<>(EResourceType.PublicationSoftwareDocumentation, "publication-softwaredocumentation"),
        new ImmutablePair<>(EResourceType.PublicationTaxonomicTreatment, "publication-taxonomictreatment"),
        new ImmutablePair<>(EResourceType.PublicationTechnicalNote, "publication-technicalnote"),
        new ImmutablePair<>(EResourceType.PublicationThesis, "publication-thesis"),
        new ImmutablePair<>(EResourceType.PublicationWorkingPaper, "publication-workingpaper"),
        new ImmutablePair<>(EResourceType.PublicationOther, "publication-other"),
        new ImmutablePair<>(EResourceType.Poster, "poster"),
        new ImmutablePair<>(EResourceType.Presentation, "presentation"),
        new ImmutablePair<>(EResourceType.Dataset, "dataset"),
        new ImmutablePair<>(EResourceType.Image, "image"),
        new ImmutablePair<>(EResourceType.ImageFigure, "image-figure"),
        new ImmutablePair<>(EResourceType.ImagePlot, "image-plot"),
        new ImmutablePair<>(EResourceType.ImageDrawing, "image-drawing"),
        new ImmutablePair<>(EResourceType.ImageDiagram, "image-diagram"),
        new ImmutablePair<>(EResourceType.ImagePhoto, "image-photo"),
        new ImmutablePair<>(EResourceType.ImageOther, "image-other"),
        new ImmutablePair<>(EResourceType.Video, "video"),
        new ImmutablePair<>(EResourceType.Software, "software"),
        new ImmutablePair<>(EResourceType.Lesson, "lesson"),
        new ImmutablePair<>(EResourceType.Other, "other")
                );
        
        ResourceType.EResourceType resourceType = null;
        
        @JsonCreator
        public ResourceType(String resourceTypeString) {
            this.types = new HashMap();
            for (ImmutablePair<ResourceType.EResourceType, String> type : 
                    (List<ImmutablePair<ResourceType.EResourceType,String>>) typeList) {
                if (type.getValue().equals(resourceTypeString)) {
                    resourceType=type.getKey();
                }
                types.put(type.getKey(),type.getValue());
            }
            if (resourceType == null)
                throw new IllegalArgumentException("Invalid resource type: " + resourceTypeString);
        }
        
        public ResourceType(ResourceType.EResourceType resourceType) {
            this.types = new HashMap();
            for (ImmutablePair<ResourceType.EResourceType, String> type : 
                    (List<ImmutablePair<ResourceType.EResourceType,String>>) typeList) {
                types.put(type.getKey(),type.getValue());
            }
            this.resourceType = resourceType;
        }
        
        @JsonValue
        @Override
        public String toString() {
            return types.get(resourceType);
        }

        @Override
        protected Object clone() {
            return new ResourceType(resourceType);
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 23 * hash + Objects.hashCode(this.resourceType);
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
            return this.resourceType == other.resourceType;
        }
        
        
    }
    
    /**
     * See https://inveniordm.docs.cern.ch/reference/metadata/#related-identifiersworks-0-n
     */
    public static class RelatedResourceType extends ResourceType {

        // Currently simply the same as ResourceType
        public RelatedResourceType(String resourceTypeString) {
            super(resourceTypeString);
        }

        public RelatedResourceType(EResourceType resourceType) {
            super(resourceType);
        }
        
        
    }
    
    /**
     * See https://inveniordm.docs.cern.ch/reference/metadata/#creators-1-n
     * 
     * Supported creator identifier schemes:
     * - ORCID
     * - GND
     * - ISNI
     * - ROR
     * 
     * Supported affiliation identifier schemes:
     * - ISNI
     * - ROR
     * 
     * Note that the identifiers' schemes are passed lowercased e.g. ORCID is orcid.
     * 
     */
    public static class PersonOrOrgIdentifierScheme {
        public static enum EScheme {
            // Supported creator identifier schemes:
            ORCID, GND,
            // Supported affiliation identifier schemes:
            ISNI, ROR
        }

        PersonOrOrgIdentifierScheme.EScheme scheme;
        
        public PersonOrOrgIdentifierScheme(PersonOrOrgIdentifierScheme.EScheme scheme) {
            this.scheme = scheme;
        }

        public PersonOrOrgIdentifierScheme(String scheme) {
            this.scheme =EScheme.valueOf(scheme.toUpperCase());
        }
        
        @JsonValue
        @Override
        public String toString() {
            return scheme.toString().toLowerCase();
        }

        @Override
        protected Object clone() {
            return new PersonOrOrgIdentifierScheme(scheme);
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.scheme);
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
            final PersonOrOrgIdentifierScheme other = (PersonOrOrgIdentifierScheme) obj;
            return this.scheme == other.scheme;
        }
        
        
    }
    
    /**
     * See https://raw.githubusercontent.com/inveniosoftware/invenio-rdm-records/bc1c38991e602cd3a495f50cba7b1d4f868df07f/invenio_rdm_records/fixtures/data/vocabularies/roles.yaml
     */
    public static class Role {
        public static enum ERole {
            ContactPerson,
            DataCollector,
            DataCurator,
            DataManager,
            Distributor,
            Editor,
            HostingInstitution,
            Producer,
            ProjectLeader,
            ProjectManager,
            ProjectMember,
            RegistrationAgency,
            RegistrationAuthority,
            RelatedPerson,
            Researcher,
            ResearchGroup,
            RightsHolder,
            Sponsor,
            Supervisor,
            WorkPackageLeader,
            Other
        }

        Role.ERole role;
        
        public Role(Role.ERole role) {
            this.role = role;
        }
        
        public Role(String role) {
            for (ERole e : ERole.values()) {
                if (e.toString().toLowerCase().equals(role)) {
                    this.role = e;
                }
            }
            if (this.role == null) {
                throw new IllegalArgumentException("Invalid role: " + role);
            }
        }
        @Override
        @JsonValue
        public String toString() {
            return role.toString().toLowerCase();
        }

        @Override
        protected Object clone() {
            return new Role(role);
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + Objects.hashCode(this.role);
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
            return this.role == other.role;
        }
        
        
    }

    /**
     * See https://raw.githubusercontent.com/inveniosoftware/invenio-rdm-records/bc1c38991e602cd3a495f50cba7b1d4f868df07f/invenio_rdm_records/fixtures/data/vocabularies/affiliations_ror.yaml
     */
    public static class OrganizationalOrInstitutionalId {
        
        public static enum EOrganization {
            CERN,                       // European Organization for Nuclear Research
            BNL,                        // Brookhaven National Laboratory
            EkoKonnect,                 // Eko-Konnect
            HZDR,                       // Helmholtz-Zentrum Dresden-Rossendorf
            INFN,                       // National Institute for Nuclear Physics
            JRC,                        // Directorate-General Joint Research Centre
            NU,                         // Northwestern University
            Tind,                       // Tind Technologies (Norway)
            ULAKBIM,                    // Turkish Academic Network and Information Center
            GrazUniversityOfTechnology, // Graz University of Technology
            TUW,                        // TU Wien
            UHH,                        // Universität Hamburg
            WWU,                        // University of Münster
            WACREN,                     // West and Central African Research and Education Network
            CIT                         // California Institute of Technology
        }
        
        List<ImmutablePair<EOrganization,String>> organizationsList = new ArrayList<>(
                List.of(
                        new ImmutablePair<>(EOrganization.CERN, "01ggx4157"),      //European Organization for Nuclear Research
                        new ImmutablePair<>(EOrganization.BNL, "02ex6cf31"),       //Brookhaven National Laboratory
                        new ImmutablePair<>(EOrganization.EkoKonnect, "047h63042"), // Eko-Konnect
                        new ImmutablePair<>(EOrganization.HZDR, "01zy2cs03"),      // Helmholtz-Zentrum Dresden-Rossendorf
                        new ImmutablePair<>(EOrganization.INFN, "005ta0471"),      // National Institute for Nuclear Physics
                        new ImmutablePair<>(EOrganization.JRC, "04j5wtv36"),       // Directorate-General Joint Research Centre
                        new ImmutablePair<>(EOrganization.NU, "000e0be47"),        // Northwestern University
                        new ImmutablePair<>(EOrganization.Tind, "04mcehe57"),      // Tind Technologies (Norway)
                        new ImmutablePair<>(EOrganization.ULAKBIM, "017k52s24"),   // Turkish Academic Network and Information Center
                        new ImmutablePair<>(EOrganization.GrazUniversityOfTechnology, "00d7xrm67"), // Graz University of Technology
                        new ImmutablePair<>(EOrganization.TUW, "04d836q62"),       // TU Wien
                        new ImmutablePair<>(EOrganization.UHH, "00g30e956"),       // Universität Hamburg
                        new ImmutablePair<>(EOrganization.WWU, "00pd74e08"),       // University of Münster
                        new ImmutablePair<>(EOrganization.WACREN, "006rqpt26"),    // West and Central African Research and Education Network
                        new ImmutablePair<>(EOrganization.CIT, "05dxps055")        // California Institute of Technology
            ));
        HashMap<EOrganization,String> organizations = new HashMap<>();
        OrganizationalOrInstitutionalId.EOrganization organization;
        
        public OrganizationalOrInstitutionalId(OrganizationalOrInstitutionalId.EOrganization organization) {
            for (ImmutablePair<EOrganization,String> pair : organizationsList) {
                organizations.put(pair.getKey(), pair.getValue());
            }
            this.organization = organization;
        }
        
        
        public OrganizationalOrInstitutionalId(String organization) {
            for (ImmutablePair<EOrganization,String> pair : organizationsList) {
                organizations.put(pair.getKey(), pair.getValue());
                if (pair.getValue().equals(organization)) {
                    this.organization = pair.getKey();
                }
            }
            if (this.organization == null)
                throw new IllegalArgumentException("Invalid organization: " + organization);
        }
        
        @JsonValue
        @Override
        public String toString() {
            return organizations.get(organization);
        }

        @Override
        protected Object clone() {
            return new OrganizationalOrInstitutionalId(organization);
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 11 * hash + Objects.hashCode(this.organization);
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
            final OrganizationalOrInstitutionalId other = (OrganizationalOrInstitutionalId) obj;
            return this.organization == other.organization;
        }
        
        
    }

    /**
     * See https://raw.githubusercontent.com/inveniosoftware/invenio-rdm-records/master/invenio_rdm_records/fixtures/data/vocabularies/languages.yaml
     * 
     * Both ISO-639-1 and ISO-639-3 language codes loaded from a resource
     */
    public static class LanguageId {

        public static class LanguageInfo {
            String id3;
            Optional<String> id2;
            String name;

            public LanguageInfo(String id3, Optional<String> id2, String name) {
                this.id3 = id3;
                this.id2 = id2;
                this.name = name;
            }
            
            @Override
            public String toString() {
                return "LanguageInfo{" + "id3=" + id3 + ", id2=" + id2 + ", name=" + name + '}';
            }
            
        }
        
        
        Optional<String> id2 = Optional.empty();
        Optional<String> id3 = Optional.empty();
        
        private LanguageId() throws IOException {
            
        }
        
        private LanguageId setId2(String id2) throws IllegalArgumentException {
            this.id2 = Optional.of(id2);
            return this;
        }
        
        private LanguageId setId3(String id3) throws IllegalArgumentException{
            this.id3 = Optional.of(id3);
            return this;
            
        }

        @Override
        public String toString() {
            if (id2.isPresent() && !id2.isEmpty())
                return id2.get();
            else if (id3.isPresent() && !id3.isEmpty())
                return id3.get();
            else
                return "";
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            try {
                LanguageId languageId = new LanguageId();
                if (id2.isPresent())
                    languageId.setId2(id2.get());
                if (id3.isPresent())
                    languageId.setId3(id3.get());
                return languageId;
            }
            catch (IOException e) {
                throw new CloneNotSupportedException("Encountered exception when cloning. " + e.toString());
            }
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hashCode(this.id2);
            hash = 97 * hash + Objects.hashCode(this.id3);
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
            final LanguageId other = (LanguageId) obj;
            if (!Objects.equals(this.id2, other.id2)) {
                return false;
            }
            return Objects.equals(this.id3, other.id3);
        }
        
        
    }

    public static class LanguageIdFactory {

        ArrayList<LanguageId.LanguageInfo> languages = new ArrayList<>();
        
        public LanguageIdFactory() throws IOException {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            
            TreeNode tree = objectMapper.createParser(this.getClass().getClassLoader()
                    .getResourceAsStream("languages.yaml"))
                    .readValueAsTree();
            for (int ct = 0 ; ct < tree.size(); ct++) {
                Optional<String> id2 = 
                        tree.get(ct).get("props").get("alpha_2").toString().replace("\"", "").isBlank() 
                        ? Optional.empty() 
                        : Optional.of(tree.get(ct).get("props").get("alpha_2").toString().replace("\"", ""));
                LanguageId.LanguageInfo langInfo = new LanguageId.LanguageInfo(tree.get(ct).get("id").toString().replace("\"", ""),
                        id2,
                        tree.get(ct).get("title").get("en").toString().replace("\"", ""));
                languages.add(langInfo);
            }
        }
        
        
        public LanguageId usingId2(String id2) throws IOException {
            if (languages.stream().anyMatch(info -> info.id2.isPresent() && 
                    info.id2.get().equals(id2))) {
                return new LanguageId().setId2(id2);
            }
            else {
                throw new IllegalArgumentException("No such ISO-639-1 language code");
            }
            
        }
        
        public LanguageId usingId3(String id3) throws IOException {
            if (languages.stream().anyMatch(info -> info.id3.equals(id3))) {
                return new LanguageId().setId3(id3);
            }
            else {
                throw new IllegalArgumentException("No such ISO-639-3 language code");
            }
            
        }

        /**
         * Convert an ISO-639-1 code to ISO-639-3
         * @param id2 the two letter ISO-639-1 code
         * @return the three letter ISO-639-3 code
         */
        public String id2toId3(String id2) {
            Optional<LanguageId.LanguageInfo> code = languages.stream()
                    .filter(info -> info.id2.isPresent() && info.id2.get().equals(id2))
                    .findFirst();
            return code.orElseThrow(() -> new IllegalArgumentException("No such ISO-639-1 code: " + id2))
                    .id3;
            
        }
        
        /**
         * Convert an ISO-639-3 code to ISO-639-1
         * @param id3 the three letter ISO-639-3 code
         * @return the two letter ISO-639-1 code
         */
        public String id3toId2(String id3) {
            Optional<LanguageId.LanguageInfo> code = languages.stream()
                    .filter(info -> info.id3.equals(id3))
                    .findFirst();
            return code.orElseThrow(() -> new IllegalArgumentException("No such ISO-639-3 code: " + id3))
                    .id2.orElseThrow(() -> new IllegalArgumentException("No ISO-639-1 code for ISO-639-3 code: " + id3));
        }
    }
    
    /**
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/master/invenio_rdm_records/fixtures/data/vocabularies/title_types.yaml
     */
    public static class TitleTypeId {
        
        public static enum ETitleType {
            AlternativeTitle,
            Subtitle,
            TranslatedTitle,
            Other 
        };
        HashMap<TitleTypeId.ETitleType, String> titleTypeMap = new HashMap<>();
        List<ImmutablePair<ETitleType, String>> titleTypes = List.of(
                new ImmutablePair<>(ETitleType.AlternativeTitle, "alternative-title"),
                new ImmutablePair<>(ETitleType.Subtitle, "subtitle"),
                new ImmutablePair<>(ETitleType.TranslatedTitle, "translated-title"),
                new ImmutablePair<>(ETitleType.Other, "other")
        );
        TitleTypeId.ETitleType type;
        public TitleTypeId(TitleTypeId.ETitleType type) {
            for (ImmutablePair<ETitleType,String> pair : titleTypes) {
                titleTypeMap.put(pair.getKey(), pair.getValue());
            }
            this.type = type;
        }

        public TitleTypeId(String type) {
            for (ImmutablePair<ETitleType,String> pair : titleTypes) {
                titleTypeMap.put(pair.getKey(), pair.getValue());
                if (pair.getValue().equals(type))
                    this.type = pair.getKey();
            }
            if (this.type == null)
                throw new IllegalArgumentException("Invalid type: " + type);
        }
        @Override
        @JsonValue
        public String toString() {
            return titleTypeMap.get(type);
        }

        @Override
        protected Object clone() {
            return new TitleTypeId(type);
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 59 * hash + Objects.hashCode(this.type);
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
            final TitleTypeId other = (TitleTypeId) obj;
            return this.type == other.type;
        }
        
        
    }
    
    /**
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/master/invenio_rdm_records/fixtures/data/vocabularies/description_types.yaml
     */
    public static class DescriptionTypeId {
        
        public static enum EDescriptionType {
            Abstract,
            Methods,
            SeriesInformation,
            TableOfContents,
            TechnicalInfo,
            Other 
        };
        HashMap<DescriptionTypeId.EDescriptionType, String> descriptionTypeMap = new HashMap<>();
        DescriptionTypeId.EDescriptionType type;
        
        List<ImmutablePair<EDescriptionType, String>> descriptionTypes = List.of(
                new ImmutablePair<>(EDescriptionType.Abstract, "abstract"),
                new ImmutablePair<>(EDescriptionType.Methods, "methods"),
                new ImmutablePair<>(EDescriptionType.SeriesInformation, "series-information"),
                new ImmutablePair<>(EDescriptionType.TableOfContents, "table-of-contents"),
                new ImmutablePair<>(EDescriptionType.TechnicalInfo, "technical-info"),
                new ImmutablePair<>(EDescriptionType.Other, "other")
        );
        public DescriptionTypeId(DescriptionTypeId.EDescriptionType type) {
            for (ImmutablePair<EDescriptionType,String> pair : descriptionTypes) {
                descriptionTypeMap.put(pair.getKey(), pair.getValue());
            }
            this.type = type;
        }

        public DescriptionTypeId(String type) {
            for (ImmutablePair<EDescriptionType,String> pair : descriptionTypes) {
                descriptionTypeMap.put(pair.getKey(), pair.getValue());
                if (pair.getValue().equals(type))
                    this.type = pair.getKey();
            }
            if (this.type == null)
                throw new IllegalArgumentException("Invalid type: " + type);
        }
        
        @Override
        @JsonValue
        public String toString() {
            return descriptionTypeMap.get(type);
        }

        @Override
        protected Object clone() {
            return new DescriptionTypeId(type);
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 83 * hash + Objects.hashCode(this.type);
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
            final DescriptionTypeId other = (DescriptionTypeId) obj;
            return this.type == other.type;
        }
        
        
    }

    /**
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/master/invenio_rdm_records/fixtures/data/vocabularies/date_types.yaml
     */
    public static class DateTypeId {

        public static enum EDateType {
            Accepted,
            Available,
            Collected,
            Copyrighted,
            Created,
            Issued,
            Other,
            Submitted,
            Updated,
            Valid,
            Withdrawn
            
        }
        
        EDateType dateType;

        public DateTypeId(EDateType dateType) {
            this.dateType = dateType;
        }

        public DateTypeId(String dateType) {
            Optional<EDateType> candidate = Arrays.asList(EDateType.values())
                    .stream().filter(v -> v.toString().equalsIgnoreCase(dateType)).findFirst();
            if (candidate.isPresent())
                this.dateType = candidate.get();
            else
                throw new IllegalArgumentException("Invalid DateTypId: " + dateType);
        }
        
        @Override
        @JsonValue
        public String toString() {
            return dateType.toString().toLowerCase();
        }

        @Override
        protected Object clone() {
            return new DateTypeId(dateType);
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 53 * hash + Objects.hashCode(this.dateType);
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
            final DateTypeId other = (DateTypeId) obj;
            return this.dateType == other.dateType;
        }
        
        
    }

    /** 
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/3af080dd1c25f8dc08f33d8540858c5b97e3851e/invenio_rdm_records/config.py#L59
     */
    public static class RecordIdentifierScheme {
        public static enum ERecordItentifierScheme {
            ARK, ArXiv, Bibcode, DOI, EAN13, EISSN, Handle, IGSN, ISBN, ISSN,
            ISTC, LISSN, LSID, PMID, PURL, UPC, URL, URN, W3ID
        }
        
        ERecordItentifierScheme scheme;

        public RecordIdentifierScheme(ERecordItentifierScheme scheme) {
            this.scheme = scheme;
        }
        
        public RecordIdentifierScheme(String scheme) {
            Optional<ERecordItentifierScheme> candidate = Arrays.asList(ERecordItentifierScheme.values())
                    .stream().filter(v -> v.toString().equalsIgnoreCase(scheme)).findFirst();
            if (candidate.isPresent())
                this.scheme = candidate.get();
            else
                throw new IllegalArgumentException("Invalid RecordIdentifierScheme: " + scheme);
        }
        
        @Override
        @JsonValue
        public String toString() {
            return scheme.toString().toLowerCase();
        }

        @Override
        protected Object clone() {
            return new RecordIdentifierScheme(scheme);
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.scheme);
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
            final RecordIdentifierScheme other = (RecordIdentifierScheme) obj;
            return this.scheme == other.scheme;
        }
        
        
    }

    /**
     * See https://raw.githubusercontent.com/inveniosoftware/invenio-rdm-records/master/invenio_rdm_records/fixtures/data/vocabularies/relation_types.yaml
     */
    public static class RelationTypeId {
        public static enum ERelationTypeId {
            IsCitedBy, Cites, IsSupplementTo, IsSupplementedBy, IsContinuedBy,
            Continues, IsDescribedBy, Describes, HasVersion, IsVersionOf,
            IsNewVersionOf, IsPreviousVersionOf, IsPartOf, HasPart,
            IsReferencedBy, References, IsDocumentedBy, Documents, IsCompiledBy,
            Compiles, IsVariantFormOf, IsOriginalFormOf, IsIdenticalTo,
            IsReviewedBy, Reviews, IsDerivedFrom, IsSourceOf, IsRequiredBy, 
            Requires, IsObsoletedBy, Obsoletes
        }
        
        ERelationTypeId relation;
        
        public RelationTypeId(ERelationTypeId relation) {
            this.relation = relation;
        }
        
        public RelationTypeId(String relation) {
            Optional<ERelationTypeId> candidate = Arrays.asList(ERelationTypeId.values())
                    .stream().filter(v -> v.toString().equalsIgnoreCase(relation)).findFirst();
            if (candidate.isPresent())
                this.relation = candidate.get();
            else
                throw new IllegalArgumentException("Invalid RelationTypeId: " + relation);
        }
        
        @Override
        @JsonValue
        public String toString() {
            return relation.toString().toLowerCase();
        }

        @Override
        protected Object clone() {
            return new RelationTypeId(relation);
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 13 * hash + Objects.hashCode(this.relation);
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
            final RelationTypeId other = (RelationTypeId) obj;
            return this.relation == other.relation;
        }
        
        
    }

    /**
     * See https://inveniordm.docs.cern.ch/reference/metadata/#related-identifiersworks-0-n
     */
    public static class RelatedRecordIdentifierScheme {

        public static enum ERelatedRecordIdentifierScheme {
            ISBN10, ISBN13, ISSN, ISTC, DOI, Handle, EAN8, EAN13, ISNI, ORCID,
            ARK, PURL, LSID, URN, URL, Bibcode, arXiv, PubMedID, PubMedCentralID,
            GND, SRA, BioProject, BioSample, Ensembl, UniProt, RefSeq, 
            GenomeAssembly
        }
        
        ERelatedRecordIdentifierScheme scheme;
        
        public RelatedRecordIdentifierScheme(ERelatedRecordIdentifierScheme scheme) {
            this.scheme = scheme;
        }
        
        public RelatedRecordIdentifierScheme(String scheme) {
            Optional<ERelatedRecordIdentifierScheme> candidate = Arrays.asList(ERelatedRecordIdentifierScheme.values())
                    .stream().filter(v -> v.toString().equalsIgnoreCase(scheme)).findFirst();
            if (candidate.isPresent())
                this.scheme = candidate.get();
            else
                throw new IllegalArgumentException("Invalid RelatedRecordIdentifierScheme: " + scheme);
        }
        
        @Override
        @JsonValue
        public String toString() {
            return scheme.toString().toLowerCase();
        }

        @Override
        protected Object clone() {
            return new RelatedRecordIdentifierScheme(scheme);
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 37 * hash + Objects.hashCode(this.scheme);
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
            final RelatedRecordIdentifierScheme other = (RelatedRecordIdentifierScheme) obj;
            return this.scheme == other.scheme;
        }
        
        
    }

    /**
     * See https://inveniordm.docs.cern.ch/reference/metadata/#references-0-n
     */
    public static class ReferenceScheme {

        public static enum EReferenceScheme {
            CrossRefFunderID, GRID, ISNI, Other
        }
        EReferenceScheme scheme;

        public ReferenceScheme(EReferenceScheme scheme) {
            this.scheme = scheme;
        }
        
        public ReferenceScheme(String scheme) {
            Optional<EReferenceScheme> candidate = Arrays.asList(EReferenceScheme.values())
                    .stream().filter(v -> v.toString().equalsIgnoreCase(scheme)).findFirst();
            if (candidate.isPresent())
                this.scheme = candidate.get();
            else
                throw new IllegalArgumentException("Invalid EReferenceScheme: " + scheme);
        }
        
        @Override
        @JsonValue
        public String toString() {
            return scheme.toString().toLowerCase();
        }

        @Override
        protected Object clone() {
            return new ReferenceScheme(scheme);
        }

        
        @Override
        public int hashCode() {
            int hash = 3;
            hash = 79 * hash + Objects.hashCode(this.scheme);
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
            final ReferenceScheme other = (ReferenceScheme) obj;
            return this.scheme == other.scheme;
        }
        
        
    }
    
    /**
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/master/invenio_rdm_records/fixtures/data/vocabularies/funders.yaml
     */
    public static class FunderId {
        
        String id;
        
        class FunderInfo {
            String id;
            Optional<String> name;

            public FunderInfo(String id, Optional<String> name) {
                this.id = id;
                this.name = name;
            }

            @Override
            public String toString() {
                return "FunderInfo{" + "id=" + id + ", name=" + name + '}';
            }

            @Override
            public int hashCode() {
                int hash = 3;
                hash = 23 * hash + Objects.hashCode(this.id);
                hash = 23 * hash + Objects.hashCode(this.name);
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
                final FunderInfo other = (FunderInfo) obj;
                if (!Objects.equals(this.id, other.id)) {
                    return false;
                }
                return Objects.equals(this.name, other.name);
            }
            
            
        }
        ArrayList<FunderInfo> funders = new ArrayList<>();
        private FunderId() throws IOException {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            
            TreeNode tree = objectMapper.createParser(this.getClass().getClassLoader()
                    .getResourceAsStream("funders.yaml"))
                    .readValueAsTree();
            for (int ct = 0 ; ct < tree.size(); ct++) {
                Optional<String> name = 
                        tree.get(ct).get("name").toString().isBlank() 
                        ? Optional.empty() 
                        : Optional.of(tree.get(ct).get("name").toString().replace("\"", ""));
                funders.add(new FunderInfo(tree.get(ct).get("id").toString(), name));
            }
        }
        
        public FunderId setId(String id) {
            this.id = id;
            return this;
        }
        
        // TODO
//        public FunderId setName(String id) {
//            // this.id = 
//            return this;
//        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            try {
                return FunderIdFactory.usingId(id);
            } catch (IOException e) {
                throw new CloneNotSupportedException("Encountered exception when cloning. " + e.toString());
            }
        }

        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 89 * hash + Objects.hashCode(this.id);
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
            final FunderId other = (FunderId) obj;
            return Objects.equals(this.id, other.id);
        }

        @Override
        @JsonValue
        public String toString() {
            return id;
        }
        
        
    }
    
    
    /**
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/master/invenio_rdm_records/fixtures/data/vocabularies/awards.yaml
     * Currently empty -> use a simple string instead of a controlled vocabulary
     */
    public static class AwardId {

        String id;
        
        public AwardId(String id) {
            this.id = id;
        }

        @Override
        @JsonValue
        public String toString() {
            return id;
        }

        @Override
        protected Object clone() {
            return new AwardId(id);
        }

        
        @Override
        public int hashCode() {
            int hash = 5;
            hash = 73 * hash + Objects.hashCode(this.id);
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
            final AwardId other = (AwardId) obj;
            return Objects.equals(this.id, other.id);
        }
        
        
        
    }
    
    public static class FunderIdFactory {
        public static FunderId usingId(String id) throws IOException {
            return new FunderId().setId(id);
        }
        
        // TODO
//        public static FunderId usingName(String name) throws IOException {
//            return new FunderId().setName(name);
//        }
    }
}
