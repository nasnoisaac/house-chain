package org.fisco.bcos.service;

import org.fisco.bcos.entity.Estate;
import org.fisco.bcos.entity.Record;
import org.fisco.bcos.entity.User;

import java.math.BigInteger;
import java.util.List;


public interface ContractService {
    public boolean validate(String privateKey);
    public void registerUser(String privateKey,User user) throws Exception;
    public void registerCompany(String privateKey, User user) throws Exception;
    public void registerEstate(String privateKey, Estate estate) throws Exception;
    public void transferEstate(String privateKey, String to, BigInteger estateId, String time, String info) throws Exception;
    public Estate getEstate(String privateKey,BigInteger estateId) throws Exception;
    public List<Estate> getMyEstates(String privateKey) throws Exception;
    public List<Estate> getEstates(String privateKey,BigInteger from,BigInteger to) throws Exception;
    public Record getRecord(String privateKey, BigInteger recordNum) throws Exception;
    public List<Record> getRecords(String privateKey,String from,String to) throws Exception;
    public User getUser(String privateKey) throws Exception;
    public User getUserByAddress(String privateKey,String address) throws  Exception;

}
