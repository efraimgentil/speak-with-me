<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Chat WebSocket</title>
<c:set var="cPath" value="${pageContext.request.contextPath}"
	scope="request" />
<link type="text/css" rel="stylesheet"
	href="${cPath}/resources/css/chat_websocket.css" />
<link rel="stylesheet"
${cPath}
	href="${cPath}/resources/css/pure-min.css">
<link rel="stylesheet" href="${cPath}/resources/css/layouts/email.css">

<script type="text/javascript" src="${cPath}/resources/js/cws.js"></script>
</head>
<body>
	<fmt:setBundle basename="messages" />

	<div id="layout" class="content pure-g" style="height: 100%">
		<div id="list" class="pure-u-1">
			<div class="email-item email-item-selected pure-g">
				<div class="pure-u-3-4">
					<h5 class="email-name">Tilo Mitra</h5>
				</div>
			</div>
		</div>

		<div id="main" class="pure-u-1">
			<table style="height: 100%; width: 100%">
				<tr>
					<td style="border: 1px solid #ddd; vertical-align: top;">
						<div id="area"></div>
					</td>
				</tr>
				<tr height="100px">
					<td style="vertical-align: top">
						<form action="">
							<div style="margin-top: 2px">
								<textarea id="messageTextArea" rows="" cols=""
									style="resize: none; width: 100%"></textarea>
							</div>
							<div style="margin-top: 2px">
								<button id="submit">Send</button>
								<button type="reset">Clean</button>
							</div>
						</form>
					</td>
				</tr>
			</table>
			<!-- 		      <div class="email-content"> -->
			<!-- 		          <div id="action-area" > -->
			<!-- 		          </div> -->
			<!-- 		      </div> -->
		</div>
	</div>
	<script type="text/javascript">
		var options = {
			wsUri : prepareURI("${cPath}"),
			messageArea : "area",
			userListApender : appendUser,
		}
		var cws = new CWS(options);
		cws.connect();

		var submitBtn = document.getElementById("submit");
		submitBtn.onclick = function(e) {
			if (this.disabled == false) {
				var btn = this;
				btn.disabled = true;
				var component = document.getElementById("messageTextArea");
				cws.sendMessage(component.value, function() {
					btn.disabled = false;
				});
			}
			return false;
		}

	    function appendUser(guest){
			var t1 = document.getElementById("user-template").cloneNode(true);
			var asString = new XMLSerializer().serializeToString(t1);
			for(key in guest) { 
			  asString = asString.replace("{{{"+key+"}}}", guest[key]);
		    }  
			var div = document.createElement("div");
			div.innerHTML = asString;
			t1 = div.childNodes[0];
			t1.classList.remove("template");
			t1.removeAttribute("xmlns");
			var list = document.getElementById("list");
			list.appendChild(t1);
	    }
		
	</script>

	<div id="user-template" class="email-item email-item-selected pure-g template">
        <div class="hidden">{{{id}}}</div>
		<div class="pure-u-3-4">
			<h5 class="email-name">{{{userName}}}</h5>
		</div>
	</div>
</body>
</html>
