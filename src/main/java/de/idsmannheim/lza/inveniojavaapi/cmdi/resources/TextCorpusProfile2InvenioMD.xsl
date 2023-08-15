<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    xmlns:math="http://www.w3.org/2005/xpath-functions/math"
    xmlns:xd="http://www.oxygenxml.com/ns/doc/xsl" xmlns:cmd="http://www.clarin.eu/cmd/1"
    xmlns:cmdp="http://www.clarin.eu/cmd/1/profiles/clarin.eu:cr1:p_1559563375778"
    exclude-result-prefixes="xs math xd" version="3.0">
    <xsl:output method="text"/>
    <xd:doc scope="stylesheet">
        <xd:desc>
            <xd:p><xd:b>Created on:</xd:b> Apr 12, 2023</xd:p>
            <xd:p><xd:b>Author:</xd:b> Schwarz</xd:p>
            <xd:p> This script takes a CMDI Metadata file of type TextCorpusProfile and generates
                JSON Metadata compatible with InvenioRDM. As many Invenio Metadata fields as
                possible are intended to be filled, see:
                https://inveniordm.docs.cern.ch/reference/metadata/#metadata </xd:p>
        </xd:desc>
    </xd:doc>

    <xsl:template match="/">
        <xsl:text>{</xsl:text>
        <xsl:text>
            "resource_type": {  <!-- Docs: InvenioRDM requires the resource type id from a customizable controlled vocabulary -->            
            "id": "</xsl:text>
        <xsl:if
            test="normalize-space(.//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceClass/text()) = 'Corpus'">
            <xsl:text>publication-annotationcollection</xsl:text>
        </xsl:if>
        <xsl:text>"</xsl:text>
        <xsl:text>},</xsl:text>
        
        <!-- CREATORS -->
        <xsl:text>"creators": [{
              "person_or_org": {
                "name": "</xsl:text><xsl:value-of
            select=".//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:LegalOwner"/><xsl:text>",</xsl:text>
        <xsl:text>"type": "organizational"</xsl:text>
        <xsl:text>}</xsl:text>
        <xsl:text>}],</xsl:text>
        
        <!-- TITLE -->
        <xsl:text>"title": "</xsl:text><xsl:value-of
            select=".//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceTitle"/><xsl:text>",</xsl:text>
        
        <!-- PUBLICATION DATE -->
        <!-- in case the value of cmdp:PublicationDate is empty take the value of cmdp:LastUpdate -->
        <xsl:variable name="publication_date" select=".//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:PublicationDate || .//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:LastUpdate" />
        <xsl:if test="publication_date">
            <xsl:text>"publication_date": "</xsl:text>
            <xsl:value-of select="publication_date" />
            <!--        <xsl:choose>
                <xsl:when
                    test="normalize-space(.//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:PublicationDate) != ''">
                    <xsl:value-of
                        select=".//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:PublicationDate"/>
                </xsl:when>
                <xsl:otherwise>
                    <xsl:value-of select=".//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:LastUpdate"/>
                </xsl:otherwise>
            </xsl:choose>-->
            <xsl:text>",</xsl:text>
        </xsl:if>
        <!-- ADDITIONAL TITLES -->
        <xsl:text>
        "additional_titles": [{
        "title": "</xsl:text><xsl:value-of
            select=".//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceName"/><xsl:text>",
        "type": {"id": "alternative-title",
        "title": {"de": "Alternativer Titel", "en" : "Alternative title"}}
        }],</xsl:text>
        
        <!-- DESCRIPTION -->
        <xsl:text>"description": "</xsl:text><xsl:value-of
            select="normalize-space(.//cmdp:TextCorpusProfile/cmdp:Creation/cmdp:Source/cmdp:Descriptions/cmdp:Description[1])"/><xsl:text>",</xsl:text>
        
        <!-- RIGHTS -->
        <!-- Für die Lizenzen kann man entweder eine gültige ID aus dieser Liste (https://github.com/inveniosoftware/invenio-rdm-records/blob/bab16215c9be0cce34bfc8d2ec574216a386a6f6/invenio_rdm_records/fixtures/data/vocabularies/licenses.csv) 
            verwenden, oder mit title und optional description und link eine eigene definieren. 
            Ich habe CC-BY etc. zu den entsprechenden IDs geändert und für die drei letzten neue Werte definiert:
            DIFF:
                 "rights": [
            -        {"id": "CC-BY"},
            -        {"id": "CC-BY-NC"},
            -        {"id": "CC-0"},
            -        {"id": "Proprietory"},
            -        {"id": "Restricted"},
            -        {"id": "Other"}
            
            +        {"id": "cc-by-4.0"},
            +        {"id": "cc-by-nc-4.0"},
            +        {"id": "cc0-1.0"},
            +        {"title": {"en": "Proprietory"},"description": {"en": "Proprietary data without public access."}, "link": "https://example.com/"},
            +        {"title": {"en": "Restricted"}},
            +        {"title": {"en": "Other"}}
                 ],
            -> Delete Proprietory und Other, nur Restricted lassen ohne Erklärung!
                 
            QAO-NC
            QAO-NC-LOC:ids
            QAO-NC-LOC:ids-NU:1
            ACA-NC
            ACA-NC-LC
            CC-BY-SA => Attribution-ShareAlike 3.0 Unported - Betrifft: rei (Reden und Interviews-Korpus), Wikipedia-Korpora, siehe: https://www.ids-mannheim.de/digspra/kl/projekte/korpora/verfuegbarkeit
            -> ":ids" is only available inside the IDS or via VPN
            -> Erklärungen zu den Lizenzen in diesem paper: https://ids-pub.bsz-bw.de/frontdoor/deliver/index/docId/3135/file/Kupietz_Luengen_Recent+developements+in+DeReKo_2014.pdf

        siehe auch: https://www.ids-mannheim.de/digspra/kl/projekte/korpora/archiv-1/
        -->
        <xsl:text>"rights": [</xsl:text>
        
        <xsl:variable name="licence" select="normalize-space(lower-case(.//cmdp:TextCorpusProfile/cmdp:Access/cmdp:Licence/text()))"/>
        
        <xsl:if test="$licence = 'qao-nc'">
            <xsl:text>{"title": {"en": "</xsl:text><xsl:value-of select="normalize-space(.//cmdp:TextCorpusProfile/cmdp:Access/cmdp:Licence/text())"
            /><xsl:text>"},"description": {"en": "academic, non-commercial, query-and-analysis only (i.e. accessible only via COSMAS-II) use"}}</xsl:text>
        </xsl:if>
        <xsl:if test="$licence = 'qao-nc-loc:ids'">
            <xsl:text>{"title": {"en": "</xsl:text><xsl:value-of select="normalize-space(.//cmdp:TextCorpusProfile/cmdp:Access/cmdp:Licence/text())"
            /><xsl:text>"},"description": {"en": "academic, non-commercial, query-and-analysis only (i.e. accessible only via COSMAS-II) use, only at the site of the IDS"}}</xsl:text>
        </xsl:if>
        <xsl:if test="$licence = 'qao-nc-loc:ids-nu:1'">
            <xsl:text>{"title": {"en": "</xsl:text><xsl:value-of select="normalize-space(.//cmdp:TextCorpusProfile/cmdp:Access/cmdp:Licence/text())"
            /><xsl:text>"},"description": {"en": "academic, non-commercial, query-and-analysis only (i.e. accessible only via COSMAS-II) use, only at the site of the IDS, only by one user at a time"}}</xsl:text>
        </xsl:if>
        <xsl:if test="$licence = 'aca-nc'">
            <xsl:text>{"title": {"en": "</xsl:text><xsl:value-of select="normalize-space(.//cmdp:TextCorpusProfile/cmdp:Access/cmdp:Licence/text())"
            /><xsl:text>"},"description": {"en": "academic, non-commercial use, no re-distribution by the end user"}}</xsl:text>
        </xsl:if>
        <xsl:if test="$licence = 'aca-nc-lc'">
            <xsl:text>{"title": {"en": "</xsl:text><xsl:value-of select="normalize-space(.//cmdp:TextCorpusProfile/cmdp:Access/cmdp:Licence/text())"
            /><xsl:text>"},"description": {"en": "academic, non-commercial use, no re-distribution by the end user, license contract with copyright holder required"}}</xsl:text>
        </xsl:if>
        <xsl:if test="$licence = 'cc-by-sa'">
            <xsl:text>{"id": "cc-by-sa-3.0"}</xsl:text>
        </xsl:if>
        <xsl:if test="$licence = 'cc-by'">
            <xsl:text>{"id": "cc-by-4.0"}</xsl:text>
        </xsl:if>
        <xsl:if test="$licence = 'cc-by-nc'">
            <xsl:text>{"id": "cc-by-nc-4.0"},</xsl:text>
        </xsl:if>
        <xsl:if test="$licence = 'cc-0'">
            <xsl:text>{"id": "cc0-1.0"}</xsl:text>
        </xsl:if>
        <xsl:if test="$licence = 'restricted'">
            <xsl:text>{"title": {"en": "Restricted"}}</xsl:text>
        </xsl:if>
        
        <!-- Wikipedia corpora have a <License>-Tag containing a text mentioning the license but there is no clear way how this is done, 
                therefore if there is a long text in the licence text rather check if the corpus name contains one of the substrings wpd/wdd/wud/wrd 
                as the license is then: cc-by-sa-3.0 (see: https://www.ids-mannheim.de/digspra/kl/projekte/korpora/verfuegbarkeit) -->
        <xsl:if test="string-length($licence) > 20">
            <!-- check if License tag text is a sentence/explanation and therefore long -->
                <xsl:variable name="corpus-name-start" select="substring(lower-case(.//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceName/text()),1,3)"/>
                <xsl:if test="matches($corpus-name-start,'wpd|wdd|wud|wrd')">
                    <xsl:text>{"id": "cc-by-sa-3.0"}</xsl:text>                                    
                </xsl:if>
        </xsl:if>
        <xsl:text>],</xsl:text>
        
        
        <!-- CONTRIBUTORS -->
        <xsl:text>    
            "contributors": [{
              "person_or_org": {
                "name": "Kupietz, Marc",</xsl:text>
        <xsl:text>"type": "personal",</xsl:text>
        <xsl:text>"given_name": "Marc",</xsl:text>
        <xsl:text>"family_name": "Kupietz"</xsl:text>
        <xsl:text>},</xsl:text>
        <xsl:text>
              "role": {"id": "datacurator"},
              "affiliations": [{
                "name": "Leibniz-Institut für Deutsche Sprache (IDS)"</xsl:text>
        <xsl:text>}]</xsl:text>
        <xsl:text>}],</xsl:text>
        
        <!-- SUBJECTS -->
        
        <xsl:variable name="language" select=".//cmdp:TextCorpusProfile/cmdp:TextCorpusContext/cmdp:SubjectLanguages/cmdp:SubjectLanguage/cmdp:Language/cmdp:ISO639/cmdp:iso-639-3-code" />
        <xsl:if test="language">
        <!-- LANGUAGES -->
        <xsl:text>"languages": [{"id": "</xsl:text><xsl:value-of
            select="language" /><xsl:text>"}],</xsl:text>
        </xsl:if>
        
        <!-- DATES -->
        <xsl:variable name="publication_date" select="normalize-space(.//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:PublicationDate/text())" />
        <xsl:variable name="last_updated" select="normalize-space(.//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:LastUpdate/text())" />
        <xsl:if test="publication_date || last_updated">
            <xsl:text>"dates": [</xsl:text>
            <xsl:if test="publication_date">
                <xsl:text>{"date": "</xsl:text>
                <xsl:value-of select="publication_date"/>
                <xsl:text>",
                    "type": {
                    "id": "other",
                    "title": {
                    "en": "Other"
                    }
                    },
                    "description": "Publication date"}</xsl:text>
            </xsl:if>
            <xsl:if test="last_updated">
                <xsl:text>,</xsl:text>
                <xsl:text>{"date": "</xsl:text>
                <xsl:value-of select="last_updated"/>
                <xsl:text>",
                    "type": {
                    "id": "updated",
                    "title": {
                    "en": "Updated"
                    }
                    },
                    "description": "Last update"}</xsl:text>
            </xsl:if>
            <xsl:text>],</xsl:text>
        </xsl:if>
        <!-- VERSION -->
        <xsl:if test="normalize-space(.//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:Version/text())">  
            <xsl:text>"version": "</xsl:text><xsl:value-of
                select="normalize-space(.//cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:Version/text())"
            /><xsl:text>",</xsl:text>
        </xsl:if>
        
        <!-- PUBLISHER -->
        <xsl:text>"publisher": "Leibniz-Institut für Deutsche Sprache (IDS)",</xsl:text>
        
        <!-- ALTERNATE IDENTIFIERS -->
        <xsl:text>"identifiers": [{
        "identifier": "</xsl:text><xsl:value-of select="./cmd:CMD/cmd:Header/cmd:MdSelfLink"/><xsl:text>",
        "scheme": "doi"
        }]</xsl:text>
        
        <!-- RELATED IDENTIFIERS/WORKS -->
        
        <!-- SIZES -->
        
        <!-- FORMATS -->
        
        <!-- LOCATIONS -->
        
        <!-- FUNDING REFERENCES -->
        
        <!-- REFERENCES -->
        <xsl:text>}</xsl:text>
    </xsl:template>

</xsl:stylesheet>
