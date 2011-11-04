<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:mo="http://purl.org/ontology/mo/"
    xmlns:foaf="http://xmlns.com/foaf/0.1/"
    xmlns:nr="http://numbers.org/"
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
            xmlns:nr="http://numbers.org/"
            >
            <xsl:apply-templates />
            
        </rdf:RDF>
    </xsl:template>
    
    <xsl:template match="topalbums">
        <rdf:Description rdf:about="http://www.last.fm/music/{translate(@artist,' ','+')}">
        <nr:total><xsl:value-of select="@total"/></nr:total>
        <nr:totalPages><xsl:value-of select="@totalPages"/></nr:totalPages>
        <nr:perPage><xsl:value-of select="@perPage"/></nr:perPage>
        </rdf:Description>
        <xsl:for-each select="album">
        <xsl:call-template name="album"/>  
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="album">
        <mo:Release>
        <rdf:integer><xsl:value-of select="@rank"/></rdf:integer>
        <mo:musicbrainz><xsl:value-of select="mbid"/></mo:musicbrainz>
        <mo:homepage><xsl:value-of select="url"/></mo:homepage>
        <xsl:for-each select="image">
        <mo:image><xsl:value-of select="."/></mo:image>
        </xsl:for-each>
        <xsl:for-each select="artist">
        <mo:producer><xsl:value-of select="mbid"></xsl:value-of></mo:producer>
        </xsl:for-each>
        </mo:Release>
        <xsl:for-each select="artist">
        <xsl:call-template name="artist">
            <xsl:with-param name="id" select="mbid"/>
        </xsl:call-template> 
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="artist">
        <xsl:param name="id"/>
        
    <mo:MusicArtist>
        <mo:produced><xsl:value-of select="$id"/></mo:produced>
        <foaf:hasAgent><xsl:value-of select="name"/></foaf:hasAgent>
        <mo:musicbrainz><xsl:value-of select="mbid"/></mo:musicbrainz>
        <mo:homepage><xsl:value-of select="url"/></mo:homepage>
    </mo:MusicArtist>
    </xsl:template>
</xsl:stylesheet>