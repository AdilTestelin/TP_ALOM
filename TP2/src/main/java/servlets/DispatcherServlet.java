package servlets;

import annotations.RequestMapping;
import controllers.HelloController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/*", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {

    private Map<String, Method> uriMappings = new HashMap<>();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("Getting request for " + req.getRequestURI());
        // TODO
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // on enregistre notre controller au démarrage de la servlet
        this.registerController(HelloController.class);
    }

    /**
     * This methods checks the following rules :
     * - The controllerClass is annotated with @Controller
     * Then all methods are scanned and processed by the registerMethod method
     * @param controllerClass the controller to scan
     */
    /**
     * This methods checks the following rules :
     * - The controllerClass is annotated with @Controller
     * Then all methods are scanned and processed by the registerMethod method
     * @param controllerClass the controller to scan
     */
    public void registerController(Class controllerClass) throws IllegalArgumentException{
        System.out.println("Analysing class " + controllerClass.getName());
        Annotation[] annotations = controllerClass.getDeclaredAnnotations();
        boolean isAnnotedWithController = false;
        for(Annotation annotation : annotations) {
            if (annotation.toString().equals("@annotations.Controller()")) {
                isAnnotedWithController = true;
            } else {
                throw new IllegalArgumentException();
            }
        }
        if (isAnnotedWithController) {
            Method[] methods = controllerClass.getDeclaredMethods();
            for (Method method : methods) {
                registerMethod(method);
            }
        }
    }

    /**
     * This methods checks the following rules :
     * - The method is annotated with @RequestMapping
     * - The @RequestMapping annotation has a URI
     * - The method does not return void
     * If these rules are followed, the method and its URI are added to the uriMapping map.
     * @param method the method to scan
     */
    public void registerMethod(Method method) {
        System.out.println("Registering method " + method.getName());
        Annotation[] declaredAnnotation = method.getDeclaredAnnotations();
        for (Annotation annotation : declaredAnnotation) {
            String[] strArray = annotation.toString().split("\\(");
            if (strArray[0].toString().equals("@annotations.RequestMapping")) {
                if(method.getAnnotation(RequestMapping.class).uri() != null && !method.getReturnType().equals(Void.TYPE)) {
                    this.getMappings().put(method.getAnnotation(RequestMapping.class).uri(), method);
                }
            }
        }
    }

    public Map<String, Method> getMappings(){
        return this.uriMappings;
    }

    public Method getMappingForUri(String uri){
        return this.uriMappings.get(uri);
    }


}
