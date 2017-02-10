package com.cjy.code.mina.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cjy.code.mina.service.QueryTradeService;

public class QueryTradeServiceImpl implements QueryTradeService {

    @Override
    public List<String> queryList(String tradeNo) {

        List<String> list = new ArrayList<>();
        list.add(tradeNo + "tradeNo111111111111");
        list.add(tradeNo + "tradeNo222222222222");
        list.add(tradeNo + "tradeNo333333333333");
        list.add(tradeNo + "tradeNo444444444444");
        return list;
    }

}
