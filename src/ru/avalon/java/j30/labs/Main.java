package ru.avalon.java.j30.labs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

/**
 * Лабораторная работа №2
 * <p>
 *
 * Тема: "JDBC - Java Database Connectivity"
 *
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */
public class Main {

    public static final String PATH_DB_PROP = "resources/database.properties";
    /**
     * Точка входа в приложение
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        try (Connection connection = getConnection()) {
            ProductCode code = new ProductCode("MO", 'N', "Movies");
            code.save(connection);
            printAllCodes(connection);
            
            System.out.println("");
            
            code.setCode("MV");
            code.save(connection);
            printAllCodes(connection);
        }
    }

    /**
     * Выводит в консоль все коды товаров
     *
     * @param connection действительное соединение с базой данных
     * @throws SQLException
     */
    private static void printAllCodes(Connection connection) throws SQLException {
        Collection<ProductCode> codes = ProductCode.all(connection);
        for (ProductCode code : codes) {
            System.out.println(code);
        }
    }

    /**
     * Возвращает URL, описывающий месторасположение базы данных
     *
     * @param properties - файл properties в котором хранится информация о
     * подключении.
     * @return URL в виде объекта класса {@link String}
     */
    private static String getUrl(Properties properties) {
        return new StringBuilder() // формируем строку информации о соединении из файла properties
                .append("jdbc:")
                .append(properties.get("database.driver"))
                .append("://")
                .append(properties.get("database.host"))
                .append(":")
                .append(properties.get("database.port"))
                .append("/")
                .append(properties.get("database.name"))
                .toString();
    }

    /**
     * Возвращает параметры соединения (содержимое файла properties)
     *
     * @param PATH_DB_PROP - путь файла database.properties
     * @return Объект класса {@link Properties}, содержащий параметры user и
     * password
     */
    private static Properties getProperties() {
        Properties properties = new Properties(); // инициализируем объект класса properties
        try (InputStream stream = ClassLoader.getSystemResourceAsStream(PATH_DB_PROP)) { // получаем поток из файла ресурсов
            properties.load(stream); // загружаем в объект класса properties наш поток из файла database.properties
        } catch (IOException e) { // ловим исключение
            throw new IllegalStateException("Database config not been found!", e); // проблема, что файл не найден (е) привела к этому исключению - файл не был найден системой
        }
        return properties;
    }

    /**
     * Возвращает соединение с базой данных Sample
     *
     * @param properties - объект класса Properties, который хранит в себе
     * прочитанные данные из исходного properties (по сути являясь копией).
     * @param url - строка, хранящая путь (данные) соединения
     * @param user - имя юзера, полученное из файла properties
     * @param password - пароль юзера, полученный из файла properties
     *
     * @return объект типа {@link Connection}
     * @throws SQLException
     */
    private static Connection getConnection() throws SQLException {

        Properties properties = getProperties(); // читаем конфигурации 
        String url = getUrl(properties); // получаем строку соединения с параметрами этого соединения

        String user = properties.getProperty("database.user"); // получаем имя юзера из файла (с помощью стандартного метода)
        String password = properties.getProperty("database.password"); // получаем пароль юзера из файла

        Connection connection = DriverManager.getConnection(url, user, password); // формируем соединение на основе ранее полученных данных
        return connection;
    }
}
