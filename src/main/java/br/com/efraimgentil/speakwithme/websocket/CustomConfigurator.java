package br.com.efraimgentil.speakwithme.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class CustomConfigurator extends ServerEndpointConfig.Configurator {
  
  @Override
  public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
    System.out.println("HANDSHAKE");
    HttpSession httpSession = (HttpSession) request.getHttpSession();
    if( httpSession.getAttribute("authenticated") != null ){
      sec.getUserProperties().put("HTTP_SESSION",  httpSession );
      sec.getUserProperties().put("hey", "SUB NIGA");
    }else{
//      response.getHeaders().put( response.SEC_WEBSOCKET_ACCEPT , new ArrayList<String>() );
      throw new RuntimeException(" NIH ");
    }
  }
  
  
}
