package Model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

public class AdminBean {
    private int id_barang, harga_barang, stok;
    private String nama_barang, desc_barang;
    private byte[] images;

    public int getId_barang() {
        return id_barang;
    }

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
    }

    public int getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(int harga_barang) {
        this.harga_barang = harga_barang;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getDesc_barang() {
        return desc_barang;
    }

    public void setDesc_barang(String desc_barang) {
        this.desc_barang = desc_barang;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    // Method to convert byte[] image data to Base64 String
    public String getBase64Image() {
        if (images != null && images.length > 0) {
            return Base64.getEncoder().encodeToString(images);
        }
        return "";
    }

    // Method to convert Base64 String back to byte[] image data
    public void setBase64Image(String base64Image) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(decodedBytes));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos); // You can change format to PNG, JPEG, etc.
            baos.flush();
            this.images = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
