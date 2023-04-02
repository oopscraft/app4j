package org.oopscraft.arch4j.core.code;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.oopscraft.arch4j.core.code.repository.CodeItemEntity;


@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@RequiredArgsConstructor
public class CodeItem {
	
    private final String id;

    private int sort;

    private String name;

    private String value;

    static CodeItem from(CodeItemEntity codeItemEntity) {
        return CodeItem.builder()
                .id(codeItemEntity.getId())
                .sort(codeItemEntity.getSort())
                .name(codeItemEntity.getName())
                .value(codeItemEntity.getValue())
                .build();
    }

}
