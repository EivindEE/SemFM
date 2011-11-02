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
				<event:event>
					<rdf:Description rdf:about="{url}">
						<rdf:type rdf:rescource="http://purl.org/ontology/mo/Performance" />
						<rdfs:label>
							<xsl:value-of select="title/." />
						</rdfs:label>
						<xsl:for-each select="artists/artist">
							<foaf:hasAgent>
								<foaf:name>
									<xsl:value-of select="." />
								</foaf:name>
							</foaf:hasAgent>
						</xsl:for-each>

						<event:place>
							<v:org>
								<rdf:Description>
									<v:organisation-name>
										<xsl:value-of select="venue/name"></xsl:value-of>
									</v:organisation-name>
								</rdf:Description>
								<v:adr>
									<rdf:Description>
										<v:street-address>
											<xsl:value-of select="venue/location/street" />
										</v:street-address>
										<v:locality>
											<xsl:value-of select="venue/location/city" />
										</v:locality>
										<v:postal-code>
											<xsl:value-of select="venue/location/postalcode" />
										</v:postal-code>
										<v:country-name>
											<xsl:value-of select="venue/location/country" />
										</v:country-name>
										<v:telephone>
											<xsl:value-of select="venue/phonenumber"/>
										</v:telephone>
									</rdf:Description>
								</v:adr>
								<geo:point>
									<geo:lat>
										<xsl:value-of select="venue/location/geo:point/geo:lat" />
									</geo:lat>
									<geo:long>
										<xsl:value-of select="venue/location/geo:point/geo:long" />
									</geo:long>
								</geo:point>

								<foaf:image>
									<xsl:value-of select="venue/image[@size='extralarge']" />
								</foaf:image>
								<foaf:homepage>
									<xsl:value-of select="venue/website" />
								</foaf:homepage>
							</v:org>
						</event:place>
						<event:time>
							<xsl:value-of select="startDate" />
						</event:time>
					</rdf:Description>
				</event:event>
			</xsl:for-each>
		</rdf:RDF>
	</xsl:template>
</xsl:stylesheet>