package houyaa.demo;

import static houyaa.HouYaa.*;

public class Demo {

    public static void main(String[] args) throws Exception {
        get("/hello", (request)->{
            request.text("world");
        });
        get("/ping", (request)->{
            request.json("{'success':true,'data':'pong'}");
        });
        post("/submit", (request)-> {
            request.json("{'success':true,'data':''}");
        });
        start();
    }
}
