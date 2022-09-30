package com.syntechsia.helper.service.syntechsiahelperservice.service.impl;

import com.syntechsia.helper.service.syntechsiahelperservice.entity.WebinarProspekEntity;
import com.syntechsia.helper.service.syntechsiahelperservice.repository.WebinarProspekRepository;
import com.syntechsia.helper.service.syntechsiahelperservice.service.DocumentService;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final WebinarProspekRepository webinarProspekRepository;

    @Autowired
    public DocumentServiceImpl(WebinarProspekRepository webinarProspekRepository) {
        this.webinarProspekRepository = webinarProspekRepository;
    }

    @Override
    public void importExcelDocument(List<MultipartFile> multipartFiles) {
        if (!multipartFiles.isEmpty()) {
            List<WebinarProspekEntity> webinarProspekEntities = new ArrayList<>();
            multipartFiles.forEach(multipartfile -> {
                try {
                    XSSFWorkbook workBook = new XSSFWorkbook(multipartfile.getInputStream());

                    XSSFSheet sheet = workBook.getSheetAt(0);
                    // looping through each row
                    for (int rowIndex = 0; rowIndex <= getNumberOfNonEmptyCells(sheet, 0) - 1; rowIndex++) {
                        // current row
                        XSSFRow row = sheet.getRow(rowIndex);
                        // skip the first row because it is a header row
                        if (rowIndex == 0) {
                            continue;
                        }

                        String emailAddress = String.valueOf(row.getCell(1));
                        String name = String.valueOf(row.getCell(2));
                        String phoneNumber = String.valueOf(row.getCell(3));
                        String education = String.valueOf(row.getCell(4));
                        String activity = String.valueOf(row.getCell(5));

                        WebinarProspekEntity webinarProspekEntity = WebinarProspekEntity.builder().email(emailAddress).name(name)
                                .phoneNumber(phoneNumber).education(education).activity(activity).invitedStatus(String.valueOf(1)).build();
                        webinarProspekEntities.add(webinarProspekEntity);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            if (!webinarProspekEntities.isEmpty()) {
                // save to database
                webinarProspekRepository.saveAll(webinarProspekEntities);
            }
        }
    }

    public static int getNumberOfNonEmptyCells(XSSFSheet sheet, int columnIndex) {
        int numOfNonEmptyCells = 0;
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row != null) {
                XSSFCell cell = row.getCell(columnIndex);
                if (cell != null && cell.getCellType() != CellType.BLANK) {
                    numOfNonEmptyCells++;
                }
            }
        }
        return numOfNonEmptyCells;
    }

}
