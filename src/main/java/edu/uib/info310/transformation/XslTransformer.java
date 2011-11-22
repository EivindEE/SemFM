package edu.uib.info310.transformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Reader;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class XslTransformer {

	private Source xml;
	private Source xsl;
	private static final Logger LOGGER = LoggerFactory.getLogger(XslTransformer.class); 

	/**
	 * Transforms an xml using the instance's xml and xsl sources
	 * @return and outputStream with the transformed document
	 * @throws TransformerException if an invalid xsl is provided
	 */
	public ByteArrayOutputStream transform() throws TransformerException{
		LOGGER.debug("Transforming " + xml + " with stylesheet " + xsl);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Result result = new StreamResult(out);

		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl);
		transformer.transform(xml, result);
		
		return out;
	}

	/**
	 * Returns the Source containing the xml
	 * @return
	 */
	public Source getXml() {
		return xml;
	}

	/**
	 * Sets the current xml source to be the provided xml source
	 * @param xml
	 */
	public void setXml(Source xml) {
		this.xml = xml;
	}

	/**
	 * Sets the current xml source to be a new source created with the provided xml file
	 * @param xml
	 */
	public void setXml(File xml) {
		this.xml = new StreamSource(xml);
	}
	
	/**
	 * Sets the current xml source to be a new source created with the provided xml reader
	 * @param xml
	 */
	public void setXml(Reader xml) {
		this.xml = new StreamSource(xml);
	}
	
	/**
	 * Sets the current xml source to be a new source created with the provided xml stream
	 * @param xml
	 */
	public void setXml(InputStream xml) {
		LOGGER.debug("Got inputstream : " + xml);
		this.xml = new StreamSource(xml);
		LOGGER.debug("This xml is " + this.xml);
	}

	/**
	 * Returns the Source containing the xsl
	 * @return
	 */
	public Source getXsl() {
		return xsl;
	}

	/**
	 * Sets the current xsl source to be the provided xsl source
	 * @param xml
	 */
	public void setXsl(Source xsl) {
		this.xsl = xsl;
	}
	/**
	 * Sets the current xsl source to be a new source created with the provided xsl file
	 * @param xml
	 */
	public void setXsl(File xsl) {
		LOGGER.debug("Setting xsl to be " + xsl.getAbsolutePath());
		this.xsl = new StreamSource(xsl);
	}


}
