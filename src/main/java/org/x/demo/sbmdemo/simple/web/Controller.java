package org.x.demo.sbmdemo.simple.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.x.demo.sbmdemo.simple.mapper.UserMapper;

@RestController
public class Controller {
    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public Object index() {
        return "SpringBoot MyBatis Simple Demo";
    }

    @RequestMapping("/user/{id}")
    public Object findUserById(@PathVariable Long id) {
        return userMapper.findById(id);
    }
}
