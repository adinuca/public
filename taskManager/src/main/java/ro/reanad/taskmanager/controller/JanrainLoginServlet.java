package ro.reanad.taskmanager.controller;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import ro.reanad.taskmanager.model.janrain.JanrainUser;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class JanrainLoginServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String apiKey = "c454ed8b22a40ad9e6618e862d700a28402e111a";

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// The user's browser will POST a token to your "token_url" you
		// specified to have them
		// redirected to after the auth process:
		String token = request.getParameter("token");
		// Do a request to the Janrain API with the token we just received.
		// see http://developers.janrain.com/documentation/api/auth_info/
		// You may wish to make this HTTP request with e.g. Apache HttpClient
		// instead.
		URL url = new URL("https://rpxnow.com/api/v2/auth_info");
		String params = String.format("apiKey=%s&token=%s",
				URLEncoder.encode(apiKey, "UTF-8"),
				URLEncoder.encode(token, "UTF-8"));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.connect();
		OutputStreamWriter writer = new OutputStreamWriter(
				connection.getOutputStream(), "UTF-8");
		writer.write(params);
		writer.close();

		JanrainUser user = getJanrainUser(connection);
		if ("ok".equalsIgnoreCase(user.getStat())) {
			setCookieAndReturnBoard(request, response, user);
		} else
			response.sendRedirect("/taskManager/index.htm");
	}

	private void setCookieAndReturnBoard(HttpServletRequest request,
			HttpServletResponse response, JanrainUser user) throws IOException {
		HttpSession session = request.getSession();
		session.setAttribute("user", user.getProfile().getVerifiedEmail());
		Cookie cookie = new Cookie("user", user.getProfile()
				.getVerifiedEmail());
		cookie.setMaxAge(1000);
		response.addCookie(cookie);
		response.sendRedirect("/taskManager/board.htm");
	}

	private JanrainUser getJanrainUser(HttpURLConnection connection)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(
				DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(connection.getInputStream(), JanrainUser.class);
	}
}
