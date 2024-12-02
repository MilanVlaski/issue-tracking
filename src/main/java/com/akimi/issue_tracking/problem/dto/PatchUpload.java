package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.problem.engineer.Patch;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PatchUpload {
    private String size;
    private String communicationType;

    public Patch toEntity() {
        return new Patch(communicationType, new BigDecimal(size));
    }
}
