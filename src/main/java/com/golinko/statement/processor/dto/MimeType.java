package com.golinko.statement.processor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MimeType {
    CSV(".csv"),
    XML(".xml"),
    JSON(".json");

    public String fileExtension;
}
