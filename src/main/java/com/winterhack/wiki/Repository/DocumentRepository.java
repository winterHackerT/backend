package com.winterhack.wiki.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.winterhack.wiki.Entity.DocumentEntity;

@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, Long> {
  public List<DocumentEntity> findAllByTitleOrderByDatetimeAsc(String title);
  public List<DocumentEntity> findAllByTitleOrderByDatetimeDesc(String title);
}
