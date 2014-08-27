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
<link rel="stylesheet"
	href="${cPath}/resources/css/pure-min.css">
<link rel="stylesheet" href="${cPath}/resources/css/layouts/email.css">

<script type="text/javascript" src="${cPath}/resources/js/cws.js"></script>
</head>
<body>
	<fmt:setBundle basename="messages" />
	<table id="table-chat">
		<tr>
			<td id="td-chat-title">
				<h1>
					Chat WebSocket -
					<fmt:message key="app.version" />
				</h1>
				<p>Essa é uma aplicação de exemplo da implementação da
					especificação JSR 356</p>
			</td>
		</tr>
		<tr>
			<td id="td-chat-area">
			     <div id="area" style="overflow-y: scroll;height: 100%;">
			         
			     </div>
			</td>
		</tr>
		<tr id="tr-chat-form">
			<td>
				<form action="">
					<div id="div-textarea">
						<textarea id="messageTextArea" rows="" cols=""></textarea>
					</div>
					<div>
						<button id="submit">Send</button>
						<button>Clean</button>
					</div>
				</form>
			</td>
		</tr>
	</table>
	
	<script type="text/javascript">
        var options = { 
        	    wsUri : prepareURI("${cPath}"),
        	    messageArea: "area"
        	    }
        var cws = new CWS( options );
        cws.connect();
        
        var submitBtn = document.getElementById("submit");
        submitBtn.onclick = function(e){
            if(this.disabled == false){
            	var btn = this;
            	btn.disabled = true;
	            var component = document.getElementById("messageTextArea");        
	            cws.sendMessage(component.value , function(){ btn.disabled = false; });
            }
            return false;
        } 
	</script>
</body>
</html>
