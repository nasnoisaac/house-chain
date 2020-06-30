package org.fisco.bcos.dao;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.contract.EstateChain;
import org.fisco.bcos.entity.Estate;
import org.fisco.bcos.entity.Record;
import org.fisco.bcos.entity.User;
import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple1;
import org.fisco.bcos.web3j.tuples.generated.Tuple5;
import org.fisco.bcos.web3j.tuples.generated.Tuple7;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@Repository
public class ContractDao {

    public void registerUser(EstateChain estateChain,String phone,String name) throws Exception {
        TransactionReceipt transactionReceipt=estateChain.registerUser(phone,name).send();
    }


    public void registerCompany(EstateChain estateChain,String name, String id, String location, String phone, String email) throws Exception {
        estateChain.registerCompany(name,id,location,phone,email).send();
    }

    // function registerEstate(string location, uint256 area, string usage,  string begin_time, string end_time)
    // public returns (int256){
    public void registerEstate(EstateChain estateChain, String location, BigInteger area, String usage, String beginTime, String endTime) throws Exception {
        TransactionReceipt transactionReceipt=estateChain.registerEstate(location,area,usage,beginTime,endTime).send();
        Tuple1<BigInteger> rnt=estateChain.getRegisterEstateOutput(transactionReceipt);
        BigInteger result=rnt.getValue1();
        log.info(result.toString());
        if (!result.equals(BigInteger.ONE)) throw new Exception("登记失败，错误码为："+ result.toString());
        //TransactionReceipt transactionReceipt = contract.someMethod(<param1>, ...).send();
    }

    //function transferEstate(address _to, uint256 _estate_id, string _time, string info) public returns(int256)
    public void transferEstate(EstateChain estateChain, String to, BigInteger estateId, String time, String info) throws Exception {
        TransactionReceipt transactionReceipt=estateChain.transferEstate(to,estateId,time,info).send();
        Tuple1<BigInteger> rnt=estateChain.getTransferEstateOutput(transactionReceipt);
        BigInteger result=rnt.getValue1();
        if (!result.equals(BigInteger.ONE)) throw new Exception("transfer fail, errCode="+result.toString());
    }

    //function getEstate(uint8 num) public view returns(address, uint256, string, uint256, string, string, string){
    public Estate getEstate(EstateChain estateChain, BigInteger num) throws Exception {
        Estate estate=new Estate();
        Tuple7<String, BigInteger, String, BigInteger, String, String, String> result=estateChain.getEstate(num).send();
        estate.setOwner(result.getValue1());
        estate.setId(result.getValue2());
        estate.setLocation(result.getValue3());
        estate.setArea(result.getValue4());
        estate.setUsage(result.getValue5());
        estate.setBeginTime(result.getValue6());
        estate.setEndTime(result.getValue7());
        return estate;
    }
    //function getMyEstates() public view returns(uint8[]){
    public List<BigInteger> getMyEstates(EstateChain estateChain) throws Exception {
        List<BigInteger> result=estateChain.getMyEstates().send();
        return result;
    }

    public BigInteger getEstatesSize(EstateChain estateChain) throws Exception{
        Tuple1<BigInteger> result=estateChain.getGetEstatesSizeOutput(estateChain.getEstatesSize().send());
        return result.getValue1();
    }

    //function getRecord(uint num) public view returns(address, address, uint256, string, string){
    public Record getRecord(EstateChain estateChain, BigInteger num) throws Exception {
        Record record=new Record();
        Tuple5<String, String, BigInteger, String, String> result=estateChain.getRecord(num).send();
        record.setFrom(result.getValue1());
        record.setTo(result.getValue2());
        record.setEstateId(result.getValue3());
        record.setTime(result.getValue4());
        record.setInfo(result.getValue5());
        return record;
    }
    // function getRecordNumber(address _from, address _to) public view returns(uint8[]){
    public List<BigInteger> getRecordNumber(EstateChain estateChain,String from,String to) throws Exception {
        List<BigInteger> result=estateChain.getRecordNumber(from,to).send();
        return result;
    }

    // function getUser() public view returns (address, string,string,string,string,string,string) {
    // return (msg.sender, user.name, user.kind, user.id, user.location, user.phone, user.email);
    public User getUser(EstateChain estateChain) throws Exception {
        Tuple7<String, String, String, String, String, String, String> result=estateChain.getUser().send();
        return getUser(result);
    }

    // function getUserByAddress(address _user) public view
    // returns (address,string,string,string,string,string,string) {
    // return (_user, user.name, user.kind, user.id, user.location, user.phone, user.email);
    public User getUserByAddress(EstateChain estateChain,String address) throws Exception{
        Tuple7<String, String, String, String, String, String, String> result=estateChain.getUserByAddress(address).send();
        return getUser(result);
    }

    private User getUser(Tuple7<String, String, String, String, String, String, String> result) {
        User user=new User();
        user.setName(result.getValue2());
        user.setKind(result.getValue3());
        user.setId(result.getValue4());
        user.setLocation(result.getValue5());
        user.setPhone(result.getValue6());
        user.setEmail(result.getValue7());
        return user;
    }
}
