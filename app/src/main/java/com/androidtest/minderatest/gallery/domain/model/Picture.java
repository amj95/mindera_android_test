package com.androidtest.minderatest.gallery.domain.model;

/**
 * Composition of Image + Size.
 */
public class Picture {

    private Photo photo;
    private Sizes sizes;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public Size getLargeSquareLabel(){
        Size response = new Size();
        for(Size size : sizes.getSize()){
            if(size.getLabel().equals("Large Square")){
                return size;
            }
        }
        return response;
    }
}
