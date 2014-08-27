package br.com.efraimgentil.speakwithme.websocket;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.EncodeException;

import org.junit.Before;
import org.junit.Test;

import br.com.efraimgentil.speakwithme.model.Guest;
import br.com.efraimgentil.speakwithme.model.constants.MessageType;
import static  org.junit.Assert.*;

public class GuestEncoderTest {
  
  private GuestsEncoder guestsEncoder;
  
  @Before
  public void setUp(){
    guestsEncoder = new GuestsEncoder();
  }
  
  @Test
  public void doesEncodeToJSONAListOfGuests () throws EncodeException{
    List<Guest> guests = new ArrayList<>();
    guests.add( new Guest( "SESSION ID 1" , "USER NAME 1") );
    guests.add( new Guest( "SESSION ID 2" , "USER NAME 2") );
    guests.add( new Guest( "SESSION ID 3" , "USER NAME 3") );
    
    String encoded = guestsEncoder.encode( guests );
    
    assertNotNull(encoded);
    assertTrue( encoded.contains("\"id\":\"SESSION ID 1\"") );
    assertTrue( encoded.contains("\"userName\":\"USER NAME 1\"") );
    assertTrue( encoded.contains("\"status\":\"\"") );
    assertTrue( encoded.contains("\"id\":\"SESSION ID 2\"") );
    assertTrue( encoded.contains("\"userName\":\"USER NAME 2\"") );
    assertTrue( encoded.contains("\"status\":\"\"") );
    assertTrue( encoded.contains("\"id\":\"SESSION ID 3\"") );
    assertTrue( encoded.contains("\"userName\":\"USER NAME 3\"") );
    assertTrue( encoded.contains("\"status\":\"\"") );
  }
  
  @Test
  public void doesReturnAEmptyEncodedJSONWhenThereIsNoGuests() throws EncodeException{
    List<Guest> guests = new ArrayList<>();
    
    String encoded = guestsEncoder.encode( guests );
    
    assertNotNull(encoded);
    assertTrue( encoded.contains("\"guests\":[]") );
  }
  
  @Test
  public void doesIncludeMessageTypeToTheEncodedList() throws EncodeException{
    List<Guest> guests = new ArrayList<>();
    
    String encoded = guestsEncoder.encode( guests );
    
    assertNotNull(encoded);
    assertTrue("The message tyep should be MessageType.USERS_CONNECTED"
        , encoded.contains("\"type\":\""+ MessageType.UPDATE.toString()  +"\"") );
  }
  
}
