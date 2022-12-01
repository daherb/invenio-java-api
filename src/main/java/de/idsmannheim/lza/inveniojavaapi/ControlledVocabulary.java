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
import java.util.HashMap;
import java.util.List;
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
                throw new IllegalArgumentException("Invalid resource type");
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

        @JsonValue
        @Override
        public String toString() {
            return scheme.toString().toLowerCase();
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
        
        @Override
        public String toString() {
            return role.toString().toLowerCase();
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
        
        HashMap<EOrganization,String> organizations = new HashMap<>();
        OrganizationalOrInstitutionalId.EOrganization organization;
        public OrganizationalOrInstitutionalId(OrganizationalOrInstitutionalId.EOrganization organization) {
            organizations.put(EOrganization.CERN, "01ggx4157");      //European Organization for Nuclear Research
            organizations.put(EOrganization.BNL, "02ex6cf31");       //Brookhaven National Laboratory
            organizations.put(EOrganization.EkoKonnect, "047h63042"); // Eko-Konnect
            organizations.put(EOrganization.HZDR, "01zy2cs03");      // Helmholtz-Zentrum Dresden-Rossendorf
            organizations.put(EOrganization.INFN, "005ta0471");      // National Institute for Nuclear Physics
            organizations.put(EOrganization.JRC, "04j5wtv36");       // Directorate-General Joint Research Centre
            organizations.put(EOrganization.NU, "000e0be47");        // Northwestern University
            organizations.put(EOrganization.Tind, "04mcehe57");      // Tind Technologies (Norway)
            organizations.put(EOrganization.ULAKBIM, "017k52s24");   // Turkish Academic Network and Information Center
            organizations.put(EOrganization.GrazUniversityOfTechnology, "00d7xrm67"); // Graz University of Technology
            organizations.put(EOrganization.TUW, "04d836q62");       // TU Wien
            organizations.put(EOrganization.UHH, "00g30e956");       // Universität Hamburg
            organizations.put(EOrganization.WWU, "00pd74e08");       // University of Münster
            organizations.put(EOrganization.WACREN, "006rqpt26");    // West and Central African Research and Education Network
            organizations.put(EOrganization.CIT, "05dxps055");       // California Institute of Technology
            
            this.organization = organization;
        }
        
        @JsonValue
        @Override
        public String toString() {
            return organizations.get(organization);
        }
    }

    /**
     * See https://raw.githubusercontent.com/inveniosoftware/invenio-rdm-records/master/invenio_rdm_records/fixtures/data/vocabularies/languages.yaml
     * 
     * Both ISO-639-2 and ISO-639-3 language codes loaded from a resource
     */
    public static class Language {

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
                return id3;
            }
        }
        
        ArrayList<LanguageInfo> languages = new ArrayList<>();
        
        Optional<String> id2 = Optional.empty();
        Optional<String> id3 = Optional.empty();
        
        public Language() throws IOException {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            
            TreeNode tree = objectMapper.createParser(this.getClass().getClassLoader()
                    .getResourceAsStream("languages.yaml"))
                    .readValueAsTree();
            for (int ct = 0 ; ct < tree.size(); ct++) {
                Optional<String> id2 = 
                        tree.get(ct).get("props").get("alpha_2").toString().isBlank() 
                        ? Optional.empty() 
                        : Optional.of(tree.get(ct).get("props").get("alpha_2").toString());
                LanguageInfo langInfo = new LanguageInfo(tree.get(ct).get("id").toString(),
                        id2,
                        tree.get(ct).get("title").get("en").toString());
                languages.add(langInfo);
            }
            
        }
        
        public Language setId2(String id2) throws IllegalArgumentException {
            if (languages.stream().anyMatch(info -> info.id2.isPresent() && 
                    info.id2.get().equals(id2))) {
                this.id2 = Optional.of(id2);
                return this;
            }
            else {
                throw new IllegalArgumentException("No such ISO-639-2 language code");
            }
        }
        
        public Language setId3(String id3) throws IllegalArgumentException{
            if (languages.stream().anyMatch(info -> info.id3.equals(id3))) {
                this.id3 = Optional.of(id3);
                return this;
            }
            else {
                throw new IllegalArgumentException("No such ISO-639-3 language code");
            }
        }
    }

    /**
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/master/invenio_rdm_records/fixtures/data/vocabularies/title_types.yaml
     */
    static class TitleTypeId {
        
        public static enum ETitleType {
            AlternativeTitle,
            Subtitle,
            TranslatedTitle,
            Other 
        };
        HashMap<TitleTypeId.ETitleType, String> titleTypes = new HashMap<>();
        TitleTypeId.ETitleType type;
        public TitleTypeId(TitleTypeId.ETitleType type) {
            titleTypes.put(ETitleType.AlternativeTitle, "alternative-title");
            titleTypes.put(ETitleType.Subtitle, "subtitle");
            titleTypes.put(ETitleType.TranslatedTitle, "translated-title");
            titleTypes.put(ETitleType.Other, "other");
            this.type = type;
        }

        @Override
        public String toString() {
            return titleTypes.get(type);
        }
        
        
    }
    
    /**
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/master/invenio_rdm_records/fixtures/data/vocabularies/description_types.yaml
     */
    static class DescriptionTypeId {
        
        public static enum EDescriptionType {
            Abstract,
            Methods,
            SeriesInformation,
            TableOfContents,
            TechnicalInfo,
            Other 
        };
        HashMap<DescriptionTypeId.EDescriptionType, String> descriptionTypes = new HashMap<>();
        DescriptionTypeId.EDescriptionType type;
        public DescriptionTypeId(DescriptionTypeId.EDescriptionType type) {
            descriptionTypes.put(EDescriptionType.Abstract, "abstract");
            descriptionTypes.put(EDescriptionType.Methods, "methods");
            descriptionTypes.put(EDescriptionType.SeriesInformation, "series-information");
            descriptionTypes.put(EDescriptionType.TableOfContents, "table-of-contents");
            descriptionTypes.put(EDescriptionType.TechnicalInfo, "technical-info");
            descriptionTypes.put(EDescriptionType.Other, "other");
            this.type = type;
        }

        @Override
        public String toString() {
            return descriptionTypes.get(type);
        }
        
        
    }

    /**
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/master/invenio_rdm_records/fixtures/data/vocabularies/date_types.yaml
     */
    static class DateTypeId {

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

        @Override
        public String toString() {
            return dateType.toString().toLowerCase();
        }
    }

    /** 
     * See https://github.com/inveniosoftware/invenio-rdm-records/blob/3af080dd1c25f8dc08f33d8540858c5b97e3851e/invenio_rdm_records/config.py#L59
     */
    static class RecordIdentifierScheme {
        public static enum ERecordItentifierScheme {
            ARK, ArXiv, Bibcode, DOI, EAN13, EISSN, Handle, IGSN, ISBN, ISSN,
            ISTC, LISSN, LSID, PMID, PURL, UPC, URL, URN, W3ID
        }
        
        ERecordItentifierScheme schema;

        public RecordIdentifierScheme(ERecordItentifierScheme schema) {
            this.schema = schema;
        }
        
        @Override
        public String toString() {
            return schema.toString().toLowerCase();
        }
    }

    /**
     * See https://raw.githubusercontent.com/inveniosoftware/invenio-rdm-records/master/invenio_rdm_records/fixtures/data/vocabularies/relation_types.yaml
     */
    static class RelationTypeId {
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
        
        @Override
        public String toString() {
            return relation.toString().toLowerCase();
        }
    }

    /**
     * See https://inveniordm.docs.cern.ch/reference/metadata/#related-identifiersworks-0-n
     */
    static class RelatedRecordIdentifierScheme {

        public static enum ERelatedRecordIdentifierScheme {
            ISBN10, ISBN13, ISSN, ISTC, DOI, Handle, EAN8, EAN13, ISNI, ORCID,
            ARK, PURL, LSID, URN, Bibcode, arXiv, PubMedID, PubMedCentralID,
            GND, SRA, BioProject, BioSample, Ensembl, UniProt, RefSeq, 
            GenomeAssembly
        }
        ERelatedRecordIdentifierScheme schema;
        public RelatedRecordIdentifierScheme(ERelatedRecordIdentifierScheme schema) {
            this.schema = schema;
        }
        
        @Override
        public String toString() {
            return schema.toString().toLowerCase();
        }
    }

    /**
     * See https://inveniordm.docs.cern.ch/reference/metadata/#references-0-n
     */
    static class ReferenceScheme {

        public static enum EReferenceSchema {
            CrossRefFunderID, GRID, ISNI, Other
        }
        EReferenceSchema schema;

        public ReferenceScheme(EReferenceSchema schema) {
            this.schema = schema;
        }
        
        @Override
        public String toString() {
            return schema.toString().toLowerCase();
        }
    }
    
}
