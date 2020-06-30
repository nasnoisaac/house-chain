package org.fisco.bcos.controller;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.constants.ContractConstants;
import org.fisco.bcos.entity.Estate;
import org.fisco.bcos.entity.Record;
import org.fisco.bcos.service.ContractService;
import org.fisco.bcos.util.RestResponce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class ContractController {
    @Autowired
    ContractService contractService;

    @RequestMapping("/api/transfer")
    public HashMap<String,Object> transferEstate(@RequestParam Map<String,Object> request){
        try{
            String privateKey=(String)request.get("privateKey");
            String to=(String)request.get("to");
            String info=(String)request.get("info");
            BigInteger estateId=new BigInteger((String)request.get("estateId"));
            Date date=new Date();
            SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
            contractService.transferEstate(privateKey,to,estateId,ft.format(date),info);
            return RestResponce.success();
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }

    @RequestMapping("/api/myEstates")
    public HashMap<String,Object> getMyEstates(@RequestParam Map<String,Object> request){
        try{
            String privateKey=(String)request.get("privateKey");
            List<Estate> estates=contractService.getMyEstates(privateKey);
            return RestResponce.success(estates);
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }

    @RequestMapping("/api/estates")
    public HashMap<String,Object> getEstates(@RequestParam Map<String,Object> request){
        try{
            String privateKey=(String)request.get("privateKey");
            BigInteger from=new BigInteger((String)request.get("from"));
            BigInteger to=new BigInteger((String)request.get("to"));
            List<Estate> estates=contractService.getEstates(privateKey,from,to);
            return RestResponce.success(estates);
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }


    @RequestMapping("/api/estate")
    public HashMap<String,Object> getEstate(@RequestParam Map<String,Object> request){
        try{
            String privateKey=(String)request.get("privateKey");
            String estateId=(String)request.get("estateId");
            Estate estate=contractService.getEstate(privateKey,new BigInteger(estateId));
            return RestResponce.success(estate);
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }

    @RequestMapping("/api/records")
    public HashMap<String,Object> getRecords(@RequestParam Map<String,Object> request){
        try{
            String privateKey=(String)request.get("privateKey");
            String from= ContractConstants.emptyAddress;
            String to= ContractConstants.emptyAddress;
            if (request.containsKey("from")) from=(String)request.get("from");
            if (request.containsKey("to")) to=(String)request.get("to");
            List<Record> estates=contractService.getRecords(privateKey,from,to);
            return RestResponce.success(estates);
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }

    @RequestMapping("/api/record")
    public HashMap<String,Object> getRecord(@RequestParam Map<String,Object> request){
        try{
            String privateKey=(String)request.get("privateKey");
            String recordId=(String)request.get("recordId");
            Record record=contractService.getRecord(privateKey,new BigInteger(recordId));
            return RestResponce.success(record);
        }catch (Exception e){
            e.printStackTrace();
            return RestResponce.fail(e.getMessage());
        }
    }
}
