package com.elevenst.terroir.product.registration.image.code;

public enum ImageDir {
    PRODUCT("/pd"), DEAL("/dl"), CATALOG("/cat"), SMART_OPTION("/smt");

    String dir;
    ImageDir(String dir){this.dir = dir;}

    @Override
    public String toString() {
        return dir;
    }
}
