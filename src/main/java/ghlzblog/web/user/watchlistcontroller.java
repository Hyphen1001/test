package ghlzblog.web.user;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ghlzblog.po.User;
import ghlzblog.service.UserService;
import ghlzblog.service.WatchService;

@Controller
public class watchlistcontroller {
	@Autowired
	private WatchService watchService;
	@Autowired
	private UserService userService;
	@GetMapping("/user/watchlist/{id}")
    public String watchlist(Model model,@PathVariable Long id, HttpSession session) {
		
		model.addAttribute("users",userService.findwatch(watchService.findByWatchid(id)));
        model.addAttribute("user",userService.checkUser(id));
        model.addAttribute("state","关注的人");
       return "user/watchlist";
    }
	
	@GetMapping("/user/watchedlist/{id}")
    public String watchedlist(Model model,@PathVariable Long id, HttpSession session) {
		
		model.addAttribute("users",userService.findwatched(watchService.findByWatchedid(id)));
        model.addAttribute("user",userService.checkUser(id));
        model.addAttribute("state","粉丝");
       return "user/watchlist";
    }
}
