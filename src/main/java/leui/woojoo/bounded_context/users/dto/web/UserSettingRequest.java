package leui.woojoo.bounded_context.users.dto.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingRequest {
    private String name;
    private String isFile;
    private MultipartFile file;
    private String isGroup;
    private String groupName;
    private String groupDetail1;
}
