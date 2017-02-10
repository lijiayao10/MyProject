package com.cjy.code.caseformat;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.CaseFormat;

import org.apache.commons.lang.ArrayUtils;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.google.common.base.Function;
import com.google.common.collect.Maps;

public class CaseFormatTest<T> {

    private static final String SERVICE = null;

    public static void main(String[] args) {
    }
    
    
    public void aaa(){
        
//        HttpServletRequest request = null;
//        
//        final Map<String, String> result = Maps.newHashMap();
//        (new BeanSerializer(new org.codehaus.jackson.map.JsonSerializer().createJsonContext(null),
//            processorFactory.load(byCode(request.getParameter(SERVICE))).process(
//                Maps.transformValues(request.getParameterMap(), new Function() {
//                    @Override
//                    public final String apply(final String[] value) {
//                        return ArrayUtils.isNotEmpty(value) ? value[0] : null;
//                    }
//                })).getResult()) {
//            /**
//             * 处理bean中的单个属性
//             */
//            @SuppressWarnings("rawtypes")
//            @Override
//            protected void onSerializableProperty(final String name, final Class type,
//                                                  final Object value) {
//                LOGGER.debug("name={}, type={}, value={}", name, type, value);
//                if (value != null) {
//                    // aaBb ==> aa_bb
//                    result.put(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name), value.toString());
//                }
//            }
//        }).serialize();
    }

}
