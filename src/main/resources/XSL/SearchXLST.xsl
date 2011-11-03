<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
    xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#"
    xmlns:mo="http://purl.org/ontology/mo/"
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
            >
            <xsl:apply-templates />
            
        </rdf:RDF>
    </xsl:template>
    
    <xsl:template match="searchresults">
        <rdf:Description>
        <rdf:ID><xsl:value-of select="@numResults"/></rdf:ID>
        <rdf:ID><xsl:value-of select="@start"/></rdf:ID>
        <rdf:ID><xsl:value-of select="@end"/></rdf:ID>
        </rdf:Description>
        <xsl:for-each select="result">
        <xsl:call-template name="result"/>
        </xsl:for-each>
    </xsl:template>
    
    <xsl:template name="result">
        <mo:Release rdf:about="{@num}">-
        <mo:ReleaseType><xsl:value-of select="@release"/></mo:ReleaseType>
        <mo:Album><xsl:value-of select="title"/></mo:Album>
        <mo:homepage><xsl:value-of select="uri"/></mo:homepage>
        <rdfs:comment><xsl:value-of select="summary"/></rdfs:comment>
        <xsl:if test="thumb">
            <mo:image><xsl:value-of select="thumb"></xsl:value-of></mo:image>
        </xsl:if>
        </mo:Release>
    </xsl:template>
    
</xsl:stylesheet>