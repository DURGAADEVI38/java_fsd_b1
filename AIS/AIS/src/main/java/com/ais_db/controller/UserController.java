package com.ais_db.controller;

import com.ais_db.dto.QuoteDto;
import com.ais_db.dto.UserRespDto;
import com.ais_db.dto.VehicleDto;
import com.ais_db.dto.VehicleRespDo;
import com.ais_db.enums.AddOnType;
import com.ais_db.service.QuoteService;
import com.ais_db.service.UserInfoService;
import com.ais_db.service.VehicleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserInfoService userinfoService;
    private final VehicleService vehicleService;
    private final QuoteService quoteService;

    //1.Adding user in db
    @PostMapping("/api/user/add")
    public void addUser(@Valid @RequestBody UserRespDto userRespDto)
    {

        userinfoService.addUser(userRespDto);
    }

    //2.User registering his vehicle
    @PostMapping("/api/vehicle/register")
    public void vehicleRegister(@Valid @RequestBody VehicleDto vehicleDto, Principal principal)
    {
        String username= principal.getName();
        vehicleService.vehicleRegister(vehicleDto,username);
    }
    @GetMapping("api/customer/my-vehicles")
    public List<VehicleRespDo> getMyVehicles(Principal principal)
    {
        String username = principal.getName();
        return vehicleService.getMyVehicles(username);
    }
    //3.Quote creation
    @PostMapping("api/generate/{vehicleId}/{policyId}")
    public QuoteDto generateQuote(@PathVariable Integer vehicleId,
                                  @PathVariable Integer policyId,
                                  @RequestBody List<AddOnType> addons
    ) {
        return quoteService.generateQuote(vehicleId,policyId,addons);
    }
}
