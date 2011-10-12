package edu.uib.info310;
import java.net.*;
import java.io.*;

public class GetUrl {	
	public static String getContent (String url) throws Exception {
        URL oracle = new URL(url);
        URLConnection yc = oracle.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        String inputLine;
        String outputLine = null;
        
        while ((inputLine = in.readLine()) != null) 
            outputLine += inputLine + "\n";
        in.close();
		return outputLine;
    }
}