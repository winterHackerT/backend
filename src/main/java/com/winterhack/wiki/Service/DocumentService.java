package com.winterhack.wiki.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
    List<DocumentEntity> list = documentRepository.findAllByTitleOrderByDatetimeDesc(title);
    AtomicLong order = new AtomicLong(list.size());

    List<ReadDocumentHistoryDTO> history = list
      .stream()
      .map(
        (x) -> new ReadDocumentHistoryDTO(order.getAndDecrement(), x)
      )
      .collect(Collectors.toList());

    return history;
  }

  public List<DocumentEntity> readRecentDocuments(int count) {
    return documentRepository.findAll(Sort.by(Sort.Direction.DESC, "datetime"));
  }

  public Set<String> findDocumentsByTitle(String title) {
    return documentRepository
      .findAllByTitleContains(title)
      .stream()
      .map((x) -> x.getTitle())
      .collect(Collectors.toSet());
  }

  public Set<String> readRandomDocuments(int count) {
    Set<String> titles = documentRepository
      .findAll()
      .stream()
      .map((x) -> x.getTitle())
      .collect(Collectors.toSet());

    List<String> list = new ArrayList<>(titles);

    Collections.shuffle(list);

    int size = list.size();
    if (count > size) {
      count = size;
    }

    Iterator<String> iterator = list.iterator();
    Set<String> result = new HashSet<String>();

    for (int index = 0; index < count; ++index) {
      result.add(iterator.next());
    }

    return result;
  }

  public Set<String> getDocumentBacklink(String documentTitle) {
    String query = String.format("[%s](/w/%s)", documentTitle, documentTitle);

    return documentRepository
      .findAllByContentContainsOrderByDatetimeDesc(query)
      .stream()
      .map((x) -> x.getTitle())
      .collect(Collectors.toSet());
  }

}
