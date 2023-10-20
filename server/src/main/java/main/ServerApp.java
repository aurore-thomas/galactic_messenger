package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class ServerApp {

    public static void main(String[] args) throws UnknownHostException {

        if (args.length != 1) {
            System.out.println("""
                --------------------------------------------------------------------------
                Error, please enter :                    
                    -> java -jar galactic_messenger_server.jar [Server port]
                --------------------------------------------------------------------------
                """);
            System.exit(0);
        }

        System.setProperty("server.port", String.valueOf(Integer.parseInt(args[0])));
        int port = Integer.parseInt(System.getProperty("server.port"));

        SpringApplication.run(ServerApp.class, args);
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ipAddress = inetAddress.getHostAddress();
        System.out.println("\nServer available at: " + ipAddress + ":" + port);
    }
}
