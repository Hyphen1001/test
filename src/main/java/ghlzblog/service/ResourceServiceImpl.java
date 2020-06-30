package ghlzblog.service;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ghlzblog.dao.ResourceRepository;
import ghlzblog.po.Comment;
import ghlzblog.po.Resource;
import ghlzblog.po.User;
import ghlzblog.util.MD5Utils;
import ghlzblog.web.user.uresourcecontroller;

@Service
public class ResourceServiceImpl implements ResourceService{
	
	@Autowired
    private ResourceRepository resourceRepository;
	
	@Override
	public Page<Resource> listResource(Pageable pageable) {
		
		return resourceRepository.findAll(pageable);
		
	}

	@Override
	public Resource checkResource(String name) {
		Resource resource = resourceRepository.findByName(name);
        return resource;
	}
	@Override
	public Resource checkResource(Long id) {
		Resource resource = resourceRepository.findById(id);
        return resource;
	}
	
	@Override
	public void AddResource(String name) {
		
		Resource temp = new Resource();
		temp.setName(name);
		resourceRepository.save(temp);
		return ;
	}
	
		
	@Override
	public void deleteResource(Long id) {
		
		Resource t = checkResource(id);
		String na = t.getName();
		
		if (t==null)
		{
			System.out.println("null");
			return ;
		}
		
		resourceRepository.delete(id);
		File file=new File(uresourcecontroller.ResourceRoot);
		File[] list = file.listFiles();
		for (File f :list)
		{
			if (f.getName().equals(na))
			{
				f.delete();
				return ;
			}
		}
	}
	
	
	
}
