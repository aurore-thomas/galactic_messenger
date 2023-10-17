package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class ServerApp {

    public static void main(String[] args) throws UnknownHostException {

        SpringApplication.run(ServerApp.class, args);
        InetAddress inetAddress = InetAddress.getLocalHost();
        String ipAddress = inetAddress.getHostAddress();
        System.out.println("Server available at:" + ipAddress + ":3000");
        System.out.println("H2 Console access : http://localhost:3000/h2");
    }
}
