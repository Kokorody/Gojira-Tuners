package Controller;

import Model.CartItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CheckoutDAO {

    public boolean saveOrder(Connection conn, String name, String email, String phone, String address, String paymentMethod, CartItem[] cartItems) throws SQLException {
        String sqlInsert = "INSERT INTO tbl_pembeli (nama, email, phone, alamat, id_barang, nama_barang, jumlah_beli, harga_satuan, total_harga, method_pembayaran, purchase_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP)";
        PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert);

        for (CartItem item : cartItems) {
            stmtInsert.setString(1, name);
            stmtInsert.setString(2, email);
            stmtInsert.setString(3, phone);
            stmtInsert.setString(4, address);
            stmtInsert.setInt(5, item.getProduct_id());
            stmtInsert.setString(6, item.getNama_barang());
            stmtInsert.setInt(7, item.getJumlah_beli());
            stmtInsert.setInt(8, item.getHarga_satuan());
            stmtInsert.setInt(9, item.getTotal_harga());
            stmtInsert.setString(10, paymentMethod); // Include payment method here
            stmtInsert.addBatch();
        }

        int[] results = stmtInsert.executeBatch();
        stmtInsert.close();

        for (int result : results) {
            if (result == PreparedStatement.EXECUTE_FAILED) {
                return false;
            }
        }

        return true;
    }

    public boolean updateStock(Connection conn, CartItem[] cartItems) throws SQLException {
        String sqlCallProc = "CALL KurangiStokBarang(?, ?)";
        PreparedStatement stmtProc = conn.prepareStatement(sqlCallProc);

        for (CartItem item : cartItems) {
            stmtProc.setInt(1, item.getProduct_id());
            stmtProc.setInt(2, item.getJumlah_beli());
            stmtProc.addBatch();
        }

        int[] results = stmtProc.executeBatch();
        stmtProc.close();

        for (int result : results) {
            if (result == PreparedStatement.EXECUTE_FAILED) {
                return false;
            }
        }

        return true;
    }
}
