package web.arcade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.arcade.domain.Comment;
import web.arcade.domain.Message;
import web.arcade.domain.Post;
import web.arcade.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllPosts() {
        return ResponseEntity.ok(messageService.findAll());
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<Message> getByMessageId(@PathVariable Long messageId) {
        return ResponseEntity.ok(messageService.getMessageById(messageId));
    }

    @GetMapping("/sent/{senderId}")
    public ResponseEntity<List<Message>> findMessagesSentBy(@PathVariable Long senderId) {
        List<Message> messages = messageService.findMessagesSentBy(senderId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/received/{recipientId}")
    public ResponseEntity<List<Message>> findMessagesReceivedBy(@PathVariable Long recipientId) {
        List<Message> messages = messageService.findMessagesReceivedBy(recipientId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/conversation/{senderId}/{recipientId}")
    public ResponseEntity<List<Message>> findConversation(@PathVariable Long senderId, @PathVariable Long recipientId) {
        List<Message> conversation = messageService.findConversation(senderId, recipientId);
        return ResponseEntity.ok(conversation);
    }

    @PostMapping("send-message/{senderId}/{recipientId}")
    public ResponseEntity sendMessage(@RequestParam Long senderId, @RequestParam Long recipientId, @RequestBody String content) {
        try {
            Message message = messageService.sendMessage(senderId, recipientId, content);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error sending message: " + e.getMessage());
        }
    }
}
