package com.ais_db.service;


import com.ais_db.dto.OfficerListDto;
import com.ais_db.dto.OfficerRespoDto;
import com.ais_db.enums.Role;
import com.ais_db.exception.ResourceNotFoundException;
import com.ais_db.model.InsuranceOfficer;
import com.ais_db.model.Policy;
import com.ais_db.model.User;
import com.ais_db.repository.InsuranceOfficerRepo;

import com.ais_db.repository.Policyrepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OfficerServiceTest {
    @Mock
    private InsuranceOfficerRepo insuranceOfficerRepo;
    @Mock
    private Policyrepo policyrepo;
    @InjectMocks
    private InsuranceOfficerService insuranceOfficerService;
//    @InjectMocks
//    private PolicyService policyService;
    private InsuranceOfficer officer;
    private InsuranceOfficer officer1;
    private Policy policy;
    @BeforeEach
    public void sampleData()
    {
        officer=new InsuranceOfficer();
        officer.setId(1);
        officer.setName("anitha");
        officer.setRole(Role.OFFICER);

        officer1=new InsuranceOfficer();
        officer1.setId(1);
        officer1.setName("anitha");
        officer1.setRole(Role.OFFICER);

        policy=new Policy();
        policy.setId(3);
        policy.setPolicyName("Bike policy");
        policy.setDetails("can be covered foe minor accidents");

    }
    @Test
    public void getAll_officers()
    {
        when(insuranceOfficerRepo.findAll()).thenReturn(List.of(officer,officer1));
        List<OfficerListDto> actualCall=insuranceOfficerService.getAllOfficers();
        assertThat(actualCall).hasSize(2);

    }
    @Test
    public void getAll_returnNull()
    {
        when(insuranceOfficerRepo.findAll()).thenReturn(List.of());
        List<OfficerListDto> actualCall=insuranceOfficerService.getAllOfficers();
        assertThat(actualCall).hasSize(0);
        assertThat(actualCall).isEmpty();

    }
    @Test
    public void getById_returnOfficer()
    {
        when(insuranceOfficerRepo.findById(100)).thenReturn(Optional.of(officer));
        assertThat(insuranceOfficerService.getById(100).getId()).isEqualTo(1);
        assertThat(insuranceOfficerService.getById(100).getName()).isEqualTo("anitha");

    }
    @Test
    public void getById_returnNull()
    {
        when(insuranceOfficerRepo.findById(100)).thenReturn(Optional.empty());
        assertThatThrownBy(()-> insuranceOfficerService.getById(100))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Invalid Id");
        verify(insuranceOfficerRepo, times(1)).findById(100);

    }
    @Test
    public void deletePolicy_mustDeleteAndReturnNothing()
    {
        when(policyrepo.findById(100)).thenReturn(Optional.of(policy));
        doNothing().when(policyrepo).delete(policy);
        insuranceOfficerService.deletePolicyById(100);
        verify(policyrepo, times(1)).findById(100);
        verify(policyrepo, times(1)).delete(policy);
    }


}
