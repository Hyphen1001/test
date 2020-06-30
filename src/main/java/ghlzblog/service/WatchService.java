package ghlzblog.service;

import java.util.List;

import ghlzblog.po.Blog;
import ghlzblog.po.watch;

public interface WatchService {
	List<watch> findByWatchid(Long watchid);
	List<watch> findByWatchedid(Long watchedid);
	watch findByWatchidAndWatchedid(long watchid,long watchedid);
	void savewatch(watch w);
	void deletewatch(Long id);
}
