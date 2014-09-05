<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="cw"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="A layout example that shows off a responsive email layout.">
<title>Email &ndash; Layout Examples &ndash; Pure</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/pure-min.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/layouts/email.css">
<title>Chat - WebSocket</title>
</head>
<body>
	<div class="content pure-g">
		<div id="login" class="pure-u-1">
			<h1>Chat with WebSocket</h1>
			<hr />
			<c:if test="${message == null && errorMap == null}">
				<div id="initialQuestion">
					<h3 class="email-content-title">Hello there! Are you already
						signed in?</h3>
					<button id="btn-already-signed" class="button-success pure-button">Yes</button>
					<button id="btn-not-signed" class="button-error pure-button">No</button>
				</div>
			</c:if>

			<form id="form-singin" class="pure-form pure-form-stacked ${type != null && type == 'singin' ? '' :  'hidden' }"
				action="${pageContext.request.contextPath}/singin" method="post">
				<fieldset>
					<legend>Sing in</legend>
					
                    <cw:errorMessage attribute="${errorMap['username']}" />
                    <cw:errorMessage attribute="${errorMap['email']}" />
                    <cw:errorMessage attribute="${errorMap['password']}" />
                    <cw:errorMessage attribute="${errorMap['passwordConfirmation']}" />
                    
                    <label for="username">Username</label>
                    <input id="username" name="username" type="text" placeholder="Username" value="${user.username}">
					<label for="email">Email</label>
					<input id="email" name="email" type="email" placeholder="Email" value="${user.email}">
					<label for="password">Password</label>
					<input id="password" name="password" type="password" placeholder="Password" >
					<label for="passwordConfirmation">Confirm Password</label>
					<input id="passwordConfirmation" name="passwordConfirmation" type="password" placeholder="Confirm the password" >

					<button type="submit" class="pure-button pure-button-primary">Sign
						in</button>
					<button type="reset" class="pure-button button-warning">Clean</button>
				</fieldset>
			</form>

			<form id="form-singon" class="pure-form pure-form-stacked ${type != null && type == 'singon' ? '' :  'hidden' }"
				action="${pageContext.request.contextPath}/singon" method="post">
				<fieldset>
					<legend>Sing on</legend>
					<cw:errorMessage attribute="${message}" />
					
					<label for="email">Email</label>
					<input id="email" type="email"	placeholder="Email" name="user.email" value="${user.email}">
					<label for="password">Password</label>
					<input id="password" type="password" placeholder="Password" name="user.password" >

					<button type="submit" class="pure-button pure-button-primary">Sign
						On</button>
					<button type="reset" class="pure-button button-warning">Clean</button>
				</fieldset>
			</form>
		</div>
	</div>
	<script>
		(function() {
			var alreadySingedButton = document
					.getElementById("btn-already-signed");
			alreadySingedButton.onclick = function(e) {
				var formSingon = document.getElementById("form-singon");
				formSingon.classList.remove("hidden");
				document.getElementById("initialQuestion").classList
						.add("hidden");
			}

			var notSingnedButton = document.getElementById("btn-not-signed");
			notSingnedButton.onclick = function() {
				var formSingon = document.getElementById("form-singin");
				formSingon.classList.remove("hidden")
				document.getElementById("initialQuestion").classList
						.add("hidden");
			}
		}());
	</script>
</body>
</html>