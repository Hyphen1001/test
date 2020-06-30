package ghlzblog.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import ghlzblog.po.User;
import ghlzblog.po.watch;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    User findById(Long id);
}
