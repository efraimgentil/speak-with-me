package br.com.efraimgentil.speakwithme.websocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import br.com.efraimgentil.speakwithme.model.Message;
import br.com.efraimgentil.speakwithme.model.constants.MessageType;

public class MessageDecoder implements Decoder.Text<Message> {

  public void init(EndpointConfig config) {
    // TODO Auto-generated method stub
  }

  public void destroy() {
    // TODO Auto-generated method stub
  }

  public Message decode(String s) throws DecodeException {
    Message message = new Message();
    try(InputStream is = new ByteArrayInputStream( s.getBytes() ) ){
      JsonReader rdr = Json.createReader(is);
      JsonObject obj = rdr.readObject();
      JsonString jsonString = obj.getJsonString("body");
      message.setBody( jsonString.getString() );
      
      jsonString = obj.getJsonString("destinataryId");
      if(jsonString != null){
        message.setDestinataryId( jsonString.getString() );
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return message;
  }

  public boolean willDecode(String s) {
    if(s.toLowerCase().contains("\"type\":\"message\"")){
      return true;
    }
    return false;
  }

}
