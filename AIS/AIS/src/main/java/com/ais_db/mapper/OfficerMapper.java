package com.ais_db.mapper;

import com.ais_db.dto.HeadRespDto;
import com.ais_db.dto.OfficerListDto;
import com.ais_db.dto.OfficerRespoDto;
import com.ais_db.model.InsuranceOfficer;
import org.springframework.stereotype.Component;

@Component
public class OfficerMapper {

    public static InsuranceOfficer maptoOfficerEntity(OfficerRespoDto officerRespoDto) {
        InsuranceOfficer insuranceOfficer=new InsuranceOfficer();
        insuranceOfficer.setName(officerRespoDto.name());
        return insuranceOfficer;

    }

    public static InsuranceOfficer maptoHeadEntity(HeadRespDto headRespDto) {
        InsuranceOfficer insuranceOfficer=new InsuranceOfficer();
        insuranceOfficer.setName(headRespDto.name());
        return insuranceOfficer;
    }



    public static OfficerListDto convertToOfficer(InsuranceOfficer insuranceOfficer) {
        return new OfficerListDto(insuranceOfficer.getId(),
                insuranceOfficer.getName());
    }
}
