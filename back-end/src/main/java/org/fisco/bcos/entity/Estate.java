package org.fisco.bcos.entity;

import lombok.Data;

import java.math.BigInteger;

/*
    struct Estate {
        address owner;      // 所有者地址
        uint256 id;         //房产id
        string location;    // 所在位置
        uint256 area;        // 面积
        string usage;       // 用途
        string begin_time;  // 开始时间
        string end_time;    // 到期时间
    }
 */
@Data
public class Estate {
    String owner;
    BigInteger id;
    String location;
    BigInteger area;
    String usage;
    String beginTime;
    String endTime;
}
