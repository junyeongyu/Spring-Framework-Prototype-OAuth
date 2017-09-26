package com.junyeong.yu.prototype.oauth;

import com.junyeong.yu.prototype.oauth.model.User;
import com.junyeong.yu.prototype.oauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sample/api")
public class SampleRestController {

    @Autowired
    private UserRepository repository;

    @RequestMapping("/")
    @ResponseBody
    public String hello() {
        return "{name: \"Hello World!\"}";
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping("/userList")
    @ResponseBody
    public Object userList() {
        Page<User> result = repository.findAll(new PageRequest(0, 10));
        List<User> userList = result.getContent();
        return userList;
    }
}