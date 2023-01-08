package com.winterhack.wiki.Controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.winterhack.wiki.Service.DocumentStarService;
import com.winterhack.wiki.Service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class DocumentController {

  private final UserService userService;
  private final DocumentService documentService;
  private final DocumentStarService documentStarService;

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

    int starCount = documentStarService.getDocumentStarCount(documentTitle);
    ReadDocumentDTO readDocumentDTO = new ReadDocumentDTO(documentEntity, starCount);

    return new ResultDTO("문서 조회", true, readDocumentDTO);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/docs/id/{documentId}")
  public ResultDTO readById(@PathVariable("documentId") long documentId) {
    Optional<DocumentEntity> documentEntityOptional = documentService.readDocumentById(documentId);

    if (!documentEntityOptional.isPresent()) {
      return new ResultDTO("해당 문서가 존재하지 않습니다", false);
    }

    DocumentEntity documentEntity = documentEntityOptional.get();

    int starCount = documentStarService.getDocumentStarCount(documentEntity.getTitle());
    ReadDocumentDTO readDocumentDTO = new ReadDocumentDTO(documentEntity, starCount);

    return new ResultDTO("문서 조회", true, readDocumentDTO);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/docs/random")
  public ResultDTO randomRead(@RequestParam(defaultValue = "1") int count) {
    Set<String> results = documentService.readRandomDocuments(count);
    return new ResultDTO("랜덤 문서 조회", true, results);
  }

  @RequestMapping(method = RequestMethod.DELETE, path = "/docs/{documentTitle}")
  public ResultDTO delete(
    HttpServletRequest request,
    Principal principal,
    @PathVariable("documentTitle") String documentTitle,
    @RequestParam(name = "message", defaultValue = "문서 삭제") String message
  ) {
    DocumentEntity documentEntity = documentService.readDocument(documentTitle);

    if (documentEntity == null || documentEntity.getContent().equals("")) {
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

  @RequestMapping(method = RequestMethod.GET, path = "/docs/history/{documentTitle}")
  public ResultDTO history(@PathVariable("documentTitle") String documentTitle) {
    List<ReadDocumentHistoryDTO> documentHistoryList = documentService.readDocumentHistory(documentTitle);
    return new ResultDTO("문서 역사 조회", true, documentHistoryList);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/docs/recent")
  public ResultDTO recent() {
    List<ReadDocumentDTO> list = documentService
      .readRecentDocuments(20)
      .stream()
      .map(
        (document) -> {
          int starCount = documentStarService.getDocumentStarCount(document.getTitle());
          return new ReadDocumentDTO(document, starCount);
        }
      )
      .collect(Collectors.toList());

    return new ResultDTO("최근 편집된 문서들", true, list);
  }

  @RequestMapping(method = RequestMethod.GET, path = "/docs/search/{query}")
  public ResultDTO search(@PathVariable("query") String query) {
    return new ResultDTO("문서 검색", true, documentService.findDocumentsByTitle(query));
  }
}
