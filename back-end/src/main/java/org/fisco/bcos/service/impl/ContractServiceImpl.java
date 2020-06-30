package org.fisco.bcos.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.constants.ContractConstants;
import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.contract.EstateChain;
import org.fisco.bcos.dao.ContractDao;
import org.fisco.bcos.entity.Estate;
import org.fisco.bcos.entity.Record;
import org.fisco.bcos.entity.User;
import org.fisco.bcos.service.ContractService;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    Web3j web3j;

    @Autowired
    ContractDao contractDao;

    private EstateChain getEstateChain(String privateKey){
        Credentials credentials=Credentials.create(privateKey);
        EstateChain estateChain=EstateChain.load(
                ContractConstants.address,
                web3j,credentials,
                new StaticGasProvider(
                        GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));
        return estateChain;
    }

    @Override
    public boolean validate(String privateKey) {
        try {
            Credentials credentials=Credentials.create(privateKey);
            EstateChain estateChain=EstateChain.load(
                    ContractConstants.address,
                    web3j,credentials,
                    new StaticGasProvider(
                            GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));
            String address=credentials.getAddress();
            log.info(address);
            User user=contractDao.getUserByAddress(estateChain,address);
            if (user.getName().equals("")) return false;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void registerUser(String privateKey, User user) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        contractDao.registerUser(estateChain,user.getPhone(),user.getName());
    }

    @Override
    public void registerCompany(String privateKey, User user) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        log.info(estateChain.toString());
        contractDao.registerCompany(estateChain,user.getName(),user.getId(),user.getLocation(),user.getPhone(),user.getEmail());
    }

    @Override
    public void registerEstate(String privateKey, Estate estate) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        contractDao.registerEstate(estateChain,estate.getLocation(),estate.getArea(),estate.getUsage(),estate.getBeginTime(),estate.getEndTime());
    }

    @Override
    public void transferEstate(String privateKey, String to, BigInteger estateId, String time, String info) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        contractDao.transferEstate(estateChain,to,estateId,time,info);
    }

    @Override
    public Estate getEstate(String privateKey, BigInteger estateId) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        return contractDao.getEstate(estateChain,estateId);
    }

    @Override
    public List<Estate> getMyEstates(String privateKey) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        List<BigInteger> numList=contractDao.getMyEstates(estateChain);
        List<Estate> estates = new ArrayList<>();
        for (BigInteger i:numList){
            Estate estate=contractDao.getEstate(estateChain,i);
            estates.add(estate);
        }
        return estates;
    }

    @Override
    public List<Estate> getEstates(String privateKey,BigInteger from,BigInteger to) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        User user=contractDao.getUser(estateChain);
        List<Estate> estates=new ArrayList<>();
        if (!user.getKind().equals("judge")) throw new Exception("Permission Denied");
        while (from.compareTo(to)!=1){
            estates.add(contractDao.getEstate(estateChain,from));
            from=from.add(BigInteger.ONE);
        }
        return estates;
    }

    @Override
    public Record getRecord(String privateKey, BigInteger recordNum) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        Record record=contractDao.getRecord(estateChain,recordNum);
        return record;
    }

    @Override
    public List<Record> getRecords(String privateKey, String from, String to) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        List<BigInteger> numList=contractDao.getRecordNumber(estateChain,from,to);
        List<Record> records = new ArrayList<>();
        for (BigInteger i:numList){
            Record record=contractDao.getRecord(estateChain,i);
            records.add(record);
        }
        return records;
    }

    @Override
    public User getUser(String privateKey) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        return contractDao.getUser(estateChain);
    }

    @Override
    public User getUserByAddress(String privateKey, String address) throws Exception {
        EstateChain estateChain=getEstateChain(privateKey);
        return contractDao.getUserByAddress(estateChain,address);
    }
}
