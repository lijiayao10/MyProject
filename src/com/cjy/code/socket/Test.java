package com.cjy.code.socket;

public class Test {

    public static final int MAXMESSAGELENGTH = Integer.MAX_VALUE;
    public static final int BYTEMASK         = 0xff;
    public static final int SHORTMASK        = 0xffff;
    public static final int BYTESHIFT        = 0x8;

    public static void main(String[] args) {
        int but5 = (1 << 5);
        int but7 = (0x80);
        int buts2and3 = 12;

        int bitmap = 1234567;
        //        bitmap |= but5;
        bitmap = bitmap | but5;
        System.out.println(bitmap);

        //        bitmap &= ~but7;
        bitmap = bitmap & ~but7;
        System.out.println(bitmap);

        bitmap &= ~(buts2and3 | but5);
        System.out.println(bitmap);

        System.out.println((3 | 7));

        System.out.println((5000 >> BYTESHIFT) & BYTEMASK);
        System.out.println(5000 & BYTEMASK);

        System.out.println((19 << 8) + (136 << 0));

        System.out.println(1 | 4);

    }

}
