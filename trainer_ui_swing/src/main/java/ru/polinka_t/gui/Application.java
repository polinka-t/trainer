package main.java.ru.polinka_t.gui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import main.java.ru.polinka_t.gui.config.SpringConfig;
import main.java.ru.polinka_t.gui.controller.MainController;


public class Application
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        MainController controller = context.getBean(MainController.class);
        controller.interactWithUser();
    }
}
