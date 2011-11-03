<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
    xmlns:mo="http://purl.org/ontology/mo/"
    xmlns:v="http://www.w3.org/2006/vcard/ns#"
    xmlns:foaf="http://xmlns.com/foaf/0.1/"
    xmlns:xsd="http://www.w3.org/2001/XMLSchema#"
    version="1.0">
    
    <xsl:output method="xml" version="1.0" encoding="UTF-8"
        indent="yes" />
    
    <xsl:template match="/">
        <rdf:RDF
            xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
            xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
            xmlns:owl="http://www.w3.org/2002/07/owl#"
            xmlns:xsd="http://www.w3.org/2001/XMLSchema#"      
            xmlns:dc="http://purl.org/dc/elements/1.1/"
            xmlns:foaf="http://xmlns.com/foaf/0.1/"
            xmlns:mo="http://purl.org/ontology/mo/"
            xmlns:v="http://www.w3.org/2006/vcard/ns#"

            >
            <xsl:apply-templates />
        </rdf:RDF>
    </xsl:template>
    
    <xsl:template match="release">
        <mo:Release rdf:about="http://api.discogs.com/release/{@id}">
            <mo:ReleaseStatus><xsl:value-of select="@status"/></mo:ReleaseStatus>
            <xsl:for-each select="images/image">
                <mo:Image><xsl:value-of select="@uri"/></mo:Image>
            </xsl:for-each> 
            <xsl:for-each select="artists/artist">
                <foaf:hasAgent><xsl:value-of select="name"/></foaf:hasAgent>
            </xsl:for-each> 

            <mo:Album><xsl:value-of select="title"/></mo:Album>
            <mo:Label><xsl:value-of select="labels/label/@name"/></mo:Label>
            <mo:catalogue_number><xsl:value-of select="labels/label/@catno"/></mo:catalogue_number>
            <mo:media_type><xsl:value-of select="formats/format/@name"/></mo:media_type>
            <mo:record_number><xsl:value-of select="formats/format/@qty"/></mo:record_number>
            <mo:release_type><xsl:value-of select="formats/format/descriptions/description"/></mo:release_type>
            <xsd:date><xsl:value-of select="released"/></xsd:date>
            <rdfs:comment><xsl:value-of select="notes"/></rdfs:comment>
            <mo:MusicalManifestation><xsl:value-of select="master_id"/></mo:MusicalManifestation>
            
            <xsl:for-each select="genres/genre">  
                <mo:Genre><xsl:value-of select="."/></mo:Genre>                
            </xsl:for-each>
            <xsl:for-each select="styles/style">                
                <mo:Genre><xsl:value-of select="."/></mo:Genre>                
            </xsl:for-each>
            
            <xsl:if test="country"><v:country-name><xsl:value-of select="country"/></v:country-name></xsl:if>
            <mo:gtin><xsl:value-of select="identifiers/identifier/@value"/></mo:gtin>
            
        </mo:Release>
            <xsl:call-template name="tracklist"></xsl:call-template>
         
    </xsl:template>
    
    <xsl:template name="tracklist">
        
        <xsl:for-each select="tracklist/track">
            <mo:Track>
                <xsl:if test="position">
                    <mo:track_number><xsl:value-of select="position"/></mo:track_number>
                </xsl:if>
                <xsl:if test="title">
                    <mo:MusicalWork><xsl:value-of select="title"/></mo:MusicalWork>
                </xsl:if>
                <xsl:if test="duration">
                    <xsd:duration><xsl:value-of select="duration"></xsl:value-of></xsd:duration>
                </xsl:if>
                <xsl:for-each select="artists/artist">
                    <foaf:hasAgent><xsl:value-of select="name"/></foaf:hasAgent>
                </xsl:for-each> 
                
            </mo:Track>
            
        </xsl:for-each>
    </xsl:template>
    
</xsl:stylesheet>