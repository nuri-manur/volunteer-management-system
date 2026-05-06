import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


class Volunteer {
    String name;
    int age;
    String skill;
    String city;

    public Volunteer(String name, int age, String skill, String city) {
        this.name = name;
        this.age = age;
        this.skill = skill;
        this.city = city;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Volunteer> list = new ArrayList<>();

        loadFromFile(list);

        while (true) {
            System.out.println("\n--- VOLUNTEER MANAGEMENT SYSTEM ---");
            System.out.println("1. Show all volunteers");
            System.out.println("2. Add new volunteer");
            System.out.println("3. Delete volunteer");
            System.out.println("4. Search by skill");
            System.out.println("5. Edit data");// НОВОЕ
            System.out.println("6. Clear all");
            System.out.println("7. Search by city");
            System.out.println("8. Average age");
            System.out.println("0. Save and Exit");
            System.out.print("Select an option: ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("⚠️ Error! Please enter a number.");
                sc.nextLine();
                continue;
            }

            if (choice == 1) {
                if(list.isEmpty()) {
                    System.out.println("The list is empty.");
                } else {
                    list.sort((v1,v2) -> v1.name.compareToIgnoreCase(v2.name));

                    System.out.println("\n" + "=".repeat(70));
                    System.out.printf("%-15s | %-5s | %-15s | %-15s%n", "NAME", "AGE", "CITY", "SKILL");
                    System.out.println("-".repeat(70));
                    for (Volunteer v: list) {
                        System.out.printf("%-15s | %-5d | %-15s | %-15s%n", v.name, v.age, v.city, v.skill);
                    }
                    System.out.println("=".repeat(70));
                }
            }
            else if (choice == 2) {
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Age: ");
                int age;
                try {
                    age = sc.nextInt();
                    sc.nextLine();
                } catch (Exception e) {
                    System.out.println("⚠ Invalid age! Please enter a number.");
                    sc.nextLine();
                    continue;
                }
                System.out.print("Skill: ");
                String skill = sc.nextLine();
                System.out.print("City: ");
                String city = sc.nextLine();
                list.add(new Volunteer(name, age, skill, city));
                System.out.println("✅ Added successfully!");
            }
            else if (choice == 3) {
                System.out.print("Enter name to delete: ");
                String delName = sc.nextLine();
                if (list.removeIf(v -> v.name.equalsIgnoreCase(delName))) {
                    System.out.println("🗑️ Deleted.");
                } else {
                    System.out.println("❌ Not found.");
                }
            }
            else if (choice == 4) {
                System.out.print("Enter skill to search: ");
                String s = sc.nextLine();
                for (Volunteer v : list) {
                    if (v.skill.equalsIgnoreCase(s)) System.out.println("📍 " + v.name);
                }
            }
            else if (choice == 5) { // ЛОГИКА РЕДАКТИРОВАНИЯ
                System.out.print("Enter name to edit: ");
                String editName = sc.nextLine();
                boolean found = false;
                for (Volunteer v : list) {
                    if (v.name.equalsIgnoreCase(editName)) {
                        System.out.println("Found: " + v.name + ". What should we update?");
                        System.out.println("New name: ");
                        v.name = sc.nextLine();
                        System.out.print("New age: ");
                        try {
                            v.age = sc.nextInt();
                            sc.nextLine();
                        } catch (Exception e) {
                            System.out.println("⚠ Invalid input. Age not changed.");
                            sc.nextLine();
                        }
                        System.out.print("New skill: ");
                        v.skill = sc.nextLine();
                        System.out.println("New city: ");
                        v.city = sc.nextLine();
                        System.out.println("✅ Data updated!");
                        found = true;
                        break;
                    }
                }
                if (!found) System.out.println("❌ Not found.");
            }
            else if (choice == 6) {
                System.out.print("Are you sure? (yes/no): ");
                String confirm = sc.nextLine();
                if(confirm.equalsIgnoreCase("yes")) {
                    list.clear();
                    System.out.println("All data cleared!");
                }
                else {
                    System.out.println("❌ Action cancelled");
                }
            }
            else if (choice == 7) {
                System.out.print("Enter city to search: ");
                String s = sc.nextLine();
                for(Volunteer v : list) {
                    if (v.city.equalsIgnoreCase(s)) System.out.println("📍 " + v.name);
                }
            }
            else if (choice == 8) {
                if (list.isEmpty()) {
                    System.out.println("No data");
                } else {
                    int totalAge = 0;
                    for (Volunteer v: list) {
                        totalAge = totalAge + v.age;
                    }
                    double average = (double) totalAge / list.size();

                    System.out.println("\n--- STATISTICS ---");
                    System.out.println("Total volunteers: " + list.size());
                    System.out.printf("Average age: %.2f years%n", average);

                }
            }
            else if (choice == 0) {
                saveToFile(list);
                System.out.println("💾 Saved. Goodbye!");
                break;
            }
        }
    }

    public static void saveToFile(ArrayList<Volunteer> list) {
        try (FileWriter writer = new FileWriter("volunteers.txt")) {
            for (Volunteer v : list) {
                writer.write(v.name + "," + v.age + "," + v.skill + "," + v.city + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public static void loadFromFile(ArrayList<Volunteer> list) {
        try {
            File f = new File("volunteers.txt");
            Scanner fs = new Scanner(f);
            while (fs.hasNextLine()) {
                String[] p = fs.nextLine().split(",");
                if (p.length == 4) {
                    list.add(new Volunteer(p[0].trim(), Integer.parseInt(p[1].trim()), p[2].trim(), p[3].trim()));
                }
            }
            fs.close();
            System.out.println("📁 Data loaded.");
        } catch (Exception e) {
            System.out.println("🆕 New database created.");
        }
    }
}
