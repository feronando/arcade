package web.arcade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.arcade.domain.Message;
import web.arcade.domain.Post;
import web.arcade.domain.Profile;
import web.arcade.repository.MessageRepository;
import web.arcade.repository.ProfileRepository;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, ProfileRepository profileRepository) {
        this.messageRepository = messageRepository;
        this.profileRepository = profileRepository;
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId).orElse(null);
    }

    public List<Message> findMessagesSentBy(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    public List<Message> findMessagesReceivedBy(Long recipientId) {
        return messageRepository.findByRecipientId(recipientId);
    }

    public List<Message> findConversation(Long senderId, Long recipientId) {
        return messageRepository.findConversation(senderId, recipientId);
    }

    public Message sendMessage(Long senderId, Long recipientId, String content) throws Exception {
        Profile sender = profileRepository.findById(senderId).orElseThrow(() -> new Exception("Profile with ID " + senderId + " not found."));
        Profile recipient = profileRepository.findById(recipientId).orElseThrow(() -> new Exception("Profile with ID " + recipientId + " not found."));

        Message message = new Message();
        message.setContent(content);
        message.setSender(sender);
        message.setRecipient(recipient);
        sender.getSentMessages().add(message);
        recipient.getReceivedMessages().add(message);
        return messageRepository.save(message);
    }
}
