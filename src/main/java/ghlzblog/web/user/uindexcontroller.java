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

import ghlzblog.dao.BlogRepository;
import ghlzblog.po.Blog;
import ghlzblog.service.BlogService;
import ghlzblog.service.TagService;
import ghlzblog.service.TypeService;

@Controller
public class uindexcontroller {
	@Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;
    
    @Autowired
    private BlogRepository blogRepository;
    
    @GetMapping("/user/blog")
    public String index(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page",blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "user/index";
    }
    
    @PostMapping("/user/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "user/search";
    }
    
    @GetMapping("/user/blog/{id}")
    public String blog(@PathVariable Long id,Model model, HttpSession session) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "user/blog";
    }
    
    @GetMapping("/user/blog/{id}/like")
    public String likeadd(@PathVariable Long id,Model model,HttpSession session) {
        
        Blog blog = blogService.getBlog(id);
        blog.setLikes(blog.getLikes()+1);
        blogRepository.save(blog);
        session.setAttribute("blog",blog);
        return "redirect:/user/blog/{id}";
    }
}	
