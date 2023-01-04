package com.winterhack.wiki.Controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.winterhack.wiki.Data.DocumentDTO;
import com.winterhack.wiki.Data.ResultDTO;
import com.winterhack.wiki.Entity.DocumentEntity;
import com.winterhack.wiki.Service.DocumentService;

@RestController
public class DocumentController {

  @Autowired
  private DocumentService documentService;
  
  @RequestMapping(method = RequestMethod.POST, path = "/docs")
  public ResultDTO create(HttpServletRequest request, @RequestBody DocumentDTO documentDTO) {
    if (documentDTO.getTitle() == null) {
      return new ResultDTO("문서 타이틀이 존재하지 않습니다",  false);
    } else if (documentDTO.getContent() == null) {
      return new ResultDTO("문서 내용이 존재하지 않습니다",  false);
    }

    documentService.addNewDocument(
      documentDTO.getTitle(),
      documentDTO.getContent(),
      null,
      request.getRemoteAddr()
    );

    return new ResultDTO("새로운 문서 생성", true);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/docs/{documentTitle}")
  public ResultDTO read(@PathVariable("documentTitle") String documentTitle) {
    Optional<DocumentEntity> documentEntity = documentService.getDocument(documentTitle);

    if (documentEntity.isPresent()) {
      return new ResultDTO("문서 조회", true, documentEntity);
    } else {
      return new ResultDTO("문서가 존재하지 않습니다", false);
    }
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/docs/{documentTitle}")
  public ResultDTO delete(HttpServletRequest request, @PathVariable("documentTitle") String documentTitle) {
    Optional<DocumentEntity> documentEntity = documentService.getDocument(documentTitle);

    if (documentEntity.isPresent()) {
      if (documentEntity.get().getContent().equals("")) {
        return new ResultDTO("문서는 이미 내용이 없습니다", false);
      } else {
        documentService.deleteDocument(documentTitle, null, request.getRemoteAddr());
        return new ResultDTO("문서 삭제", true);
      }

    } else {
      return new ResultDTO("문서가 존재하지 않습니다", false);
    }
  }

}
