package com.winterhack.wiki.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Entity.DocumentEntity;
import com.winterhack.wiki.Entity.DocumentStarEntity;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Repository.DocumentStarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentStarService {

  @Autowired
  private final DocumentStarRepository documentStarRepository;

  public int getDocumentStarCount(String documentTitle) {
    List<UserEntity> userList = documentStarRepository
      .findAllByDocumentTitle(documentTitle)
      .stream()
      .map((x) -> x.getUser())
      .collect(Collectors.toList());

    Set<UserEntity> userSet = new HashSet<>(userList);

    return userSet.size();
  }

  public void toggleDocumentStar(DocumentEntity documentEntity, UserEntity userEntity) {
    Optional<DocumentStarEntity> documentStarEntityOptional = documentStarRepository
      .findByDocumentTitleAndUser(documentEntity.getTitle(), userEntity);

    if (documentStarEntityOptional.isPresent()) {
      // Disabled star
      documentStarRepository.delete(documentStarEntityOptional.get());

    } else {
      // Enabled star
      DocumentStarEntity documentStarEntity = new DocumentStarEntity();

      documentStarEntity.setDocumentTitle(documentEntity.getTitle());
      documentStarEntity.setUser(userEntity);
      
      documentStarRepository.save(documentStarEntity);
    }
  }
  
}
