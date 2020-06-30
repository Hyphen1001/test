package ghlzblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.watchRepository;
import ghlzblog.po.watch;


@Service
public class WatchServicelmpl implements WatchService {

	@Autowired
	watchRepository watch;
	
	@Override
	public List<watch> findByWatchid(Long watchid) {
		return watch.findByWatchid(watchid);
	}
	
	@Override
	public List<watch> findByWatchedid(Long watchedid) {
		// TODO 自动生成的方法存根
		return watch.findByWatchedid(watchedid);
	}

	@Override
	public watch findByWatchidAndWatchedid(long watchid, long watchedid) {
		// TODO 自动生成的方法存根
		return watch.findByWatchidAndWatchedid(watchid, watchedid);
	}

	@Override
	public void savewatch(watch w) {
		watch.saveAndFlush(w);
	}

	@Override
	public void deletewatch(Long id) {
		watch.delete(id);
	}

}
