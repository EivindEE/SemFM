<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:template match="/resp">
		<rdf:RDF 
			xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
			xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" 	
			xmlns:owl="http://www.w3.org/2002/07/owl#"
			xmlns:xsd="http://www.w3.org/2001/XMLSchema#" 
			xmlns:dc="http://purl.org/dc/elements/1.1/"
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

				<rdfs:label>
					<xsl:value-of select="release/title" />
				</rdfs:label>
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
					<mo:track>
					
			http://api.discogs.com/release/<xsl:value-of select="//release/@id"/>
						<xsl:value-of select="position" />
					</mo:track>
				</xsl:for-each>
			</rdf:Description>

			<xsl:for-each select="release/tracklist/track">
			<rdf:Description rdf:about="http://api.discogs.com/release/{//release/@id}{position}">
				<rdf:type rdf:resource="http://purl.org/ontology/mo/track"/>
						<mo:track_number>
							<xsl:value-of select="position" />
						</mo:track_number>

						<rdfs:label>
							<xsl:value-of select="title" />
						</rdfs:label>
						<time:duration>
							<xsl:value-of select="duration"/>
						</time:duration>
						<mo:Track rdf:resource="http://api.discogs.com/release/{//release/@id}"/>
			</rdf:Description>
			</xsl:for-each>
			
		</rdf:RDF>
	</xsl:template>
</xsl:stylesheet>