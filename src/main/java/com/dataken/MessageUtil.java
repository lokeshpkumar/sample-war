package com.dataken;

public class MessageUtil {

    @Log(msg="blah", args={String.class, Integer.class})
    public String buildMessage(String prefix) {
        String name = Thread.currentThread().getName();
        System.out.println(name + "------------ Building the message --------------");
        Object jj = doSomething();
        System.out.println("This is jj: " + jj);
        return prefix + " This is a message";
    }

    @Log(msg="doingSomething", args={String.class, Integer.class})
    public Object doSomething() {
        System.out.println("Doing something ...");
        return "blah";
    }

}
