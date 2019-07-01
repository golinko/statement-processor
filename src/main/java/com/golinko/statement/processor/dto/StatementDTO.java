package com.golinko.statement.processor.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.golinko.statement.processor.web.converter.LocalDateConverter;
import lombok.*;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"reference", "accountNumber", "description", "startBalance", "mutation", "endBalance", "date"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatementDTO {
    @NonNull
    private Long reference;

    @NonNull
    private String accountNumber;

    @NonNull
    private String description;

    @NonNull
    @PositiveOrZero
    private BigDecimal startBalance;

    @NonNull
    private BigDecimal mutation;

    @NonNull
    @PositiveOrZero
    private BigDecimal endBalance;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonDeserialize(converter = LocalDateConverter.class)
    @PastOrPresent
    private LocalDate date;

    @AssertTrue(message = "not equals to sum of start balance and mutation")
    private boolean isEndBalance() {
        return startBalance.add(mutation).compareTo(endBalance) == 0;
    }
}
