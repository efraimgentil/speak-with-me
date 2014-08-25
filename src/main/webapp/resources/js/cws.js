/**
 * 
 */
var CWS = function(options ) {
	options = options || {  };
	var cws = {
	    wsUri : options.wsUri,
		websocket : null,
		userListApender : options.userListApender || undefined,
		usersOnline : {},
		currentUser : null,
		connect : function() {
			this.websocket = new WebSocket( this.wsUri );
			this.websocket.cws = this;
			this.websocket.onopen = this.onOpen;
			this.websocket.onclose = this.onClose;
			this.websocket.onmessage = this.onMessage;
		},
		onOpen : function() {
			console.log("CONNECT");
		},
		onMessage : function(message) {
			console.log("RECEIVING MESSAGE...");
			console.log( message.data );
			var json = JSON.parse(message.data);
			console.log( json );
			
			if("USERS_CONNECTED" == json.type){
				if(cws.userListApender == undefined){
					console.log("You should inform the options.userListApender attribute");
				}else{
					var guests = json.guests;
					var guestLength = guests.length;
					for( i = 0 ; i < guestLength ; i++ ){
						var guest = guests[i];
						cws.userListApender( guest );
						cws.usersOnline[guest.id] = guest;
					} 
				}
			}else{
				//cws.appendText(json.date  + " - " + json.userWhoSend + ": " + json.body , json.type );
				var user = cws.usersOnline[json.userId];
				if( currentUser == null || user.id == currentUser.id ){
					cws.appendText(json.date  + " - " + json.userWhoSend + ": " + json.body , json.type );
				}
				if(user != null && user != undefined){
					user["messages"] = user["messages"] || [];
					user["messages"].push( json );
				}
			}
		},
		onClose : function(data){
			console.log("CLOSE");
			console.log( data );
			cws.appendText( "Connection closed ");
		},
		appendText: function(text , type){
			var a = document.getElementById( options.messageArea );
			p = document.createElement("p");
			if(type){
				p.classList.add(type.toLowerCase());
			}
			p.textContent = text;
			a.appendChild( p );
		},
		sendMessage: function(message , callback){
			cws.websocket.send(message);
			if(callback) callback();
		}
	};
	return cws;
};

function prepareURI(contextPath){
	var hostPath = location.host;
    var isRuningOnOpenshift = hostPath.indexOf("rhcloud.com") >= 0;
    var port = isRuningOnOpenshift ? 8000 : 8080;
    
    if( hostPath.indexOf(":"+port) < 0 ){
    	hostPath += ":" + port ;
    }
	return "ws://" + hostPath + contextPath +"/speak/"; 
}