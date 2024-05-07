package es.upm.pproject.geditor.model;

public class ImageModel {
    private int width;
    private int height;

    public ImageModel() {
        this.width = 800;
        this.height = 600;
    }

    public void setDimensions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}