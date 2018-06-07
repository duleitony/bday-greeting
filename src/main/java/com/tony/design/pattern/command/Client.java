package com.tony.design.pattern.command;

public class Client {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command command = new ConcreteCommand(receiver);
        //Client run the command directly
        command.execute();

        //Client run the command via invoker
        Invoker invoker = new Invoker();
        invoker.setCommand(command);
        invoker.action();
    }
}
