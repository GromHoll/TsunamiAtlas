package edu.atlas.dart;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Entry {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("./appContext.xml");
        DartController dartController = context.getBean(DartController.class);
        new Thread(dartController).start();
    }

}
