package univer.oko;

import java.io.Serializable;

public class Message implements Serializable {
    public static final String ERROR = "error";
    public static final String WARNING = "warning";
    public static final String INFORMATION = "info";
    public static final String SUCCESSFUL = "success";

    private String text;
    private String type;
    private Object tag;

    private Message(String text, String type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getType() {
        return type;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public static Message createErrorMessage(String message) {
        return new Message(message, Message.ERROR);
    }

    public static Message createWarningMessage(String message) {
        return new Message(message, Message.WARNING);
    }

    public static Message createInfoMessage(String message) {
        return new Message(message, Message.INFORMATION);
    }

    public static Message createSuccessMessage(String message) {
        return new Message(message, Message.SUCCESSFUL);
    }

    public boolean equals(Object o) {
        if (this == o) return true;

        /**
         * Чтобы сравнивать по Message Со строкой
         */
        if ((o instanceof String) && (o.equals(type))) {
            return true;
        }


        if (!(o instanceof Message)) return false;

        final Message message = (Message) o;

        return !(type != null ? !type.equals(message.type) : message.type != null);

    }

    public int hashCode() {
        return (type != null ? type.hashCode() : 0);
    }
}
