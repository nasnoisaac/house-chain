package org.fisco.bcos.entity;

import lombok.Data;

/*
    struct UserEntity { // 这里的用户实体可以是任何，包括节点，用户，企业
        string name;    // 用户实体的名称
        string kind;    // 用户类型 - user,company,judge
        string id;      // 企业编号或身份证
        string location;
        string phone;
        string email;
    }
 */
@Data
public class User {
    String name;
    String kind;
    String id;
    String location;
    String phone;
    String email;
}
