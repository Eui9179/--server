package leui.woojoo.base.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

@Getter
public class EventSendSms extends ApplicationEvent {

    private final String phoneNumber;
    private final String cp;

    public EventSendSms(Object source, String phoneNumber, String cp) {
        super(source);
        this.phoneNumber = phoneNumber;
        this.cp = cp;
    }
}
