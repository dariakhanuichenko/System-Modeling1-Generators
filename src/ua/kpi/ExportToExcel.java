package ua.kpi;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ExportToExcel {

    private static final String SHEET_NAME = "GENERATOR";
    private static final String CHART_TITLE = "Гістограма частот  Генератор ";

    public static void exportReportByLoadGeneralToExcel(Map<List<Double>, Integer> intervals, int numberGenerator) throws IOException {
        int currentRow = 0;

        List<List<Double>> keys = new ArrayList<>(intervals.keySet());
        List<Integer> values = new ArrayList<>(intervals.values());

        try (XSSFWorkbook wb = new XSSFWorkbook(); ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet(SHEET_NAME);
            Row row;
            CellStyle cellStyle = wb.createCellStyle();
            cellStyle.setBorderTop(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            sheet.setDefaultColumnWidth(20);
            sheet.setDisplayGridlines(true);

            //generate title
            sheet.createRow(currentRow++);

            sheet.createRow(currentRow++);

            //main table
            for (int i = 0; i < intervals.size(); i++) {
                row = sheet.createRow(currentRow++);
                row.createCell(0).setCellValue(Math.ceil(keys.get(i).get(0) * 100) / 100 + " - " + Math.ceil(keys.get(i).get(1) * 100) / 100);
                row.createCell(1).setCellValue(values.get(i));

                setRowStyle(row, cellStyle);
            }

            //generate header
            List<String> header =
                    Arrays.asList("Інтервал", "Частота");

            generateHeader(wb, 2, 0, header);
            sheet.createRow(currentRow++);

            //generate chart
            XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();
            //set chart position
            ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 3, 3, 9, 20);

            XSSFChart chart = drawing.createChart(anchor);
            chart.setTitleText(CHART_TITLE + numberGenerator);

            CTChart ctChart = chart.getCTChart();
            CTPlotArea ctPlotArea = ctChart.getPlotArea();
            CTBarChart ctBarChart = ctPlotArea.addNewBarChart();
            CTBoolean ctBoolean = ctBarChart.addNewVaryColors();
            ctBoolean.setVal(true);
            ctBarChart.addNewBarDir().setVal(STBarDir.COL);

            CTBarSer ctBarSer = ctBarChart.addNewSer();
            CTSerTx ctSerTx = ctBarSer.addNewTx();
            CTStrRef ctStrRef = ctSerTx.addNewStrRef();
            // добавить одну серию
            ctBarSer.addNewIdx().setVal(5);
            CTAxDataSource cttAxDataSource = ctBarSer.addNewCat();
            ctStrRef = cttAxDataSource.addNewStrRef();
            // установить  градацию по х (отлично, хорошо, удовлетворительно ..)
            ctStrRef.setF(SHEET_NAME + "!$A$3:$A$" + (2 + intervals.size()));
            CTNumDataSource ctNumDataSource = ctBarSer.addNewVal();
            CTNumRef ctNumRef = ctNumDataSource.addNewNumRef();
            // установить значения
            ctNumRef.setF(SHEET_NAME + "!$B$3" + ":$B$" + (2 + intervals.size()));

            ctBarSer.addNewSpPr().addNewLn().addNewSolidFill().addNewSrgbClr().setVal(new byte[]{0, 0, 0});


            setPlotProperties(ctPlotArea, ctBarChart, ctChart);

            chart.deleteLegend();

            try (FileOutputStream outputStream = new FileOutputStream(CHART_TITLE + "_" + numberGenerator + ".xlsx")) {
                wb.write(outputStream);
            }

        }
    }


    private static void setPlotProperties(CTPlotArea ctPlotArea, CTBarChart ctBarChart, CTChart ctChart) {

        //telling the BarChart that it has axes and giving them Ids
        ctBarChart.addNewAxId().setVal(123456);
        ctBarChart.addNewAxId().setVal(123457);

        //cat axis
        CTCatAx ctCatAx = ctPlotArea.addNewCatAx();
        ctCatAx.addNewAxId().setVal(123456); //id of the cat axis
        CTScaling ctScaling = ctCatAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctCatAx.addNewDelete().setVal(false);
        ctCatAx.addNewAxPos().setVal(STAxPos.B);
        ctCatAx.addNewCrossAx().setVal(123457); //id of the val axis
        ctCatAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);

        //val axis
        CTValAx ctValAx = ctPlotArea.addNewValAx();
        ctValAx.addNewAxId().setVal(123457); //id of the val axis
        ctScaling = ctValAx.addNewScaling();
        ctScaling.addNewOrientation().setVal(STOrientation.MIN_MAX);
        ctValAx.addNewDelete().setVal(false);
        ctValAx.addNewAxPos().setVal(STAxPos.L);
        ctValAx.addNewCrossAx().setVal(123456); //id of the cat axis
        ctValAx.addNewTickLblPos().setVal(STTickLblPos.NEXT_TO);

        //legend
        CTLegend ctLegend = ctChart.addNewLegend();
        ctLegend.addNewLegendPos().setVal(STLegendPos.B);
        ctLegend.addNewOverlay().setVal(false);

    }

    private static void setRowStyle(Row row, CellStyle cellStyle) {
        row.getCell(0).setCellStyle(cellStyle);
        row.getCell(1).setCellStyle(cellStyle);
    }

    private static void generateHeader(Workbook wb, int rowNumber, int startCol, List<String> headers) {
        Row row = wb.getSheetAt(0).createRow(rowNumber);
        CellStyle cellStyle = wb.createCellStyle();
        CellStyle bufferCellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setBorderTop(BorderStyle.MEDIUM);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        for (int i = 0; i < headers.size(); i++) {
            row.createCell(startCol + i).setCellValue(headers.get(i));
            row.getCell(startCol + i).setCellStyle(cellStyle);
        }
        bufferCellStyle.cloneStyleFrom(cellStyle);
        bufferCellStyle.setBorderLeft(BorderStyle.MEDIUM);
        row.getCell(startCol).setCellStyle(bufferCellStyle);
        bufferCellStyle = wb.createCellStyle();
        bufferCellStyle.cloneStyleFrom(cellStyle);
        bufferCellStyle.setBorderRight(BorderStyle.MEDIUM);
        row.getCell(startCol + headers.size() - 1).setCellStyle(bufferCellStyle);
    }

}
