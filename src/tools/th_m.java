/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tools;

import java.io.InputStream;

/**
 *
 * @author root
 */
public class th_m extends Thread {

    double a[][];
    double b[][][];
    int i;
    double res[][][];

    public th_m(double a[][], double b[][][], double res[][][],int i) {
        this.a = a;
        this.b = b;
        this.res = res;
        this.i=i;
    }

    public void run() {
        array_operation ao = new array_operation();
        for (int j_s = 0; j_s < b[0].length; j_s++) {
            double re[] = {0, 0};
            for (int j = 0; j < b.length; j++) {
                try {

                    double rt[] = ao.complex_dot(b[j][j_s], a[j]);
                    re = ao.adddot(re, rt);
                } catch (Exception e) {
                }
            }
            res[i][j_s] = re;
        }

    }
}
