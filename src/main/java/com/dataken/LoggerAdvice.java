package com.dataken;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;

import javax.websocket.Session;
import java.lang.annotation.Annotation;
import java.util.Arrays;

@Aspect
public class LoggerAdvice {
    //    @Before("execution(* *.*(..)) && @annotation(testAnnotation) ")
    @Before("execution(* *.*(..)) && @annotation(log)")
    public void myBeforeLogger(JoinPoint joinPoint, Log log) {

        String name = Thread.currentThread().getName();
        System.out.println("Okay - we're in the before handler... thread name: " + name);
        System.out.println("The test annotation value is: ");

        Signature signature = joinPoint.getSignature();
        String methodName = signature.getName();
        String stuff = signature.toString();
        String arguments = Arrays.toString(joinPoint.getArgs());
        System.out.println("Write something in the log... We are just about to call method: "
                + methodName + " with arguments " + arguments + "\nand the full toString: "
                + stuff);

    }

    @Around("execution(* *.doSomething())  && @annotation(log)")
    public Object aroundObject(ProceedingJoinPoint pjp, Log log) {
        System.out.println("What ami doing here ...");
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Log logAnnotation = methodSignature.getMethod().getAnnotation(Log.class);
        System.out.println("Annotation: " + Arrays.toString(logAnnotation.args()));
        return "WHAT IS THIS";
    }

}
