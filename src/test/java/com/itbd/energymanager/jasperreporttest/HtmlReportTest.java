package com.itbd.energymanager.jasperreporttest;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class HtmlReportTest {
    @Test
    public void test() {
        try {
            // Load the .jasper file
            String reportPath = "J:\\home\\tomcat\\eng\\org\\reports\\class\\Blank_A4.jrxml";
//            JasperReport jasperReport = (JasperReport) JRLoader.loadObjectFromFile(reportPath);
            JasperDesign jasperDesign = JRXmlLoader.load(reportPath);
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


            // Set parameters if any
            Map<String, Object> parameters = new HashMap<>();
            // parameters.put("paramName", paramValue);

            // Fill the report with data (use a data source, here empty)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            // Create a StringWriter to capture the HTML output
            StringWriter stringWriter = new StringWriter();

            // Create an HTML exporter
            HtmlExporter exporter = new HtmlExporter();

            // Configure the exporter
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(stringWriter));

            // Export the report to HTML
            exporter.exportReport();

            // Get the HTML content as a string
            String htmlContent = stringWriter.toString();

            // Print or use the HTML string as needed
            System.out.println(htmlContent);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}
