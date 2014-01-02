package ro.reanad.taskmanager.controller.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("board.htm")
public class BoardController {

    private static final String BOARD_JSP = "WEB-INF/jsp/board.jsp";
    private static final String USER = "user";

    @RequestMapping(method = RequestMethod.GET)
	protected ModelAndView getBoard(HttpServletRequest request,
			HttpServletResponse response) {
		String user = (String) request.getSession().getAttribute(USER);
				return new ModelAndView(BOARD_JSP, USER, user);
	}

}
