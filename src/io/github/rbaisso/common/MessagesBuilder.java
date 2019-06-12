package io.github.rbaisso.common;

import java.util.ArrayList;
import java.util.List;

public class MessagesBuilder {

    public static List<Message> getSorted(){
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(0,"ola", 4));
        messages.add(new Message(1,"sou", 4));
        messages.add(new Message(2,"o", 4));
        messages.add(new Message(3,"client", 4));

        return messages;
    }

    public static List<Message> getMissPosition(){
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(0,"ola", 4));
        messages.add(new Message(1,"sou", 4));
        messages.add(new Message(3,"client", 4));

        return messages;
    }

    public static List<Message> getUnsorted(){
        List<Message> messages = new ArrayList<>();
        messages.add(new Message(1,"sou", 4));
        messages.add(new Message(3,"client", 4));
        messages.add(new Message(0,"ola", 4));
        messages.add(new Message(2,"o", 4));

        return messages;
    }
}
