package com.junyeong.yu.prototype.boot;

import com.junyeong.yu.prototype.boot.model.User;
import com.junyeong.yu.prototype.boot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @RequestMapping("/userList")
    @ResponseBody
    public Object userList() {
        Page<User> result = repository.findAll(new PageRequest(0, 10));
        List<User> userList = result.getContent();
        return userList;
    }
}