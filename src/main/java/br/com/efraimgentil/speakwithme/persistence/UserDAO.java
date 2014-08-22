package br.com.efraimgentil.speakwithme.persistence;

import br.com.efraimgentil.speakwithme.model.User;

public interface UserDAO {
  
  public User userByEmailAndPassword(String email , String password);
  
}
