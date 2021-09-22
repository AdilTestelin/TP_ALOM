package controllers;

import annotations.Controller;
import annotations.RequestMapping;
import servlets.DispatcherServlet;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Controller
public class HelloController {

    @RequestMapping(uri="/hello")
    public String sayHello(){
        return "Hello World !";
    }

    @RequestMapping(uri="/bye")
    public String sayGoodBye(){
        return "Goodbye !";
    }

    @RequestMapping(uri="/boum")
    public String explode(){
        throw new RuntimeException("Explosion !");
    }

    /** public static void registerController(Class controllerClass) throws IllegalArgumentException{
        System.out.println("Analysing class " + controllerClass.getName());
        Annotation[] annotations = controllerClass.getDeclaredAnnotations();
        boolean isAnnotedWithController = false;
        for(Annotation annotation : annotations) {
            if (annotation.equals(null) || !annotation.toString().equals("@annotations.Controller()")) {
                System.out.println(annotation.toString());
                throw new IllegalArgumentException();
            } else {
                isAnnotedWithController = true;
            }
        }
        if (isAnnotedWithController) {
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method : methods) {
                // registerMethod(method);
            }
        }
    }

    public void registerMethod(Method method) {
        System.out.println("Registering method " + method.getName());
        Annotation[] declaredAnnotation = method.getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotation) {
            String[] strArray = annotation.toString().split("\\(");
            if (strArray[0].toString().equals("@annotations.RequestMapping")) {
                if(method.getAnnotation(RequestMapping.class).uri() != null && !method.getReturnType().equals(Void.TYPE)) {
                    // this.getMappings().put(method.getAnnotation(RequestMapping.class).uri(), method);
                }
            }
        }
    }

    public static void main(String[] args) {
        registerController(String.class);
    } **/
}