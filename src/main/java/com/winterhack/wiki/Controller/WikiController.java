package com.winterhack.wiki.Controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WikiController {
  
  @RequestMapping(method = RequestMethod.GET, path = "/hello")
  public String sayHello() {
    return "Hello, World";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/secure")
  public String getSecure() {
    return "SECURE TEST API";
  }

  @RequestMapping(method = RequestMethod.GET, path = "/addr")
  public String getAddr(HttpServletRequest request) {
    return request.getRemoteAddr();
  }

}
