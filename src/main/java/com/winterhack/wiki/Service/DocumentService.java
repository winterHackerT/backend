package com.winterhack.wiki.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Data.UserDTO;
import com.winterhack.wiki.Entity.DocumentEntity;
import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Repository.DocumentRepository;
import com.winterhack.wiki.Repository.UserRepository;

@Service
public class DocumentService {

  @Autowired
  private DocumentRepository documentRepository;

  @Autowired
  private UserRepository userRepository;

  public void addNewDocument(String title, String content, UserDTO userDTO, String addr) {
    DocumentEntity documentEntity = new DocumentEntity();

    documentEntity.setTitle(title);
    documentEntity.setContent(content);
    documentEntity.setDatetime(LocalDateTime.now());
    documentEntity.setAddr(addr);

    if (userDTO != null) {
      UserEntity userEntity = userRepository.findByUsername(userDTO.getUsername()).get();
      documentEntity.setUser(userEntity);
    }

    documentRepository.save(documentEntity);
  }

  public Optional<DocumentEntity> getDocument(String title) {
    List<DocumentEntity> list = documentRepository.findAllByTitleOrderByDatetimeDesc(title);

    return list.size() == 0 ? Optional.empty() : Optional.of(list.get(0));
  }

  public void deleteDocument(String title, UserDTO user, String addr) {
    addNewDocument(title, "", user, addr);
  }

}
