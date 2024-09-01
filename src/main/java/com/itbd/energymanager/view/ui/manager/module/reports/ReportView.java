package com.itbd.energymanager.view.ui.manager.module.reports;

import com.itbd.energymanager.services.reports.WReportBuilder;
import jakarta.annotation.PreDestroy;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import lombok.extern.slf4j.Slf4j;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;


@Component
@FxmlView("/view/ui/manager/module/reports/report_view.fxml")
@Slf4j
public class ReportView {

    private final FxWeaver fxWeaver;
    private final WReportBuilder wReportBuilder;

    public ReportView(FxWeaver fxWeaver, WReportBuilder wReportBuilder) {
        this.fxWeaver = fxWeaver;
        this.wReportBuilder = wReportBuilder;
    }

    private JasperPrint jasperPrint;

    private SimpleIntegerProperty currentPage;
    private int imageHeight = 0;
    private int imageWidth = 0;
    private int reportPages = 0;

    @FXML
    private BorderPane root;

    @FXML
    private Button btnPrint, btnSave, btnFirstPage, btnBackPage, btnNextPage, btnLastPage, btnZoomIn, btnZoomOut;

    @FXML
    private TextField txtPage;

    @FXML
    private ImageView report;
    @FXML
    private Label lblReportPages;

    private DialogPane dialogPane;

    private Dialog<Void> dialog;

    @FXML
    public void initialize() {
        this.dialog = new Dialog<>();
        this.dialogPane = dialog.getDialogPane();
        dialogPane.setContent(root);

        dialogPane.getButtonTypes().add(ButtonType.CLOSE);
        dialogPane.lookupButton(ButtonType.CLOSE).setVisible(false);

        currentPage = new SimpleIntegerProperty(this, "currentPage", 1);


        dialog.setResizable(true);
        dialog.initModality(Modality.WINDOW_MODAL);
    }

    @FXML
    void btnPrint(ActionEvent event) {
        try {
            JasperPrintManager.printReport(jasperPrint, true);
            dialog.close();
        } catch (JRException ex) {
            log.error(ex.getMessage());
        }
    }

    @FXML
    void btnSave(ActionEvent event) {
        FileChooser.ExtensionFilter pdf = new FileChooser.ExtensionFilter("Portable Document Format", "*.pdf");
        FileChooser.ExtensionFilter html = new FileChooser.ExtensionFilter("HyperText Markup Language", "*.html");
        FileChooser.ExtensionFilter xml = new FileChooser.ExtensionFilter("eXtensible Markup Language", "*.xml");
        FileChooser.ExtensionFilter xls = new FileChooser.ExtensionFilter("Microsoft Excel 2007", "*.xls");
        FileChooser.ExtensionFilter xlsx = new FileChooser.ExtensionFilter("Microsoft Excel 2016", "*.xlsx");

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save As");
        chooser.getExtensionFilters().addAll(pdf, html, xml, xls, xlsx);
        chooser.setSelectedExtensionFilter(pdf);

        File file = chooser.showSaveDialog(null);

        if (file != null) {
            List<String> selectedExtension = chooser.getSelectedExtensionFilter().getExtensions();
            exportTo(file, selectedExtension.get(0));
        }
    }

    @FXML
    void btnBackPage(ActionEvent event) {
        renderPage(getCurrentPage() - 1);
    }

    @FXML
    void btnFirstPage(ActionEvent event) {
        renderPage(1);
    }

    @FXML
    void btnLastPage(ActionEvent event) {
        renderPage(reportPages);
    }

    @FXML
    void btnNextPage(ActionEvent event) {
        renderPage(getCurrentPage() + 1);
    }

    @FXML
    void btnZoomIn(ActionEvent event) {
        zoom(0.15);
    }

    @FXML
    void btnZoomOut(ActionEvent event) {
        zoom(-0.15);
    }

    @FXML
    void txtPage(ActionEvent event) {
        try {
            int page = Integer.parseInt(txtPage.getText());
            renderPage(((page > 0 && page <= reportPages) ? page : 1));
        } catch (NumberFormatException e) {
            renderPage(1);
        }
    }

    // ***********************************************
    // Properties
    // ***********************************************

    /**
     * Set the currentPage property value
     *
     * @param pageNumber Page number
     */
    public void setCurrentPage(int pageNumber) {
        currentPage.set(pageNumber);
    }

