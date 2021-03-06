package com.base.http.exception;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;


/**
 * @description：
 * @author：bux on 2018/4/18 16:30
 * @email: 471025316@qq.com
 */
public class RxErrorHandler {


    public ApiException handlerError(Throwable e) {
        ApiException exception = new ApiException();

        if (e instanceof BaseException) {

            return (ApiException) e;

        } else if (e instanceof JsonParseException) {
            exception.setCode(ExCodeConstant.JSON_ERROR);
        } else if (e instanceof MalformedJsonException) {
            exception.setCode(ExCodeConstant.MALFORMED_JSON_EXCEPTION);
        } else if (e instanceof HttpException) {

            exception.setCode(((HttpException) e).code());
        } else if (e instanceof SocketTimeoutException) {

            exception.setCode(ExCodeConstant.SOCKET_TIMEOUT_ERROR);
        } else if (e instanceof SocketException) {
            exception.setCode(ExCodeConstant.SOCKET_ERROR);

        } else if(e instanceof UnknownHostException){
            exception.setCode(ExCodeConstant.ERROR_HTTP_HOST);
        }else {

            exception.setCode(ExCodeConstant.UNKNOWN_ERROR);

        }

        exception.setDisplayMessage(ErrorMessageFactory.create(exception.getCode()));
        return exception;
    }

    public void showErrorMessage(BaseException e) {

        //RxToast.showToast(e.getDisplayMessage());
    }
}
