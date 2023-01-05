package com.winterhack.wiki.Controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winterhack.wiki.Data.ResultDTO;


@RestController
public class DocumentController {

  @RequestMapping(method = RequestMethod.POST, path = "/docs")
  public ResultDTO postDocument() {}

  @RequestMapping(method = RequestMethod.GET, path = "/docs/{documentTitle}")
  public ResultDTO getDocument(@PathVariable("documentTitle") String documentTitle) {}

  @RequestMapping(method = RequestMethod.DELETE, path = "/docs/{documentTitle}")
  public ResultDTO deleteDocument(@PathVariable("documentTitle") String documentTitle) {}

}
