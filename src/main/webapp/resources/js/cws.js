/**
 * 
 */
var CWS = function(options ) {
	options = options || {  };
	var cws = {
	    wsUri : options.wsUri,
		websocket : null,
		userListApender : options.userListApender || undefined,
		usersOnline : { 
			
			length : function(){
				var size = 0
				for( k in this){
					if(k != "length"){
						size++;
					}
				}
				return size;
			}
		},
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
			
			var messageType = json.type;
			
			if("UPDATE" == messageType){
				if(cws.userListApender == undefined){
					console.log("You should inform the options.userListApender attribute");
				}else{
					if("USERS_CONNECTED" == json.updateType){
						var guests = json.guests;
						var guestLength = guests.length;
						for( i = 0 ; i < guestLength ; i++ ){
							var guest = guests[i];
							cws.userListApender( guest );
							cws.usersOnline[guest.id] = guest;
						} 
					}
					if("NEW_USER_CONNECTED" == json.updateType ){
						var guest = json.guest;
						cws.userListApender( guest );
						cws.usersOnline[guest.id] = guest;
					}
				}
			}
			if("MESSAGE" == messageType){
				var user = cws.usersOnline[json.userId];
				if( ( cws.currentUser == null && cws.usersOnline.length() == 0 ) || ( user != undefined && cws.currentUser != null && user['id'] == cws.currentUser.id )  ){
					cws.appendText(json.date  + " - " + json.userWhoSend + ": " + json.body , json.level );
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
		appendText: function(text , level){
			var a = document.getElementById( options.messageArea );
			p = document.createElement("p");
			if(level){
				p.classList.add(level.toLowerCase());
			}
			p.textContent = text;
			a.appendChild( p );
		},
		clearTextArea: function(){
			var a = document.getElementById( options.messageArea );
			a.innerHTML ="";
		},
		sendMessage: function(message , callback){
			cws.websocket.send(message);
			if(callback) callback();
		},
		selectUser: function(id){
			cws.currentUser = cws.usersOnline[id] || null;
			if(cws.currentUser == null ){
				console.log("User not found");
			}else{
				cws.clearTextArea();
				arrMessages = cws.currentUser["messages"] || [];
				if(arrMessages.length > 0){
					for(i = 0 ; i < arrMessages.length ; i++){
						var json = arrMessages[i];
						cws.appendText(json.date  + " - " + json.userWhoSend + ": " + json.body , json.level );
					}
				}
			}
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