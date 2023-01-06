package com.winterhack.wiki.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winterhack.wiki.Data.User.CreateUserDTO;
import com.winterhack.wiki.Data.User.ReadUserDTO;
import com.winterhack.wiki.Data.User.ResultDTO;
import com.winterhack.wiki.Data.User.UpdateUserDTO;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Exception.User.CreateUserException;
import com.winterhack.wiki.Exception.User.DeleteUserException;
import com.winterhack.wiki.Exception.User.ReadUserException;
import com.winterhack.wiki.Exception.User.UpdateUserException;
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

  @RequestMapping(method = RequestMethod.GET, path = "/user/{username}")
  public ResultDTO get(@PathVariable("username") String username) {
    try {
      UserEntity userEntity = userService.readUser(username);
      return new ResultDTO("사용자 정보 조회", true, new ReadUserDTO(userEntity));

    } catch (ReadUserException error) {
      return new ResultDTO(error.getMessage(), false);
    }
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/user/{username}")
  public ResultDTO delete(@PathVariable("username") String username) {
    try {
      userService.deleteUser(username);
      return new ResultDTO(String.format("사용자 삭제 (%s)", username), true);

    } catch (DeleteUserException error) {
      return new ResultDTO(error.getMessage(), false);
    }
  }

  @RequestMapping(method = RequestMethod.PUT, path = "/user/{username}")
  public ResultDTO update(@PathVariable("username") String username, @RequestBody UpdateUserDTO updateUser) {
    try {
      userService.updateUser(username, updateUser);
      return new ResultDTO("사용자 정보가 변경되었습니다", true);

    } catch (UpdateUserException error) {
      return new ResultDTO(error.getMessage(), false);
    }
  }

}
