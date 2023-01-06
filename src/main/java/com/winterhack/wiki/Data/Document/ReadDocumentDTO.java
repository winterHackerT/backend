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
  private int starCount;

  public ReadDocumentDTO(DocumentEntity documentEntity, int starCount) {
    this.id = documentEntity.getId();
    this.title = documentEntity.getTitle();
    this.content = documentEntity.getContent();
    this.datetime = documentEntity.getDatetime();
    this.addr = documentEntity.getAddr();
    this.starCount = starCount;

    UserEntity userEntity = documentEntity.getUser();
    this.username = userEntity == null ? null : userEntity.getUsername();
  }

}
