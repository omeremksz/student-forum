package com.omerfurkan.studentforum.controllers;

import com.omerfurkan.studentforum.entities.DirectMessage;
import com.omerfurkan.studentforum.requests.DirectMessageCreateRequest;
import com.omerfurkan.studentforum.requests.DirectMessageUpdateRequest;
import com.omerfurkan.studentforum.services.DirectMessageService;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/direct-messages")
public class DirectMessageController {
    private DirectMessageService directMessageService;

    public DirectMessageController(DirectMessageService directMessageService) {
        this.directMessageService = directMessageService;
    }

    @GetMapping
    public List<DirectMessage> getAllDirectMessages() {
        return directMessageService.getAllDirectMessages();
    }

    @GetMapping("/{directMessageId}")
    public DirectMessage getDirectMessageById(@PathVariable Long directMessageId) {
        return directMessageService.getDirectMessageById(directMessageId);
    }

    @PostMapping
    public DirectMessage createNewDirectMessage(@RequestBody DirectMessageCreateRequest directMessageCreateRequest) {
        return directMessageService.createNewDirectMessage(directMessageCreateRequest);
    }

    @PutMapping("/{directMessageId}")
    public DirectMessage updateDirectMessageById(@PathVariable Long directMessageId,
                                                 @RequestBody DirectMessageUpdateRequest directMessageUpdateRequest) {
        return directMessageService.updateDirectMessageById(directMessageId, directMessageUpdateRequest);
    }

    @DeleteMapping("/{directMessageId}")
    public void deleteDirectMessageById(@PathVariable Long directMessageId) {
        directMessageService.deleteDirectMessageById(directMessageId);
    }
}
