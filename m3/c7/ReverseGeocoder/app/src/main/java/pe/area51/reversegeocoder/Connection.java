package pe.area51.reversegeocoder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connection {

    public static String doHttpGet(final String url) throws IOException {
        final HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
        final InputStreamReader inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
        final StringBuilder stringBuilder = new StringBuilder();
        final int bufferSize = 2048;
        final char[] buffer = new char[bufferSize];
        int n = 0;
        while ((n = inputStreamReader.read(buffer)) != -1) {
            stringBuilder.append(buffer, 0, n);
        }
        return stringBuilder.toString();
    }

}
