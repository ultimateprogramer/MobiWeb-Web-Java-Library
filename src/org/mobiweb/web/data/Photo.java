/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mobiweb.web.data;

/**
 *
 * @author Ahmed Maawy
 */
public class Photo {
    String photoName;
    String photoValue;

    public Photo(String photoName, String photoValue) {
        this.photoName = photoName;
        this.photoValue = photoValue;
    }

    public Photo() {
        this.photoName = "";
        this.photoValue = "";
    }

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getPhotoValue() {
        return photoValue;
    }

    public void setPhotoValue(String photoValue) {
        this.photoValue = photoValue;
    }
}
