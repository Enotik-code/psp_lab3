package com.company;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {

    private InetAddress serverAddress = null;
    private int port = 0;
    private DatagramSocket datagramSocket = null;
    private DatagramPacket datagramPacket = null;
    byte[] buffer = null;

    public Client(int port) {
        try {
            this.port = port;
            serverAddress = InetAddress.getByName("localhost");
            datagramSocket = new DatagramSocket();
        }
        catch (SocketException | UnknownHostException ex) {
            System.out.print(ex.getMessage());
        }
    }

    public void process() {
        try {
            sendMessageType(MessageType.CONNECTION);

            if (getMessageType() == MessageType.MESSAGE) {
                String message = getMessage();
                System.out.print(message);

                Scanner scanner = new Scanner(System.in);
                if (scanner.hasNextLine()){
                    sendMessageType(MessageType.MESSAGE);
                    sendMessage(scanner.nextLine());
                }

                if (getMessageType() == MessageType.MESSAGE) {
                    message = getMessage();
                    System.out.print(message);
                    if (scanner.hasNextLine()) {
                        sendMessageType(MessageType.MESSAGE);
                        sendMessage(scanner.nextLine());
                    }

                    if (getMessageType() == MessageType.MESSAGE) {
                        message = getMessage();
                        System.out.print(message);
                        if (scanner.hasNextLine()) {
                            sendMessageType(MessageType.MESSAGE);
                            sendMessage(scanner.nextLine());
                        }

                        if (getMessageType() == MessageType.MESSAGE) {
                            message = getMessage();
                            System.out.print(message);
                        }
                        else {
                            message = getMessage();
                            System.out.print(message);
                        }
                    }
                    else {
                        message = getMessage();
                        System.out.print(message);
                    }
                }
                else {
                    message = getMessage();
                    System.out.print(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (datagramSocket != null)
                datagramSocket.close();
        }
    }

    private MessageType getMessageType() throws IOException {
        buffer = new byte[256];
        datagramPacket = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(datagramPacket);
        return MessageType.valueOf(new String(datagramPacket.getData(), 0, datagramPacket.getLength()));
    }

    private String getMessage() throws IOException {
        buffer = new byte[256];
        DatagramPacket responsePacket = new DatagramPacket(buffer, buffer.length);
        datagramSocket.receive(responsePacket);
        return new String(responsePacket.getData(), 0, responsePacket.getLength());
    }

    private void sendMessageType(MessageType messageType) throws IOException {
        buffer = new byte[256];
        buffer = messageType.toString().getBytes();
        DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length, serverAddress, this.port);
        datagramSocket.send(requestPacket);
    }

    private void sendMessage(String message) throws IOException {
        buffer = new byte[256];
        buffer = message.getBytes();
        DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length, serverAddress, this.port);
        datagramSocket.send(requestPacket);
    }

}
