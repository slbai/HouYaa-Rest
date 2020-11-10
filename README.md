HouYaa-Rest
--
tiny rest api framework base on netty, aim to describe the most important parts in a rest framework

Features
--
1. Tiny, only dependence on netty
2. easy to use, explode simple api
3. easy to understand for those who are not familiar with how web framework work

Disadvantage
--
1. not much exception handling logic
2. not support other http method

Demo
--
```java
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

```

Test
--
```shell script
curl -XGET   http://localhost:8888/hello
curl -XPOST  http://localhost:8888/submit
```