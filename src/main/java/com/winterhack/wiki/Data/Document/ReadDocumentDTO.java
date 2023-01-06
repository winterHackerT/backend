package com.winterhack.wiki.Data.Document;

import java.time.LocalDateTime;

import com.winterhack.wiki.Entity.DocumentEntity;
import com.winterhack.wiki.Entity.UserEntity;

import lombok.Data;

@Data
public class ReadDocumentDTO {
  
  private long id;
  private String title;
  private String content;
  private LocalDateTime datetime;
  private String username;
  private String addr;

  public ReadDocumentDTO(DocumentEntity documentEntity) {
    id = documentEntity.getId();
    title = documentEntity.getTitle();
    content = documentEntity.getContent();
    datetime = documentEntity.getDatetime();
    addr = documentEntity.getAddr();

    UserEntity userEntity = documentEntity.getUser();

    if (userEntity != null) {
      username = userEntity.getUsername();
    }
  }

}
