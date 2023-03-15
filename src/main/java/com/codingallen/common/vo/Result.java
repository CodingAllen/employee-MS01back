package com.codingallen.common.vo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    //インタフェースのコード
    private Integer code;
    //表示したいメッセージ
    private String message;
    //ジェネリック型のT
    //てデータを返す
    private T data;
//データを返すことができれば、successメソッドを返す
    public static <T> Result<T> success(){
        return new Result<>(20000,"success",null);
    }
    //データの伝送の判断
    public static <T> Result<T> success(T data){
        return new Result<>(20000,"success",data);
    }
    //メッセージの伝送とデータの伝送の判断
    public static <T> Result<T> success(T data, String message){
        return new Result<>(20000,message,data);
    }
    //メッセージだけの判断
    public static <T> Result<T> success( String message){
        return new Result<>(20000,message,null);
    }
    //登録失敗の判断

    public static<T>  Result<T> fail(){
        return new Result<>(20001,"fail",null);
    }

    public static<T>  Result<T> fail(Integer code){
        return new Result<>(code,"fail",null);
    }

    public static<T>  Result<T> fail(Integer code, String message){
        return new Result<>(code,message,null);
    }

    public static<T>  Result<T> fail( String message){
        return new Result<>(20001,message,null);
    }


}
