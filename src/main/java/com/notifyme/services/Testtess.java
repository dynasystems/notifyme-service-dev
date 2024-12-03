package com.notifyme.services;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;


// train classifier on app start
    public class Testtess {
        public static void main(String[] args) {
            File image = new File("/home/giliardi/Imagens/placaCarro.jpeg");
            Tesseract tessInst = new Tesseract();
            //tessInst.setLanguage("por");
            tessInst.setDatapath("/usr/share/tesseract-ocr/5/tessdata/");
            try {
                String result = tessInst.doOCR(image);
                System.out.println(result);
            } catch (TesseractException e) {
                System.err.println(e.getMessage());
            }

        }
    }

