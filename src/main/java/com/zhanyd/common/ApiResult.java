package com.zhanyd.common;

public class ApiResult<T> {

	private int code;
	private String message;
	private T data;
	
	public ApiResult(){
		
	}
	
	public ApiResult(int code,String message,T data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public ApiResult<T> success(T data){
		ApiResult<T> apiResult = new ApiResult(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getDesc(),data);
		return apiResult;
	}
	
	public ApiResult<T> fail(int code,String message){
		ApiResult<T> apiResult = new ApiResult(code,message,null);
		return apiResult;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
