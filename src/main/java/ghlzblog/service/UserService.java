package ghlzblog.service;

import java.util.List;

import ghlzblog.po.User;
import ghlzblog.po.watch;


public interface UserService {

    User checkUser(String username, String password);
    User checkUser(String username);
    User checkUser(Long id);
    List<User> findwatch(List<watch> watch);
    List<User> findwatched(List<watch> watch);
    void AddUser(String username,String password,String avatar,String email);
}
