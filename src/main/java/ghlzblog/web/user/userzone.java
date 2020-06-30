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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ghlzblog.dao.UserRepository;
import ghlzblog.po.User;
import ghlzblog.po.watch;
import ghlzblog.service.BlogService;
import ghlzblog.service.TagService;
import ghlzblog.service.TypeService;
import ghlzblog.service.UserService;
import ghlzblog.service.WatchService;
import ghlzblog.util.MD5Utils;


@Controller
public class userzone {
	@Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private WatchService watchService;
    
    @Autowired
    private UserRepository userRepository;

    
    @GetMapping("/user/{id}")
    public String utu(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model,@PathVariable Long id) {
        model.addAttribute("page",blogService.listBlog(pageable,id));
        return "user/userZone";
    }
    
    @GetMapping("/user/userzone/{id}")
    public String otu(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model,@PathVariable Long id, HttpSession session) {
        model.addAttribute("page",blogService.listBlog(pageable,id));
        model.addAttribute("user",userService.checkUser(id));
        User user = (User) session.getAttribute("user");
    	User user2 =  userService.checkUser(id);
    	User user3 =  userService.checkUser(user.getId());
        if(user.getId()!=id)
        {
        	if(watchService.findByWatchidAndWatchedid(user3.getId(),user2.getId())==null)
        		model.addAttribute("state","关注");
        	else
        		model.addAttribute("state","取消关注");
        	return "user/userspace";
        }	
        else
        	return "redirect:/user/"+id;
    }
    @GetMapping("/user/watch/{id}")
    public String watch(@PathVariable Long id, HttpSession session,Model model,Pageable pageable) {
    	User user1 = (User) session.getAttribute("user");
    	User user2 =  userService.checkUser(id);
    	User user3 =  userService.checkUser(user1.getId());
    	model.addAttribute("page",blogService.listBlog(pageable,id));
        model.addAttribute("user",userService.checkUser(id));
    	if(watchService.findByWatchidAndWatchedid(user3.getId(),user2.getId())==null) {
    		user3.setWatch(user3.getWatch()+1);
    		user2.setWatched(user2.getWatched()+1);
    		userRepository.saveAndFlush(user3);
    		userRepository.saveAndFlush(user2);
    		watch w=new watch();
    		w.setWatchedid(user2.getId());
    		w.setWatchid(user3.getId());
    		watchService.savewatch(w);
    		model.addAttribute("state","取消关注");
    		return "user/userspace";
    	}
    	else {
    		user3.setWatch(user3.getWatch()-1);
    		user2.setWatched(user2.getWatched()-1);
    		userRepository.saveAndFlush(user3);
    		userRepository.saveAndFlush(user2);
    		watch w=watchService.findByWatchidAndWatchedid(user3.getId(), user2.getId());
    		watchService.deletewatch(w.getId());
    		model.addAttribute("state","关注");
    		return "user/userspace";
    	}
    	
    }
    
    @GetMapping("/user/{id}/change")
    public String change() {        
        return "user/changeInfo";
    }
    
    public boolean EmailLegal(String email)
 {
  String regex = "\\w+@\\w+(\\.\\w{2,3})*\\.\\w{2,3}";
  return email.matches(regex);
 }
    
    @PostMapping("/user/{id}/change/changeinfo")
    public String changeinfo(@RequestParam String nickname,
      @RequestParam String email,
      @RequestParam String avatar,
      @PathVariable Long id,
      HttpSession session,
      RedirectAttributes attributes) { 
     if (!EmailLegal(email)) {
      attributes.addFlashAttribute("message","邮箱格式有误");
   return "redirect:/user/{id}/change";
     }
     if (nickname.length()>16)
     {
      attributes.addFlashAttribute("message","请保证用户名小于16位");
   return "redirect:/user/{id}/change";
     }
     User user = userService.checkUser(id); 
     user.setNickname(nickname);
     user.setEmail(email);
     user.setAvatar(avatar);
     userRepository.save(user);
     session.setAttribute("user",user);
        return "redirect:/user/{id}";
    }
    
    @PostMapping("/user/{id}/change/changepass")
    public String changepass(@RequestParam String oldpw,
      @RequestParam String newpw,
      @RequestParam String newpw2,
      @PathVariable Long id,
      RedirectAttributes attributes) { 
     
     User user = userService.checkUser(id); 
     if (!user.getPassword().equals(MD5Utils.code(oldpw)))
     {
      attributes.addFlashAttribute("message","原密码错误");
   return "redirect:/user/{id}/change";
     }
     else if (!newpw.equals(newpw2))
     {
      attributes.addFlashAttribute("message","两次密码不一致");
   return "redirect:/user/{id}/change";
     }
     else if (newpw.length()<6||newpw.length()>16)
     {
      attributes.addFlashAttribute("message","密码应在6-16位之间");
   return "redirect:/user/{id}/change";
     }
     user.setPassword(MD5Utils.code(newpw));
     userRepository.save(user);
     
     attributes.addFlashAttribute("message","密码修改成功");
     return "redirect:/user/{id}/change";
    }
}
