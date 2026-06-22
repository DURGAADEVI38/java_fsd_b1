package com.ais_db.mapper;

import com.ais_db.dto.PolicyDto;
import com.ais_db.model.Policy;
import org.springframework.stereotype.Component;

@Component
public class PolicyMapper {
    public static PolicyDto toDto(Policy policy) {
        System.out.println("STATUS FROM DB = " + policy.getPolicyStatus());

        return new PolicyDto(
                policy.getId(),
                policy.getPolicyName(),
                policy.getDetails(),
                policy.getActivePolicy(),
                policy.getPolicyClaimed(),
                policy.getExpiryDate(),
                policy.getPolicyStatus(),
                policy.getBasePremium()


        );
    }
}
