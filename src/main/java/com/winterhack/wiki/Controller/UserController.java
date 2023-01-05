package com.winterhack.wiki.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winterhack.wiki.Data.ResultDTO;
import com.winterhack.wiki.Data.UserDTO;
import com.winterhack.wiki.Service.UserService;

@RestController
public class UserController {

  @Autowired
  private UserService userService;
  
  @RequestMapping(method = RequestMethod.POST, path = "/user")
  public ResultDTO create(@RequestBody UserDTO userDTO) {
    userService.addNewUser(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail());

    return new ResultDTO("새로운 유저 생성", true);
  }

}
