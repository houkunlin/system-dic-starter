package com.houkunlin.system.dict.starter;

import com.github.benmanes.caffeine.cache.Cache;
import com.houkunlin.system.dict.starter.bean.DictTypeVo;
import com.houkunlin.system.dict.starter.bean.DictValueVo;
import com.houkunlin.system.dict.starter.cache.DictCacheFactory;
import com.houkunlin.system.dict.starter.store.DictStore;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 系统字典工具
 *
 * @author HouKunLin
 */
@SuppressWarnings("all")
@Component
public class DictUtil {
    public static final String TYPE_PREFIX = "dict:t:";
    public static final String VALUE_PREFIX = "dict:v:";

    private static DictStore store;
    /**
     * 字典值缓存
     */
    private static Cache<String, String> cache;
    private static Cache<String, AtomicInteger> missCache;
    private static int missNum = Integer.MAX_VALUE;

    public DictUtil(final DictStore store, final DictCacheFactory cacheFactory) {
        DictUtil.store = store;
        cache = cacheFactory.build();
        missCache = cacheFactory.build();
        missNum = cacheFactory.getDictProperties().getCache().getMissNum();
    }

    public static DictTypeVo getDictType(String type) {
        if (type == null || store == null) {
            return null;
        }
        return store.getDictType(type);
    }

    public static String getDictText(String type, String value) {
        if (type == null || value == null || store == null) {
            return null;
        }
        if (cache == null || missCache == null) {
            return store.getDictText(type, value);
        }
        final String dictKey = dictKey(type, value);
        final String result = cache.getIfPresent(dictKey);
        if (result != null) {
            return result;
        }
        final AtomicInteger integer = missCache.get(dictKey, s -> new AtomicInteger(1));
        if (integer.get() > missNum) {
            return null;
        }

        final String dictText = store.getDictText(type, value);
        if (dictText == null) {
            // 未命中数据
            integer.incrementAndGet();
        } else {
            cache.put(dictKey, dictText);
        }
        return dictText;
    }

    public static String dictKey(String type) {
        return TYPE_PREFIX + type;
    }

    public static String dictKey(DictValueVo value) {
        return VALUE_PREFIX + value.getDictType() + ":" + value.getValue();
    }

    public static String dictKey(String type, Object value) {
        return VALUE_PREFIX + type + ":" + value;
    }
}
