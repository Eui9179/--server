package leui.woojoo.domain.users.dto.web;

import leui.woojoo.domain.users.dto.Messages;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SmsRequest {
    private String type;
    private String contentType;
    private String countryCode;
    private String from;
    private String content;
    private List<Messages> messages;
}
