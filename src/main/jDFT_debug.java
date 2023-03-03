/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import com.DBProcess;
import com.google.gson.Gson;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class jDFT_debug {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, ClassNotFoundException, JSchException, SftpException {

        String fileName = args[0];

        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line = "";
        String data = "";
        while ((line = br.readLine()) != null) {
            data += line;
        }

        String id = args[1];
        Gson gson = new Gson();
        init_data init = gson.fromJson(data, init_data.class);
        String lokal[] = {"jdbc:mysql://" + init.cs.host + ":3306/SIAKAD_MIPA_I?allowPublicKeyRetrieval=true&useUnicode=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
            "" + init.cs.user_db + "", "" + init.cs.pass + ""};

        array_operation ao = new array_operation();

        double weig[] = new double[1];
        double k_point[][] = new double[1][3];
        init.weig = new double[1];
        init.k_point = new double[1][];
        init.cluster = 0;
        if (init.status.equals("scf")) {
            DBProcess db_1 = new DBProcess(lokal);
            ArrayList<String[]> dat = db_1.getArrayData("SELECT K_POINT,weight,id FROM SOLID.WORK;");
            db_1.closeDB();
            String dta[] = dat.get(0)[0].split(",");
            double k_point_[] = {Double.parseDouble(dta[0].replace(" ", "")), Double.parseDouble(dta[1].replace(" ", "")), Double.parseDouble(dta[2].replace(" ", ""))};
            double wigth = Double.parseDouble(dat.get(0)[1]);

            init.weig[0] = wigth;
            init.k_point[0] = k_point_;

            utama_gen ug = new utama_gen();
            ug.main(init);
        } else if (init.status.equals("bands")) {

            DBProcess db_1 = new DBProcess(lokal);
            ArrayList<String[]> dat = db_1.getArrayData("SELECT K_POINT,weight,id FROM SOLID.WORK where ID_WORK='" + id + "'");
            db_1.closeDB();
            int in = 0;
            for (String[] strings : dat) {
                String dta[] = strings[0].split(",");
                double k_point_[] = {Double.parseDouble(dta[0].replace(" ", "")), Double.parseDouble(dta[1].replace(" ", "")), Double.parseDouble(dta[2].replace(" ", ""))};
                double wigth = Double.parseDouble(strings[1]);

                init.weig[0] = wigth;
                init.k_point[0] = k_point_;
                utama_gen_band_ ug = new utama_gen_band_();
                ug.main(init, in);
                in++;
                db_1 = new DBProcess(lokal);
                db_1.setData_1("UPDATE `SOLID`.`WORK` SET `OUTPUT` = '" + init.out + "' WHERE (`id` = '" + strings[2] + "');");
                db_1.closeDB();
            }

        }
    }

}
