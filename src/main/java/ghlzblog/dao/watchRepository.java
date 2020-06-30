package ghlzblog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.watch;

public interface watchRepository extends JpaRepository<watch,Long>{
	List<watch> findByWatchid(long watchid);
	List<watch> findByWatchedid(long watchedid);
	watch findByWatchidAndWatchedid(long watchid,long watchedid);
}
