package web.arcade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.arcade.domain.Tag;
import web.arcade.domain.User;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("SELECT t FROM Tag t WHERE LOWER(t.name) = LOWER(:name)")
    Tag findTagByName(@Param("name") String name);
}