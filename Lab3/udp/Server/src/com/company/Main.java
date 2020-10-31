package com.company;

import java.net.SocketException;

public class Main {

    public static void main(String[] args) {
        try{
            UdpServer udpServer = new UdpServer();
            udpServer.start();
        }
        catch(SocketException e){
            e.printStackTrace();
        }
    }
}
