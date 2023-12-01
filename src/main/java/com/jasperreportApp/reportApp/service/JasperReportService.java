package com.jasperreportApp.reportApp.service;

import com.jasperreportApp.reportApp.entity.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRSaver;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportService {
    public byte[] getItemReport(List<Item> items, String format)  {

        JasperReport jasperReport;


            try {

                File file = ResourceUtils.getFile("classpath:templates/item-report.jrxml");
                jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
                JRSaver.saveObject(jasperReport, "item-report.jasper");

                //Set report data
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);
                Map<String, Object> parameters = new HashMap<>();
                parameters.put("title", "Item Report");

                //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                JasperPrint jasperPrint = null;
                byte[] reportContent;

                try {
                    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
                    switch (format) {
                        case "pdf" -> reportContent = JasperExportManager.exportReportToPdf(jasperPrint);
                        case "xml" -> reportContent = JasperExportManager.exportReportToXml(jasperPrint).getBytes();
                        default -> throw new RuntimeException("Unknown report format");
                    }
                } catch (JRException e) {
                    //handle exception
                    throw new RuntimeException(e);
                }

                //byte[] reportContent = JasperExportManager.exportReportToPdf(jasperPrint);

                return reportContent;
            } catch (FileNotFoundException | JRException ex) {
                throw new RuntimeException(ex);
            }








    }
}
