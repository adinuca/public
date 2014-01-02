package ro.reanad.taskmanager.controller.authentication;

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
    private static final String JANRAIN_URL = "https://rpxnow.com/api/v2/auth_info";
    private static final String API_KEY_S_TOKEN_S = "apiKey=%s&token=%s";
    private static final String UTF_8 = "UTF-8";
    private static final String POST = "POST";
    private static final String TASK_MANAGER_INDEX_HTM = "/taskManager/index.htm";
    private static final String USER = "user";
    private static final String TASK_MANAGER_BOARD_HTM = "/taskManager/board.htm";
    private String apiKey = "801b632c30f5400cd14b040880557bad72b9d13b";

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
		URL url = new URL(JANRAIN_URL);
		String params = String.format(API_KEY_S_TOKEN_S,
				URLEncoder.encode(apiKey, UTF_8),
				URLEncoder.encode(token, UTF_8));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(POST);
		connection.setDoOutput(true);
		connection.connect();
		OutputStreamWriter writer = new OutputStreamWriter(
				connection.getOutputStream(), UTF_8);
		writer.write(params);
		writer.close();

		JanrainUser user = getJanrainUser(connection);
		if ("ok".equalsIgnoreCase(user.getStat())) {
			setCookieAndReturnBoard(request, response, user);
		} else
			response.sendRedirect(TASK_MANAGER_INDEX_HTM);
	}

	private void setCookieAndReturnBoard(HttpServletRequest request,
			HttpServletResponse response, JanrainUser user) throws IOException {
		HttpSession session = request.getSession();
        String userEmail = user.getProfile().getVerifiedEmail();
		session.setAttribute(USER, user.getProfile().getVerifiedEmail());
		Cookie cookie = new Cookie(USER, user.getProfile()
				.getVerifiedEmail());
		cookie.setMaxAge(1000);
		response.addCookie(cookie);
		response.sendRedirect(TASK_MANAGER_BOARD_HTM);
	}

	private JanrainUser getJanrainUser(HttpURLConnection connection)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(
				DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper.readValue(connection.getInputStream(), JanrainUser.class);
	}
}
