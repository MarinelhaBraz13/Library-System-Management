package com.library.dao;
import com.library.model.BorrowRecord;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
public class BorrowRecordDAO {
	
    // ==========================================
    // Get ALL borrow records
    // Used by ADMIN
    // ==========================================

    public List<BorrowRecord> getAllBorrowRecords() {
        List<BorrowRecord> records =
                new ArrayList<>();
        String sql =
                "SELECT br.id, " +
                "u.username, " +
                "b.title AS book_title, " +
                "br.borrow_date, " +
                "br.return_date, " +
                "br.status " +
                "FROM borrow_records br " +
                "JOIN users u ON br.user_id = u.id " +
                "JOIN books b ON br.book_id = b.id " +
                "ORDER BY br.id DESC";
        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery()
        ) {
            while (rs.next()) {

                BorrowRecord record =
                        new BorrowRecord();
                record.setId(
                        rs.getInt("id")
                );
                record.setUsername(
                        rs.getString("username")
                );
                record.setBookTitle(
                        rs.getString("book_title")
                );
                record.setBorrowDate(
                        rs.getString("borrow_date")
                );
                record.setReturnDate(
                        rs.getString("return_date")
                );
                record.setStatus(
                        rs.getString("status")
                );
                records.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return records;
    }

    // ==========================================
    // Get borrow records for ONE USER
    // Used by STUDENT
    // ==========================================

    public List<BorrowRecord> getBorrowRecordsByUserId(
            int userId) {
        List<BorrowRecord> records =
                new ArrayList<>();
        String sql =
                "SELECT br.id, " +
                "u.username, " +
                "b.title AS book_title, " +
                "br.borrow_date, " +
                "br.return_date, " +
                "br.status " +
                "FROM borrow_records br " +
                "JOIN users u ON br.user_id = u.id " +
                "JOIN books b ON br.book_id = b.id " +
                "WHERE br.user_id = ? " +
                "ORDER BY br.id DESC";
        try (
            Connection conn =
                    DBConnection.getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {
            ps.setInt(1, userId);
            ResultSet rs =
                    ps.executeQuery();
            while (rs.next()) {
                BorrowRecord record =
                        new BorrowRecord();
                record.setId(
                        rs.getInt("id")
                );
                record.setUsername(
                        rs.getString("username")
                );
                record.setBookTitle(
                        rs.getString("book_title")
                );
                record.setBorrowDate(
                        rs.getString("borrow_date")
                );
                record.setReturnDate(
                        rs.getString("return_date")
                );
                record.setStatus(
                        rs.getString("status")
                );
                records.add(record);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return records;
    }
 // Get borrow records by date range
 // Used by ADMIN
 public List<BorrowRecord> getBorrowRecordsByDays(
         int days) {

     List<BorrowRecord> records =
             new ArrayList<>();
     String sql =
             "SELECT br.id, " +
             "u.username, " +
             "b.title AS book_title, " +
             "br.borrow_date, " +
             "br.return_date, " +
             "br.status " +
             "FROM borrow_records br " +
             "JOIN users u ON br.user_id = u.id " +
             "JOIN books b ON br.book_id = b.id " +
             "WHERE br.borrow_date >= DATE_SUB(CURDATE(), INTERVAL ? DAY) " +
             "ORDER BY br.id DESC";
     try (
         Connection conn =
                 DBConnection.getConnection();

         PreparedStatement ps =
                 conn.prepareStatement(sql)
     ) {

         ps.setInt(1, days);

         ResultSet rs =
                 ps.executeQuery();

         while (rs.next()) {

             BorrowRecord record =
                     new BorrowRecord();

             record.setId(
                     rs.getInt("id")
             );

             record.setUsername(
                     rs.getString("username")
             );

             record.setBookTitle(
                     rs.getString("book_title")
             );

             record.setBorrowDate(
                     rs.getString("borrow_date")
             );

             record.setReturnDate(
                     rs.getString("return_date")
             );

             record.setStatus(
                     rs.getString("status")
             );

             records.add(record);
         }

     } catch (Exception e) {

         e.printStackTrace();
     }
     return records;
 }
}
