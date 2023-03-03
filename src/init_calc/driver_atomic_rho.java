package init_calc;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import main.parameter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.cluster_ftp;
import main.config_folder;
import tools.array_operation;

/**
 *
 * @author agung
 */
public class driver_atomic_rho {

    public void main(parameter param) {
        for (int i = 0; i < param.pos.length; i++) {
            param.tot_muatan += param.upf_data.get(param.atom_p[i]).zp;
        }
        atomic_rho ar = new atomic_rho();
        for (int i = 0; i < param.upf_data.size(); i++) {
            ar.main(param.upf_data.get(param.atom[i]), param, i);
        }
        double rhocg_[][] = new double[param.g.gg.length][2];
        double rhocg_3d_[][] = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];
        array_operation mo = new array_operation();
        double mu = 0;
        for (int ik = 0; ik < param.upf_data.size(); ik++) {
            mu += param.upf_data.get(param.atom[ik]).rhocg[0][0] * param.omega;
            rhocg_ = mo.adddot(rhocg_, param.upf_data.get(param.atom[ik]).rhocg);
        }

        for (int i = 0; i < param.g.gg.length; i++) {
            rhocg_[i] = mo.mdot(rhocg_[i], param.tot_muatan / (mu));
            double dt[] = {rhocg_[i][0], rhocg_[i][1]};
            rhocg_3d_[param.nl.get(i) - 1] = dt;
        }
        param.rhocg = rhocg_;
        param.rhocg_3d = rhocg_3d_;
        rhog2r rr = new rhog2r();

        rr.main(param);

    }

    public void main_band(parameter param) throws IOException, ClassNotFoundException, JSchException, SftpException {
        for (int i = 0; i < param.pos.length; i++) {
            param.tot_muatan += param.upf_data.get(param.atom_p[i]).zp;
        }
        atomic_rho ar = new atomic_rho();
        for (int i = 0; i < param.upf_data.size(); i++) {
            ar.main(param.upf_data.get(param.atom[i]), param, i);
        }
        double rhocg_[][] = new double[param.g.gg.length][2];
        double rhocg_3d_[][] = new double[(int) (param.nr1 * param.nr2 * param.nr3)][2];
        array_operation mo = new array_operation();
        double mu = 0;
        for (int ik = 0; ik < param.upf_data.size(); ik++) {
            mu += param.upf_data.get(param.atom[ik]).rhocg[0][0] * param.omega;
            rhocg_ = mo.adddot(rhocg_, param.upf_data.get(param.atom[ik]).rhocg);
        }

        for (int i = 0; i < param.g.gg.length; i++) {
            rhocg_[i] = mo.mdot(rhocg_[i], param.tot_muatan / (mu));
            double dt[] = {rhocg_[i][0], rhocg_[i][1]};
            rhocg_3d_[param.nl.get(i) - 1] = dt;
        }

        ObjectInputStream inputStream;
        try {
            cluster_ftp cf = new cluster_ftp();
            String hjkl = cf.main(new config_folder().url_out + "rhog",param.cs,param);

            inputStream = new ObjectInputStream(new FileInputStream(hjkl));

            param.rhocg = (double[][]) inputStream.readObject();
            inputStream.close();
            hjkl = cf.main(new config_folder().url_out + "rhor",param.cs,param);

            inputStream = new ObjectInputStream(new FileInputStream(hjkl));

            param.rhocr = (double[][]) inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(driver_atomic_rho.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
