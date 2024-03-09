<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>${title}</title>
<script type="text/javascript" charset="utf-8">
var contextPath = "<c:out value="${pageContext.request.contextPath}" />";

	function loadXMLDoc(req)
	{
	if (window.XMLHttpRequest)
	  {
	  xhttp=new XMLHttpRequest();
	  }
	else
	  {
	  xhttp=new ActiveXObject("Microsoft.XMLHTTP");
	  }
	var url =  contextPath+ '${path}'+'?param='+req;
		xhttp.open("GET",url,false);
		xhttp.send("");
		return xhttp.responseXML;
	}

function displayResult()
{
xml=loadXMLDoc("xml");
xsl=loadXMLDoc("xsl");
/* alert(xml);
alert(xsl); */
//xml = '${xml}';
//xsl = '${xsl}';
// code for IE
if (window.ActiveXObject)
  {
  ex=xml.transformNode(xsl);
  document.getElementById("example").innerHTML=ex;
  }
// code for Mozilla, Firefox, Opera, etc.
else if (document.implementation && document.implementation.createDocument)
  {
  xsltProcessor=new XSLTProcessor();
  xsltProcessor.importStylesheet(xsl);
  resultDocument = xsltProcessor.transformToFragment(xml,document);
  document.getElementById("example").appendChild(resultDocument);
  }
}
</script>
</head>
<body onload="displayResult()">
<div id="example" />
</body>
</html>
