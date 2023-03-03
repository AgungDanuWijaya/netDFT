package main;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.IOException;
import java.io.InputStream;

/**
 * A program demonstrates how to upload files from local computer to a remote
 * FTP server using Apache Commons Net API.
 *
 * @author www.codejava.net
 */
public class FTPcomadn1 {

    public static void main(String[] args) throws IOException, JSchException, SftpException {
        String Server[] = {"silamipa.untad.ac.id", "fisika.fmipa.untad.ac.id", "sila.fekon.untad.ac.id", "satu.feb.untad.ac.id", "dua.feb.untad.ac.id"};
        String key[] = {"/home/agung/kuda/silamipa", "/home/agung/kuda/fisika", "/home/agung/kuda/sila", "/home/agung/kuda/ekon1", "/home/agung/kuda/ekon2"};

        int i_ = 3;
        try {
            System.err.println("ii" + i_);
            JSch jsch = new JSch();
            jsch.setKnownHosts(key[i_]);
            Session session = jsch.getSession("root", Server[i_]);
            session.setPassword("DWAgungDanuWijaya_971992^");
            session.connect();
            String command = "java -jar /root/kuda/dist/jDFT.jar input " + (i_ + 1) + "";
            System.out.println(command);
            Channel channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);
            InputStream in = channel.getInputStream();

            channel.connect();

            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    System.out.print(new String(tmp, 0, i));
                }
                if (channel.isClosed()) {
                    System.out.println("exit-status: " + channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }

            channel.disconnect();
            session.disconnect();
        } catch (Exception e) {
        }
    }
}
