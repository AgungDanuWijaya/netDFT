package cluster;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.IOException;
import main.ftptr;

/**
 * A program demonstrates how to upload files from local computer to a remote
 * FTP server using Apache Commons Net API.
 *
 * @author www.codejava.net
 */
public class run_cluster {

    public static void main(String[] args) throws IOException, JSchException, SftpException {
        String Server[] = {"silamipa.untad.ac.id", "fisika.fmipa.untad.ac.id", "sila.fekon.untad.ac.id", "satu.feb.untad.ac.id", "dua.feb.untad.ac.id"};
        String key[] = {"/home/agung/kuda/silamipa", "/home/agung/kuda/fisika", "/home/agung/kuda/sila", "/home/agung/kuda/ekon1", "/home/agung/kuda/ekon2"};
        String pass = "";
        for (int i_ = 0; i_ < Server.length; i_++) {
            ftptr thread = new ftptr(Server, key, i_, pass);
            thread.start();
        }
    }
}
