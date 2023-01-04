package com.winterhack.wiki.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
public class WikiController {
  
  @RequestMapping(method = RequestMethod.GET, path = "/hello")
  public String sayHello() {
    return "Hello, World";
  }

}
