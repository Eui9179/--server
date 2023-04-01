package leui.woojoo.domain.groups.dto;

import leui.woojoo.domain.groups.Groups;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupSimple {
    private String name;
    private String detail1;

    public GroupSimple(Groups groups) {
        this.name = groups.getGroupName();
        this.detail1 = groups.getDetail1();
    }
}
