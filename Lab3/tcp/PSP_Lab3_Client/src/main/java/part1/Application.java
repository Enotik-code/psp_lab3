package part1;

import java.io.*;
import java.net.*;
public class Application {
    public static void main(String[] arg) {

        try {
            System.out.println("server connecting....");
            Socket clientSocket = new Socket("127.0.0.1",2525);//установление //соединения между локальной машиной и указанным портом узла сети
            System.out.println("connection established....");
            BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));//создание
//буферизированного символьного потока ввода
            ObjectOutputStream coos = new ObjectOutputStream(clientSocket.getOutputStream());//создание
//потока вывода
            ObjectInputStream  cois = new ObjectInputStream(clientSocket.getInputStream());//создание
//потока ввода
            System.out.println("list - list of swimmers. result - result of championship. add 1-2.32 - to add result' \n\t('quite' − programme terminate)");
            String clientMessage = stdin.readLine();
            System.out.println("you've entered: "+clientMessage);
            while(!clientMessage.equals("quite")) {//выполнение цикла, пока строка //не будет равна «quite»
                coos.writeObject(clientMessage);//потоку вывода присваивается //значение строковой переменной (передается серверу)
                System.out.println("~server~: "+cois.readObject());//выводится на //экран содержимое потока ввода (переданное сервером)
                System.out.println("---------------------------");
                clientMessage = stdin.readLine();//ввод текста с клавиатуры
                System.out.println("you've entered: "+clientMessage);//вывод в
//консоль строки и значения строковой переменной
            }
            coos.close();//закрытие потока вывода
            cois.close();//закрытие потока ввода
            clientSocket.close();//закрытие сокета
        }catch(Exception e)	{
            e.printStackTrace();//выполнение метода исключения е
        }
    }
}
