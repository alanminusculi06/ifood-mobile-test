package br.com.minusculi.ifoodmobiletest.data.remote.google.objects;

public class Document {

    public Document(String content) {
        this.type = "PLAIN_TEXT";
        this.content = content;
    }

    public String type;
    public String content;

}
