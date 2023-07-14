import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductFetcher {
    private static final String API_URL = "https://dummyjson.com/products";
    private static final String X_CONS_ID = "1234567";
    private static final String USER_KEY = "faY7385H";

    public static void main(String[] args) {
        try {
            ProductFetcher productFetcher = new ProductFetcher();
            JSONObject products = productFetcher.fetchProducts();

            productFetcher.sortProductsByRating(products.getJSONArray("products"));

            JSONArray productList = products.getJSONArray("products");
            for (int i = 0; i < productList.length(); i++) {
                JSONObject product = productList.getJSONObject(i);
                String title = product.getString("title");
                double rating = product.getDouble("rating");
                System.out.println("Product: " + title);
                System.out.println("Rating: " + rating);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject fetchProducts() throws IOException, JSONException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("X-Cons-ID", X_CONS_ID);
        connection.setRequestProperty("user_key", USER_KEY);

        StringBuilder responseData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                responseData.append(line);
            }
        } finally {
            connection.disconnect();
        }

        return new JSONObject(responseData.toString());
    }


    private void sortProductsByRating(JSONArray products) throws JSONException {
        int n = products.length();

        if (n <= 1) {
            return; // Tidak ada perlu dilakukan pengurutan
        }

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            double minValue = products.getJSONObject(i).getDouble("rating");

            for (int j = i + 1; j < n; j++) {
                double currentRating = products.getJSONObject(j).getDouble("rating");
                if (currentRating < minValue) {
                    minIndex = j;
                    minValue = currentRating;
                }
            }

            if (minIndex != i) {
                Object temp = products.get(i);
                products.put(i, products.get(minIndex));
                products.put(minIndex, temp);
            }
        }
    }

}

