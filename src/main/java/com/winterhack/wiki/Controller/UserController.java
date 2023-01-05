package com.winterhack.wiki.Controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winterhack.wiki.Data.ResultDTO;
import com.winterhack.wiki.Exception.CreateUserException;
import com.winterhack.wiki.Service.UserService;

@RestController
public class UserController {

  @Autowired
  private UserService userService;
  
  @RequestMapping(method = RequestMethod.POST, path = "/user")
  public ResultDTO create(@Valid @RequestBody CreateUserDTO user) {
    try {
      userService.createUser(user.getUsername(), user.getPassword(), user.getEmail());
      return new ResultDTO("새로운 유저 생성", true);

    } catch (CreateUserException error) {
      return new ResultDTO(error.getMessage(), false);
    }
  }
  }

}
