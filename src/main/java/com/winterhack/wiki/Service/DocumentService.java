package com.winterhack.wiki.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Data.Document.ReadDocumentHistoryDTO;
import com.winterhack.wiki.Entity.DocumentEntity;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Repository.DocumentRepository;

@Service
public class DocumentService {

  @Autowired
  private DocumentRepository documentRepository;

  public void postDocument(String title, String content, UserEntity user, String addr, String working, String message) {
    DocumentEntity documentEntity = new DocumentEntity();

    documentEntity.setTitle(title);
    documentEntity.setContent(content);
    documentEntity.setUser(user);
    documentEntity.setAddr(addr);
    documentEntity.setWorking(working);
    documentEntity.setMessage(message);
    documentEntity.setDatetime(LocalDateTime.now());

    documentRepository.save(documentEntity);
  }

  public DocumentEntity readDocument(String title) {
    List<DocumentEntity> list = documentRepository.findAllByTitleOrderByDatetimeDesc(title);
    return list.size() == 0 ? null : list.get(0);
  }

  public Optional<DocumentEntity> readDocumentById(long documentId) {
    return documentRepository.findById(documentId);
  }

  public List<ReadDocumentHistoryDTO> readDocumentHistory(String title) {
    List<DocumentEntity> list = documentRepository.findAllByTitleOrderByDatetimeAsc(title);
    AtomicLong order = new AtomicLong();

    List<ReadDocumentHistoryDTO> history = list
      .stream()
      .map(
        (x) -> new ReadDocumentHistoryDTO(order.getAndIncrement(), x)
      )
      .collect(Collectors.toList());

    return history;
  }

}
