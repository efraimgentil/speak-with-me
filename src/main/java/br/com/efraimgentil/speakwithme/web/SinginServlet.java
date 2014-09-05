package br.com.efraimgentil.speakwithme.web;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.efraimgentil.speakwithme.model.User;
import br.com.efraimgentil.speakwithme.model.constants.UserType;
import br.com.efraimgentil.speakwithme.persistence.UserDAO;

@WebServlet("/singin")
public class SinginServlet extends HttpServlet {

  private static final long serialVersionUID = -1375284776999038694L;
  
  @Inject
  private UserDAO userDAO;
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    
    String username = (String) req.getParameter("username");
    String email = (String) req.getParameter("email");
    String password = (String) req.getParameter("password");
    String confirmPassword = (String) req.getParameter("passwordConfirmation");
    
    User user = new User();
    user.setEmail(email);
    user.setUsername(username);
    user.setPassword(password);
    user.setPasswordConfirmation(confirmPassword);
    user.setUserType( UserType.GUEST );
    
    Map<String, String> errors = user.validate();
    if(errors.isEmpty()){
      userDAO.persist(user);
    }else{
      req.setAttribute("errorMap", errors);
      req.setAttribute("type","singin");
      req.setAttribute("user", user );
      RequestDispatcher rd = req.getRequestDispatcher("/welcome.jsp");
      rd.forward(req, resp);
    }
  }
  
  
}
