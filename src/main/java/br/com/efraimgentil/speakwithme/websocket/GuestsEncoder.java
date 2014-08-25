package br.com.efraimgentil.speakwithme.websocket;

import java.io.StringWriter;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonGenerator;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import br.com.efraimgentil.speakwithme.model.Guest;
import br.com.efraimgentil.speakwithme.model.constants.MessageType;

public class GuestsEncoder implements Encoder.Text<List<Guest>> {

  @Override
  public void init(EndpointConfig config) {
    // TODO Auto-generated method stub
  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub
  }

  @Override
  public String encode(List<Guest> guests) throws EncodeException {
    StringWriter writer = new StringWriter();
    JsonGenerator jg = Json.createGenerator(writer);
    jg.writeStartObject();
    jg.writeStartArray("guests");
    for (Guest guest : guests) {
      jg.writeStartObject();
      jg.write( "id" , guest.getId() );
      jg.write( "userName" , guest.getUserName() );
      jg.write( "status" , guest.getStatus() != null ? guest.getStatus() : "" );
      jg.writeEnd();
    }
    jg.writeEnd();
    jg.write( "type" , MessageType.USERS_CONNECTED.toString() );
    jg.writeEnd();
    jg.flush();
    return writer.toString();
  }

}
