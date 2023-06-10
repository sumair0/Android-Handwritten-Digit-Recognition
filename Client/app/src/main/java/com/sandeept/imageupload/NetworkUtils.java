package com.sandeept.imageupload;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class NetworkUtils {

    /*
    * Reference - https://stackoverflow.com/questions/2469451/upload-files-from-java-client-to-a-http-server
    * */

    public static int uploadBitmap(String method, String urlToUplaod, int port, String filename, byte[] bitmap) throws IOException {

        String boundary = Long.toHexString(System.currentTimeMillis());
        String CRLF = "\r\n";

        URL url = new URL(method, urlToUplaod, port, filename);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "multipart/form-data; boundary = " + boundary);

        OutputStream outputStream = connection.getOutputStream();
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);

        writer.append("--").append(boundary).append(CRLF);
        writer.append("Content-Disposition: form-data; name=\"uploadFile\"; fileName=\"").append(boundary).append("\"").append(CRLF);
        writer.append("Content-Type: image/jpeg").append(CRLF);
        writer.append("Content-Transfer-Encoding: binary").append(CRLF);
        writer.append(CRLF).flush();

        outputStream.write(bitmap);
        outputStream.flush();
        writer.append(CRLF).flush();

        writer.append("--").append(boundary).append("--").append(CRLF).flush();

        return connection.getResponseCode();
    }
}
