import java.util.HashMap;

public class Player {
    private String name;
    private Room currentRoom;
    private HashMap<String, Item> inventory;
    private int maxWeight = 30;  // Peso máximo que o jogador pode carregar (em unidades)
    private int currentWeight = 0;  // Peso atual no inventário

    public Player(String name, Room startRoom) {
        this.name = name;
        this.currentRoom = startRoom;
        this.inventory = new HashMap<>();
    }

    public void addItemToInventory(Item item) {
        if (currentWeight + item.getWeight() > maxWeight) {
            System.out.println("Você não pode carregar isso! Tá muito pesado.");
        } else {
            inventory.put(item.getDescription().toLowerCase(), item); // Chave em minúsculas
            currentWeight += item.getWeight();
        }
    }

    public boolean hasItem(String itemName) {
        return inventory.containsKey(itemName);
    }

    public void removeItemFromInventory(String itemName) {
        Item item = inventory.get(itemName);
        if (item != null) {
            currentWeight -= item.getWeight();
            inventory.remove(itemName);
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public HashMap<String, Item> getInventory() {
        return inventory;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void increaseMaxWeight(int amount) {
        maxWeight += amount;
        System.out.println("Sua capacidade de carga aumentou! A nova capacidade é " + maxWeight + ".");
    }

}
