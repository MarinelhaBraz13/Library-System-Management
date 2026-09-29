package com.library.dao;

import com.library.model.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // ==========================================
    // Get all books
    // ==========================================

    public List<Book> getAllBooks() {

        List<Book> books =
                new ArrayList<>();
        String sql =
                "SELECT * FROM books";
        try (
            Connection conn =
                    DBConnection.getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql);
            ResultSet rs =
                    ps.executeQuery()
        ) {
            while (rs.next()) {

                Book book =
                        new Book();
                book.setId(
                        rs.getInt("id")
                );
                book.setTitle(
                        rs.getString("title")
                );
                book.setAuthor(
                        rs.getString("author")
                );
                book.setCategory(
                        rs.getString("category")
                );
                book.setQuantity(
                        rs.getInt("quantity")
                );
                book.setAvailable(
                        rs.getInt("available")
                );
                books.add(book);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return books;
    }
    
    // ==========================================
    // Search books by ID, title, author, or category
    // ==========================================

    public List<Book> searchBooks(String search) {
        List<Book> books =
                new ArrayList<>();
        String sql =
                "SELECT * FROM books " +
                "WHERE CAST(id AS CHAR) LIKE ? " +
                "OR title LIKE ? " +
                "OR author LIKE ? " +
                "OR category LIKE ?";
        try (
            Connection conn =
                    DBConnection.getConnection();

            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {
            String keyword =
                    "%" + search + "%";
            ps.setString( 1,keyword);
            ps.setString( 2,keyword);
            ps.setString( 3,keyword);
            ps.setString( 4,keyword);

            ResultSet rs =
                    ps.executeQuery();
            while (rs.next()) {
                Book book =new Book();
                book.setId(rs.getInt("id")
                );

                book.setTitle(rs.getString("title")
                );

                book.setAuthor(rs.getString("author")
                );

                book.setCategory(rs.getString("category")
                );

                book.setQuantity(rs.getInt("quantity")
                );

                book.setAvailable(rs.getInt("available")
                );
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return books;
    }

    // ==========================================
    // Get one book by ID
    // ==========================================

    public Book getBookById(int id) {

        Book book =null;
        String sql =
                "SELECT * FROM books WHERE id=?";
        try (
            Connection conn =
                    DBConnection.getConnection();
            PreparedStatement ps =
                    conn.prepareStatement(sql)
        ) {
            ps.setInt(1,id);
            ResultSet rs =
                    ps.executeQuery();
            if (rs.next()) {
                book =new Book();

                book.setId(rs.getInt("id")
                );

                book.setTitle(rs.getString("title")
                );

                book.setAuthor(rs.getString("author")
                );

                book.setCategory(rs.getString("category")
                );

                book.setQuantity(rs.getInt("quantity")
                );

                book.setAvailable(rs.getInt("available")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }
}