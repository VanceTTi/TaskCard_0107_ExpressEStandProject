package com.fjt.bean;

/**
 *专门用来发消息的一个类，供给给前后端交互的json
 */
public class Message {

    private int status;//状态码：0表示成功，-1表示失败

    //会给用户给出消息的内容
    private String result;//消息内容
    private Object data;//消息所携带的一组数据
    //↓↓↓↓↓↓↓这两条会转换成json↓↓↓↓↓↓↓↓↓
    /*
    {
        status:0,
        result:"",
        data:{
        }
    }
     */

    public Message() {
    }

    public Message(int status, String result) {
        this.status = status;
        this.result = result;
    }

    public Message(String result) {
        this.result = result;
    }

    /**
     * 没有回复数据的构造方法，只有状态码
     * @param status
     */
    public Message(int status) {
        this.status = status;
    }

    public Message(int status, String result, Object data) {
        this.status = status;
        this.result = result;
        this.data = data;
    }

    //get/set
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status=" + status +
                ", result='" + result + '\'' +
                ", data=" + data +
                '}';
    }
}
