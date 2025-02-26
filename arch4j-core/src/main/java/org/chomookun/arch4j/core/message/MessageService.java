package org.chomookun.arch4j.core.message;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.chomookun.arch4j.core.common.data.ValidationUtil;
import org.chomookun.arch4j.core.message.entity.MessageEntity;
import org.chomookun.arch4j.core.message.repository.MessageRepository;
import org.chomookun.arch4j.core.message.model.Message;
import org.chomookun.arch4j.core.message.model.MessageSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    @PersistenceContext
    private final EntityManager entityManager;

    private final MessageRepository messageRepository;

    /**
     * Saves message
     * @param message message
     * @return saved message
     */
    @Transactional
    public Message saveMessage(Message message) {
        ValidationUtil.validate(message);
        MessageEntity messageEntity = messageRepository.findById(message.getMessageId())
                .orElse(MessageEntity.builder()
                    .messageId(message.getMessageId())
                    .build());
        messageEntity.setSystemUpdatedAt(LocalDateTime.now());  // disable dirty checking
        messageEntity.setName(message.getName());
        messageEntity.setValue(message.getValue());
        messageEntity.setNote(message.getNote());
        // save
        MessageEntity savedMessageEntity = messageRepository.saveAndFlush(messageEntity);
        entityManager.refresh(savedMessageEntity);
        return Message.from(savedMessageEntity);
    }

    /**
     * Gets message
     * @param messageId message id
     * @return message
     */
    public Optional<Message> getMessage(String messageId) {
        return messageRepository.findById(messageId).map(Message::from);
    }

    /**
     * Deletes message
     * @param messageId message id
     */
    @Transactional
    public void deleteMessage(String messageId) {
        messageRepository.deleteById(messageId);
        messageRepository.flush();
    }

    /**
     * Gets messages
     * @param messageSearch message search
     * @param pageable pageable
     * @return page of messages
     */
    public Page<Message> getMessages(MessageSearch messageSearch, Pageable pageable) {
        Page<MessageEntity> messageEntityPage = messageRepository.findAll(messageSearch, pageable);
        List<Message> messages = messageEntityPage.getContent().stream()
                .map(Message::from)
                .collect(Collectors.toList());
        return new PageImpl<>(messages, pageable, messageEntityPage.getTotalElements());
    }

}
