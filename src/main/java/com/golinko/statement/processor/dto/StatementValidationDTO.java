package com.golinko.statement.processor.dto;

import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "errors")
public class StatementValidationDTO {

    @NonNull
    private Long reference;

    @NonNull
    @Builder.Default
    private Map<String, List<String>> errors = new HashMap<>();
}
