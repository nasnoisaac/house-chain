package org.fisco.bcos;

import static org.junit.Assert.assertTrue;


import lombok.extern.slf4j.Slf4j;
import org.fisco.bcos.constants.ContractConstants;
import org.fisco.bcos.constants.GasConstants;
import org.fisco.bcos.contract.EstateChain;
import org.fisco.bcos.temp.HelloWorld;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ContractTest extends BaseTest {

    @Autowired private Web3j web3j;
    @Autowired private Credentials credentials;

    @Test
    public void deployAndCallHelloWorld() throws Exception {
        // deploy contract
        HelloWorld helloWorld =
                HelloWorld.deploy(
                                web3j,
                                credentials,
                                new StaticGasProvider(
                                        GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT))
                        .send();
        if (helloWorld != null) {
            System.out.println("HelloWorld address is: " + helloWorld.getContractAddress());
            // call set function
            helloWorld.set("Hello, World!").send();
            // call get function
            String result = helloWorld.get().send();
            System.out.println(result);
            assertTrue("Hello, World!".equals(result));
        }
    }

    @Test
    public void deployAndCallEstateChain() throws Exception {
        // deploy contract
        EstateChain estateChain =
                EstateChain.load(
                        ContractConstants.address,
                        web3j,
                        credentials,
                        new StaticGasProvider(
                                GasConstants.GAS_PRICE, GasConstants.GAS_LIMIT));

        if (estateChain != null) {
            log.info("EstateChain address is: " + estateChain.getContractAddress());

        }
    }
}
