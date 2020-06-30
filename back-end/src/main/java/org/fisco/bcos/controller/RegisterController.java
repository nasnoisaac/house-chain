package org.fisco.bcos.controller;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.dao.AccountDao;
import org.fisco.bcos.entity.Account;
import org.fisco.bcos.entity.Estate;
import org.fisco.bcos.entity.User;
import org.fisco.bcos.service.ContractService;
import org.fisco.bcos.util.RestResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class RegisterController {
    @Autowired
    AccountDao accountDao;
    @Autowired
    ContractService contractService;

    @RequestMapping(value = "/api/register",method = RequestMethod.POST)
    public HashMap<String,Object> Register(@RequestParam Map<String,Object> request){
        HashMap<String,Object> map=new HashMap<>();
        User user=new User();
        user.setName((String)request.get("name"));
        user.setPhone((String)request.get("phone"));
        try{
            Account account=accountDao.createAccount();
            contractService.registerUser(account.getPrivateKey(),user);
            map.put("privateKey",account.getPrivateKey());
            map.put("publicKey",account.getPublicKey());
            map.put("address",account.getAddress());
            return RestResponce.success(map);
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/company",method = RequestMethod.POST)
    public HashMap<String,Object> RegisterCompany(@RequestParam Map<String,Object> request){
        try{
            User user=new User();
            user.setName((String)request.get("name"));
            user.setId((String)request.get("id"));
            user.setLocation((String)request.get("location"));
            user.setPhone((String)request.get("phone"));
            user.setEmail((String)request.get("email"));
            String privateKey = (String)request.get("privateKey");
            contractService.registerCompany(privateKey,user);
            return RestResponce.success("企业注册成功");
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/estate",method = {RequestMethod.POST})
    public HashMap<String,Object> RegisterEstate(@RequestParam Map<String,Object> request){
        try{
            Estate estate=new Estate();
            estate.setLocation((String)request.get("location"));
            estate.setUsage((String)request.get("usage"));
            estate.setArea(new BigInteger((String)request.get("area")));
            estate.setBeginTime((String)request.get("beginTime"));
            estate.setEndTime((String)request.get("endTime"));
            String privateKey = (String)request.get("privateKey");
            contractService.registerEstate(privateKey,estate);
            return RestResponce.success("estate register success");
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }

}
