package com.winterhack.wiki.Data.Document;

import java.time.LocalDateTime;

import com.winterhack.wiki.Entity.DocumentEntity;
import com.winterhack.wiki.Entity.UserEntity;

import lombok.Data;

@Data
public class ReadDocumentHistoryDTO {
  
  private long id;
  private Long order;
  private String working;
  private String message;
  private LocalDateTime datetime;
  private String username;
  private String addr;
  private int length;

  public ReadDocumentHistoryDTO(long order, DocumentEntity documentEntity) {
    this.id = documentEntity.getId();
    this.order = order;
    this.working = documentEntity.getWorking();
    this.message = documentEntity.getMessage();
    this.datetime = documentEntity.getDatetime();
    this.addr = documentEntity.getAddr();
    this.length = documentEntity.getContent().length();

    UserEntity userEntity = documentEntity.getUser();
    this.username = userEntity == null ? null : userEntity.getUsername();
  }

}
