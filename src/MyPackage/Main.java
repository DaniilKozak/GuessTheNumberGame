package MyPackage;


import java.io.*;

public class Main {
    static int attemptCounter = 0;
    static final int secretNumber = (int) (Math.random() * 100);
    static int bestGame = Integer.MAX_VALUE;
    static final String file = "Games.txt"; // убедитесь, что вы корректно указали путь к фалу Games.txt
    public static void main(String[] args) throws IOException {
        loadBestResult();
//        Вот тут ниже с кайфом вызовите метод someGame()
        someGame();
    }

    static void someGame() throws IOException {
        try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Введите число или команду: ");
            String currentLine = consoleReader.readLine();
            if (currentLine.equals("RESULT")) {
                System.out.println("Ваше текущее количество попыток: " + attemptCounter);
                System.out.println("На теущий момент " + (bestGame == Integer.MAX_VALUE ? "лучшей игры еще нет" : "в лучшей игре: " + bestGame + "попыток"));
                someGame();
            } else {
                int currentNum = Integer.parseInt(currentLine);
                attemptCounter++;
                if (currentNum == secretNumber) {
                    System.out.println("Твое количество попыток: " + attemptCounter + "\nПравильный овтет: " + secretNumber);
                    if(attemptCounter < bestGame){
                        System.out.println("Поздравляю! Это новый рекорд.");
                        saveBestResult();
                    }
                    return;
                } else if (currentNum > secretNumber) {
                    System.out.println("Не ожидал от тебя такого. Загаданное число меньше, брат");
                } else {
                    System.out.println("Я сам в шоке, но, загаданное число больше, брат");
                }
                someGame();
            }
        }catch(NumberFormatException e){
            System.out.println("Вы ввели что-то помимо цифр. \nУбедитесь в том, что вы вводите только цифры и попробуйте снова");
            attemptCounter++;
            someGame();
        }
    }

    static void loadBestResult(){
        try (BufferedReader fileReader = new BufferedReader(new FileReader(file.replaceAll("\"","")))){
            String line = fileReader.readLine();
            if(line != null) bestGame = Integer.parseInt(line);
        }catch (IOException | NumberFormatException e) {
            bestGame = Integer.MAX_VALUE;
        }
    }

    static void saveBestResult() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file.replaceAll("\"","")))){
            fileWriter.write(String.valueOf(attemptCounter));
        } catch (IOException e){
            System.out.println("Не сегодня бедолага");
        }
    }

}
