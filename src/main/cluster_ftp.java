package main;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import java.io.IOException;

/**
 * A program demonstrates how to upload files from local computer to a remote
 * FTP server using Apache Commons Net API.
 *
 * @author www.codejava.net
 */
public class cluster_ftp {

    public String main(String url, config_ssh cs, parameter param) throws IOException, JSchException, SftpException {
        if (param.cluster == 1) {
            JSch jsch = new JSch();
            jsch.setKnownHosts(cs.key);

            Session jschSession = jsch.getSession(cs.user, cs.host);
            jschSession.setPassword(cs.pass);

            jschSession.connect();
            ChannelSftp channelSftp = (ChannelSftp) jschSession.openChannel("sftp");
            channelSftp.connect();
            int yu = (int) (Math.random() * 100000);
            String remoteFile = url;
            String localDir = new config_folder().url_temp + "_" + yu;
            System.out.println(localDir);
            channelSftp.get(remoteFile, localDir);
            channelSftp.exit();
            channelSftp.disconnect();
            jschSession.disconnect();
            return localDir;
        } else {
            return url;
        }
    }
}
