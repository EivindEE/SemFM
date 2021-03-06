<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
		<xsl:output method="xml" version="1.0"
encoding="UTF-8" indent="yes"/>
	<xsl:template match="/lfm">
		<rdf:RDF xmlns:ao="http://purl.org/ontology/ao/core#"
			xmlns:bio="http://purl.org/vocab/bio/0.1/" xmlns:cc="http://web.resource.org/cc/"
			xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:dcterms="http://purl.org/dc/terms/"
			xmlns:event="http://purl.org/NET/c4dm/event.owl#" xmlns:foaf="http://xmlns.com/foaf/0.1/"
			xmlns:frbr="http://purl.org/vocab/frbr/core#" xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#"
			xmlns:keys="http://purl.org/NET/c4dm/keys.owl#" xmlns:mo="http://purl.org/ontology/mo/"
			xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
			xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:time="http://www.w3.org/2006/time#"
			xmlns:vs="http://www.w3.org/2003/06/sw-vocab-status/ns#" xmlns:wot="http://xmlns.com/wot/0.1/"
			xmlns:xsd="http://www.w3.org/2001/XMLSchema#" xml:base="http://purl.org/ontology/mo/">
			
			<xsl:param name="itemUri"/>
			
			<rdf:Description rdf:about="{$itemUri}">
			<foaf:name>
					<xsl:value-of select="similarartists/@artist"/>
				</foaf:name>
				<rdf:type rdf:resource="http://purl.org/ontology/mo/MusicArtist" />

				<xsl:for-each select="similarartists/artist">
					<mo:similar-to rdf:resource="http://{url}" />
				</xsl:for-each>
			</rdf:Description>

			<xsl:for-each select="similarartists/artist">
				<rdf:Description rdf:about="http://{url}">
					<foaf:name>
						<xsl:value-of select="name" />
					</foaf:name>

					<mo:image rdf:resource="{image[@size='extralarge']}" />
				</rdf:Description>
			</xsl:for-each>


		</rdf:RDF>
	</xsl:template>
</xsl:stylesheet>