    /**
     * Get the currentPage property value
     *
     * @return Current page value
     */
    public int getCurrentPage() {
        return currentPage.get();
    }

    /**
     * Get the currentPage property
     *
     * @return currentPage property
     */
    public SimpleIntegerProperty currentPageProperty() {
        return currentPage;
    }

    // ***********************************************
    // Button Action
    // ***********************************************
    private void printAction() {
        btnPrint.setOnAction(event -> {
            try {
                JasperPrintManager.printReport(jasperPrint, true);
//                close();
            } catch (JRException ex) {
                log.error(ex.getMessage());
            }
        });
    }

    /**
     * When the user reach first or last page he cannot go forward or backward
     *
     * @param pageNumber Page number
     */
    private void disableUnnecessaryButtons(int pageNumber) {
        boolean isFirstPage = (pageNumber == 1);
        boolean isLastPage = (pageNumber == reportPages);

        btnBackPage.setDisable(isFirstPage);
        btnFirstPage.setDisable(isFirstPage);
        btnNextPage.setDisable(isLastPage);
        btnLastPage.setDisable(isLastPage);
    }

    // ***********************************************
    // Export Utilities
    // ***********************************************

    /**
     * Choose the right export method for each file extension
     *
     * @param file      File
     * @param extension File extension
     */
    private void exportTo(File file, String extension) {
        switch (extension) {
            case "*.pdf":
                exportToPdf(file);
                break;
            case "*.html":
                exportToHtml(file);
                break;
            case "*.xml":
                exportToXml(file);
                break;
            case "*.xls":
                exportToXls(file);
                break;
            case "*.xlsx":
                exportToXlsx(file);
                break;
            default:
                exportToPdf(file);
        }
    }

    /**
     * Export report to html file
     */
    public void exportToHtml(File file) {
        try {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, file.getPath());
        } catch (JRException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Export report to Pdf file
     */
    public void exportToPdf(File file) {
        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint, file.getPath());
        } catch (JRException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Export report to old Microsoft Excel file
     */
    public void exportToXls(File file) {
        try {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            exporter.exportReport();
        } catch (JRException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Export report to Microsoft Excel file
     */
    public void exportToXlsx(File file) {
        try {
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(file));
            exporter.exportReport();
        } catch (JRException ex) {
            log.error(ex.getMessage());
        }
    }

    /**
     * Export report to XML file
     */
    public void exportToXml(File file) {
        try {
            JasperExportManager.exportReportToXmlFile(jasperPrint, file.getPath(), false);
        } catch (JRException ex) {
            log.error(ex.getMessage());
        }
    }

    // ***********************************************
    // Image related methods
    // ***********************************************

    /**
     * Renderize page to image
     *
     * @param pageNumber Page number
     * @throws JRException
     */
    private Image pageToImage(int pageNumber) {
        try {
            float zoom = (float) 1.33;
            BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, pageNumber - 1, zoom);
            WritableImage fxImage = new WritableImage(imageHeight, imageWidth);

            return SwingFXUtils.toFXImage(image, fxImage);
        } catch (JRException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    /**
     * Render specific page on screen
     *
     * @param pageNumber
     */
    private void renderPage(int pageNumber) {
        setCurrentPage(pageNumber);
        disableUnnecessaryButtons(pageNumber);
        txtPage.setText(Integer.toString(pageNumber));
        report.setImage(pageToImage(pageNumber));
    }

    /**
     * Scale image from ImageView
     *
     * @param factor Zoom factor
     */
    public void zoom(double factor) {
        report.setScaleX(report.getScaleX() + factor);
        report.setScaleY(report.getScaleY() + factor);
        report.setFitHeight(imageHeight + factor);
        report.setFitWidth(imageWidth + factor);
    }

    /**
     * Load report from JasperPrint
     *
     * @param title       Dialog title
     * @param jasperPrint JasperPrint object
     */
    public void viewReport(String title, JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;

        imageHeight = jasperPrint.getPageHeight() + 284;
        imageWidth = jasperPrint.getPageWidth() + 201;
        reportPages = jasperPrint.getPages().size();
        lblReportPages.setText("/ " + reportPages);

        if (reportPages > 0) {
            renderPage(1);
        }
        dialog.setTitle(title);
        dialog.show();
    }


    @PreDestroy
    private void cleanup() {
        log.info("I am finish distributor!");
    }
}
