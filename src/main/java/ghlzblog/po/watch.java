package ghlzblog.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_watch")
public class watch {
	@Id
    @GeneratedValue
    private long id;
	private long watchedid;
	private long watchid;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getWatchid() {
		return watchid;
	}
	public void setWatchid(long watchid) {
		this.watchid = watchid;
	}
	public long getWatchedid() {
		return watchedid;
	}
	public void setWatchedid(long watchedid) {
		this.watchedid = watchedid;
	}
}
