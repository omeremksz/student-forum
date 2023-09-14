package com.omerfurkan.studentforum.services;

import com.omerfurkan.studentforum.entities.DirectMessage;
import com.omerfurkan.studentforum.entities.User;
import com.omerfurkan.studentforum.repositories.DirectMessageRepository;
import com.omerfurkan.studentforum.requests.DirectMessageCreateRequest;
import com.omerfurkan.studentforum.requests.DirectMessageUpdateRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class DirectMessageService {
    private DirectMessageRepository directMessageRepository;
    private UserService userService;

    public DirectMessageService(DirectMessageRepository directMessageRepository, UserService userService) {
        this.directMessageRepository = directMessageRepository;
        this.userService = userService;
    }

    public List<DirectMessage> getAllDirectMessages() {
        return directMessageRepository.findAll();
    }

    public DirectMessage getDirectMessageById(Long directMessageId) {
        return directMessageRepository.findById(directMessageId).orElse(null);
    }

    public DirectMessage createNewDirectMessage(DirectMessageCreateRequest directMessageCreateRequest) {
        User sender = userService.getUserById(directMessageCreateRequest.getSenderId());
        User receiver = userService.getUserById(directMessageCreateRequest.getReceiverId());

        if (sender == null || receiver == null) {
            return null;
        } else {
            DirectMessage directMessageToSave = new DirectMessage();

            directMessageToSave.setSender(sender);
            directMessageToSave.setReceiver(receiver);
            directMessageToSave.setContentText(directMessageCreateRequest.getContentText());
            directMessageToSave.setSentDate(LocalDateTime.now());
            directMessageToSave.setUpdateDate(LocalDateTime.now());

            return directMessageRepository.save(directMessageToSave);
        }

    }

    public DirectMessage updateDirectMessageById(Long directMessageId, DirectMessageUpdateRequest directMessageUpdateRequest) {
        Optional<DirectMessage> directMessage = directMessageRepository.findById(directMessageId);

        if (directMessage.isPresent()) {
            DirectMessage directMessageToUpdate = directMessage.get();

            directMessageToUpdate.setContentText(directMessageUpdateRequest.getContentText());
            directMessageToUpdate.setUpdateDate(LocalDateTime.now());

            return directMessageRepository.save(directMessageToUpdate);
        } else {
            return null;
        }
    }

    public void deleteDirectMessageById(Long directMessageId) {
        directMessageRepository.deleteById(directMessageId);
    }
}
