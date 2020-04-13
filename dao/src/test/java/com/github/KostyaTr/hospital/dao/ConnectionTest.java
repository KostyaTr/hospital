package com.github.KostyaTr.hospital.dao;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConnectionTest {

    @Test
    void connection() throws SQLException {
        DataSource dataSource = new DataSource();
        Connection connection = dataSource.getConnection();
        assertNotNull(connection);
        DatabaseMetaData metaData = connection.getMetaData();
        assertEquals("jdbc:mysql://localhost:3306/hospital_test?useUnicode=true&serverTimezone=Europe/Moscow",
                metaData.getURL());
    }
}
