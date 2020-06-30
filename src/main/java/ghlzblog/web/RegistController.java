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
@RequestMapping("/regist")
public class RegistController {
	
	@Autowired
	private UserService userService;
	
	private static boolean now = true;
	@GetMapping
    public String registPage() {
        return "register";
    }
	
	public boolean EmailLegal(String email)
	{
		String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
		return email.matches(regex);
	}
	
	@PostMapping("/register")
	public String regist(@RequestParam String username,
	       @RequestParam String password,
	       @RequestParam String password2,
	       @RequestParam String email,
	       @RequestParam String avatar,
	       HttpSession session,
	       RedirectAttributes attributes) {
		
//		if (now)
//		{
//			now = false;
//		}
//		else {
//			Thread.yield();
//		}
		if (username.length() < 6 || username.length() > 16)
		{
			attributes.addFlashAttribute("message","用户名应在6-16位之间");
			return "redirect:/regist";
		}
		else if (password.length() < 6 || password.length() > 16)
		{
			attributes.addFlashAttribute("message","密码应在6-16位之间");
			return "redirect:/regist";
		}
		else if (!password.equals(password2)) {
			attributes.addFlashAttribute("message","两次密码不一致");
			return "redirect:/regist";
		}
		else if (!EmailLegal(email))
		{
			attributes.addFlashAttribute("message","邮箱格式有误");
			return "redirect:/regist";
		}
		else {
			User user = userService.checkUser(username);
			if (user!=null)
			{
				attributes.addFlashAttribute("message","用户名已存在");
				return "redirect:/regist";
			}
		}
	   userService.AddUser(username,password,avatar,email);
	   return "redirect:/user";
	}
}
