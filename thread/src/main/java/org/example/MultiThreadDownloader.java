package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class MultiThreadDownloader {
    public static void main(String[] args) {

    }
}

class WebPageDownloader implements Runnable{


    private String url;

    public WebPageDownloader(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        System.out.println("Börja hämta " + url );
        try {
            URL website = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(website.openStream()));

            String inputLine;

            while ((inputLine = in.readLine())!=null){
                System.out.println(url + " ==> " + inputLine.substring(0,Math .min(50)));
            }

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
