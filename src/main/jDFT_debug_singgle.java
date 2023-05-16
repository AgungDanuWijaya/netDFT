/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.google.gson.Gson;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class jDFT_debug_singgle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, ClassNotFoundException, JSchException, SftpException {
        String data = "{\n" +
"	\"degauss_\": 0.02,\n" +
"	\"status\": \"scf\",\n" +
"	\"smar\": 0,\n" +
"	\"random\": 1,\n" +
"	\"usp\": 0.0,\n" +
"	\"celldm\": [10.6867, 0.0, 0.0, 0.0],\n" +
"	\"ecutwfc\": 60.0,\n" +
"	\"ecutrho\": 244.0,\n" +
"	\"ibrav\": 2,\n" +
"	\"iband\": 16,\n" +
"	\"num_atom\": 2,\n" +
"	\"nat\": 2,\n" +
"	\"mix\": 0.7,\n" +
"	\"atom\": [\"As\", \"Ga\"],\n" +
"	\"upf_url\": [\"/home/agung/Documents/kkk/q-e-qe-6.6/pseudo/As.upf\", \"/home/agung/Documents/kkk/q-e-qe-6.6/pseudo/Ga.upf\"],\n" +
"	\"usp_\": [0, 0],\n" +
"	\"term\": 5.653,\n" +
"	\"lattice\": [\n" +
"		[5.653, 0.0, 5.653],\n" +
"		[5.653, 5.653, 0.0],\n" +
"		[0.0, 5.653, 5.653]\n" +
"	],\n" +
"	\"pos\": [\n" +
"		[0.0, 0.0, 0.0],\n" +
"		[0.25, 0.25, 0.25]\n" +
"	],\n" +
"	\"atom_pos\": [1, 0],\n" +
"	\"weig\": [2.0],\n" +
"	\"k_point\": [\n" +
"		[0.0, 0.0, 0.0]\n" +
"	]\n" +
"}";
        System.out.println(data);
        Gson gson = new Gson();
        init_data init = gson.fromJson(data, init_data.class);
        array_operation ao = new array_operation();

        double weig[] = ao.copy(init.weig);
        double k_point[][] = ao.copy(init.k_point);
        init.weig = new double[1];
        init.k_point = new double[1][];
        init.cluster = 0;
        if (init.status.equals("scf")) {
            init.weig[0] = weig[0];
            init.k_point[0] = k_point[0];
            utama_gen ug = new utama_gen();
            ug.main(init);
        } else if (init.status.equals("bands")) {
            for (int i = 0; i < weig.length; i++) {
                init.weig[0] = weig[i];
                init.k_point[0] = k_point[i];
                utama_gen_band_ ug = new utama_gen_band_();
                ug.main(init, i);
            }

        }

    }

}
