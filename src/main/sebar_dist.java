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
public class sebar_dist {

    public static void main(String[] args) throws IOException, JSchException, SftpException {
      
       String Server[] = {"silamipa.untad.ac.id", "fisika.fmipa.untad.ac.id","sila.fekon.untad.ac.id","satu.feb.untad.ac.id","dua.feb.untad.ac.id"};
        String key[] = {"/home/agung/kuda/silamipa", "/home/agung/kuda/fisika","/home/agung/kuda/sila","/home/agung/kuda/ekon1","/home/agung/kuda/ekon2"};
       
        for (int i = 0; i < Server.length; i++) {
         JSch jsch = new JSch();
       jsch.setKnownHosts(key[i]);
        Session jschSession = jsch.getSession("root", Server[i]);
        jschSession.setPassword("DWAgungDanuWijaya_971992^");
        jschSession.connect();
        ChannelSftp channelSftp = (ChannelSftp) jschSession.openChannel("sftp");
        channelSftp.connect();

        String remoteFile = "/root/kuda/dist/jDFT.jar";
        String localDir = "/home/kaktus/Documents/JavaQsolid-old/dist/netDFT.jar";

        channelSftp.put(localDir, remoteFile );
        System.out.println("main.FTPDownloadFileDemo.main()");
        channelSftp.exit();
        channelSftp.disconnect();
        jschSession.disconnect();	   
        }
        
    }
}
