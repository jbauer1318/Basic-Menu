import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Menu eatAtJoesMenu = new Menu();
        eatAtJoesMenu.add("Lobster Dinner", MAIN_DISH, NOT_HEART_HEALTHY, 24.99);
        eatAtJoesMenu.add("Rice Pudding", DESSERT, NOT_HEART_HEALTHY, 3.50);
        eatAtJoesMenu.add("Nachos", APPETIZERS, NOT_HEART_HEALTHY, 2.99);
        eatAtJoesMenu.add("Spaghetti", MAIN_DISH, HEART_HEALTHY, 15.06);
        eatAtJoesMenu.add("Hamburger", MAIN_DISH, NOT_HEART_HEALTHY, 7.99);
        eatAtJoesMenu.add("Ice Cream", DESSERT, NOT_HEART_HEALTHY, 4.89);
        eatAtJoesMenu.add("Salad", APPETIZERS, HEART_HEALTHY, 4.00);

        String name;
        int category;
        boolean heartHealthy;
        double price;

        String menuPrompt = "1 – Display appetizers\n" + "2 – Display main dishes\n" + "3 – Display desserts\n" + "4 – Display heart healthy items\n" + "5 – Display items under a specified price\n" + "6 – Display all menu items\n" + "7 – Add menu item\n" + "8 – Remove menu item\n" + "0 – EXIT";
        String inputPrompt = "Please enter your option:";
        String allItems = "ALL MENU ITEMS";
        String appDisplay = "APPETIZERS";
        String heartHealthyDisplay = "ALL HEART HEALTHY MENU ITEMS";
        String mainDishDisplay = "MAIN DISHES";
        String dessertItem = "ALL DESSERT MENU ITEMS";

        String addItemName = "Input Item Name:";
        String addItemCategory = "Input Item Category:\n" + "1 - Appetizers\n" + "2 - Main Dish\n" + "3 - Dessert";
        String addItemHeartHealth = "Is this item heart healthy? Y/N";
        String addItemPrice = "How much does the item cost? (no $ sign)";

        String maxPrice = "What is upper price threshold?";
        String deletePrompt = "Press [d] to delete item or [Enter] to continue";


        Scanner console = new Scanner(System.in);
        boolean exit = false;
        MenuIterator iterator;
        do {
            int selection = menu(menuPrompt, inputPrompt, 0, 8);
            switch (selection) {

                case 1: 
                    iterator = eatAtJoesMenu.getItemIterator(APPETIZERS);
                    printItr(appDisplay, iterator);

                    break;

                case 2: 

                    iterator = eatAtJoesMenu.getItemIterator(MAIN_DISH);
                    printItr(mainDishDisplay, iterator);

                    break;

                case 3: 
                    iterator = eatAtJoesMenu.getItemIterator(DESSERT);
                    printItr(dessertItem, iterator);

                    break;

                case 4: 
                    iterator = eatAtJoesMenu.getHeartHealthyIterator(HEART_HEALTHY);
                    printItr(heartHealthyDisplay, iterator);

                    break;

                case 5: 
                    double priceThreshold = getDouble(console, maxPrice);
                    String underPrice = "ALL ITEMS UNDER $" + priceThreshold;
                    iterator = eatAtJoesMenu.getPriceIterator(priceThreshold);
                    printItr(underPrice, iterator);

                    break;

                case 6: 

                    iterator = eatAtJoesMenu.getAllItemsIterator();
                    printItr(allItems, iterator);

                    break;

                case 7: 
                    name = getStringLine(addItemName);
                    category = getIntRange(console, addItemCategory, 3, 1);
                    heartHealthy = twoOptions(console, addItemHeartHealth, "Y", "N");
                    price = getDouble(console, addItemPrice);
                    eatAtJoesMenu.add(name, category, heartHealthy, price);

                    break;

                case 8: 

                    iterator = eatAtJoesMenu.getAllItemsIterator();
                    MenuItem item;
                    while (iterator.hasNext()) {
                        item = iterator.next();
                        System.out.println(item.getName() + " $" + item.getPrice());
                        if (enterOptions(deletePrompt, "D"))
                            System.out.println("Kept: " + item.getName() + "\n~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                        else {
                            System.out.println("Deleted: " + item.getName() + "\n~~~~~~~~~~~~~~~~~~~~~~~~~\n");
                            eatAtJoesMenu.delete(iterator);
                        }
                    }

                    break;

                case 0: //EXIT MENU
                    exit = true;

                    break;
            }
        } while (!exit);
    }

    private static String getStringLine(String prompt) {
        Scanner input = new Scanner(System.in);
        System.out.println(prompt);
        return input.nextLine();
    }

    private static int getInt(Scanner input, String prompt) {
        System.out.println(prompt);
        while (!input.hasNextInt()) {
            input.next();//consume
            System.out.println("Not an integer. Try again!");
            System.out.println(prompt);
        }
        return input.nextInt();
    }

    private static double getDouble(Scanner input, String prompt) {
        System.out.println(prompt);
        while (!input.hasNextDouble()) {
            input.next();//consume
            System.out.println("Not a number. Try again!");
            System.out.println(prompt);
        }
        return input.nextDouble();
    }

    private static boolean twoOptions(Scanner input, String prompt, String yes, String no) {
        System.out.println(prompt);
        String letter = input.next();
        while (!letter.equalsIgnoreCase(yes) && !letter.equalsIgnoreCase(no)) {
            System.out.println("Not " + yes + " or " + no + ". Try again!");
            System.out.println(prompt);
            letter = input.next();
        }
        return letter.equalsIgnoreCase(yes);
    }

    private static boolean enterOptions(String prompt, String no) {
        Scanner input = new Scanner(System.in);
        System.out.println(prompt);
        String key = input.nextLine();
        while (!key.equals("") && !key.equalsIgnoreCase(no)) {
            System.out.println("Not [Enter] or " + no + ". Try again!");
            System.out.println(prompt);
            key = input.nextLine();
        }
        return key.equals("");
    }

    private static void printItr(String display, MenuIterator itr) {
        System.out.println(display);
        MenuItem item;
        while (itr.hasNext()) {
            item = itr.next();
            System.out.println(item.getName() + " $" + item.getPrice());
        }
    }

    private static int getIntRange(Scanner input, String prompt, int MAX, int LOW) {
        int x = getInt(input, prompt);
        while (x > MAX || x < LOW) {

            System.out.println("INPUT ERROR!!! Invalid size.");
            x = getInt(input, prompt);
        }
        return x;
    }

    private static int menu(String menuOptions, String inputPrompt, int min, int max) {
        System.out.println("\nYour options are:\n-----------------");
        System.out.println(menuOptions);
        Scanner console = new Scanner(System.in);
        int input = getInt(console, inputPrompt);
        while (min > input || input > max) {
            System.out.println(input + " is not a valid option");
            input = getInt(console, inputPrompt);
        }
        return input;
    }
}



