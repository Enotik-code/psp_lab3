package part1;

import part1.service.StringService;
import part1.service.SwimService;

import java.io.*;
import java.net.*;

public class Application {
    public static void main(String[] arg) {
        SwimService swimService = new SwimService();
        StringService stringService = new StringService();
        ServerSocket serverSocket = null;
        Socket clientAccepted = null;//объявление объекта класса Socket
        ObjectInputStream sois = null;//объявление байтового потока ввода
        ObjectOutputStream soos = null;//объявление байтового потока вывода
        swimService.setSwimmers();
        //String s = "add 1-234";
        //s = s.substring(4);
        //System.out.println(stringService.getNumberOfSwimmer(s) + " номер");
        //System.out.println(stringService.getResultFromNumber(s) + " рез");
        try {
            System.out.println("server starting....");
            serverSocket = new ServerSocket(2525);//создание сокета сервера для //заданного порта
            clientAccepted = serverSocket.accept();//выполнение метода, который //обеспечивает реальное подключение сервера к клиенту
            System.out.println("connection established....");
            sois = new ObjectInputStream(clientAccepted.getInputStream());
            soos = new ObjectOutputStream(clientAccepted.getOutputStream());//создание потока
            String clientMessageRecieved = (String) sois.readObject();//объявление //строки и присваивание ей данных потока ввода, представленных
            while (!clientMessageRecieved.equals("quite"))//выполнение цикла: пока
            {
                if (clientMessageRecieved.startsWith("list")) {
                    System.out.println("message recieved: '" + clientMessageRecieved + "'");
                    soos.writeObject(swimService.getSwimmers());//потоку вывода
                    clientMessageRecieved = (String) sois.readObject();//строке

                }
                if (clientMessageRecieved.startsWith("result")) {
                    System.out.println("message recieved: '" + clientMessageRecieved + "'");
                    soos.writeObject(swimService.getPosition());//потоку вывода
                    clientMessageRecieved = (String) sois.readObject();//строке

                }
                if (clientMessageRecieved.startsWith("add")) {
                    System.out.println("message recieved: '" + clientMessageRecieved + "'");
                    clientMessageRecieved = clientMessageRecieved.substring(4);
                    swimService.setResult(stringService.getNumberOfSwimmer(clientMessageRecieved), stringService.getResultFromNumber(clientMessageRecieved));
                    System.out.println(swimService.getSwimmers());
                    soos.writeObject("success");//потоку вывода
                    clientMessageRecieved = (String) sois.readObject();
                }
            }
        } catch (Exception e) {
        } finally {
            try {
                sois.close();//закрытие потока ввода
                soos.close();//закрытие потока вывода
                clientAccepted.close();//закрытие сокета, выделенного для клиента
                serverSocket.close();//закрытие сокета сервера
            } catch (Exception e) {
                e.printStackTrace();//вызывается метод исключения е
            }
        }
    }
}

