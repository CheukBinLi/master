package project.master.test.dbmanager.dao_service_controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.portlet.ModelAndView;

@Controller
@Scope("prototype")
@RequestMapping("/system/user/")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "user_{number}", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPage(HttpServletRequest request, HttpServletResponse response) {
		return new ModelAndView(request.getPathInfo()).addAllObjects(request.getParameterMap());
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView post(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("post");
		return null;
	}

	@RequestMapping(value = "doupdate", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("post");
		return null;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public Object put(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		return null;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		return null;
	}

	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public Object delete(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		return null;
	}

}
