package com.akimi.issue_tracking.problem;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ProblemStateConverter implements AttributeConverter<ProblemState, String> {

    @Override
    public String convertToDatabaseColumn(ProblemState attribute) {
        return attribute.serbian;
    }

    @Override
    public ProblemState convertToEntityAttribute(String dbData) {
        return ProblemState.valueOfIgnoreCase(dbData);
    }

}
