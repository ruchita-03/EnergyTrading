import java.sql.*;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.text.ParseException;


public class TradeManager {
    private static final Scanner sc = new Scanner(System.in);
public static void addTrade() {
    try (Connection con = DBConnection.getConnection()) {

        // ✅ Fix leftover newline from previous input
        sc.nextLine(); // <-- Add this to clear input buffer

        // ✅ Prompt and validate Trade Date
        String date;
        java.sql.Date sqlDate;
        while (true) {
            System.out.print("Enter Trade Date (YYYY-MM-DD): ");
            date = sc.nextLine().trim();
            if (date.contains(" ")) {
                System.out.println("Please enter only the date (YYYY-MM-DD), nothing else.");
                continue;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            try {
                java.util.Date parsed = sdf.parse(date);
                sqlDate = new java.sql.Date(parsed.getTime());
                break;
            } catch (ParseException pe) {
                System.out.println("Invalid date format! Please use YYYY-MM-DD.");
            }
        }

        // ✅ Other inputs
        System.out.print("Enter Counterparty: ");
        String counterparty = sc.nextLine().trim();

        System.out.print("Enter Commodity: ");
        String commodity = sc.nextLine().trim();

        System.out.print("Enter Volume: ");
        double volume = sc.nextDouble();

        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        sc.nextLine(); // consume leftover newline

        System.out.print("Enter Trade Type (BUY/SELL): ");
        String tradeType = sc.nextLine().trim().toUpperCase();

        // ✅ Insert into database
        String sql = "INSERT INTO Trades (TradeDate, Counterparty, Commodity, Volume, Price, TradeType) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDate(1, sqlDate);
        ps.setString(2, counterparty);
        ps.setString(3, commodity);
        ps.setDouble(4, volume);
        ps.setDouble(5, price);
        ps.setString(6, tradeType);

        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Trade added successfully!");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}






    public static void viewAllTrades() {
    try (Connection con = DBConnection.getConnection()) {
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM Trades");
        while (rs.next()) {
            System.out.printf("%d | %s | %s | %s | %.2f | %.2f | %s%n",
                    rs.getInt("TradeID"),
                    rs.getDate("TradeDate"),
                    rs.getString("Counterparty"),
                    rs.getString("Commodity"),
                    rs.getDouble("Volume"),
                    rs.getDouble("Price"),
                    rs.getString("TradeType"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public static void updateTrade() {
    try (Connection con = DBConnection.getConnection()) {
        System.out.print("Enter TradeID to update: ");
        int tradeId = sc.nextInt();
        System.out.print("Enter new Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter new Volume: ");
        double volume = sc.nextDouble();

        String sql = "UPDATE Trades SET Price = ?, Volume = ? WHERE TradeID = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setDouble(1, price);
        ps.setDouble(2, volume);
        ps.setInt(3, tradeId);

        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Trade updated successfully!");
        else System.out.println("TradeID not found.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public static void deleteTrade() {
    try (Connection con = DBConnection.getConnection()) {
        System.out.print("Enter TradeID to delete: ");
        int tradeId = sc.nextInt();

        String sql = "DELETE FROM Trades WHERE TradeID = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, tradeId);

        int rows = ps.executeUpdate();
        if (rows > 0) System.out.println("Trade deleted successfully!");
        else System.out.println("TradeID not found.");
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public static void searchTrades() {
    try (Connection con = DBConnection.getConnection()) {
        sc.nextLine(); // consume newline
        System.out.print("Enter Counterparty or Commodity to search: ");
        String search = sc.nextLine();

        String sql = "SELECT * FROM Trades WHERE Counterparty LIKE ? OR Commodity LIKE ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, "%" + search + "%");
        ps.setString(2, "%" + search + "%");

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            System.out.printf("%d | %s | %s | %s | %.2f | %.2f | %s%n",
                    rs.getInt("TradeID"),
                    rs.getDate("TradeDate"),
                    rs.getString("Counterparty"),
                    rs.getString("Commodity"),
                    rs.getDouble("Volume"),
                    rs.getDouble("Price"),
                    rs.getString("TradeType"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static void main(String[] args) {
   
    int choice;
    do {
        System.out.println("\n--- Energy Trade Management ---");
        System.out.println("1. Add Trade");
        System.out.println("2. View All Trades");
        System.out.println("3. Update Trade");
        System.out.println("4. Delete Trade");
        System.out.println("5. Search Trades");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
        choice = sc.nextInt();

        switch (choice) {
            case 1 -> addTrade();
            case 2 -> viewAllTrades();
            case 3 -> updateTrade();
            case 4 -> deleteTrade();
            case 5 -> searchTrades();
            case 6 -> System.out.println("Exiting...");
            default -> System.out.println("Invalid choice!");
        }
    } while (choice != 6);
}

}
