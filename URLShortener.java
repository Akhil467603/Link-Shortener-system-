import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class URLShortener {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();
    private static final int SHORT_URL_LENGTH = 6;

    private Map<String, String> shortToLongURLMap;
    private Map<String, String> longToShortURLMap;

    public URLShortener() {
        shortToLongURLMap = new HashMap<>();
        longToShortURLMap = new HashMap<>();
    }

    public String shortenURL(String longURL) {
        if (longToShortURLMap.containsKey(longURL)) {
            return longToShortURLMap.get(longURL);
        }

        String shortURL;
        do {
            shortURL = generateShortURL();
        } while (shortToLongURLMap.containsKey(shortURL));

        shortToLongURLMap.put(shortURL, longURL);
        longToShortURLMap.put(longURL, shortURL);

        return shortURL;
    }

    public String expandURL(String shortURL) {
        if (shortToLongURLMap.containsKey(shortURL)) {
            return shortToLongURLMap.get(shortURL);
        }
        throw new IllegalArgumentException("Invalid short URL: " + shortURL);
    }

    private String generateShortURL() {
        StringBuilder shortURL = new StringBuilder(SHORT_URL_LENGTH);
        Random random = new Random();

        for (int i = 0; i < SHORT_URL_LENGTH; i++) {
            shortURL.append(ALPHABET.charAt(random.nextInt(BASE)));
        }

        return shortURL.toString();
    }

    public static void main(String[] args) {
        URLShortener urlShortener = new URLShortener();

        // Command line interface
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (true) {
            System.out.println("Enter a command: 'shorten <longURL>' or 'expand <shortURL>' or 'exit'");
            String command = scanner.nextLine();
            if (command.equalsIgnoreCase("exit")) {
                break;
            }

            String[] parts = command.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid command. Try again.");
                continue;
            }

            String action = parts[0];
            String url = parts[1];

            try {
                if (action.equalsIgnoreCase("shorten")) {
                    String shortURL = urlShortener.shortenURL(url);
                    System.out.println("Shortened URL: " + shortURL);
                } else if (action.equalsIgnoreCase("expand")) {
                    String longURL = urlShortener.expandURL(url);
                    System.out.println("Original URL: " + longURL);
                } else {
                    System.out.println("Invalid action. Try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}
