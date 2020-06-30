package ghlzblog.web.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ghlzblog.service.ResourceService;
import ghlzblog.service.TagService;

@Controller
public class uresourcecontroller {	
	
	@Autowired
    private ResourceService resourceService;
	
	public static final String ResourceRoot = "C:/upload/";
	
	 @GetMapping("/user/resource")
	 public String toresource(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
     Pageable pageable, Model model) {
		 model.addAttribute("page",resourceService.listResource(pageable));
	        return "user/resource";
	 }
	 
	 @PostMapping("/user/resource/resourceupload")
	 public String upload (@RequestParam("file") MultipartFile file,
			 RedirectAttributes attributes) {
		 	
			// 获取原始名字
			String fileName = file.getOriginalFilename();
			String temp = file.getOriginalFilename();
			// 获取后缀名
			// String suffixName = fileName.substring(fileName.lastIndexOf("."));
			// 文件保存路径
			String filePath = ResourceRoot;
			// 文件重命名，防止重复
			fileName = filePath + fileName;
			if (resourceService.checkResource(temp)!=null)
			{
				attributes.addFlashAttribute("message","该文件名已存在");				
				return "redirect:/user/resource";
			}
			// 文件对象
			File dest = new File(fileName);
			
			// 判断路径是否存在，如果不存在则创建
			if(!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			try {
				// 保存到服务器中
				file.transferTo(dest);
				attributes.addFlashAttribute("message","上传成功");
				resourceService.AddResource(temp);
				return "redirect:/user/resource";
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			attributes.addFlashAttribute("message","上传失败");
			return "redirect:/user/resource";
		}
	 	
	 
	 @GetMapping("/user/resource/resourcedelete/{id}")
	 public String delete (@PathVariable Long id,
			 RedirectAttributes attributes) {	
		 	
		 	resourceService.deleteResource(id);		 	
	        attributes.addFlashAttribute("message", "删除成功");
			return "redirect:/user/resource";
	 }
	 
	 @GetMapping("/user/resource/resourcedownload/{id}")
	 public void download(@PathVariable Long id,
			 HttpServletResponse response,
			 RedirectAttributes attributes) {	
		 	
		 	ghlzblog.po.Resource temp = resourceService.checkResource(id);
		 	String nam = temp.getName();
		 	String encodedFileName = URLEncoder.encode(nam);
		 	File file = new File(ResourceRoot + temp.getName());
			// 穿件输入对象
		 	try {
		 		FileInputStream fis = new FileInputStream(file);
				// 设置相关格式
				response.setContentType("application/force-download");
				// 设置下载后的文件名以及header
				response.addHeader("Content-disposition", "attachment;fileName=" + encodedFileName);
				System.out.println(nam);
				// 创建输出对象
				OutputStream os = response.getOutputStream();
				// 常规操作
				byte[] buf = new byte[1024];
				int len = 0;
				while((len = fis.read(buf)) != -1) {
					os.write(buf, 0, len);
				}
				fis.close();
		 	}
		 	catch (Exception e) {
		 		e.printStackTrace();
			}
				        
			return;
	 }
}
