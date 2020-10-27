package com.epf.RentManager.exception;

public class ServiceException extends Exception{
    public ServiceException(){
        super();
    }
    public ServiceException(String msg){
        super(msg);
    }
}
