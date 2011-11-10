<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/lfm">
		<rdf:RDF xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
			xmlns:rdfs="http://www.w3.org/2000/01/rdf-schema#" xmlns:terms="http://purl.org/dc/terms/"
			xmlns:foaf="http://xmlns.com/foaf/0.1/" xmlns:ov="http://open.vocab.org/terms/"
			xmlns:event="http://purl.org/NET/c4dm/event.owl#" xmlns:mo="http://purl.org/ontology/mo/"
			xmlns:owl="http://www.w3.org/2002/07/owl#" xmlns:geo="http://www.w3.org/2003/01/geo/wgs84_pos#"
			xmlns:time="http://www.w3.org/2006/time#" xmlns:gn="http://www.geonames.org/ontology#"
			xmlns:rooms="http://vocab.deri.ie/rooms#" xmlns:v="http://www.w3.org/2006/vcard/ns#">

			<xsl:for-each select="events/event">
				<rdf:Description rdf:about="{url}">
					<rdf:type rdf:resource="http://purl.org/ontology/mo/Performance" />
					<rdfs:label>
						<xsl:value-of select="title" />
					</rdfs:label>
					<xsl:for-each select="artists/artist">
						<xsl:variable name="artistSafe">
							<xsl:value-of select="." />
						</xsl:variable>

						<xsl:variable name="artistSafe2" select="translate($artistSafe,' ','+')" />

						<foaf:hasAgent
							rdf:resource="http://www.last.fm/music/{translate($artistSafe2,'&#38;','&#38;amp;')}" />
					</xsl:for-each>

					<event:place rdf:resource="http://www.last.fm/venue/{venue/id}" />
					<event:time>
						<xsl:call-template name="formatdate">
							<xsl:with-param name="datestr" select="startDate" />
						</xsl:call-template>
					</event:time>
				</rdf:Description>
			</xsl:for-each>
			<xsl:for-each select="events/event/venue">
				<rdf:Description rdf:about="http://www.last.fm/venue/{id}">
					<v:organisation-name>
						<xsl:value-of select="name"></xsl:value-of>
					</v:organisation-name>
					<geo:lat>
						<xsl:value-of select="location/geo:point/geo:lat" />
					</geo:lat>

					<geo:long>
						<xsl:value-of select="location/geo:point/geo:long" />
					</geo:long>

					<!-- <v:country-name> -->
					<!-- <xsl:value-of select="location/country" /> -->
					<!-- </v:country-name> -->

					<v:locality>
						<xsl:value-of select="location/city" />
					</v:locality>

					<!-- <v:postal-code> -->
					<!-- <xsl:value-of select="location/postalcode" /> -->
					<!-- </v:postal-code> -->

					<!-- <v:street-address> -->
					<!-- <xsl:value-of select="location/street" /> -->
					<!-- </v:street-address> -->

					<!-- <foaf:image> -->
					<!-- <xsl:value-of select="image[@size='extralarge']" /> -->
					<!-- </foaf:image> -->

					<!-- <v:telephone> -->
					<!-- <xsl:value-of select="phonenumber" /> -->
					<!-- </v:telephone> -->

					<!-- <foaf:homepage> -->
					<!-- <xsl:value-of select="website" /> -->
					<!-- </foaf:homepage> -->


				</rdf:Description>
			</xsl:for-each>
			<xsl:for-each select="events/event/artists/artist">
				<xsl:variable name="artistSafe">
					<xsl:value-of select="." />
				</xsl:variable>

				<xsl:variable name="artistSafe2" select="translate($artistSafe,' ','+')" />

				<rdf:Description
					rdf:about="http://www.last.fm/music/{translate($artistSafe2,'&#38;','&#38;amp;')}">
					<!-- <rdf:Description rdf:about="http://www.last.fm/music/{.}"> -->
					<foaf:name>
						<xsl:value-of select="." />
					</foaf:name>
				</rdf:Description>
			</xsl:for-each>
		</rdf:RDF>


	</xsl:template>
	<xsl:template name="formatdate">
		<xsl:param name="datestr" />
		<!-- input format ddmmyyyy -->
		<!-- output format dd/mm/yyyy -->

		<xsl:variable name="weekday">
			<xsl:value-of select="substring($datestr,1,3)" />
		</xsl:variable>


		<xsl:variable name="dd">
			<xsl:value-of select="substring($datestr,6,2)" />
		</xsl:variable>

		<xsl:variable name="mm">
			<xsl:value-of select="substring($datestr,9,3)" />
		</xsl:variable>

		<xsl:variable name="yyyy">
			<xsl:value-of select="substring($datestr,13,4)" />
		</xsl:variable>
		
		<xsl:variable name="time">
			<xsl:value-of select="substring($datestr,18,5)" />
		</xsl:variable>
		
		<xsl:value-of select="$dd" />
		<xsl:value-of select="'/'" />
		<xsl:value-of select="$mm" />
		<xsl:value-of select="'/'" />
		<xsl:value-of select="$yyyy" />
		<xsl:value-of select="'/'" />
		<xsl:value-of select="$time" />
	</xsl:template>

</xsl:stylesheet>