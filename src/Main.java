import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        try {
            // URL endpoint untuk mendapatkan data JSON
            URL url = new URL("http://example.com/api/widget");

            // Membuat koneksi HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Cons-ID", "43527");
            connection.setRequestProperty("X-Timestamp", "165346788");

            // Membaca response dari server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            // Cetak response JSON
            System.out.println(response.toString());

            // Menutup koneksi
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
