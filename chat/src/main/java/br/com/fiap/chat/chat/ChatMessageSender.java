package br.com.fiap.chat.chat;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ChatMessageSender {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageReceiver chatMessageReceiver;

    public ChatMessageSender(RabbitTemplate rabbitTemplate, SimpMessagingTemplate messagingTemplate, ChatMessageReceiver chatMessageReceiver) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageReceiver = chatMessageReceiver;
    }

    public void sendMessage(String message) {
        chatMessageReceiver.getMessages().add(message);
        messagingTemplate.convertAndSend("/topic/messages", message);
    }

    public void sendAllMessages() {
        List<String> allMessages = chatMessageReceiver.getMessages();
        for (String message : allMessages) {
            messagingTemplate.convertAndSend("/topic/messages", message);
        }
    }
}
