package com.winterhack.wiki.Data.Document;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class PostDocumentDTO {
  
  @NotEmpty
  private String title;
  
  @NotEmpty
  private String content;

}
