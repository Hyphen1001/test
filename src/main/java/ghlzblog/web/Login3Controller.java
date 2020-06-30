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
@RequestMapping("/user")
public class Login3Controller {

	@Autowired
	private UserService userService;
	
    @GetMapping
    public String loginPage() {
        return "login";
    }
	@PostMapping("/login")
	public String login(@RequestParam String username,
	       @RequestParam String password,
	       HttpSession session,
	       RedirectAttributes attributes) {
	   User user = userService.checkUser(username, password);
	   if (user != null) {
		   user.setPassword(null);
		   System.out.println("hree");
	       session.setAttribute("user",user);
	       return "user/welcome";
	   } else {
	       attributes.addFlashAttribute("message", "用户名和密码错误");
	       return "redirect:/user";
	   }
	}
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/";
    }
}