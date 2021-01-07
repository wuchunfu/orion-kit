package com.orion.excel.type;

import com.orion.utils.Strings;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel 图片类型
 *
 * @author ljh15
 * @version 1.0.0
 * @since 2020/12/27 22:50
 */
public enum ExcelPictureType {

    /**
     * 自动
     */
    AUTO(0, 0, ""),

    /**
     * EMF Windows
     */
    EMF(Workbook.PICTURE_TYPE_EMF, XSSFWorkbook.PICTURE_TYPE_EMF, "emf"),

    /**
     * WMF Windows
     */
    WMF(Workbook.PICTURE_TYPE_WMF, XSSFWorkbook.PICTURE_TYPE_WMF, "wmf"),

    /**
     * PICT Mac
     */
    PICT(Workbook.PICTURE_TYPE_PICT, XSSFWorkbook.PICTURE_TYPE_PICT, "pict"),

    /**
     * JPG
     */
    JPG(Workbook.PICTURE_TYPE_JPEG, XSSFWorkbook.PICTURE_TYPE_JPEG, "jpg"),

    /**
     * JPG
     */
    JPEG(Workbook.PICTURE_TYPE_JPEG, XSSFWorkbook.PICTURE_TYPE_JPEG, "jpeg"),

    /**
     * PNG
     */
    PNG(Workbook.PICTURE_TYPE_PNG, XSSFWorkbook.PICTURE_TYPE_PNG, "png"),

    /**
     * BIT_MAT
     */
    BIT_MAT(Workbook.PICTURE_TYPE_DIB, XSSFWorkbook.PICTURE_TYPE_DIB, "bid"),

    /**
     * GIF
     */
    GIF(-1, XSSFWorkbook.PICTURE_TYPE_GIF, "gif"),

    /**
     * TIFF
     */
    TIFF(-1, XSSFWorkbook.PICTURE_TYPE_TIFF, "tiff"),

    /**
     * EPS
     */
    EPS(-1, XSSFWorkbook.PICTURE_TYPE_EPS, "eps"),

    /**
     * BMP Windows
     */
    BMP(-1, XSSFWorkbook.PICTURE_TYPE_BMP, "bmp"),

    /**
     * WPG
     */
    WPG(-1, XSSFWorkbook.PICTURE_TYPE_WPG, "wpg");

    ExcelPictureType(int type1, int type2, String suffix) {
        this.type1 = type1;
        this.type2 = type2;
        this.suffix = suffix;
    }

    private int type1;

    private int type2;

    private String suffix;

    public int getType1() {
        return type1;
    }

    public int getType2() {
        return type2;
    }

    public String getSuffix() {
        return suffix;
    }

    /**
     * 获取后缀类型
     *
     * @param suffix suffix
     * @return ExcelPictureType
     */
    public static ExcelPictureType of(String suffix) {
        if (Strings.isEmpty(suffix)) {
            return PNG;
        }
        for (ExcelPictureType value : ExcelPictureType.values()) {
            if (value.getSuffix().equals(suffix)) {
                return value;
            }
        }
        return PNG;
    }

}
