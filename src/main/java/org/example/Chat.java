package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/*
    1. Отправлять сообщения из текстового поля сообщения в лог по нажатию кнопки или
по нажатию клавиши Enter на поле ввода сообщения;
    2. Продублировать импровизированный лог (историю) чата в файле;
    3. При запуске клиента чата заполнять поле истории из файла, если он существует.
Обратите внимание, что чаще всего история сообщений хранится на сервере и заполнение
истории чата лучше делать при соединении с сервером, а не при открытии окна клиента.
*/

public class Chat extends JFrame {
    JLabel lbLogin = new JLabel("Login:");
    JLabel lbPassword = new JLabel("Password:");
    JLabel lbMessage = new JLabel("Message:");

    JTextField tfLogin = new JTextField();
    JTextField tfPassword = new JTextField();
    JTextField tfMessage = new JTextField();

    JTextArea taMessageLog = new JTextArea();

    JPanel paneServer = new JPanel(new GridLayout(5, 1, 5, 5));
    JPanel paneHistory = new JPanel(new GridLayout(1,1));
    JPanel paneClient = new JPanel(new GridLayout(3, 1, 5, 5));

    String login;
    String password;
    String message;

    JButton btnConnect = new JButton("Connect");
    JButton btnSend = new JButton("Send");

    Repository repo;

    public Chat() {
        repo = new Repository();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("MyChat");
        setSize(800, 600);
        setResizable(false);
        setLayout(new BorderLayout());

        paneServer.setBorder(new EmptyBorder(5,5,5,5));
        paneClient.setBorder(new EmptyBorder(5,5,5,5));

        paneServer.add(lbLogin);
        paneServer.add(tfLogin);
        paneServer.add(lbPassword);
        paneServer.add(tfPassword);
        paneServer.add(btnConnect);

        paneHistory.add(taMessageLog);
        paneClient.add(lbMessage);
        paneClient.add(tfMessage);
        paneClient.add(btnSend);

        add(paneServer, BorderLayout.NORTH);
        add(paneHistory, BorderLayout.CENTER);
        add(paneClient, BorderLayout.SOUTH);
        setVisible(true);
        btnSend.setEnabled(false);

        btnConnect.addActionListener(e -> connect());
        btnSend.addActionListener(e -> sendMessage());
        tfMessage.addActionListener(e -> sendMessage());
    }

    void connect() {
        login = tfLogin.getText();
        password = tfPassword.getText();
        if(!login.isEmpty() && !password.isEmpty()) {
            tfLogin.setText("");
            tfPassword.setText("");
            btnSend.setEnabled(true);
            btnConnect.setEnabled(false);
            repo.loadHistory(taMessageLog);
            taMessageLog.append("Привет, " + login + "\n");
        }
        else {
            tfLogin.setText("Нужно ввести какой-то логин");
            tfPassword.setText("Нужно ввести какой-то пароль");
        }
    }

    void sendMessage() {
        message = login + ": " + tfMessage.getText() + "\n";
        taMessageLog.append(message);
        tfMessage.setText("");
        repo.writeMessageToHistory(message);
        System.out.println("Message sent: " + message);
    }

    public static void main(String[] args) {
        new Chat();
    }
}
