package edu.uib.info310.transformation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class XslTransformer {

	private Source xml;
	private Source xsl;
	
	/**
	 * Transforms an xml using the instance's xml and xsl sources
	 * @return and outputStream with the transformed document
	 * @throws TransformerException if an invalid xsl is provided
	 */
	public OutputStream transform() throws TransformerException{
		OutputStream out = new ByteArrayOutputStream();
		Result result = new StreamResult(out);
		
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(xsl);
		transformer.transform(xml, result);
		
		return out;
	}
	
    
	public Source getXml() {
		return xml;
	}

	public void setXml(Source xml) {
		this.xml = xml;
	}
	
	public void setXml(File xml) {
		this.xml = new StreamSource(xml);
	}

	public Source getXsl() {
		return xsl;
	}

	public void setXsl(Source xsl) {
		this.xsl = xsl;
	}
	
	public void setXsl(File xsl) {
		this.xsl = new StreamSource(xsl);
	}
	
}
