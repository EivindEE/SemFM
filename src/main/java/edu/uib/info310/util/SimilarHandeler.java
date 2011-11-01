package edu.uib.info310.util;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

@Component
public class SimilarHandeler extends DefaultHandler {
	private boolean isMusic;
	private boolean isResult;

	private static final String RESULTS = "results";
	private static final String NAME = "name";
	private static final String TYPE = "type";
	private static final String RESOURCE = "resource";
	private static final String MUSIC = "music";

	private String name;
	
	// @Autowired
	private StringBuilder builder;

	private List<String> similar;

	public SimilarHandeler(){
		similar = new LinkedList<String>();
		builder = new StringBuilder();
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(isResult){
			super.characters(ch, start, length);
			int end = start + length;
			for(; start < end; start++){
				builder.append(ch[start]);
			}
		}
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		
		if(qName.equals(RESULTS))
			isResult = true;
		else if(isResult){
			if(qName.equals(TYPE) || qName.equals(NAME))
				builder = new StringBuilder();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);

		if(isResult){

			if(qName.equals(TYPE)){
				if(builder.toString().equals(MUSIC)){
					isMusic = true;
				}
			}
			else if(qName.equals(NAME)){
				name = builder.toString();
			}
			else if(qName.equals(RESOURCE)){
				if(isMusic){
					similar.add(name);
				}
				isMusic = false;
			}
		}

	}

	public List<String> getSimilar(){
		return similar;
	}


}
