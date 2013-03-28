/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mobiweb.web;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.mobiweb.web.data.*;
import org.mobiweb.web.expeptions.InvalidHeaderException;

/**
 *
 * @author Ahmed Maawy
 */
public class MobileMessage {
    private boolean headerValid;
    private HttpServletRequest httpServletRequest;

    private ArrayList fileList;
    private ArrayList photoList;
    private ArrayList parameterList;

    public boolean isHeaderValid() {
        return headerValid;
    }

    public MobileMessage(HttpServletRequest request) {
        this.httpServletRequest = request;
        headerValid = false;

        fileList = new ArrayList();
        photoList = new ArrayList();
        parameterList = new ArrayList();

        prepareHeaders();
    }
    
    private void populateHeaders(String [] value) {

        // Used to populate the file, photo and parameter collections
        
        int fileListIndex = 0;
        int photoListIndex = 0;
        int parameterListIndex = 0;

        for(int MyLoop = 0; MyLoop < value.length; MyLoop ++) {
            
            // Process the sting again to understand which elemenst lie in them
            int ParameterValueIndex = 0;
            String parameterName = "";
            String parameterValue = "";

            int FileValueIndex = 0;
            String fileName = "";
            String fileValue = null;

            int PhotoValueIndex = 0;
            String photoName = "";
            String photoValue = null;

            int ParameterNameIndex = value[MyLoop].indexOf("name=\"param_");
            int PhotoNameIndex = value[MyLoop].indexOf("name=\"photo_");
            int FileNameIndex = value[MyLoop].indexOf("name=\"file_");

            if(ParameterNameIndex != -1) {
                // Contains a parameter
                ParameterValueIndex = value[MyLoop].indexOf("\r\n\r\n", ParameterNameIndex);
                parameterName = value[MyLoop].substring(ParameterNameIndex + 12, ParameterValueIndex - 1);
                parameterValue = value[MyLoop].substring(ParameterValueIndex + 2, value[MyLoop].length() - 1);

                parameterValue = parameterValue.trim();

                // Add to parameters collection

                Parameter newParameter = new Parameter(parameterName, parameterValue);

                parameterList.add(newParameter);

            } else if(PhotoNameIndex != -1) {
                // Contains a photo
                PhotoValueIndex = value[MyLoop].indexOf("\r\n\r\n", PhotoNameIndex) + 1;
                photoName = value[MyLoop].substring(PhotoNameIndex + 12, PhotoValueIndex - 1);
                photoValue = value[MyLoop].substring(PhotoValueIndex + 2, value[MyLoop].length() - 1);

                photoValue = photoValue.trim();

                // Add to photos collection

                Photo newPhoto = new Photo(photoName, photoValue);

                photoList.add(newPhoto);
            } else if(FileNameIndex != -1) {
                // Contains a file
                FileValueIndex = value[MyLoop].indexOf("\r\n\r\n", FileNameIndex) + 1;
                fileName = value[MyLoop].substring(FileNameIndex + 11, FileValueIndex - 1);
                fileValue = value[MyLoop].substring(FileValueIndex + 2, value[MyLoop].length() - 1);

                fileValue = fileValue.trim();

                // Add to files collection

                File newFile = new File(fileName, fileValue);

                fileList.add(newFile);
            }
        }
    }

    private void prepareHeaders() {
        // The headers are still not valid
        headerValid = false;

        try {
            String mobileRequest = "", requestLine;

            // Read the bytes sent from the mobile request

            while((requestLine = httpServletRequest.getReader().readLine()) != null) {
                mobileRequest += requestLine + "\r\n";
            }

            // Get the content type header establish validity and try establish if there is a boundary

            String ContentType = httpServletRequest.getHeader("Content-Type");

            if(!ContentType.contains("multipart/form-data")) {
                headerValid = false;
                return;
            }

            // Establish the content boundary

            String ContentTypeSplit[] = ContentType.split(";");

            String ContentBoundary = "";

            for(int MyLoop = 0; MyLoop < ContentTypeSplit.length; MyLoop ++) {
                if (ContentTypeSplit[MyLoop].contains("boundary")) {
                    String boundary[] = ContentTypeSplit[MyLoop].split("=");
                    ContentBoundary = boundary[1];
                }
            }

            String myParameters[] = null;

            if(!ContentBoundary.equals("")) {
                headerValid = true;
                
                myParameters = mobileRequest.split("--" + ContentBoundary);

                populateHeaders(myParameters);
            }
        } catch (IOException ex) {
             ex.printStackTrace();
        }
    }

    public File getFile(String fileName) throws InvalidHeaderException {
        if(!headerValid) {
            throw new InvalidHeaderException();
        }

        for(int MyLoop = 0; MyLoop < fileList.size(); MyLoop ++) {
            if(((File)fileList.get(MyLoop)).getFileName().equals(fileName)) {
                return (File)fileList.get(MyLoop);
            }
        }

        // Not found
        
        return null;
    }

    public Photo getPhoto(String photoName) throws InvalidHeaderException {
        if(!headerValid) {
            throw new InvalidHeaderException();
        }

        for(int MyLoop = 0; MyLoop < photoList.size(); MyLoop ++) {
            if(((Photo)photoList.get(MyLoop)).getPhotoName().equals(photoName)) {
                return (Photo)photoList.get(MyLoop);
            }
        }

        // Not found

        return null;
    }

    public Parameter getParameter(String parameterName) throws InvalidHeaderException {
        if(!headerValid) {
            throw new InvalidHeaderException();
        }

        for(int MyLoop = 0; MyLoop < parameterList.size(); MyLoop ++) {
            if(((Parameter)parameterList.get(MyLoop)).getParameterName().equals(parameterName)) {
                return (Parameter)parameterList.get(MyLoop);
            }
        }

        // Not found

        return null;
    }
}
