package leui.woojoo.utils;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class NotificationUtils implements Runnable{

    private final FirebaseMessaging instance;
    private List<String> fcmTokenList;
    private String title;
    private String body;

    public void setData(List<String> fcmTokenList, String title, String body) {
        this.fcmTokenList = fcmTokenList;
        this.title = title;
        this.body = body;
    }

    public void sendMessageToFriends() throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .setImage(null)
                .build();
        List<String> newFcmList = fcmTokenList.stream()
                .filter(fcm -> (fcm != null && !fcm.equals("")))
                .toList();

        if (newFcmList.size() > 0) {
            MulticastMessage message = MulticastMessage.builder()
                    .setNotification(notification)
                    .addAllTokens(newFcmList)
                    .build();
            sendMulticastMessage(message);
        }
    }

    public String sendMessage(Message message) throws FirebaseMessagingException {
        return this.instance.send(message);
    }

    public void sendMulticastMessage(MulticastMessage message) throws FirebaseMessagingException {
        this.instance.sendMulticast(message);
    }

    @Override
    public void run() {
        try {
            sendMessageToFriends();
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }

}