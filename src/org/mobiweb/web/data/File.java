/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mobiweb.web.data;

/**
 *
 * @author Ahmed Maawy
 */
public class File {
    String fileName;
    String fileValue;

    public File(String fileName, String fileValue) {
        this.fileName = fileName;
        this.fileValue = fileValue;
    }

    public File() {
        this.fileName = "";
        this.fileValue = "";
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileValue() {
        return fileValue;
    }

    public void setFileValue(String fileValue) {
        this.fileValue = fileValue;
    }
}
