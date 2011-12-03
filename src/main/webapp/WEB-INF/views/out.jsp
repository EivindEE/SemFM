
<% 
String outType = request.getParameter("out");
String contentType = "";
if(outType.equalsIgnoreCase("xml") || outType.equalsIgnoreCase("true")){
	contentType = "text/xml";
}
else if(outType.equalsIgnoreCase("json")){
	contentType = "application/json";
}
else if(outType.equalsIgnoreCase("ttl")  || outType.equalsIgnoreCase("turtle")){
	contentType = "text/turtle";
}
else if(outType.equalsIgnoreCase("n3")){
	contentType = "text/n3";
}
else{
	contentType = "text/plain";
}
response.setContentType(contentType + " ; charset=UTF-8");  
%>
<%=response.getContentType()%>
${model}
