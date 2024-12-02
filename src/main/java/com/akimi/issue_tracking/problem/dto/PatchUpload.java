package com.akimi.issue_tracking.problem.dto;

import com.akimi.issue_tracking.problem.engineer.Patch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PatchUpload {
    private String size;
    private String customerCommunicationType;

    public Patch toEntity() {
        return new Patch(customerCommunicationType, new BigDecimal(size));
    }
}
