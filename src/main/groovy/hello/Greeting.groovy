package hello;

import org.springframework.stereotype.*
import org.springframework.beans.factory.annotation.*

//@Component
public class Greeting {

    private final long id;
    private final String content;

//    @Value("${testme}")
//    private String testme

//    public Greeting() {
//
//    }

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    //accessor methods seem to be required in order to support Jackson mapping
    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

}