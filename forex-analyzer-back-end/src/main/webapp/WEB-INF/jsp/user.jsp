<%@ include file = "/WEB-INF/jsp/fragments/jspheader.jspf" %>
<%@ include file = "/WEB-INF/jsp/fragments/resources.jspf" %>

<!doctype html>
<html>
	<head>
		<title>User Page</title>
		<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
		<link rel="stylesheet" type="text/css" href="${appStyle}" />
	</head>
	<body>
		<!-- html -->
		<div>
			<h1 id="userPageTitle" class="pinktext">User page</h1>
		</div><br />
		<div><a href="/">Home</a></div><br />
		<div id="testjs"><button id="testjsbutton">Test JS</button></div>
		
		<!-- js -->
		<script src="https://code.jquery.com/jquery-1.11.3.min.js"></script>
		<script src="${appJs}"></script>
	</body>
</html>