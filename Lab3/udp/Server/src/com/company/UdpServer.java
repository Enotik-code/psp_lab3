package com.company;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Date;

public class UdpServer extends Thread {

    private final DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;

    private int portNumber = 7777;
    private byte[] buffer = null;

    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double result = 0;

    public UdpServer() throws SocketException {
        setName("UdpServer");
        datagramSocket = new DatagramSocket(portNumber);
    }

    public void run(){
        try{
            System.out.print("Server started!");

            while (! isInterrupted()) {
                if (getMessageType() == MessageType.CONNECTION) {
                    sendMessageType(MessageType.MESSAGE);
                    sendMessage("Enter value of x: ");

                    if (getMessageType() == MessageType.MESSAGE) {
                        String message = getMessage();

                        if (message.matches("^[0-9.]+$") && Double.parseDouble(message) != 0.0) {
                            x = Double.parseDouble(message);

                            sendMessageType(MessageType.MESSAGE);
                            sendMessage("Enter value of y: ");
                            if (getMessageType() == MessageType.MESSAGE) {
                                message = getMessage();

                                if (message.matches("^[0-9.]+$") && Double.parseDouble(message) != 0.0) {
                                    y = Double.parseDouble(message);

                                    sendMessageType(MessageType.MESSAGE);
                                    sendMessage("Enter value of z: ");
                                    if (getMessageType() == MessageType.MESSAGE) {
                                        message = getMessage();

                                        if (message.matches("^[0-9.]+$")) {
                                            z = Double.parseDouble(message);

                                            result = Math.abs(Math.pow(x,y / x) - Math.pow(y / x, 0.5)) + (y - x) * ((Math.cos(y) - Math.exp(z/(y-x)))/(1 + Math.pow(y - x, 2)));

                                            sendMessageType(MessageType.MESSAGE);
                                            sendMessage("Result: " + Double.valueOf(result).toString());
                                        }
                                        else {
                                            sendMessageType(MessageType.ERROR);
                                            sendMessage("Incorrect z value!");
                                        }
                                    }
                                }
                                else {
                                    sendMessageType(MessageType.ERROR);
                                    sendMessage("Incorrect y value!");
                                }
                            }
                        }
                        else {
                            sendMessageType(MessageType.ERROR);
                            sendMessage("Incorrect x value!");
                        }
                    }
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        finally{
            datagramSocket.close();
        }
    }


    private void sendMessageType(MessageType messageType) throws IOException {
        buffer = new byte[256];
        buffer = messageType.toString().getBytes();
        DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length,  datagramPacket.getAddress(), datagramPacket.getPort());
        datagramSocket.send(requestPacket);
    }

    private void sendMessage(String message) throws IOException {
        buffer = new byte[256];
        buffer = message.getBytes();
        DatagramPacket requestPacket = new DatagramPacket(buffer, buffer.length, datagramPacket.getAddress(), datagramPacket.getPort());
        datagramSocket.send(requestPacket);
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
}
