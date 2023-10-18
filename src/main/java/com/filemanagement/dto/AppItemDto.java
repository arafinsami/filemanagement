package com.filemanagement.dto;


import com.filemanagement.entity.AppItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppItemDto {

    private Long id;

    private String name;

    private String type;

    public static AppItemDto from(AppItem appItem) {
        AppItemDto dto = new AppItemDto();
        dto.setId(appItem.getId());
        dto.setType(appItem.getType());
        dto.setName(appItem.getName());
        return dto;
    }

    public AppItem to() {
        AppItem appItem = new AppItem();
        appItem.setName(name);
        appItem.setType(type);
        return appItem;
    }

    public void update(AppItem appItem) {
        appItem.setName(name);
        appItem.setType(type);
    }
}
