package com.winterhack.wiki.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.winterhack.wiki.Entity.DocumentStarEntity;
import com.winterhack.wiki.Entity.UserEntity;

@Repository
public interface DocumentStarRepository extends JpaRepository<DocumentStarEntity, Long> {
  List<UserEntity> findAllByDocumentTitle(String documentTitle);
}
