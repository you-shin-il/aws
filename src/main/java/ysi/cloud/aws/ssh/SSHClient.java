package ysi.cloud.aws.ssh;

import com.jcraft.jsch.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ysi.cloud.aws.keypair.Keypair;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

/**
 * Created by ysi.
 * User: ysi
 * Date: 2019-07-29
 * Time: 오후 1:07
 */
@Service
public class SSHClient {
    @Autowired
    private Keypair keypair;

    /**
     * 볼륨 연결 후 초기화 실행
     *
     * @param publicIp
     * @param shellScriptParam
     * @param shellScriptPath
     */
    public boolean action(String publicIp, String shellScriptPath, String shellScriptParam) {
        return true;
    }

    public void getSession(String keypairName) {
        ClassPathResource resource = keypair.getKeypairResourcePath(keypairName);

        JSch jsch = new JSch();
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        //config.put("StrictHostKeyChecking", "no");
        //config.put("PreferredAuthentications", "publickey,keyboard-interactive,password");

        try {
            jsch.addIdentity(resource.getFile().getAbsolutePath());
            Session session = jsch.getSession("ubuntu", "13.125.227.157", 22);
            session.setConfig(config);
            session.connect();
            ChannelExec channelExec = (ChannelExec) session.openChannel("exec");
            channelExec.setCommand("sudo printf 'd\\nn\\np\\n\\n\\n' | /home/ubuntu/test.sh /dev/xvd");
            actionExec(channelExec);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (JSchException jschException) {
            jschException.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void actionExec(ChannelExec channelExec) throws IOException, JSchException, InterruptedException {
        InputStream inputStream = channelExec.getInputStream(); // <- 일반 출력 스트림
        final InputStream errStream = channelExec.getErrStream();// <- 일반 에러 스트림
        byte[] buffer = new byte[BUFFER_SIZE];

        channelExec.connect();

        while(true) {
            while (inputStream.available() > 0) {
                int i = inputStream.read(buffer, 0, BUFFER_SIZE);
                if (i < 0) {
                    break;
                }

                System.out.println(new String(buffer, 0, i));
            }

            while (errStream.available() > 0) {
                int i = errStream.read(buffer, 0, BUFFER_SIZE);
                if (i > 0) {
                    System.out.println("====error====");
                    System.err.println(new String(buffer, 0, i));
                }
            }

            if (channelExec.isClosed()) {
                if (inputStream.available() > 0 || errStream.available() > 0) {
                    continue;
                }
                break;
            }

            TimeUnit.MILLISECONDS.sleep(100);
        }

        final int exitStatus = channelExec.getExitStatus();
    }
}
