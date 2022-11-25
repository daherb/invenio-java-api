/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class ControlledVocabulary {
    
    /**
     * See https://raw.githubusercontent.com/inveniosoftware/invenio-rdm-records/bc1c38991e602cd3a495f50cba7b1d4f868df07f/invenio_rdm_records/fixtures/data/vocabularies/resource_types.yaml
     */
    public static class ResouceType {
        
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
        HashMap<ResouceType.EResourceType,String> types;
        
        ResouceType.EResourceType resourceType;
        public ResouceType(ResouceType.EResourceType resourceType) {
            this.types = new HashMap();
            types.put(EResourceType.Publication, "publication");
            types.put(EResourceType.PublicationAnnotationCollection, "publication-annotationcollection");
            types.put(EResourceType.PublicationBook,"publication-book");
            types.put(EResourceType.PublicationSection, "publication-section");
            types.put(EResourceType.PublicationConferencePaper, "publication-conferencepaper");
            types.put(EResourceType.PublicationDataManagementPlan, "publication-datamanagementplan");
            types.put(EResourceType.PublicationArticle, "publication-article");
            types.put(EResourceType.PublicationPatent, "publication-patent");
            types.put(EResourceType.PublicationPreprint, "publication-preprint");
            types.put(EResourceType.PublicationDeliverable, "publication-deliverable");
            types.put(EResourceType.PublicationMilestone, "publication-milestone");
            types.put(EResourceType.PublicationProposal, "publication-proposal");
            types.put(EResourceType.PublicationReport, "publication-report");
            types.put(EResourceType.PublicationSoftwareDocumentation, "publication-softwaredocumentation");
            types.put(EResourceType.PublicationTaxonomicTreatment, "publication-taxonomictreatment");
            types.put(EResourceType.PublicationTechnicalNote, "publication-technicalnote");
            types.put(EResourceType.PublicationThesis, "publication-thesis");
            types.put(EResourceType.PublicationWorkingPaper, "publication-workingpaper");
            types.put(EResourceType.PublicationOther, "publication-other");
            types.put(EResourceType.Poster, "poster");
            types.put(EResourceType.Presentation, "presentation");
            types.put(EResourceType.Dataset, "dataset");
            types.put(EResourceType.Image, "image");
            types.put(EResourceType.ImageFigure, "image-figure");
            types.put(EResourceType.ImagePlot, "image-plot");
            types.put(EResourceType.ImageDrawing, "image-drawing");
            types.put(EResourceType.ImageDiagram, "image-diagram");
            types.put(EResourceType.ImagePhoto, "image-photo");
            types.put(EResourceType.ImageOther, "image-other");
            types.put(EResourceType.Video, "video");
            types.put(EResourceType.Software, "software");
            types.put(EResourceType.Lesson, "lesson");
            types.put(EResourceType.Other, "other");
            this.resourceType = resourceType;
        }

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
        
        HashMap<EOrganization,String> organizations;
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
