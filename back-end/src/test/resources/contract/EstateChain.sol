pragma solidity ^0.4.25;

contract EstateChain{

    struct Estate {
        address owner;      // 所有者地址
        string id;          // 编号
        string location;    // 所在位置
        uint256 area;        // 面积
        string usage;       // 用途
        string begin_time;  // 开始时间
        string end_time;    // 到期时间
    }

    struct UserEntity { // 这里的用户实体可以是任何，包括节点，用户，企业
        string name;    // 用户实体的名称
        string kind;    // 用户类型 - user,company,judge
        string id;      // 企业编号或身份证
        string location;
        string phone;
        string email;
    }

    // 交易记录
    struct Record {    
        address from;     // 转让方地址
        address to;       // 接受方地址
        string estate_id; // 不动产编号
        string time;      // 转让时间
        string info;      // 交易信息
    }


    mapping(address => UserEntity) account;  // 每个地址对应一个用户实体

    Estate[] estates;     // 记录链上所有的音乐版权信息
    Record[] records;   // 记录链上所有的授权信息

    //normal user registration
    function registerUser(string _name, string _phone) public {
        account[msg.sender].name = _name;
        account[msg.sender].phone = _phone;
        account[msg.sender].kind = "user";
    }

    //company registration
    function registerCompany(string _name, string _id, string _location, string _phone, string _email) public {
        account[msg.sender].name = _name;
        account[msg.sender].phone = _phone;
        account[msg.sender].kind = "company";
        account[msg.sender].id = _id;
        account[msg.sender].email = _email;
        account[msg.sender].location = _location;
    }

    //judge registration
    function registerJudge(string _name, string _id, string _location, string _phone, string _email) public {
        account[msg.sender].name = _name;
        account[msg.sender].phone = _phone;
        account[msg.sender].kind = "judge";
        account[msg.sender].id = _id;
        account[msg.sender].email = _email;
        account[msg.sender].location = _location;
    }

    /*
    描述 : 登记不动产
    参数 :
            id : 编号
            location : 位置
            area : 面积
            usage : 用途
            begin_time : 有效期开始时间
            end_time : 有效期截止时间
    返回值:
            1 : 登记成功
            -1 : 登记失败，非公司不能登记
    */
    function registerEstate(string id, string location, uint256 area, string usage,  string begin_time, string end_time) public returns (int256){
        if (keccak256(account[msg.sender].kind) !=  keccak256("company")){
            return -1;
        }
        estates.push(Estate(msg.sender, id, location, area, usage, begin_time, end_time));
        return 1;
    }

    /*
    描述 : 不动产转让
    参数 ：
            _to : 接受用户地址
            _estate_id ： 不动产编号
            _time ： 转让时间
    返回值：
            1  转让成功
            -1 账户不存在或无对应不动产
    */
    function transferEstate(address _to, string _estate_id, string _time, string info) public returns(int256){
        address from = msg.sender;
        uint8 num = 0;
        for (num = 0; num < estates.length; num++){
            if(from == estates[num].owner && keccak256(estates[num].id) == keccak256(_estate_id)){   
                estates[num].owner = _to;
                records.push(Record(from, _to, _estate_id, _time, info));
                return 1;
            }
        }
        return -1;
    }

    // 根据序号返回回不动产信息
    function getEstate(uint8 num) public view returns(address, string, string, uint256, string, string, string){
        return (estates[num].owner, estates[num].id, estates[num].location, estates[num].area,
            estates[num].usage, estates[num].begin_time, estates[num].end_time);
    }

    
    // 返回发送请求的所有不动产序号
    function getEstates() public view returns(uint8[]){
        uint8[] storage nums;
        for(uint8 i = 0; i < estates.length; i++){
            if (estates[i].owner == msg.sender){
                nums.push(i);
            }
        }
        return nums;
    }



    /*
    描述 : 根据编号查询不动产
    输入 :
            _id : 编号
    返回 : 
            第一个参数为1时，查询成功，返回的是不动产信息
            第一个参数为-1时，查询失败，不存在
    */
    function searchEstate(string _id) public view returns (int256, address, string, string, uint256, string, string, string){
        for(uint8 i = 0; i < estates.length; i++){
            if(keccak256(estates[i].id) == keccak256(_id) ){
                return (1, estates[i].owner, estates[i].id, estates[i].location, 
                    estates[i].area, estates[i].usage, estates[i].begin_time, estates[i].end_time);
            }
        }
        return (-1, 0, "0", "0", 0, "0", "0", "0");
    }
    
    function getRecord(uint num) public view returns(address, address, string, string, string){
        return (records[num].from, records[num].to, records[num].estate_id, records[num].time, records[num].info);
    }

    function getRecordNumber(address _from, address _to) public view returns(uint8[]){
        uint8 i = 0;
        uint8[] storage res;
        for( i = 0; i < records.length; i++){
            if(records[i].from == _from && records[i].to == _to){
                res.push(i);
            }
        }
        return res;
    }


    // 返回发送请求者的信息
    function getUser() public view returns (address, string,string,string,string,string,string) {
        UserEntity storage user = account[msg.sender];
        return (msg.sender, user.name, user.kind, user.id, user.location, user.phone, user.email);
    }

    // 根据地址返回用户信息
    function getUserByAddress(address _user) public view returns (address,string,string,string,string,string,string) {
        UserEntity storage user = account[_user];
        return (_user, user.name, user.kind, user.id, user.location, user.phone, user.email);
    }
}

