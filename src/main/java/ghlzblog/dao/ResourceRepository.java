package ghlzblog.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.Resource;


public interface ResourceRepository extends JpaRepository<Resource,Long>{

	Resource findByName(String name);
	Resource findById(Long id);
}
