package com.winterhack.wiki.Controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.winterhack.wiki.Data.Document.PostDocumentDTO;
import com.winterhack.wiki.Data.Document.ReadDocumentDTO;
import com.winterhack.wiki.Data.Document.ReadDocumentHistoryDTO;
import com.winterhack.wiki.Data.User.ResultDTO;
import com.winterhack.wiki.Entity.DocumentEntity;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Exception.User.ReadUserException;
import com.winterhack.wiki.Service.DocumentService;
import com.winterhack.wiki.Service.UserService;


@RestController
public class DocumentController {

  @Autowired
  private UserService userService;

  @Autowired
  private DocumentService documentService;

  @RequestMapping(method = RequestMethod.POST, path = "/docs")
  public ResultDTO post(HttpServletRequest request, Principal principal, @RequestBody @Valid PostDocumentDTO document) {
    String working = "문서 수정";
    DocumentEntity documentEntity = documentService.readDocument(document.getTitle());

    if (documentEntity == null) {
      working = "새로운 문서 추가";
    } else if (documentEntity.getContent().equals("")) {
      working = "삭제된 문서 복구";
    }

    UserEntity user = null;

    if (principal != null) {
      try {
        user = userService.readUser(principal.getName());
        
      } catch (ReadUserException error) {
        return new ResultDTO("사용자 인증 오류: " + error.getMessage(), false);
      }
    }

    documentService.postDocument(
      document.getTitle(),
      document.getContent(),
      user,
      request.getRemoteAddr(),
      working,
      document.getMessage()
    );

    return new ResultDTO("문서 작성", true);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/docs/{documentTitle}")
  public ResultDTO read(@PathVariable("documentTitle") String documentTitle) {
    DocumentEntity documentEntity = documentService.readDocument(documentTitle);

    if (documentEntity == null || documentEntity.getContent().equals("")) {
      return new ResultDTO("해당 문서가 존재하지 않습니다", false);
    }

    ReadDocumentDTO readDocumentDTO = new ReadDocumentDTO();

    readDocumentDTO.setTitle(documentEntity.getTitle());
    readDocumentDTO.setContent(documentEntity.getContent());
    readDocumentDTO.setDatetime(documentEntity.getDatetime());
    readDocumentDTO.setAddr(documentEntity.getAddr());

    UserEntity userEntity = documentEntity.getUser();

    if (userEntity != null) {
      readDocumentDTO.setUsername(userEntity.getUsername());
    }

    return new ResultDTO("문서 조회", true, readDocumentDTO);
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/docs/{documentTitle}")
  public ResultDTO delete(
    HttpServletRequest request,
    Principal principal,
    @PathVariable("documentTitle") String documentTitle,
    @RequestParam("message") String message
  ) {
    DocumentEntity documentEntity = documentService.readDocument(documentTitle);

    if (documentEntity == null) {
      return new ResultDTO("문서가 존재하지 않습니다", false);
    }

    UserEntity user = null;

    if (principal != null) {
      try {
        user = userService.readUser(principal.getName());
        
      } catch (ReadUserException error) {
        return new ResultDTO("사용자 인증 오류: " + error.getMessage(), false);
      }
    }

    documentService.postDocument(
      documentTitle,
      "",
      user,
      request.getRemoteAddr(),
      "문서 삭제",
      message
    );

    return new ResultDTO("문서 삭제", true);
  }
}
