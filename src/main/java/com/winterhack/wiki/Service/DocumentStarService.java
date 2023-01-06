package com.winterhack.wiki.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winterhack.wiki.Entity.UserEntity;
import com.winterhack.wiki.Repository.DocumentStarRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentStarService {

  @Autowired
  private final DocumentStarRepository documentStarRepository;

  public int getDocumentStarCount(String documentTitle) {
    List<UserEntity> userList = documentStarRepository.findAllByDocumentTitle(documentTitle);
    Set<UserEntity> userSet = new HashSet<>(userList);

    return userSet.size();
  }
  
}
