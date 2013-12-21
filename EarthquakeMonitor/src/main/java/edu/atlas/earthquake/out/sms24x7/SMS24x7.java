package edu.atlas.earthquake.out.sms24x7;

import java.io.IOException;

public interface SMS24x7 {

    String send(String email, String password, String from, String to, String text) throws IOException;

    String login(String email, String password) throws IOException;

    String send(String from, String to, String text) throws IOException;

    String logout() throws IOException;
}