/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class ann_prepare {

    /**
     * @param args the command line arguments
     */
    public void prepare_cmn(String dat, main_function kernel, String url) throws ClassNotFoundException {
        String j[] = dat.split(",");
        int simpul[] = new int[j.length];
        int jk = 0;
        for (Object object : j) {
            simpul[jk++] = Integer.parseInt(object.toString());
        }
        kernel.c_node = simpul;
        kernel.URL_ANN = url;
        kernel.weight = new double[2][][][];
        init(kernel);
    }

    public void init(main_function kernel) throws ClassNotFoundException {
        array_operation mp = new array_operation();
        int[] node = new int[kernel.c_node.length - 1];
        for (int i = 1; i < kernel.c_node.length; i++) {
            node[i - 1] = kernel.c_node[i];
        }
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(kernel.URL_ANN));
            kernel.weight = (double[][][][]) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
        }
        double thv[][] = new double[node.length][];
        double th[] = new double[node.length];
        for (int i = 0; i < thv.length; i++) {
            double w_l[] = new double[node[i]];
            w_l = mp.adddot(w_l, -0.00);
            thv[i] = w_l;
            th[i] = 0.00;
        }
        kernel.th = th;
        kernel.thv = thv;
    }

    public double[] main(double rho) throws ClassNotFoundException {
        main_function kernel = new main_function();
        ann_prepare ann = new ann_prepare();
        ann.prepare_cmn("1,2,2,1", kernel, "ann_new_dft");
        drv_ann av = new drv_ann();
        double input[] = {rho};
        double delta = Math.pow(10, -7);
        double Fx[] = av.Ex(kernel, input);
        array_operation mp = new array_operation();
        double[] dFxdn = mp.mdot(mp.adddot(mp.mdot(Fx, -1), av.Ex(kernel, mp.adddot(input, delta))),
                1.0 / delta);
        Fx = mp.mdot(Fx, mp.powdot(input, -1));
        double out_[]={Fx[0],dFxdn[0]};
       // System.out.println(Fx[0] + " " + dFxdn[0]);
       return out_;
    }

}
