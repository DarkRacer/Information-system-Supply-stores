package Product;

import javafx.collections.ObservableList;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExportProduct extends main.SetingsExcel {

    protected void reportProduct (ObservableList<Product> list)  {
        String name = "Отчёт по товарам";

        String currentTime;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
        currentTime = sdf.format(date);

        File file = new File("Excel/" + name + " от " + currentTime + ".xlsx");
        XSSFWorkbook book = new XSSFWorkbook();
        XSSFSheet sheet = book.createSheet(name);

        XSSFCellStyle style = createCellStyl(book);
        style.setFont(createFontMain(book));


        XSSFCellStyle style1 = createCellStyl(book);
        style1.setFont(createFont1(book));

        XSSFCellStyle style2 = createCellStyl(book);
        style2.setFont(createFont2(book));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));

        Row row = sheet.createRow(0);
        Cell name1 = row.createCell(0);
        name1.setCellValue(name);
        name1.setCellStyle(style);

        Row row1 = sheet.createRow(1);

        Cell idCell = row1.createCell(0);
        idCell.setCellValue("id");
        idCell.setCellStyle(style1);

        Cell mCell = row1.createCell(1);
        mCell.setCellValue("Товар");
        mCell.setCellStyle(style1);

        Cell pCell = row1.createCell(2);
        pCell.setCellValue("Кол-во товара");
        pCell.setCellStyle(style1);

        Cell dCell = row1.createCell(3);
        dCell.setCellValue("Цена за единицу (р.)");
        dCell.setCellStyle(style1);

        int rowNum = 1;
        for (Product product : list) {
            createSheetHeaderProduct(sheet, ++rowNum, product, style2);
        }

        sheet.autoSizeColumn (0);
        sheet.autoSizeColumn (1);
        sheet.autoSizeColumn (2);
        sheet.autoSizeColumn (3);

        try {
            book.write(new FileOutputStream(file));
            book.close();
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSheetHeaderProduct(XSSFSheet sheet, int rowNum, Product product, XSSFCellStyle style) {
        Row row = sheet.createRow(rowNum);

        Cell Cell = row.createCell(0);
        Cell.setCellValue(product.getId());
        Cell.setCellStyle(style);

        Cell Cell1 = row.createCell(1);
        Cell1.setCellValue(product.getName());
        Cell1.setCellStyle(style);

        Cell Cell2 = row.createCell(2);
        Cell2.setCellValue(product.getKolTov());
        Cell2.setCellStyle(style);

        Cell Cell3 = row.createCell(3);
        Cell3.setCellValue(product.getPrice());
        Cell3.setCellStyle(style);
    }

    protected void reportProductWord(ObservableList<Product> list) throws IOException {

        String name = "Отчёт по товарам";

        String currentTime;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        currentTime = sdf.format(date);

        String current;
        Date date1 = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
        current = sdf1.format(date1);

        String output = "Word/" + name + " от " + current + ".docx";
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText(name);
        titleRun.setColor("000000");
        titleRun.setBold(true);
        titleRun.setFontFamily("Times New Roman");
        titleRun.setFontSize(18);

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText("от " + currentTime);
        subTitleRun.setColor("000000");
        subTitleRun.setFontFamily("Times New Roman");
        subTitleRun.setFontSize(16);
        subTitleRun.setTextPosition(20);

        XWPFTable table = document.createTable();
        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(9500));

        XWPFTableRow tableRow0 = table.getRow(0);
        tableRow0.getCell(0).setText("id");

        XWPFTableCell tableCell1 = tableRow0.addNewTableCell();
        tableCell1.setText("Товар");

        XWPFTableCell tableCell2 = tableRow0.addNewTableCell();
        tableCell2.setText("Кол-во товара");

        XWPFTableCell tableCell3 = tableRow0.addNewTableCell();
        tableCell3.setText("Цена за единицу (р.)");

        for (Product product : list) {
            createWordTable(table, product);
        }

        FileOutputStream out = null;
        out = new FileOutputStream(output);
        document.write(out);
        out.close();
        Desktop.getDesktop().open(new File(output));
    }

    protected void reportProductWordPrint(ObservableList<Product> list) throws IOException {

        String name = "Отчёт по товарам";

        String currentTime;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        currentTime = sdf.format(date);

        String current;
        Date date1 = new Date();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_MM_dd_hh_mm");
        current = sdf1.format(date1);

        String output = "Word/" + name + " от " + current + ".docx";
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText(name);
        titleRun.setColor("000000");
        titleRun.setBold(true);
        titleRun.setFontFamily("Times New Roman");
        titleRun.setFontSize(18);

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText("от " + currentTime);
        subTitleRun.setColor("000000");
        subTitleRun.setFontFamily("Times New Roman");
        subTitleRun.setFontSize(16);
        subTitleRun.setTextPosition(20);

        XWPFTable table = document.createTable();
        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(9500));

        XWPFTableRow tableRow0 = table.getRow(0);
        tableRow0.getCell(0).setText("id");

        XWPFTableCell tableCell1 = tableRow0.addNewTableCell();
        tableCell1.setText("Товар");

        XWPFTableCell tableCell2 = tableRow0.addNewTableCell();
        tableCell2.setText("Кол-во товара");

        XWPFTableCell tableCell3 = tableRow0.addNewTableCell();
        tableCell3.setText("Цена за единицу (р.)");

        for (Product product : list) {
            createWordTable(table, product);
        }

        FileOutputStream out = null;
        out = new FileOutputStream(output);
        document.write(out);
        out.close();

        Desktop.getDesktop().print(new File(output));
    }

    private static void createWordTable(XWPFTable table, Product product) {
        XWPFTableRow tableRow1 = table.createRow();
        tableRow1.getCell(0).setText(String.valueOf(product.getId()));
        tableRow1.getCell(1).setText(product.getName());
        tableRow1.getCell(2).setText(product.getKolTov());
        tableRow1.getCell(3).setText(String.valueOf(product.getPrice()));
    }
}

