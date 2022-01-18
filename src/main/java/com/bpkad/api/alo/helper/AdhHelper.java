package com.bpkad.api.alo.helper;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component("AdhHelper")
public class AdhHelper {
    static String[] hurufUntukTerbilang ={"","Satu","Dua","Tiga","Empat","Lima","Enam","Tujuh","Delapan","Sembilan","Sepuluh","Sebelas"};

    public static BigDecimal moneyNullToZero(BigDecimal obj) {
        if (obj == null) {
            return BigDecimal.ZERO;
        }

        return obj;
    }

    public static String terbilang(Long angka){
        if(angka < 12)
            return hurufUntukTerbilang[angka.intValue()];
        if(angka >=12 && angka <= 19)
            return hurufUntukTerbilang[angka.intValue() % 10] + " Belas";
        if(angka >= 20 && angka <= 99)
            return terbilang(angka / 10) + " Puluh " + hurufUntukTerbilang[angka.intValue() % 10];
        if(angka >= 100 && angka <= 199)
            return "Seratus " + terbilang(angka % 100);
        if(angka >= 200 && angka <= 999)
            return terbilang(angka / 100) + " Ratus " + terbilang(angka % 100);
        if(angka >= 1000 && angka <= 1999)
            return "Seribu " + terbilang(angka % 1000);
        if(angka >= 2000 && angka <= 999999)
            return terbilang(angka / 1000) + " Ribu " + terbilang(angka % 1000);
        if(angka >= 1000000 && angka <= 999999999)
            return terbilang(angka / 1000000) + " Juta " + terbilang(angka % 1000000);
        if(angka >= 1000000000 && angka <= 999999999999L)
            return terbilang(angka / 1000000000) + " Milyar " + terbilang(angka % 1000000000);
        if(angka >= 1000000000000L && angka <= 999999999999999L)
            return terbilang(angka / 1000000000000L) + " Triliun " + terbilang(angka % 1000000000000L);
        if(angka >= 1000000000000000L && angka <= 999999999999999999L)
            return terbilang(angka / 1000000000000000L) + " Quadrilyun " + terbilang(angka % 1000000000000000L);
        return "";
    }
}
