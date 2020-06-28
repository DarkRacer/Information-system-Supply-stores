package main;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class SetingsExcel
{
    protected static XSSFCellStyle createCellStyl(XSSFWorkbook book)
    {
        BorderStyle thin  = BorderStyle.THIN;
        short       black = IndexedColors.BLACK.getIndex();

        XSSFCellStyle style = book.createCellStyle();

        style.setWrapText(true);
        style.setAlignment        (HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment  .CENTER);

        style.setBorderTop        (thin);
        style.setBorderBottom     (thin);
        style.setBorderRight      (thin);
        style.setBorderLeft       (thin);

        style.setTopBorderColor   (black);
        style.setRightBorderColor (black);
        style.setBottomBorderColor(black);
        style.setLeftBorderColor  (black);

        return style;
    }

    protected static XSSFFont createFontMain (XSSFWorkbook book)
    {
        XSSFFont font = book.createFont();
        font.setFontHeightInPoints((short) 17);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("Times New Roman");

        return(font);
    }

    protected static XSSFFont createFont1 (XSSFWorkbook book)
    {
        XSSFFont font = book.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("Times New Roman");

        return(font);
    }

    protected static XSSFFont createFont2 (XSSFWorkbook book)
    {
        XSSFFont font = book.createFont();
        font.setFontHeightInPoints((short) 14);
        font.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        font.setFontName("Times New Roman");

        return(font);
    }
   }
