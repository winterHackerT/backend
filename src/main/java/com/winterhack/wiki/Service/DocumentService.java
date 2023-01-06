package com.winterhack.wiki.Service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Entity.DocumentEntity;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Repository.DocumentRepository;

@Service
public class DocumentService {

  @Autowired
  private DocumentRepository documentRepository;

  public void postDocument(String title, String content, UserEntity user, String addr) {
    DocumentEntity documentEntity = new DocumentEntity();

    documentEntity.setTitle(title);
    documentEntity.setContent(content);
    documentEntity.setUser(user);
    documentEntity.setAddr(addr);
    documentEntity.setDatetime(LocalDateTime.now());

    documentRepository.save(documentEntity);
  }

  public DocumentEntity readDocument(String title) {
    List<DocumentEntity> list = documentRepository.findAllByTitleOrderByDatetimeDesc(title);
    return list.size() == 0 ? null : list.get(0);
  }

}
