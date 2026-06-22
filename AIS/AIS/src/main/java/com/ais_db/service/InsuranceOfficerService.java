package com.ais_db.service;


import com.ais_db.dto.HeadRespDto;
import com.ais_db.dto.OfficerListDto;
import com.ais_db.dto.OfficerRespoDto;
import com.ais_db.dto.PolicyDto;
import com.ais_db.enums.PolicyStatus;
import com.ais_db.enums.Role;
import com.ais_db.exception.ResourceNotFoundException;
import com.ais_db.exception.UserAlreadyExistsException;

import com.ais_db.mapper.CustomerMapper;
import com.ais_db.mapper.OfficerMapper;
import com.ais_db.mapper.UserMapper;

import com.ais_db.model.Customer;
import com.ais_db.model.InsuranceOfficer;
import com.ais_db.model.Policy;
import com.ais_db.model.User;
import com.ais_db.repository.InsuranceOfficerRepo;
import com.ais_db.repository.Policyrepo;
import com.ais_db.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceOfficerService {
        private final InsuranceOfficerRepo insuranceOfficerRepo;
        private final PasswordEncoder passwordEncoder;
        private final UserService userService;
        private final Policyrepo policyrepo;
        private final UserRepo userRepo;
    @Value("${officer.password.temp}")
    private String officerTempPassword;

    public InsuranceOfficer getById(int offId) {
        return insuranceOfficerRepo.findById(offId)
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Id"));
    }
    public InsuranceOfficer findByUsername(String username) {
        return insuranceOfficerRepo.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Officer not found"));
    }


    public void addOff(OfficerRespoDto officerRespoDto) {

        String username = officerRespoDto.username();
        String password = officerTempPassword;
        Role role = Role.OFFICER;

        if (userRepo.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(
                    "Username is already taken, use a different username"
            );
        }
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setRole(role);

        user = userService.save(user);

        InsuranceOfficer insuranceOfficer =
                OfficerMapper.maptoOfficerEntity(officerRespoDto);

        insuranceOfficer.setUser(user);
        insuranceOfficerRepo.save(insuranceOfficer);
    }


    public void createPolicy(PolicyDto policyDto) {
        Policy policy = new Policy();

        policy.setPolicyName(policyDto.policyName());
        policy.setDetails(policyDto.details());
        policy.setActivePolicy(policyDto.activePolicy());
        policy.setPolicyClaimed(policyDto.policyClaimed());
        policy.setExpiryDate(policyDto.expiryDate());
        policy.setPolicyStatus(PolicyStatus.ACTIVE);
        policy.setBasePremium(policyDto.basePremium());
        policyrepo.save(policy);
    }


    public Policy updatePolicy(int policyId,int actCount, int claimCount) {

        Policy policy = policyrepo.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found"));
        policy.setActivePolicy(actCount);
        policy.setPolicyClaimed(claimCount);
        return policyrepo.save(policy);
    }


    public Policy getPolicyById(int policyId) {
        return policyrepo.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found with id: " + policyId));
    }

    public void deletePolicyById(int policyId) {
        Policy policy = policyrepo.findById(policyId)
                .orElseThrow(() -> new RuntimeException("Policy not found with id: " + policyId));

        policyrepo.delete(policy);
    }

    public void addHead(HeadRespDto headRespDto) {
        User user= UserMapper.maptoHeadEntity(headRespDto);
        InsuranceOfficer insuranceOfficer= OfficerMapper.maptoHeadEntity(headRespDto);
        user.setRole(Role.HEAD);
        user.setPassword(passwordEncoder.encode(headRespDto.password()));
        user= userService.save(user);
        insuranceOfficer.setUser(user);
        insuranceOfficerRepo.save(insuranceOfficer);
    }


    public List<OfficerListDto> getAllOfficers() {
        List<InsuranceOfficer> officers = insuranceOfficerRepo.findAll();

        return officers.stream()
                .map(OfficerMapper::convertToOfficer)
                .toList();
    }
}
