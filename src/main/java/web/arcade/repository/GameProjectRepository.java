package web.arcade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.arcade.domain.GameProject;

import java.util.List;

@Repository
public interface GameProjectRepository extends JpaRepository<GameProject, Long> {
    @Query("SELECT gp FROM GameProject gp WHERE LOWER(gp.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    List<GameProject> findByTitleContaining(@Param("title") String title);

    @Query("SELECT gp FROM GameProject gp ORDER BY gp.releaseDate DESC")
    List<GameProject> findRecentlyReleased();

    @Query("SELECT gp FROM GameProject gp JOIN gp.tags t WHERE t.tagId = :tagId")
    List<GameProject> findByTagId(@Param("tagId") Long tagId);

    @Query("SELECT gp FROM GameProject gp WHERE gp.author.profileId = :profileId")
    List<GameProject> findByAuthorId(@Param("profileId") Long profileId);

    @Query("SELECT gp FROM GameProject gp WHERE gp.isRecruiting = TRUE")
    List<GameProject> findRecruitingProjects();
}