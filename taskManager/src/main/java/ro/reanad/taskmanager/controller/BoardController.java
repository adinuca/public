package ro.reanad.taskmanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("board.htm")
public class BoardController {
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView getBoard(HttpServletRequest request,
			HttpServletResponse response) {
		String user = (String) request.getSession().getAttribute("user");
				return new ModelAndView("WEB-INF/jsp/board.jsp", "user", user);
	}
	/*@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView postBoard(HttpServletRequest request,
			HttpServletResponse response) {
				return new ModelAndView("WEB-INF/jsp/board.jsp");
	}*/
}
