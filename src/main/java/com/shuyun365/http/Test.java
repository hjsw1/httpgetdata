package com.shuyun365.http;

import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 * Created by sw on 2018/3/12
 */
public class Test {
    public static final String TARGET_HTTPS_SERVER = "www.alphavantage.co";
    public static final int    TARGET_HTTPS_PORT   = 443;

    public static void main(String[] args) throws Exception {

        Socket socket = SSLSocketFactory.getDefault().
                createSocket(TARGET_HTTPS_SERVER, TARGET_HTTPS_PORT);
        try {
            Writer out = new OutputStreamWriter(
                    socket.getOutputStream(), "ISO-8859-1");
            out.write("GET / HTTP/1.1\r\n");
            out.write("Host: " + TARGET_HTTPS_SERVER + ":" +
                    TARGET_HTTPS_PORT + "\r\n");
            out.write("Agent: SSL-TEST\r\n");
            out.write("\r\n");
            out.flush();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "ISO-8859-1"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        } finally {
            socket.close();
        }
    }

}
