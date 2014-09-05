package br.com.efraimgentil.speakwithme.websocket;

import java.io.StringWriter;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import br.com.efraimgentil.speakwithme.model.UserUpdate;
import br.com.efraimgentil.speakwithme.model.constants.MessageType;

public class UserUpdateEncoder implements Encoder.Text<UserUpdate> {

  @Override
  public void init(EndpointConfig config) {
    // TODO Auto-generated method stub
  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub
  }

  @Override
  public String encode(UserUpdate guest) throws EncodeException {
    StringWriter writer = new StringWriter();
    JsonGenerator jg = Json.createGenerator(writer);
    jg.writeStartObject();
    jg.writeStartObject("guest");
    jg.write( "id" , guest.getId() );
    jg.write( "userName" , guest.getUserName() );
    jg.write( "email" , guest.getEmail() != null ? guest.getEmail()  : "" );
    jg.write( "status" , guest.getStatus() != null ? guest.getStatus() : "" );
    jg.writeEnd();
    jg.write( "type" , MessageType.UPDATE.toString() );
    jg.write( "updateType" , guest.getUpdateType().toString() );
    jg.writeEnd();
    jg.flush();
    return writer.toString();
  }

}
