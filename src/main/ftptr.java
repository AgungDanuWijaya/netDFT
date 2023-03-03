/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.io.InputStream;

/**
 *
 * @author root
 */
public class ftptr extends Thread {
String key[];
        String Server[];
        int i_;
    public ftptr(String Server[],String key[],int i_) {
    this.Server=Server;
    this.key=key;
    this.i_=i_;

    }

    public void run() {
        try {
            System.err.println("ii"+i_);
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
