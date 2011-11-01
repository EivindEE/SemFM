<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
	<meta charset="UTF-8" />
	<title>Rihanna - SemFM</title>
	<link rel="shortcut icon" type="image/x-icon" href="resources/images/favicon.ico" />
	<link rel="icon" type="image/png" href="resources/images/favicon.png" />
	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.4/jquery.min.js"></script>
	<script type="text/javascript" src="resources/javascript/thune.scroller.js"></script>
	<script type="text/javascript">
		$(function() {
			$(".album_list_carousel").thuneScroller({
				btnNext: ".next",
				btnPrev: ".prev",
				speed: 200,
				visible: 4,
				scroll: 4
			});
		});
	</script>
	<link rel="stylesheet" type="text/css" href="resources/css/screen.css" />
	</head>
<body onload="initialize()">