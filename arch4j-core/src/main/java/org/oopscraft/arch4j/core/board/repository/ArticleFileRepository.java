package org.oopscraft.arch4j.core.board.repository;

import org.oopscraft.arch4j.core.board.ArticleFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleFileRepository extends JpaRepository<ArticleFileEntity, ArticleFileEntity.Pk> {

    List<ArticleFileEntity> findAllByArticleIdOrderByCreatedAtAsc(String articleId);

    void deleteByArticleId(String articleId);

}
