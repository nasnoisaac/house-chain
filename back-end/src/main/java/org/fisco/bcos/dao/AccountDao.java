package org.fisco.bcos.dao;

import org.fisco.bcos.entity.Account;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.EncryptType;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AccountDao {
    public Account createAccount(){
        Account account = new Account();
        //创建普通外部账户
        EncryptType.encryptType = 0;
        //创建国密外部账户，向国密区块链节点发送交易需要使用国密外部账户
        // EncryptType.encryptType = 1;
        Credentials credentials = GenCredential.create();
        //账户地址
        String address = credentials.getAddress();
        //账户私钥
        String privateKey = credentials.getEcKeyPair().getPrivateKey().toString(16);
        //账户公钥
        String publicKey = credentials.getEcKeyPair().getPublicKey().toString(16);

        account.setAddress(address);
        account.setPublicKey(publicKey);
        account.setPrivateKey(privateKey);
        return account;
    }
}
