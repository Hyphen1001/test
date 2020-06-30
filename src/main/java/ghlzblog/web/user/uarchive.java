package ghlzblog.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ghlzblog.service.BlogService;


@Controller
public class uarchive {

    @Autowired
    private BlogService blogService;

    @GetMapping("/user/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "user/archives";
    }
}
