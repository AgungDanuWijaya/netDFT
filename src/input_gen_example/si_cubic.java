package input_gen_example;

import com.google.gson.Gson;
import java.io.IOException;
import main.config_ssh;
import main.init_data;
import main.utama_gen;

/**
 *
 * @author agung
 */
public class si_cubic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, IOException {
        init_data init = new init_data();
        init.status = "scf";
        init.smar = 0;
        init.random = 0;
        init.usp = 0;
        double celldm[] = {10.2, 0, 0, 0};
        init.ecutwfc = 18;
        init.ecutrho = 4 * 18;
        init.ibrav = 0;
        init.iband = 6;
        init.num_atom = 1;
        init.nat = 3;
        init.mix = 0.7;
        String atom[] = {"Si"};
        init.atom = atom;
        String upf_url[] = {"/root/kuda/Si.pz-vbc.UPF"};
        int usp_[] = {0, 0, 0};
        init.term = celldm[0];
        double lattice[][] = {
            {init.term, 0.0000000000, 0},
            {0.0, init.term, 0},
            {0, 0, init.term}
        };
        double pos[][] = {
            {0.0000000000, 0.0000000000, 0.0000000000},
            {0.25, 0.25, 0.25},
            {0.3, 0.3, 0.3}};
        int atom_pos[] = {0, 0, 0};

        double k_point[][] = {{-1, 0.5, 0}};
        double weig[] = new double[k_point.length];
        for (int i = 0; i < weig.length; i++) {
            weig[i] = 2.0 / k_point.length;

        }
        init.upf_url = upf_url;
        init.k_point = k_point;
        init.weig = weig;
        init.atom_pos = atom_pos;
        init.pos = pos;
        init.lattice = lattice;

        init.celldm = celldm;
        config_ssh cs = new config_ssh();
        cs.host = "silamipa.untad.ac.id";
        cs.key = "/home/agung/.ssh/known_hosts";
        cs.user = "root";
        cs.user_db = "mabok_janda";
        cs.pass = "DWAgungDanuWijaya_971992^";
        init.cs = cs;
        Gson gson = new Gson();

        String json = gson.toJson(init);
        System.out.println(json);

        //   utama_gen ug = new utama_gen();
        // ug.main(init);
        //https://jsonlint.com/
        /* java -jar "/home/agung/Documents/solid/terpan/JavaQsolid-old (1)/JavaQsolid-old/dist/jDFT.jar" input.dat > out.dat*/
    }

}
