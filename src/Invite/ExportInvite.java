package Invite;

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

public class ExportInvite extends main.SetingsExcel {

    protected void reportInvite(ObservableList<Invite> list)  {
        String name = "Отчёт по заявкам";

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

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));

        Row row = sheet.createRow(0);
        Cell name1 = row.createCell(0);
        name1.setCellValue(name);
        name1.setCellStyle(style);

        Row row1 = sheet.createRow(1);

        Cell idCell = row1.createCell(0);
        idCell.setCellValue("id");
        idCell.setCellStyle(style1);

        Cell mCell = row1.createCell(1);
        mCell.setCellValue("Магазин");
        mCell.setCellStyle(style1);

        Cell pCell = row1.createCell(2);
        pCell.setCellValue("Товар");
        pCell.setCellStyle(style1);

        Cell dCell = row1.createCell(3);
        dCell.setCellValue("Доставка");
        dCell.setCellStyle(style1);

        Cell colCell = row1.createCell(4);
        colCell.setCellValue("Кол-во товара");
        colCell.setCellStyle(style1);

        Cell dateCell = row1.createCell(5);
        dateCell.setCellValue("Дата");
        dateCell.setCellStyle(style1);

        Cell sumCell = row1.createCell(6);
        sumCell.setCellValue("Сумма (p.)");
        sumCell.setCellStyle(style1);

        Cell statusCell = row1.createCell(7);
        statusCell.setCellValue("Статус");
        statusCell.setCellStyle(style1);


        int rowNum = 1;
        for (Invite invite : list) {
            createSheetHeaderInvite(sheet, ++rowNum, invite, style2);
        }

        sheet.autoSizeColumn (0);
        sheet.autoSizeColumn (1);
        sheet.autoSizeColumn (2);
        sheet.autoSizeColumn (3);
        sheet.autoSizeColumn (4);
        sheet.autoSizeColumn (5);
        sheet.autoSizeColumn (6);
        sheet.autoSizeColumn (7);

        try {
            book.write(new FileOutputStream(file));
            book.close();
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createSheetHeaderInvite(XSSFSheet sheet, int rowNum, Invite invite, XSSFCellStyle style) {
            Row row = sheet.createRow(rowNum);

            Cell Cell = row.createCell(0);
            Cell.setCellValue(invite.getId());
            Cell.setCellStyle(style);

            Cell Cell1 = row.createCell(1);
            Cell1.setCellValue(invite.getMarket());
            Cell1.setCellStyle(style);

            Cell Cell2 = row.createCell(2);
            Cell2.setCellValue(invite.getTov());
            Cell2.setCellStyle(style);

            Cell Cell3 = row.createCell(3);
            Cell3.setCellValue(invite.getDelivery());
            Cell3.setCellStyle(style);

            Cell Cell4 = row.createCell(4);
            Cell4.setCellValue(invite.getCol());
            Cell4.setCellStyle(style);

            Cell Cell5 = row.createCell(5);
            Cell5.setCellValue(invite.getDate());
            Cell5.setCellStyle(style);

            Cell Cell6 = row.createCell(6);
            Cell6.setCellValue(invite.getSum());
            Cell6.setCellStyle(style);

            Cell Cell7 = row.createCell(7);
            Cell7.setCellValue(invite.getStatus());
            Cell7.setCellStyle(style);
        }

    protected void reportInviteWord(ObservableList<Invite> list) throws IOException {

        String name = "Отчёт по заявкам";

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
        tableCell1.setText("Магазин");

        XWPFTableCell tableCell2 = tableRow0.addNewTableCell();
        tableCell2.setText("Товар");

        XWPFTableCell tableCell3 = tableRow0.addNewTableCell();
        tableCell3.setText("Доставка");

        XWPFTableCell tableCell4 = tableRow0.addNewTableCell();
        tableCell4.setText("Кол-во товара");

        XWPFTableCell tableCell5 = tableRow0.addNewTableCell();
        tableCell5.setText("Дата");

        XWPFTableCell tableCell6 = tableRow0.addNewTableCell();
        tableCell6.setText("Сумма");

        XWPFTableCell tableCell7 = tableRow0.addNewTableCell();
        tableCell7.setText("Статус");

        for (Invite invite : list) {
            createWordTable(table, invite);
        }

        FileOutputStream out = null;
        out = new FileOutputStream(output);
        document.write(out);
        out.close();
        Desktop.getDesktop().open(new File(output));
    }

    protected void reportInviteWordPrint(ObservableList<Invite> list) throws IOException {

        String name = "Отчёт по заявкам";

        String currentTime;
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        currentTime = sdf.format(date);

        String output = "Word/Печать.docx";
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
        tableCell1.setText("Магазин");

        XWPFTableCell tableCell2 = tableRow0.addNewTableCell();
        tableCell2.setText("Товар");

        XWPFTableCell tableCell3 = tableRow0.addNewTableCell();
        tableCell3.setText("Доставка");

        XWPFTableCell tableCell4 = tableRow0.addNewTableCell();
        tableCell4.setText("Кол-во товара");

        XWPFTableCell tableCell5 = tableRow0.addNewTableCell();
        tableCell5.setText("Дата");

        XWPFTableCell tableCell6 = tableRow0.addNewTableCell();
        tableCell6.setText("Сумма");

        XWPFTableCell tableCell7 = tableRow0.addNewTableCell();
        tableCell7.setText("Статус");

        for (Invite invite : list) {
            createWordTable(table, invite);
        }

        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();

        Desktop.getDesktop().print(new File(output));
    }

    private static void createWordTable(XWPFTable table, Invite invite) {
        XWPFTableRow tableRow1 = table.createRow();
        tableRow1.getCell(0).setText(String.valueOf(invite.getId()));
        tableRow1.getCell(1).setText(invite.getMarket());
        tableRow1.getCell(2).setText(invite.getTov());
        tableRow1.getCell(3).setText(invite.getDelivery());
        tableRow1.getCell(4).setText(invite.getCol());
        tableRow1.getCell(5).setText(invite.getDate());
        tableRow1.getCell(6).setText(String.valueOf(invite.getSum()));
        tableRow1.getCell(7).setText(invite.getStatus());
    }

}

