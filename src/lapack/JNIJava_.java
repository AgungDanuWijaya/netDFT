package lapack;

import init_calc.aainit;
import java.io.FileWriter;
import java.io.IOException;
import tools.array_operation;

public class JNIJava_ {

    public gev_object main(double H[][][], double S[][][], int iband) throws IOException {
        //gev_object a=new JNIJava().main(H, S, iband);
        gev_object a = new scalapackJava().main(H, S, iband);
        return a;
    }

}
