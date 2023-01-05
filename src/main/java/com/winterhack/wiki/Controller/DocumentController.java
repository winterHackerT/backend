package com.winterhack.wiki.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winterhack.wiki.Data.ResultDTO;
import com.winterhack.wiki.Service.DocumentService;


@RestController
public class DocumentController {

  @Autowired
  private DocumentService documentService;

  @RequestMapping(method = RequestMethod.POST, path = "/docs")
  public ResultDTO post() {}

  @RequestMapping(method = RequestMethod.GET, path = "/docs/{documentTitle}")
  public ResultDTO read(@PathVariable("documentTitle") String documentTitle) {}

  @RequestMapping(method = RequestMethod.DELETE, path = "/docs/{documentTitle}")
  public ResultDTO delete(@PathVariable("documentTitle") String documentTitle) {}

}
