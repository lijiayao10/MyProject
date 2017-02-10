package com.cjy.code;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;

public class JsonTest {

    private class Obj1 {
        private String name = "1";

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    private String name = "2";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Obj1   obj1  = new Obj1();

    private String name2 = "3";

    public Obj1 getObj1() {
        return obj1;
    }

    public void setObj1(Obj1 obj1) {
        this.obj1 = obj1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public static void main(String[] args) throws IOException {
        JsonTest jsonTest = new JsonTest();

        JSONObject jsonObject = new JSONObject();
        System.out.println(jsonObject.toJSONString(jsonTest));

        String jsonStr = "{\"adjustedRate\":\"0.161290322\",\"benifitCertNo\":\"577**57\",\"benifitCertType\":\"P\",\"benifitId\":\"15100853694921920710\",\"benifitName\":\"寮�\",\"holderCertNo\":\"\",\"holderCertType\":\"Q\",\"holderId\":\"126873040\",\"holderName\":\"kunz****job\",\"itemName\":\"6702\",\"logDate\":\"20151009\",\"standardRate\":\"2.48%\"}";
        FileUtils.readFileToString(new File("C:\\Users\\caojiayao\\Desktop\\json.html"));

        JSONObject jsonObject2 = JSONObject.parseObject(jsonStr);

        //        jsonObject2
        //                .put("bizData",
        //                        JSONObject
        //                                .parseObject("{\"bizOrderId\":\"1324689922354030\",\"endStation\":\"鍝堝皵婊╘",\"endTime\":\"20151014165000\",\"hasInvoice\":\"true\",\"invoiceAddr\":\"鍖椾含;鍖椾含甯�鏈濋槼鍖�鍗楃（鎴�瑗垮ぇ鏈涜矾鐢�2鍙峰浗瀹跺箍鍛婁骇涓氬洯鍖築搴",\"invoiceAddressee\":\"寮犲潳\",\"invoiceName\":\"寮犲潳\",\"invoiceTel\":\"13810687224\",\"invoiceZip\":\"100022\",\"outOrderId\":\"53362196040\",\"passengerIdCard\":\"5774457\",\"passengerName\":\"寮�\",\"startStation\":\"鍝堝皵婊ㄤ笢\",\"startTime\":\"20151014162200\",\"ticketNo\":\"null\",\"trafficNo\":\"6225\"}"));
        //        
//        jsonObject2
//                .put("bizData",
//                        "{\"bizOrderId\":\"1324689922354030\",\"endStation\":\"鍝堝皵婊╘",\"endTime\":\"20151014165000\",\"hasInvoice\":\"true\",\"invoiceAddr\":\"鍖椾含;鍖椾含甯�鏈濋槼鍖�鍗楃（鎴�瑗垮ぇ鏈涜矾鐢�2鍙峰浗瀹跺箍鍛婁骇涓氬洯鍖築搴",\"invoiceAddressee\":\"寮犲潳\",\"invoiceName\":\"寮犲潳\",\"invoiceTel\":\"13810687224\",\"invoiceZip\":\"100022\",\"outOrderId\":\"53362196040\",\"passengerIdCard\":\"5774457\",\"passengerName\":\"寮�\",\"startStation\":\"鍝堝皵婊ㄤ笢\",\"startTime\":\"20151014162200\",\"ticketNo\":\"null\",\"trafficNo\":\"6225\"}");

        System.out.println(jsonObject2.toJSONString());
        System.out.println(jsonObject2.get("bizData").getClass().getName());
    }
}
