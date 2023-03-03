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
public class jDFT_debug_singgke {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, ClassNotFoundException, JSchException, SftpException {
        String data = "{\n"
                + "	\"degauss_\": 0.02,\n"
                + "	\"status\": \"scf\",\n"
                + "	\"smar\": 1,\n"
                + "	\"random\": 0,\n"
                + "	\"usp\": 1.0,\n"
                + "	\"celldm\": [6.73, 0.0, 0.0, 0.0],\n"
                + "	\"ecutwfc\": 25.0,\n"
                + "	\"ecutrho\": 300.0,\n"
                + "	\"ibrav\": 2,\n"
                + "	\"iband\": 16,\n"
                + "	\"num_atom\": 1,\n"
                + "	\"nat\": 1,\n"
                + "	\"mix\": 0.7,\n"
                + "	\"atom\": [\"Cu\"],\n"
                + "	\"upf_url\": [\"/home/agung/Documents/solid/q-e-qe-6__.6/pseudo/Cup.upf\"],\n"
                + "	\"usp_\": [1],\n"
                + "	\"pos\": [\n"
                + "		[0.0, 0.0, 0.0]\n"
                + "	],\n"
                + "	\"atom_pos\": [0],\n"
                + "	\"weig\": [2.0],\n"
                + "	\"k_point\": [\n"
                + "		[0.125, 0.125, 0.125]\n"
                + "	]\n"
                + "}";

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
