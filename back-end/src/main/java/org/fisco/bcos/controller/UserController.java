package org.fisco.bcos.controller;


import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.constants.ContractConstants;
import org.fisco.bcos.dao.AccountDao;
import org.fisco.bcos.entity.Account;
import org.fisco.bcos.entity.User;
import org.fisco.bcos.service.ContractService;
import org.fisco.bcos.util.RestResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UserController {
    @Autowired
    AccountDao accountDao;
    @Autowired
    ContractService contractService;

    @RequestMapping("/api/login")
    public HashMap<String,Object> login(@RequestParam Map<String,Object> request){
        String privateKey = (String) request.get("privateKey");
        log.info("privateKey:" + privateKey);
        try{
            if (contractService.validate(privateKey)) return RestResponce.success();
            return RestResponce.fail();
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }

    @RequestMapping("/api/userinfo")
    public HashMap<String,Object> userinfo(@RequestParam Map<String,Object> request){
        String privateKey = (String) request.get("privateKey");
        String address= ContractConstants.emptyAddress;
        if (request.containsKey("address")) address=(String)request.get("address");
        try{
            if (address==ContractConstants.emptyAddress) return RestResponce.success(contractService.getUser(privateKey));
            return RestResponce.success(contractService.getUserByAddress(privateKey,address));
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }



}