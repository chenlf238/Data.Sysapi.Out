package data.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import witparking.core.entitys.ReturnMessage;
import witparking.core.exceptions.BusinessException;
import witparking.core.exceptions.ExceptionCodeConstants;
import witparking.core.exceptions.ExceptionConstants;

@ControllerAdvice
public class WebExceptionHandel {
    private static final Logger logger = LoggerFactory.getLogger(WebExceptionHandel.class);

    /**
     * 自定义业务异常统一返回前端
     *
     * @param be
     * @return String  ReturnMessage
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public String handleBusinessException(BusinessException be) {
        //日志打印
        logger.error("[WEH] handleBusinessException 异常 =" + be.getMessage());
        //如果是自定义异常，转换信息回传到前端
        return new ReturnMessage(false, ExceptionConstants.MSG_BUSINESS_EXCEPTION + be.getMessage(), be.getBusinessCode() == null ? ExceptionCodeConstants.ERROR : be.getBusinessCode(), "").toJsonString();
    }


    /**
     * 通用异常统一返回前端
     *
     * @param e
     * @return String  ReturnMessage
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String handleException(Exception e) {
        //日志打印
        logger.error("[WEH] handleException 异常 =" + e.getMessage(), e);
        //如果是非包装异常，则包装成统一的内部异常返回
        return new ReturnMessage(false, ExceptionConstants.MSG_SYSTEM_EXCEPTION, ExceptionCodeConstants.ERROR, "").toJsonString();
    }

}
