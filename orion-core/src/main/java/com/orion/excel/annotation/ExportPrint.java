package com.orion.excel.annotation;

import com.orion.excel.type.ExcelPaperType;

import java.lang.annotation.*;

/**
 * Excel 导出打印配置
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/12/31 10:35
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExportPrint {

    /**
     * 是否打印网格线
     */
    boolean printGridLines() default true;

    /**
     * 是否打印行标题和列标题
     */
    boolean printHeading() default false;

    /**
     * 打印自适应
     */
    boolean fit() default true;

    /**
     * 每页行数
     */
    int limit() default -1;

    /**
     * 重复打印的行和列
     * 0 rowStartIndex
     * 1 rowEndIndex
     * 2 columnStartIndex
     * 3 columnEndIndex
     * [1, 3] = [0, 1, 0, 3]
     */
    int[] repeat() default {};

    /**
     * 纸张大小
     */
    ExcelPaperType paper() default ExcelPaperType.DEFAULT;

    /**
     * 是否是彩色
     */
    boolean color() default false;

    /**
     * 是否横向打印
     */
    boolean landScapePrint() default false;

    /**
     * 是否设置打印方向
     */
    boolean setPrintOrientation() default false;

    /**
     * 缩放比例  10 - 400
     */
    int scale() default 100;

    /**
     * 是否打印批注
     */
    boolean notes() default false;

    /**
     * 水平分辨率
     */
    int horizontalResolution() default -1;

    /**
     * 垂直分辨率
     */
    int verticalResolution() default -1;

    /**
     * 宽
     */
    int width() default -1;

    /**
     * 高
     */
    int height() default -1;

    /**
     * 页眉边距
     */
    int headerMargin() default -1;

    /**
     * 页脚边距
     */
    int footerMargin() default -1;

    /**
     * 是否使用起始页
     */
    boolean usePage() default true;

    /**
     * 起始页码
     */
    int pageStart() default 1;

    /**
     * 打印份数
     */
    int copies() default 1;

    /**
     * 是否为草稿模式
     */
    boolean draft() default false;

    /**
     * 是否自上而下
     */
    boolean topToBottom() default true;

    /**
     * 页面是否水平居中
     */
    boolean horizontallyCenter() default false;

    /**
     * 页面是否垂直居中
     */
    boolean verticallyCenter() default false;

}
