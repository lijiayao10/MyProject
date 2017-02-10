package com.cjy.code;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ��Test2.java��ʵ��������TODO ��ʵ������
 * 
 * @author caojiayao 2015-8-10 ����3:31:19
 */
public class Test2 {
    public static void main(String[] args) throws Exception {

        //        Object test2 = new Test2();
        //
        //        System.out.println(test2.getClass());
        //
//                LineIterator lineIterator = FileUtils.lineIterator(new File("d://policy_biz_id_2015.dat"));
        //
        //        while (lineIterator.hasNext()) {
        //            System.out.println(lineIterator.nextLine());
        //        }

        //        System.out.println(System.nanoTime());
        //
        //        if (1 != 1)
        //            ;
        //        System.out.println("ddd");
        //
        //        byte[] bs = new byte[] { 122, 111, 111, 111, 122, 111, 111, 111, 122, 111, 111, 111, 122,
        //                111, 111, 111, 122, 111, 111, 111, 122, 111, 111, 111, 122, 111, 111, 111, 122, 111,
        //                111, 111 };
        //
        //        System.out.println(new String(bs, "UTF-8"));

        //        Proxy<Test> proxy = new Proxy<>();
        //
        //        proxy.getInst();

        Test2.getArray();
        System.out.println(Runtime.getRuntime().availableProcessors() + 1);
    }

    public static Object[][] getArray() {
        Collection<String[]> records = new ArrayList<>();

        Object[][] result = new Object[records.size()][];
        int i = 0;
        for (String[] strs : records) {
            result[i++] = strs;
        }

        
        return result;
    }

    //    public static class Proxy<T> {
    //
    //        T t = new T();
    //
    //        public void getInst() {
    //            List<T> list = new ArrayList<>();
    //
    //            JaxWsPortProxyFactoryBean bean = new JaxWsPortProxyFactoryBean();
    //            bean.setServiceInterface(t.getClass());
    //
    //            System.out.println();
    //        }
    //    }

}
