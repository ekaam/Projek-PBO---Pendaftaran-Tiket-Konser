import java.io.*;
import java.util.*;

public class TicketingSystem {
    private List<User> users = new ArrayList<>();
    private List<Concert> concerts = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public TicketingSystem() {
        concerts.add(new Concert("CRSL CONCERT: The Euphoria", "2 February 2025", "Stadium Kridosono, Jogja", 500000));
        concerts.add(new Concert("The 90' Festival", "3 January 2024", "Beach City International Stadium, Jakarta", 750000));
        concerts.add(new Concert("Kukar Love Story", "21 January 2024", "Parking Area, Putri Kalang Melenu Building, Tenggarong", 300000));
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n=== TIKET KONSER BY HONGDEA ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Pilih Opsi: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> login();
                case 2 -> register();
                case 3 -> exit = true;
                default -> System.out.println("Pilihan Tidak Valid. Coba Lagi.");
            }
        }
    }

    private void register() {
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        users.add(new User(username, password));
        System.out.println("Registrasi Berhasil!");
    }

    private void login() {
        System.out.print("Masukkan username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.validatePassword(password)) {
                System.out.println("Login Berhasil!");
                showConcerts(user);
                return;
            }
        }
        System.out.println("Kesalahan username atau password.");
    }

    private void showConcerts(User user) {
        System.out.println("\nKonser Tersedia:");
        for (int i = 0; i < concerts.size(); i++) {
            System.out.println((i + 1) + ". " + concerts.get(i).getDetails());
        }
        System.out.print("Pilih Konser (Masukkan Angka): ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > concerts.size()) {
            System.out.println("Pilihan Tidak Valid.");
        } else {
            bookTicket(user, concerts.get(choice - 1));
        }
    }

    private void bookTicket(User user, Concert concert) {
        System.out.print("Masukkan Nama Lengkap: ");
        String fullName = scanner.nextLine();
        System.out.print("Masukkan email: ");
        String email = scanner.nextLine();
        System.out.print("Masukkan No. Telepon: ");
        String phoneNumber = scanner.nextLine();

        System.out.print("Masukkan Jumlah Tiket: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        double total = concert.getPrice() * quantity;
        System.out.println("Total: Rp " + total);

        System.out.println("Pilih Metode Pembayaran:");
        System.out.println("1. Transfer Bank");
        System.out.println("2. E-Wallet");
        System.out.println("3. Kartu Kredit");
        System.out.print("Pilih Metode Pembayaran (1-3): ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        String paymentMethod = switch (paymentChoice) {
            case 1 -> "Transfer Bank";
            case 2 -> "E-Wallet";
            case 3 -> "Kartu Kredit";
            default -> "Tidak Diketahui";
        };

        System.out.print("Konfirmasi pembayaran Via " + paymentMethod + "? (yes/no): ");
        String confirmation = scanner.nextLine();

        if (confirmation.equalsIgnoreCase("yes")) {
            printTicket(fullName, concert, quantity, total, paymentMethod);
        } else {
            System.out.println("Booking Dibatalkan.");
        }
    }

    private void printTicket(String fullName, Concert concert, int quantity, double total, String paymentMethod) {
        String ticketDetails = "\n--- Detail Tiket ---\n" +
                "Nama: " + fullName + "\n" +
                "Konser: " + concert.getName() + "\n" +
                "Jumlah: " + quantity + "\n" +
                "Pilihan Pembayaran: " + paymentMethod + "\n" +
                "Total Harga: Rp " + total + "\n";

        System.out.println(ticketDetails);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tickets.txt", true))) {
            writer.write(ticketDetails);
            writer.newLine();
            System.out.println("Tiket Tersimpan Di tickets.txt");
        } catch (IOException e) {
            System.out.println("Error saving ticket: " + e.getMessage());
        }
    }
}