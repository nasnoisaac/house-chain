package org.fisco.bcos.util;

import java.util.HashMap;
import java.util.Map;

public class RestResponce {
    public static HashMap<String,Object> success(Object res){
        HashMap<String,Object> rnt=new HashMap<>();
        rnt.put("success",1);
        rnt.put("data",res);
        return rnt;
    }

    public static HashMap<String,Object> success(){
        HashMap<String,Object> rnt=new HashMap<>();
        rnt.put("success",1);
        return rnt;
    }
    public static HashMap<String,Object> fail(String message){
        HashMap<String,Object> rnt=new HashMap<>();
        rnt.put("success",0);
        rnt.put("message",message);
        return rnt;
    }
    public static HashMap<String,Object> fail(){
        HashMap<String,Object> rnt=new HashMap<>();
        rnt.put("success",0);
        rnt.put("message","");
        return rnt;
    }
}
