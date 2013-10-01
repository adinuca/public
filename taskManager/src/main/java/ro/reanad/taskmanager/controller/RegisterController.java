package ro.reanad.taskmanager.controller;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.metamodel.source.annotations.JandexHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.bind.annotation.ModelAttribute;

import ro.reanad.taskmanager.model.User;
import ro.reanad.taskmanager.model.janrain.JanrainUser;
import ro.reanad.taskmanager.service.UserService;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class RegisterController extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(RegisterController.class);
	private String apiKey="c454ed8b22a40ad9e6618e862d700a28402e111a";

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // The user's browser will POST a token to your "token_url" you specified to have them
        // redirected to after the auth process:
        String token = request.getParameter("token");
        // Do a request to the Janrain API with the token we just received.
        // see http://developers.janrain.com/documentation/api/auth_info/
        // You may wish to make this HTTP request with e.g. Apache HttpClient instead.
        URL url = new URL("https://rpxnow.com/api/v2/auth_info");
        String params = String.format("apiKey=%s&token=%s", URLEncoder.encode(apiKey, "UTF-8"), URLEncoder.encode(token, "UTF-8"));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.connect();
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        writer.write(params);
        writer.close();

     // Here, we're just copying the response returned by the API to the page served to the browser.
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/javascript");
        IOUtils.copy(connection.getInputStream(), response.getOutputStream());
      //2. Convert JSON to Java object
       /* ObjectMapper mapper = new ObjectMapper();
        JanrainUser user = mapper.readValue(connection.getInputStream(), JanrainUser.class);
        if ("ok".equalsIgnoreCase(user.getStat())){
        	logger.info("User has just logged in "+user.toString());
			ServletRequest session = (ServletRequest) request.getSession();
			session .setAttribute("user", user.getProfile().getPreferredUsername());
			Cookie cookie = new Cookie("user",  user.getProfile().getPreferredUsername());
			cookie.setMaxAge(1000);
			response. addCookie(cookie);
			response.sendRedirect("/board.htm");
			
        }else{
        	response.sendRedirect("/index.htm");
        }*/
    }
      /*  if (result.hasErrors()) {
            return new ModelAndView("index.jsp");
        } else {
            try {
                loginService.register(user);
            } catch (Exception e) {
                logger.error(e.getStackTrace());
            }
            return new ModelAndView("index.jsp");
        }*/

    }

            
            
            
            
