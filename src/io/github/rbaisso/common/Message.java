package io.github.rbaisso.common;

import java.util.Objects;

public class Message {
    private Integer id;
    private String value;
    private Integer packetSize;

    public Message(Integer id, String value, Integer packetSize) {
        this.id = id;
        this.value = value;
        this.packetSize = packetSize;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getPacketSize() {
        return packetSize;
    }

    public void setPacketSize(Integer packetSize) {
        this.packetSize = packetSize;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", value='" + value + '\'' +
                ", packetSize=" + packetSize;
    }

    public byte[] getBytes() {
        return this.toString().getBytes();
    }

    public Message(byte[] object) {
        String stringObject = new String(object);
        stringObject = stringObject.trim();
        String[] split = stringObject.split("=|,");

        this.id = Integer.valueOf(split[1]);
        this.value = split[3];
        this.packetSize = Integer.valueOf(split[5]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

}
