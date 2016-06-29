
import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by donar on 16/5/9.
 */
public class UploadUtil {
    static String username="root";
    static String host="ssh.byatern.com";
    static int port=22;
    static String password="xxxxxx";
    public static void upload(File[] resources,String destPath,File[] localFiles) throws JSchException, SftpException, IOException {
        ChannelSftp sftp = null;
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession(username, host, port);
        sshSession.setPassword(password);
        Properties sshConfig = new Properties();
        sshConfig.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(sshConfig);
        sshSession.connect();
        Channel channel = sshSession.openChannel("sftp");
        channel.connect();
        long start =System.currentTimeMillis();
        sftp = (ChannelSftp) channel;
        sftp.cd("/var/www/gitblog-2.2/blog");
        try{
            sftp.mkdir(destPath);
        }catch (SftpException e){
            System.out.println("文件夹重复创建不抛异常");
        }
        sftp.cd(destPath);
        //上传md文件
        for(File localFile:localFiles){
            sftp.put(new FileInputStream(localFile), localFile.getName());
            long end=System.currentTimeMillis();
            System.out.println("上传文件'" + localFile + "'到 " + host + " 的 " + destPath + " 目录中 共耗时： " + (end - start) + "  ms");
        }
        //上传资源文件
        if(resources!=null){
            try{
                sftp.mkdir("resources");
                sftp.cd("resources");
            }catch (SftpException e){
                System.out.println("文件夹重复创建不抛异常");
            }
            for(File resourceFile:resources){
                sftp.put(new FileInputStream(resourceFile), resourceFile.getName());
                long end=System.currentTimeMillis();
                System.out.println("上传文件'" + resourceFile + "'到 " + host + " 的 " + destPath + " resources目录中 共耗时： " + (end - start) + "  ms");
            }
        }
        sftp.disconnect();
        channel.disconnect();
        sshSession.disconnect();
    }
}
