package lapack;

import init_calc.aainit;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import tools.array_operation;

public class scalapackJava {

    public void tulis(String text, String fle) {
        try {
            FileWriter myWriter = new FileWriter(fle);
            myWriter.write(text);
            myWriter.close();
          
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public gev_object main(double H[][][], double S[][][], int iband) throws IOException {
        double h[] = new double[H.length * H[0].length * 2];
        double s[] = new double[S.length * S[0].length * 2];

        String H_r = "";
        String H_i = "";
        for (int i = 0; i < H.length; i++) {
            for (int j = 0; j < H[i].length; j++) {
                H_r = H_r + H[i][j][0] + " ";
                H_i = H_i + H[i][j][1] + " ";
            }
            H_r = H_r + "\n";
            H_i = H_i + "\n";
        }

        tulis(H_r, "data.txt");
        tulis(H_i, "datac.txt");

        H_r = "";
        H_i = "";
        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < S[i].length; j++) {
                H_r = H_r + S[i][j][0] + " ";
                H_i = H_i + S[i][j][1] + " ";
            }
            H_r = H_r + "\n";
            H_i = H_i + "\n";
        }

        tulis(H_r, "data_1.txt");
        tulis(H_i, "data_1c.txt");
        tulis(H.length + "", "dim");
         tulis(iband + "", "iband");
        runing();
        double vec[][][] = read();
        double e[] = read_e(H.length);
        double e_[] = new double[H.length];
        double e__[] = new double[H.length];

        for (int i = 0; i < e.length; i++) {
            e_[i]=e[i];
             e__[i]=e[i];
        }
        
        gev_object a = new gev_object();
        a.eigen = e_;
        a.eigen_ = e__;
        a.vec = vec;
       
        return a;
    }

    public void compile() {
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

    public void runing() {
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

    public double[][][] read() throws FileNotFoundException, IOException {
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

            return hasi;
        }

    }

    public double[] read_e(int n) throws FileNotFoundException, IOException {
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

            int in = 0;
            double e_[] = new double[n];
            for (int i = 0; i < data_s.length; i++) {
                try {
                    e_[in] = Double.parseDouble(data_s[i]);
                    in++;
                } catch (NumberFormatException e) {
                }

            }
            new array_operation().disp(e_);

            return e_;
        }

    }

}
