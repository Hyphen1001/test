package ghlzblog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ghlzblog.po.Comment;
import ghlzblog.po.Resource;
import ghlzblog.po.User;


public interface ResourceService {
	
	Page<Resource> listResource(Pageable pageable);
	
	Resource checkResource(String name);
	Resource checkResource(Long id);
		    
	void AddResource(String name);
	
	void deleteResource(Long id);
}
