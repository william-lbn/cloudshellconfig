package com.hyperos.cloudshellconfig.response;

import com.hyperos.cloudshellconfig.exception.UserSystemException;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

/**
 * @author： libin
 * @email： 909445583@qq.com
 * @date： 2023/3/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Data
@Tag(name="统一响应体",description = "统一响应对象描述")
public class ResponseData<T> {
    /**
     * 结果状态 ,具体状态码参见ResponseCode
     */
    @Parameter(name = "status", description = "响应状态码", required = true)
    @Schema(description = "响应状态码", example = "100")
    private int status;


    /**
     * 响应消息
     **/
    @Parameter(name = "message", description = "响应消息", required = true)
    @Schema(description = "响应消息", example = "Succeed")
    private String message;

    /**
     * 响应数据
     **/
    @Parameter(name = "data", description = "响应body体", required = true)
    @Schema(description = "响应body体", example = "[\n" +
            "        {\n" +
            "            \"env\": \"PRD\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"env\": \"PRE\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"env\": \"SIT\"\n" +
            "        }\n" +
            "    ]")
    private T data;

    /**
     * 接口请求时间
     **/
    @Parameter(name = "timestamp", description = "接口请求时间", required = true)
    @Schema(description = "接口请求时间", example = "1684306125247")
    private long timestamp;


    /**
     * 初始化，增加接口请求事件
     */
    public ResponseData() {
        this.timestamp = System.currentTimeMillis();
    }


    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> success() {
        ResponseData<T> resultData = new ResponseData<>();
        resultData.setStatus(ResponseCode.RC100.getCode());
        resultData.setMessage(ResponseCode.RC100.getMessage());
        return resultData;
    }

    /**
     * 成功
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> success(UserSystemException userSystemException) {
        ResponseData<T> resultData = new ResponseData<>();
        resultData.setStatus(userSystemException.getErrorCode());
        resultData.setMessage(userSystemException.getMessage());
        return resultData;
    }

    /**
     * 成功
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> success(String message) {
        ResponseData<T> resultData = new ResponseData<>();
        resultData.setStatus(ResponseCode.RC100.getCode());
        resultData.setMessage(message);
        return resultData;
    }

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> success(T data) {
        ResponseData<T> resultData = new ResponseData<>();
        resultData.setStatus(ResponseCode.RC100.getCode());
        resultData.setMessage(ResponseCode.RC100.getMessage());
        resultData.setData(data);
        return resultData;
    }

    /**
     * 失败
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> fail(String message) {
        ResponseData<T> resultData = new ResponseData<>();
        resultData.setStatus(ResponseCode.RC999.getCode());
        resultData.setMessage(message);
        return resultData;
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> fail(int code, String message) {
        ResponseData<T> resultData = new ResponseData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }

    /**
     * 失败
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> fail() {
        ResponseData<T> resultData = new ResponseData<>();
        resultData.setStatus(ResponseCode.RC999.getCode());
        resultData.setMessage(ResponseCode.RC999.getMessage());
        return resultData;
    }

}
