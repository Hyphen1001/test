package ghlzblog.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ghlzblog.po.User;
import ghlzblog.service.UserService;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	
	@GetMapping
	public String ResourcePage()
	{
		return "resource";
	}
	
	
}
