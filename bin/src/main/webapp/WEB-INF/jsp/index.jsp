<%@ include file = "/WEB-INF/jsp/fragments/jspheader.jspf" %>
<%@ include file = "/WEB-INF/jsp/fragments/resources.jspf" %>

<!doctype html>
<html>
	<head>
		<title>Index Page</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="${appStyle}" />
	</head>
	<body>
		<!-- html -->
		<div id="statictext" class="pinktext">Static Text</div><br />
		<div id="message" class="pinktext">${message}</div><br />
		<div id="testjs" >
			<button id="testjsbutton">Test JS</button>
		</div><br />
		<div><a href="/user">UserPage</a></div>
		
		<!-- js -->
		<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
		<script src="${appJs}"></script>
	</body>
</html>