<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="xml" version="1.0"
encoding="UTF-8" indent="yes"/>
	<xsl:template match="/resp">
	
		<rdf:RDF 
			xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
			xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" 	
			xmlns:owl="http://www.w3.org/2002/07/owl#"
			xmlns:xsd="http://www.w3.org/2001/XMLSchema#" 
			xmlns:dc="http://purl.org/dc/terms/"
			xmlns:foaf="http://xmlns.com/foaf/0.1/" 
			xmlns:mo="http://purl.org/ontology/mo/"
			xmlns:v="http://www.w3.org/2006/vcard/ns#" 
			xmlns:po="http://purl.org/ontology/po/"
			xmlns:time="http://www.w3.org/2006/time#" >




			<rdf:Description rdf:about="http://api.discogs.com/release/{release/@id}">
			<rdf:type rdf:resource="http://purl.org/ontology/mo/release" />

			<!-- <xsl:for-each select="release/images/image">
					<mo:image rdf:recource="{@uri}" />
				</xsl:for-each> -->
				<xsl:for-each select="release/artists/artist">
					<foaf:hasAgent>
						<xsl:value-of select="name" />
					</foaf:hasAgent>
				</xsl:for-each>
				<mo:discogs>
					<xsl:value-of select="release/@id" />
				</mo:discogs>
				<foaf:name>
					<xsl:value-of select="release/title" />
				</foaf:name>
				<mo:label>
					<xsl:value-of select="release/labels/label/@name" />
				</mo:label>
				<mo:catalogue_number>
					<xsl:value-of select="release/labels/label/@catno" />
				</mo:catalogue_number>

				<dc:issued>
					<xsl:value-of select="release/released" />
				</dc:issued>

				<rdfs:comment>
					<xsl:value-of select="release/notes" />
				</rdfs:comment>

				<xsl:for-each select="release/genres/genre">
					<mo:genre>
						<xsl:value-of select="." />
					</mo:genre>
				</xsl:for-each>

				<v:country-name>
					<xsl:value-of select="release/country" />
				</v:country-name>


				<xsl:for-each select="release/tracklist/track">
					<mo:track rdf:resource="http://api.discogs.com/release/{../../@id}/track/{position}"/>
				</xsl:for-each>
			</rdf:Description>

			<xsl:for-each select="release/tracklist/track">
			<rdf:Description rdf:about="http://api.discogs.com/release/{//release/@id}/track/{position}">
				<rdf:type rdf:resource="http://purl.org/ontology/mo/Track"/>
						<mo:track_number>
							<xsl:value-of select="position" />
						</mo:track_number>

						<foaf:name>
							<xsl:value-of select="title" />
						</foaf:name>
						<time:duration>
							<xsl:value-of select="duration"/>
						</time:duration>
			</rdf:Description>
			</xsl:for-each>
			
		</rdf:RDF>
	</xsl:template>
</xsl:stylesheet>