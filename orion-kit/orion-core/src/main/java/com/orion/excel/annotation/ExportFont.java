package com.orion.excel.annotation;

import com.orion.utils.Strings;

import java.lang.annotation.*;

/**
 * Excel 导出字体注解
 * 可以加到 get方法 或者 字段上, 但是必须有get方法
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/5/27 12:11
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExportFont {

    /**
     * 字体名称
     */
    String fontName() default Strings.EMPTY;

    /**
     * 字体大小
     */
    int fontSize() default -1;

    /**
     * 字体颜色
     */
    String fontColor() default Strings.EMPTY;

    /**
     * 是否加粗
     */
    boolean bold() default false;

    /**
     * 是否使用斜体
     */
    boolean italic() default false;

    /**
     * 是否使用下滑线
     */
    boolean under() default false;

}
