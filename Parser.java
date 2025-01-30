import java.util.Scanner;

public class Parser {
    private CommandWords commands;
    private Scanner reader;

    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    public Command getCommand() {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> ");
        inputLine = reader.nextLine().trim(); // Remove espaços extras

        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next(); // Primeira palavra é o comando
            if (tokenizer.hasNext()) {
                // Captura o RESTANTE da linha como segunda palavra
                word2 = inputLine.substring(word1.length()).trim();
            }
        }

        if (commands.isCommand(word1)) {
            return new Command(word1, word2);
        } else {
            return new Command(null, word2);
        }
    }
}