package lapack;

import init_calc.aainit;
import java.io.FileWriter;
import java.io.IOException;
import tools.array_operation;

public class JNIJava {

    static {
        System.load(new config().url_lib);
    }

    private native double[] sumsquaredc(int param[], double S[], double H[]);

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

    public gev_object main(double H[][][], double S[][][], int iband) {
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

        array_operation ao = new array_operation();
        
       
        
        for (int i = 0; i < H.length; i++) {
            for (int j = 0; j < H[i].length; j++) {
                h[(i * H.length) + j] = H[i][j][0];
                h[(H.length * H[0].length) + ((i * H.length) + j)] = H[i][j][1];
                s[(i * H.length) + j] = S[i][j][0];
                s[(H.length * H[0].length) + ((i * H.length) + j)] = S[i][j][1];
            }
        }
        int r[] = {H.length, H[0].length, iband};

        double res[] = sumsquaredc(r, s, h);
        double data[][] = new double[2][H.length * H[0].length];
        int in = 0;
        for (int i = 0; i < H.length * H[0].length * 2; i = i + 2) {
            data[0][in] = res[i];
            data[1][in] = res[i + 1];
            in += 1;
        }

        double vec[][][] = new double[H.length][H[0].length][2];
        for (int i = 0; i < H.length; i++) {
            for (int j = 0; j < H[i].length; j++) {
                vec[i][j][0] = data[0][(i * H.length) + j];
                vec[i][j][1] = data[1][(i * H.length) + j];
                //System.out.print(vec[i][j][0]+"  "+vec[i][j][1]);
            }
            //System.out.println("");
        }
        System.out.println("lapack.JNIJava.main()");
         ao.disp_imag(vec);
        // ao.disp_imag(vec);
        /*System.out.println("vector0");
        for (int j = 0; j < iband; j++) {
            System.out.print(vec[0][j][0] + " , " + vec[0][j][1] + ",");
        }
        System.out.println("");
        System.out.println("vector1");
        for (int j = 0; j < iband; j++) {
            System.out.print(vec[1][j][0] + " , " + vec[1][j][1] + ",");
        }*/
        double eigen[] = new double[H.length];
        double eigen_[] = new double[H.length];
        in = 0;
        //System.out.println("");
        //System.out.println("Eigen ");
        for (int i = H.length * H[0].length * 2; i < H.length * H[0].length * 2 + H.length; i++) {
            eigen[in] = res[i];
            eigen_[in] = res[i];
            //System.out.print(" hasil "+eigen[in] + " , ");
            in += 1;
        }
        ao.disp(eigen);
        gev_object a = new gev_object();
        a.eigen = eigen;
        a.eigen_ = eigen_;
        a.vec = vec;
        return a;
    }

}
