package com.winterhack.wiki.Data.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PostDocumentDTO {
  
  @Size(min = 2)
  @Pattern(regexp = "\\s*\\S+\\s*")
  @NotEmpty
  private String title;
  
  @Size(min = 2)
  @NotEmpty
  private String content;

  @Size(min = 2)
  @NotEmpty
  private String message;

}
