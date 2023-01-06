package com.winterhack.wiki.Controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winterhack.wiki.Data.User.ResultDTO;
import com.winterhack.wiki.Entity.DocumentEntity;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Exception.User.ReadUserException;
import com.winterhack.wiki.Service.DocumentService;
import com.winterhack.wiki.Service.DocumentStarService;
import com.winterhack.wiki.Service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DocumentStarController {

  private final UserService userService;
  private final DocumentService documentService;
  private final DocumentStarService documentStarService;
  
  @RequestMapping(method = RequestMethod.GET, path = "/docs/star/{documentTitle}")
  public ResultDTO toggle(Principal principal, @PathVariable("documentTitle") String documentTitle) {
    // NOTE: Authentication required

    DocumentEntity documentEntity = documentService.readDocument(documentTitle);

    if (documentEntity == null) {
      return new ResultDTO("해당 문서가 존재하지 않습니다", false);

    } else if (principal == null) {
      return new ResultDTO("사용자 인증이 필요합니다", false);
    }

    try {
      UserEntity userEntity = userService.readUser(principal.getName());
      documentStarService.toggleDocumentStar(documentEntity, userEntity);
  
      return new ResultDTO("문서 스타 상태가 변경되었습니다", true);

    } catch (ReadUserException error) {
      return new ResultDTO("사용자가 존재하지 않습니다", false);
    }
  }

}
