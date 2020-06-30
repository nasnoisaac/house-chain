package org.fisco.bcos;

import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.dao.AccountDao;
import org.fisco.bcos.entity.Account;
import org.fisco.bcos.entity.Estate;
import org.fisco.bcos.entity.User;
import org.fisco.bcos.service.ContractService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigInteger;

@Slf4j
public class EstateChainTest extends BaseTest{
    @Autowired
    AccountDao accountDao;
    @Autowired
    ContractService contractService;
    @Test
    public void testRegister() throws Exception {
        Account account1=accountDao.createAccount();
        User company=new User();
        log.info(String.format("\naddress: %s\nprivateKey: %s\npublicKey: %s",account1.getAddress(),account1.getPrivateKey(),account1.getPublicKey()));
        company.setName("ch");
        company.setLocation("gz");
        company.setEmail("123");
        company.setPhone("123");
        company.setId("xx");
        //contractService.registerCompany(account1.getPrivateKey(),company);
        Estate estate=new Estate();
        estate.setBeginTime("2017-01-01");
        estate.setEndTime("2017-12-01");
        estate.setArea(new BigInteger("123"));
        estate.setUsage("shangpu");
        estate.setLocation("gz");
        contractService.registerEstate(account1.getPrivateKey(),estate);

    }
    @Test
    public void testEstates() throws Exception{
        Account account1=accountDao.createAccount();
        log.info(String.format("\naddress: %s\nprivateKey: %s\npublicKey: %s",account1.getAddress(),account1.getPrivateKey(),account1.getPublicKey()));
    }
}
