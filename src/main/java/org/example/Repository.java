package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Repository {
    File file;

    public Repository() {
        file = new File("History.txt");
        if(!file.exists()) {
            System.out.println("Файл с историей не найден!");
        }
    }

    public void writeMessageToHistory(String message) {
        try(FileWriter fw = new FileWriter(file, true)) {
            fw.append(message);
        }
        catch (IOException ex) {
            System.out.println("Ошибка ввода-вывода при записи в файл");
        }
    }

    public void loadHistory(JTextArea ta) {
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                ta.append(scanner.nextLine() + "\n");
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Ошибка при загрузке истории сообщений");
        }
    }
}
