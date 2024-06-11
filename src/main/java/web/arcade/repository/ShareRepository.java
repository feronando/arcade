package web.arcade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.arcade.domain.Share;

import java.util.List;

@Repository
public interface ShareRepository extends JpaRepository<Share, Long> {
    @Query("SELECT s FROM Share s WHERE s.sharedBy.profileId = :profileId")
    List<Share> findBySharedByProfileId(@Param("profileId") Long profileId);

    @Query("SELECT s FROM Share s WHERE s.sharedContent.contentId = :contentId")
    List<Share> findByContentId(@Param("contentId") Long contentId);
}