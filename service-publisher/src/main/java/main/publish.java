package main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.IAdminOpService;

import java.io.IOException;

public class publish {
    public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("publisher-appContext.xml");
        context.start();
        System.out.println("服务已启动");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
