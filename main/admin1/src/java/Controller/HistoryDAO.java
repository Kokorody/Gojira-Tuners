package Controller;

import Database.DBConnection;
import Model.HistoryBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {

    public List<HistoryBean> getAllHistory() {
        List<HistoryBean> historyList = new ArrayList<>();
        
        try {
            DBConnection db = new DBConnection();
            Connection conn = db.setConnection();
            String sql = "SELECT * FROM tbl_pembeli";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                HistoryBean history = new HistoryBean();
                history.setId_pembeli(rs.getInt("id_pembeli"));
                history.setNama(rs.getString("nama"));
                history.setEmail(rs.getString("email"));
                history.setPhone(rs.getString("phone"));
                history.setAlamat(rs.getString("alamat"));
                history.setId_barang(rs.getInt("id_barang"));
                history.setNama_barang(rs.getString("nama_barang"));
                history.setJumlah_beli(rs.getInt("jumlah_beli"));
                history.setHarga_satuan(rs.getInt("harga_satuan"));
                history.setTotal_harga(rs.getInt("total_harga"));
                history.setMethod_pembayaran(rs.getString("method_pembayaran"));
                history.setPurchase_date(rs.getTimestamp("purchase_date"));
                historyList.add(history);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return historyList;
    }
    
    // New method for searching history based on name and phone
    public List<HistoryBean> searchHistory(String nama, String phone) {
        List<HistoryBean> historyList = new ArrayList<>();
        
        try {
            DBConnection db = new DBConnection();
            Connection conn = db.setConnection();
            String sql = "SELECT * FROM tbl_pembeli WHERE nama LIKE ? AND phone LIKE ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + nama + "%");
            ps.setString(2, "%" + phone + "%");
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                HistoryBean history = new HistoryBean();
                history.setId_pembeli(rs.getInt("id_pembeli"));
                history.setNama(rs.getString("nama"));
                history.setEmail(rs.getString("email"));
                history.setPhone(rs.getString("phone"));
                history.setAlamat(rs.getString("alamat"));
                history.setId_barang(rs.getInt("id_barang"));
                history.setNama_barang(rs.getString("nama_barang"));
                history.setJumlah_beli(rs.getInt("jumlah_beli"));
                history.setHarga_satuan(rs.getInt("harga_satuan"));
                history.setTotal_harga(rs.getInt("total_harga"));
                history.setMethod_pembayaran(rs.getString("method_pembayaran"));
                history.setPurchase_date(rs.getTimestamp("purchase_date"));
                historyList.add(history);
            }
            
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return historyList;
    }
}
