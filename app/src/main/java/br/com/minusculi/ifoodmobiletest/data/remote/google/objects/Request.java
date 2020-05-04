package br.com.minusculi.ifoodmobiletest.data.remote.google.objects;

public class Request {

    public Request(Document document) {
        this.encodingType = "UTF8";
        this.document = document;
    }

    public Document document;
    public String encodingType;

}

