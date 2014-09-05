package br.com.efraimgentil.speakwithme.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import br.com.efraimgentil.speakwithme.model.User;
import br.com.efraimgentil.speakwithme.model.constants.SessionKeys;
import br.com.efraimgentil.speakwithme.model.constants.UserType;
import br.com.efraimgentil.speakwithme.persistence.UserDAO;

@WebServlet("/singon")
public class SingonServlet extends HttpServlet {
  
  private static final long serialVersionUID = -8630368835759444475L;
  
  @Inject
  private UserDAO userDAO;
  
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    super.doGet(req, resp);
  }
  
  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,
      IOException {
    User user = new User();
    user.setEmail( req.getParameter("user.email")  );
    user.setPassword( req.getParameter("user.password" ));
    
    User authenticatedUser = userDAO.userByEmailAndPassword( user.getEmail() , user.getPassword() );
    if(authenticatedUser != null){
      HttpSession session = req.getSession(true);
      session.setAttribute( SessionKeys.IS_LOGGED , true );
      session.setAttribute( SessionKeys.USER_AUTHENTICATED , authenticatedUser );
      if(UserType.OWNER.equals( authenticatedUser.getUserType() )){
        resp.sendRedirect("owner.jsp");
      }else{
        resp.sendRedirect("chat.jsp");
      }
      return;
    }else{
      RequestDispatcher rd = req.getRequestDispatcher("/welcome.jsp");
      req.setAttribute("type","singon");
      req.setAttribute("message","Email and/or password invalid");
      req.setAttribute("user", user);
      rd.forward(req, resp);
    }
  }
  
}
