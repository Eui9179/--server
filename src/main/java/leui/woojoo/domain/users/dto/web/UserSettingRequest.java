package leui.woojoo.domain.users.dto.web;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSettingRequest {
    private String name;
    private String is_file;
    private MultipartFile file;
    private String is_group;
    private String group_name;
    private String group_detail1;

    public String getName() {
        return name;
    }

    public String getIsFile() {
        return is_file;
    }

    public String getGroupName() {
        return group_name;
    }

    public String getGroupDetail1() {
        return group_detail1;
    }

    public String getIsGroup() {
        return is_group;
    }

    public MultipartFile getFile() {
        return file;
    }
}
