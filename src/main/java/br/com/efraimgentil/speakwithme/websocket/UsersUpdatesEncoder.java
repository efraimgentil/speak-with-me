package br.com.efraimgentil.speakwithme.websocket;

import java.io.StringWriter;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import br.com.efraimgentil.speakwithme.model.UserUpdate;
import br.com.efraimgentil.speakwithme.model.constants.MessageType;
import br.com.efraimgentil.speakwithme.model.constants.UpdateType;

public class UsersUpdatesEncoder implements Encoder.Text<List<UserUpdate>> {

  @Override
  public void init(EndpointConfig config) {
    // TODO Auto-generated method stub
  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub
  }

  @Override
  public String encode(List<UserUpdate> guests) throws EncodeException {
    StringWriter writer = new StringWriter();
    JsonGenerator jg = Json.createGenerator(writer);
    jg.writeStartObject();
    jg.writeStartArray("guests");
    for (UserUpdate guest : guests) {
      jg.writeStartObject();
      jg.write( "id" , guest.getId() );
      jg.write( "userName" , guest.getUserName() );
      jg.write( "status" , guest.getStatus() != null ? guest.getStatus() : "" );
      jg.writeEnd();
    }
    jg.writeEnd();
    jg.write( "type" , MessageType.UPDATE.toString() );
    jg.write( "updateType" , UpdateType.USERS_CONNECTED.toString() );
    jg.writeEnd();
    jg.flush();
    return writer.toString();
  }

}
