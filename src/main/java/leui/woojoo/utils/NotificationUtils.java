package leui.woojoo.utils;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class NotificationUtils {

    private final FirebaseMessaging instance;

    public void sendMessageToFriends(List<String> fcmTokenList, String title, String body) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .setImage(null)
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(fcmTokenList)
                .build();

        sendMulticastMessage(message);
    }

    public String sendMessage(Message message) throws FirebaseMessagingException {
        return this.instance.send(message);
    }

    public BatchResponse sendMulticastMessage(MulticastMessage message) throws FirebaseMessagingException {
        return this.instance.sendMulticast(message);
    }
}