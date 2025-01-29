import java.util.HashMap;

public class Room {
    private String description;
    private HashMap<String, Room> exits; // Usando um HashMap para armazenar as saídas
    private HashMap<String, Item> items; // Usando um HashMap para armazenar os objetos Item na sala

    // Construtor da classe Room
    public Room(String description) {
        this.description = description;
        this.exits = new HashMap<>();
        this.items = new HashMap<>();
    }

    // Método para adicionar uma saída
    public void setExit(String direction, Room neighbor) {
        exits.put(direction.toLowerCase(), neighbor); // Adiciona a saída ao HashMap
    }

    // Método para adicionar um item à sala
    public void addItem(String itemName, Item item) {
        items.put(itemName.toLowerCase(), item); // Adiciona o item ao HashMap
    }

    // Método para obter a descrição da sala
    public String getDescription() {
        return description;
    }

    // Método para obter uma saída baseada na direção
    public Room getExit(String direction) {
        return exits.get(direction.toLowerCase()); // Retorna a sala correspondente à direção
    }

    // Método que retorna uma string com todas as saídas disponíveis
    public String getExitString() {
        if (exits.isEmpty()) {
            return "There are no exits.";
        }

        StringBuilder exitString = new StringBuilder("Saídas: ");
        for (String direction : exits.keySet()) {
            exitString.append(direction).append(", "); // Adiciona vírgula para separar as direções
        }

        return exitString.toString().trim().replaceAll(",$", ""); // Remove a última vírgula
    }

    // Método que retorna uma descrição longa da sala, incluindo saídas e itens
    public String getLongDescription() {
        StringBuilder longDescription = new StringBuilder(getDescription() + "\n");

        // Adiciona as saídas
        longDescription.append(getExitString()).append("\n");

        // Adiciona os itens presentes na sala
        if (items.isEmpty()) {
            longDescription.append("Não tem itens nesse lugar.\n");
        } else {
            longDescription.append("Itens: ");
            for (Item item : items.values()) {
                longDescription.append(item.toString()).append(", ");
            }
            longDescription.setLength(longDescription.length() - 2); // Remove a última vírgula e espaço
            longDescription.append("\n");
        }

        return longDescription.toString().trim(); // Retorna a descrição completa da sala
    }

    // Método para verificar se um item está presente na sala
    public boolean hasItem(String itemName) {
        return items.containsKey(itemName.toLowerCase());
    }

    public Item getItem(String itemName) {
        return items.get(itemName.toLowerCase()); // Retorna o item da sala
    }

    public void removeItem(String itemName) {
        items.remove(itemName.toLowerCase()); // Remove o item da sala
    }

}
