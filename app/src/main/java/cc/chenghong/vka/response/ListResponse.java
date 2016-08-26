package cc.chenghong.vka.response;

import java.util.List;

/**
 * 列表返回基类
 *
 * @param <T>
 * @author hcl
 */
public class ListResponse<T> extends BaseResponse {
    public List<T> data;

    /**
     * 判断集合是否为零
     *
     * @return
     */
    public boolean isSizeZero() {
        if (data == null) {
            return true;
        } else {
            return data.size() == 0;
        }
    }
}
