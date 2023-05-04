package leui.woojoo.bounded_context.groups.dto;

import leui.woojoo.bounded_context.groups.entity.Groups;
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
