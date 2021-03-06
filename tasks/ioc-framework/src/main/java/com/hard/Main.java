package com.hard;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        AppContext appContext = new AppContext();

        Initializer initializer = new Initializer(appContext);
        initializer.initContext();

        Dao dao = (Dao) appContext.getBean("dao");

        dao.getAll();
    }
}

/**
 * Annotations
 */

@Retention(RetentionPolicy.RUNTIME)
@interface Bean {
    String value() default "";
}

@Retention(RetentionPolicy.RUNTIME)
@interface Autowired {
    String value() default "";
}

/**
 * Entity
 */

@Bean("dao")
class Dao {
    public void getAll() {
        System.out.println("getAll");
    }
}

@Bean("service")
class Service {
    @Autowired("dao")
    private Dao dao;

    public void getAll() {
        dao.getAll();
    }
}

@Bean("controller")
class Controller {
    @Autowired("service")
    private Service service;

    public void getAll() {
        service.getAll();
    }
}

/**
 * Initial Context
 */

class InitialContext {
    public Object getService(String id) {
        switch (id) {
            case "dao":
                return new Dao();
            case "service":
                return new Service();
            case "controller":
                return new Controller();
        }

        return null;
    }
}

/**
 * Locator
 */

class AppContext {
    private InitialContext initialContext = new InitialContext();
    private Map<String, Object> container = new HashMap<>();

    public Object getBean(String id) {
        Object object = container.get(id);

        if (object == null) {
            object = initialContext.getService(id);
            container.put(id, object);
        }

        return object;
    }
}

/**
 * Initializer
 */

class Initializer {
    private AppContext appContext;

    public Initializer(AppContext appContext) {
        this.appContext = appContext;
    }

    public void initContext() {
        Collection<Class<?>> classes = getClasses();

        for (Class<?> clazz : classes)
            inspectClass(clazz);
    }

    private Collection<Class<?>> getClasses() {
        Collection<Class<?>> classes = new ArrayList<>();

        classes.add(Dao.class);
        classes.add(Service.class);
        classes.add(Controller.class);

        return classes;
    }

    private void inspectClass(Class<?> clazz) {
        Annotation[] annotations = clazz.getAnnotations();

        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Bean.class)) {
                String value = getValueBean(clazz, Bean.class);
                appContext.getBean(value);
            }
        }
    }

    private String getValueBean(Class<?> clazz, Class<? extends Bean> beanClass) {
        Bean bean = clazz.getAnnotation(beanClass);
        String value = bean.value();
        return value;
    }
}
