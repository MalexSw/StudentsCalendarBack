package com;

import java.util.Scanner;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.postgresq.tools.DatabaseSeeder;
import com.tools.apiInit;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Java Terminal. Type 'help' for commands.");

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "help":
                    System.out.println("Commands:");
                    System.out.println("  start-api      - Start dummy API");
                    System.out.println("  seed-db        - Seed sample calendars and events");
                    System.out.println("  stop-api       - Stop dummy API");
                    System.out.println("  create-table   - Create a test table in PostgreSQL");
                    System.out.println("  insert-test    - Insert sample data");
                    System.out.println("  query-test     - Query sample data");
                    System.out.println("  exit           - Quit program");
                    break;

                case "start-api":
                    apiInit.initAPI();
                    break;

                case "seed-db":
                    apiInit.initAPI();
                    apiInit.getContext().getBean(DatabaseSeeder.class).seedAndPrint();
                    break;

                case "stop-api":
                    apiInit.stopAPI();
                    break;

                case "create-table":
                    //executeSQL("CREATE TABLE IF NOT EXISTS test_table(id SERIAL PRIMARY KEY, name VARCHAR(50))");
                    break;

                case "insert-test":
                    //executeSQL("INSERT INTO test_table(name) VALUES('Alice'),('Bob')");
                    break;

                case "query-test":
                    //queryTest();
                    break;

                case "exit":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Unknown command. Type 'help' for commands.");
            }
        }
    }

    // private static void executeSQL(String sql) {
    //     try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    //          Statement stmt = conn.createStatement()) {
    //         stmt.executeUpdate(sql);
    //         System.out.println("Executed: " + sql);
    //     } catch (SQLException e) {
    //         System.out.println("SQL Error: " + e.getMessage());
    //     }
    // }
    // private static void queryTest() {
    //     try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
    //          Statement stmt = conn.createStatement();
    //          ResultSet rs = stmt.executeQuery("SELECT * FROM test_table")) {
    //         while (rs.next()) {
    //             System.out.println("id: " + rs.getInt("id") + ", name: " + rs.getString("name"));
    //         }
    //     } catch (SQLException e) {
    //         System.out.println("SQL Error: " + e.getMessage());
    //     }
    // }
}
