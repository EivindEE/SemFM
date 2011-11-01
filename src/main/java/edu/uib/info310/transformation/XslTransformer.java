package edu.uib.info310.transformation;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;

public class XslTransformer {
	
	public XslTransformer() {
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws TransformerConfigurationException {
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		
	}
}
