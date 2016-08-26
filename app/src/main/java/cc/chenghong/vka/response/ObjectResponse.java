package cc.chenghong.vka.response;

import java.util.List;
/**
 * 对象响应基类
 * @author hcl
 *
 * @param <T>
 */
public class ObjectResponse<T> extends BaseResponse {
	public T data;
}
