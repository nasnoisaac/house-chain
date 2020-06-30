package org.fisco.bcos.entity;


import lombok.Data;

import java.math.BigInteger;

/*
    struct Record {
        address from;     // 转让方地址
        address to;       // 接受方地址
        string estate_id; // 不动产编号
        string time;      // 转让时间
        string info;      // 交易信息
    }
 */
@Data
public class Record {
    String from;
    String to;
    BigInteger estateId;
    String time;
    String info;
}
