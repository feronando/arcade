package web.arcade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web.arcade.domain.Message;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.sender.profileId = :senderId")
    List<Message> findBySenderId(@Param("senderId") Long senderId);

    @Query("SELECT m FROM Message m WHERE m.recipient.profileId = :recipientId")
    List<Message> findByRecipientId(@Param("recipientId") Long recipientId);

    @Query("SELECT m FROM Message m WHERE (m.sender.profileId = :senderId AND m.recipient.profileId = :recipientId) OR (m.sender.profileId = :recipientId AND m.recipient.profileId = :senderId) ORDER BY m.sentDate ASC")
    List<Message> findConversation(@Param("senderId") Long senderId, @Param("recipientId") Long recipientId);
}