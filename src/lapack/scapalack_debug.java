/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lapack;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import tools.array_operation;

/**
 *
 * @author root
 */
public class scapalack_debug {

    public static void main(String[] args) throws IOException {
        //  compile();
       // int iband;
       // runing();
        read();
        String dat="        0.00028656382523974545        0.00077455690438563813";
        System.out.println(dat.substring(0, 34)+" "+dat.substring(35));
        //read_e(36);
    }

    public static void compile() {
        try {

            // -- Linux --
            // Run a shell command
            // Process process = Runtime.getRuntime().exec("mpirun /home/agung/Downloads/scalapack-2.2.0_/scalapack-2.2.0/sss/a.out");
            // Run a shell script
            Process process = Runtime.getRuntime().exec("gfortran am.f90 libscalapack.a libblas.a liblapack.a /usr/lib/x86_64-linux-gnu/libmpi.so.40");

            // -- Windows --
            // Run a command
            //Process process = Runtime.getRuntime().exec("cmd /c dir C:\\Users\\mkyong");
            //Run a bat file
            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));

            
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);

            } else {
                System.out.println(output);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void runing() {
        try {

            // -- Linux --
            // Run a shell command
            // Process process = Runtime.getRuntime().exec("mpirun /home/agung/Downloads/scalapack-2.2.0_/scalapack-2.2.0/sss/a.out");
            // Run a shell script
            Process process = Runtime.getRuntime().exec("mpirun a.out");

            // -- Windows --
            // Run a command
            //Process process = Runtime.getRuntime().exec("cmd /c dir C:\\Users\\mkyong");
            //Run a bat file
            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getErrorStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);

            } else {
                System.out.println(output);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void read() throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("hasil"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            String data_s[] = everything.split("\n");

            int dimen[] = {0, 0};
            int in = 0;

            String hj[] = data_s[0].split(" ");
            for (int i = 0; i < hj.length; i++) {
                if (hj[i].equals(" ") == false && hj[i].equals("") == false) {
                    dimen[in] = Integer.parseInt(hj[i]);
                    in++;
                }
            }

            double hasi[][][] = new double[dimen[0]][dimen[1]][2];
            int x = 0;
            int y = 0;
            for (int i_ = 1; i_ < data_s.length; i_++) {
                double hasil_t[] = {Double.parseDouble(data_s[i_].substring(0, 34).replace(" ", "")), Double.parseDouble(data_s[i_].substring(35).replace(" ", ""))};

                hasi[x][y][0] = hasil_t[0];
                hasi[x][y][1] = hasil_t[1];

                if (y < dimen[1] - 1) {
                    y++;
                } else {
                    y = 0;
                    x++;
                }

            }

            array_operation ao = new array_operation();
            ao.disp_imag(hasi);

        }

    }

    public static void read_e(int n) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("gjjg"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            String data_s[] = everything.split("  ");
       
           int in=0;
            double e_[]=new double[n];
            for (int i = 0; i < data_s.length; i++) {
                try {
                    e_[in]=Double.parseDouble(data_s[i]);
                    in++;
                } catch (NumberFormatException e) {
                }
                
            }
            new array_operation().disp(e_);
           

        }
    }

}
