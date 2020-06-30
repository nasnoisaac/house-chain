package org.fisco.bcos.entity;

import lombok.Data;

@Data
public class Account {
    String address;
    String publicKey;
    String privateKey;
}
