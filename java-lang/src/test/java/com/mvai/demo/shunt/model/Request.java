package com.mvai.demo.shunt.model;

public class Request {


    public String uri;
    public String uuid;
    public String param;




    public static class Builder{

        public String uuid;
        public String param;
        public String uri;


        public Builder setUuid(String uuid){
            this.uuid = uuid;
            return this;
        }

        public Builder setParam(String param){
            this.param = param;
            return this;
        }
        public Builder setUri(String uri){
            this.uri = uri;
            return this;
        }
        public Request build(){
            Request r = new Request();
            r.uuid = this.uuid;
            r.param = this.param;
            return r;
        }
    }

}
