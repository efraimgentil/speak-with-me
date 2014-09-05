package br.com.efraimgentil.speakwithme.model;

import java.util.Map;

public interface Validator<Entidade> {
  
  public Map<String, String> validate();
  
}
