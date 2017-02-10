package com.cjy.code;

import java.io.BufferedReader;
import java.io.FileReader;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Test {

    private String id;

    public static void main(String[] args) {
        int i = 0b1100110;

        System.out.println(i);

    }

    static String getCountent(String path) throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

    //    static String getCountent2(String path) throws Exception {
    //        Object localObject1 = null;
    //        Object localObject4 = null;
    //        Object localObject3;
    //        try {
    //            BufferedReader br = new BufferedReader(new FileReader(path));
    //            try {
    //                return br.readLine();
    //            } finally {
    //                if (br != null)
    //                    br.close();
    //            }
    //        } finally {
    //            if (localObject2 == null)
    //                localObject3 = localThrowable;
    //            else if (localObject3 != localThrowable)
    //                localObject3.addSuppressed(localThrowable);
    //        }
    //    }
}
