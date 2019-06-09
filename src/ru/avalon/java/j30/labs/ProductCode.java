package ru.avalon.java.j30.labs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Класс описывает представление о коде товара и отражает соответствующую
 * таблицу базы данных Sample (таблица PRODUCT_CODE).
 *
 * @author Daniel Alpatov <danial.alpatov@gmail.com>
 */
public class ProductCode {

    /**
     * Код товара
     */
    private String code;
    /**
     * Кода скидки
     */
    private char discountCode;
    /**
     * Описание
     */
    private String description;

    /**
     * Основной конструктор типа {@link ProductCode}
     *
     * @param code код товара
     * @param discountCode код скидки
     * @param description описание
     */
    public ProductCode(String code, char discountCode, String description) {
        this.code = code;
        this.discountCode = discountCode;
        this.description = description;
    }

    /**
     * Инициализирует объект значениями из переданного {@link ResultSet}
     *
     * @param set {@link ResultSet}, полученный в результате запроса,
     * содержащего все поля таблицы PRODUCT_CODE базы данных Sample.
     */
    private ProductCode(ResultSet set) throws SQLException {
        this.code = set.getString("PROD_CODE"); // получаем строку CODE из результата запроса
        this.discountCode = set.getString("DISCOUNT_CODE").charAt(0);
        this.description = set.getString("DESCRIPTION");
    }

    /**
     * Возвращает код товара
     *
     * @return Объект типа {@link String}
     */
    public String getCode() {
        return code;
    }

    /**
     * Устанавливает код товара
     *
     * @param code код товара
     */
    public void setCode(String code) throws SQLException {
        this.code = code;
    }

    /**
     * Возвращает код скидки
     *
     * @return Объект типа {@link String}
     */
    public char getDiscountCode() {
        return discountCode;
    }

    /**
     * Устанавливает код скидки
     *
     * @param discountCode код скидки
     */
    public void setDiscountCode(char discountCode) {
        this.discountCode = discountCode;
    }

    /**
     * Возвращает описание
     *
     * @return Объект типа {@link String}
     */
    public String getDescription() {
        return description;
    }

    /**
     * Устанавливает описание
     *
     * @param description описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Хеш-функция типа {@link ProductCode}.
     *
     * @return Значение хеш-кода объекта типа {@link ProductCode}
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, discountCode, description);
    }

    /**
     * Сравнивает некоторый произвольный объект с текущим объектом типа на
     * полное соответствие {@link ProductCode}
     *
     * @param obj Объект, с которым сравнивается текущий объект.
     * @return true, если объект obj тождественен текущему объекту. В обратном
     * случае - false.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ProductCode) { // проверка: является ли otherObject Personality. Если да, то выполняем:
            ProductCode otherProductCode = (ProductCode) obj; // приведение otherObject типа Object к типу Personality
            return code.equals(otherProductCode.code)
                    && discountCode == otherProductCode.discountCode
                    && description.equalsIgnoreCase(otherProductCode.description);
        }
        return false; // если otherObject не Person, то возвращаем false
    }

    /**
     * Возвращает строковое представление кода товара.
     *
     * @return Объект типа {@link String}
     */
    @Override
    public String toString() {
        return new StringBuilder() // формируем строку 
                .append("PRODUCT_CODE: ")
                .append("CODE: ")
                .append(code)
                .append(", DISCOUNT_CODE: ")
                .append(discountCode)
                .append(", DESCRIPTION: ")
                .append(description)
                .append("/")
                .toString();
    }

    /**
     * Возвращает запрос на выбор всех записей из таблицы PRODUCT_CODE базы
     * данных Sample
     *
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getSelectQuery(Connection connection) throws SQLException {
        if (connection == null) { // Нет соединения!
            throw new IllegalArgumentException("No connection!");
        }
        String querySelect = "SELECT * FROM PRODUCT_CODE"; // Здесь указываем из какой таблицы брать данные
        return connection.prepareStatement(querySelect);
    }

    /**
     * Возвращает запрос на добавление записи в таблицу PRODUCT_CODE базы данных
     * Sample
     *
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getInsertQuery(Connection connection) throws SQLException {
        if (connection == null) { // Нет соединения!
            throw new IllegalArgumentException("No connection!");
        }
        String queryInsert = "INSERT INTO PRODUCT_CODE (PROD_CODE, DISCOUNT_CODE, DESCRIPTION) values (?, ?, ?)"; // Здесь указываем в какую таблицу класть данные
        return connection.prepareStatement(queryInsert);
    }

    /**
     * Возвращает запрос на обновление значений записи в таблице PRODUCT_CODE
     * базы данных Sample
     *
     * @param connection действительное соединение с базой данных
     * @return Запрос в виде объекта класса {@link PreparedStatement}
     */
    public static PreparedStatement getUpdateQuery(Connection connection) throws SQLException {
        if (connection == null) { // Нет соединения!
            throw new IllegalArgumentException("No connection!");
        }
        String queryUpdate
                = "UPDATE PRODUCT_CODE "
                + "SET DISCOUNT_CODE = ?, DESCRIPTION = ? "
                + "WHERE PROD_CODE = ?";
        return connection.prepareStatement(queryUpdate);
    }

    /**
     * Преобразует {@link ResultSet} в коллекцию объектов типа
     * {@link ProductCode}
     *
     * @param set {@link ResultSet}, полученный в результате запроса,
     * содержащего все поля таблицы PRODUCT_CODE базы данных Sample
     * @return Коллекция объектов типа {@link ProductCode}
     * @throws SQLException
     */
    public static Collection<ProductCode> convert(ResultSet set) throws SQLException {
        List<ProductCode> collectionOfProducts = new ArrayList<>();
        if (set != null) { // проверка
            while (set.next()) {
                collectionOfProducts.add(new ProductCode(set));
            }
            return collectionOfProducts;
        }
        throw new IllegalArgumentException("ResultSet is Empty");
    }

    /**
     * Сохраняет текущий объект в базе данных.
     * <p>
     * Если запись ещё не существует, то выполняется запрос типа INSERT.
     * <p>
     * Если запись уже существует в базе данных, то выполняется запрос типа
     * UPDATE.
     *
     * @param connection действительное соединение с базой данных
     */
    public void save(Connection connection) throws SQLException {
        Collection<ProductCode> productsCodeCollection = all(connection);
        PreparedStatement statement;
        if (productsCodeCollection.contains(this)) {
            statement = getUpdateQuery(connection);
        } else {
            statement = getInsertQuery(connection);
        }
        statement.setString(1, code);
        statement.setString(2, String.valueOf(discountCode));
        statement.setString(3, description);
        statement.execute();
    }

    /**
     * Возвращает все записи таблицы PRODUCT_CODE в виде коллекции объектов типа
     * {@link ProductCode}
     *
     * @param connection действительное соединение с базой данных
     * @return коллекция объектов типа {@link ProductCode}
     * @throws SQLException
     */
    public static Collection<ProductCode> all(Connection connection) throws SQLException {
        try (PreparedStatement statement = getSelectQuery(connection)) {
            try (ResultSet result = statement.executeQuery()) {
                return convert(result);
            }
        }
    }
}
