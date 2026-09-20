package com.mc.flooringmastery.test.service;

import com.mc.flooringmastery.dao.order.OrderDao;
import com.mc.flooringmastery.dao.product.ProductDao;
import com.mc.flooringmastery.dao.tax.TaxDao;
import com.mc.flooringmastery.dto.Order;
import com.mc.flooringmastery.dto.Product;
import com.mc.flooringmastery.dto.Tax;
import com.mc.flooringmastery.service.FlooringMasteryDataValidationException;
import com.mc.flooringmastery.service.FlooringMasteryService;
import com.mc.flooringmastery.service.FlooringMasteryServiceImpl;
import com.mc.flooringmastery.test.service.stub.OrderDaoStubImpl;
import com.mc.flooringmastery.test.service.stub.ProductDaoStubImpl;
import com.mc.flooringmastery.test.service.stub.TaxDaoStubImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlooringMasteryServiceImplTest {

    FlooringMasteryService service;
    private LocalDate orderDate = LocalDate.of(2026, 12, 20);

    @BeforeEach
    void setUp() {
        OrderDao orderDao = new OrderDaoStubImpl();
        TaxDao taxDao = new TaxDaoStubImpl();
        ProductDao productDao = new ProductDaoStubImpl();

        service = new FlooringMasteryServiceImpl(
                orderDao,
                taxDao,
                productDao
        );
    }

    @Test
    void getAllOrders() {
        List<Order> orders = service.getAllOrders(orderDate);
        assertEquals(1, orders.size());
        assertEquals(10, orders.get(0).getOrderNumber());
    }

    @Test
    void getOrder() {
        Order retrievedOrder = service.getOrder(orderDate, 10);
        Order expectedOrder  = new Order(
                10,
                "Ada Lovelace",
                "WA",
                new BigDecimal("9.25"),
                "Tile",
                new BigDecimal("200"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15"),
                new BigDecimal("700.00"),
                new BigDecimal("830.00"),
                new BigDecimal("141.53"),
                new BigDecimal("1671.53")
        );

        assertEquals(expectedOrder , retrievedOrder);

    }

    @Test
    void getAllTaxes() {
        List<Tax> taxes = service.getAllTaxes();

        assertEquals(1, taxes.size());
        assertEquals("WA", taxes.get(0).getStateAbr());
    }

    @Test
    void getAllProducts() {
        List<Product> products = service.getAllProducts();

        assertEquals(1, products.size());
        assertEquals("Tile", products.get(0).getProductType());
    }

    @Test
    void createOrder() {
        Order newOrder = service.createOrder(
                orderDate,
                "John Smith",
                "WA",
                "Tile",
                new BigDecimal("100")
        );

        assertEquals(11, newOrder.getOrderNumber());
        assertEquals("John Smith", newOrder.getOrderName());
        assertEquals("WA", newOrder.getState());
        assertEquals("Tile", newOrder.getProductType());

        assertEquals(new BigDecimal("100"), newOrder.getArea());
        assertEquals(new BigDecimal("3.50"), newOrder.getCostPerSquareFoot());
        assertEquals(new BigDecimal("4.15"), newOrder.getLabourCostPerSquareFoot());

        assertEquals(new BigDecimal("350.00"), newOrder.getMaterialCost());
        assertEquals(new BigDecimal("415.00"), newOrder.getLabourCost());
        assertEquals(new BigDecimal("70.76"), newOrder.getTax());
        assertEquals(new BigDecimal("835.76"), newOrder.getTotal());
    }

    @Test
    void addOrder() {
        Order newOrder = service.createOrder(
                orderDate,
                "John Smith",
                "WA",
                "Tile",
                new BigDecimal("100")
        );

        Order addedOrder = service.addOrder(orderDate, newOrder);

        assertEquals(newOrder, addedOrder);
    }

    @Test
    void editOrder() {
        Order editedOrder = service.editOrder(
                orderDate,
                10,
                "John Smith",
                "WA",
                "Tile",
                new BigDecimal("150")
        );

        assertEquals(10, editedOrder.getOrderNumber());
        assertEquals("John Smith", editedOrder.getOrderName());
        assertEquals("WA", editedOrder.getState());
        assertEquals("Tile", editedOrder.getProductType());
        assertEquals(new BigDecimal("150"), editedOrder.getArea());

        Order retrievedOrder = service.getOrder(orderDate, 10);

        assertEquals(editedOrder, retrievedOrder);
    }

    @Test
    void removeOrder() {
        Order removedOrder = service.removeOrder(orderDate, 10);

        assertEquals(10, removedOrder.getOrderNumber());
        assertEquals("Ada Lovelace", removedOrder.getOrderName());
    }

    @Test
    void validateDate() {
        // Valid - future date
        assertDoesNotThrow(() ->
                service.validateDate(LocalDate.now().plusDays(1))
        );

        // Invalid - today
        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.validateDate(LocalDate.now())
        );

        // Invalid - past
        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.validateDate(LocalDate.now().minusDays(1))
        );
    }

    @Test
    void validateCustomer() {
        // Valid
        assertDoesNotThrow(() ->
                service.validateCustomer("John Smith")
        );

        // Invalid - empty
        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.validateCustomer("")
        );

        // Invalid - only spaces
        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.validateCustomer("   ")
        );

        // Invalid character
        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.validateCustomer("John@Smith")
        );
    }

    @Test
    void validateState() {
        // Valid
        assertDoesNotThrow(() ->
                service.validateState("WA")
        );

        // Invalid
        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.validateState("XX")
        );
    }

    @Test
    void validateProduct() {
        // Valid
        assertDoesNotThrow(() ->
                service.validateProduct("Tile")
        );

        // Invalid
        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.validateProduct("Something")
        );
    }

    @Test
    void validateOrderArea() {
        // Valid - exactly the minimum
        assertDoesNotThrow(() ->
                service.validateOrderArea(new BigDecimal("100"))
        );

        // Valid - above minimum
        assertDoesNotThrow(() ->
                service.validateOrderArea(new BigDecimal("150"))
        );

        // Invalid - just below minimum
        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.validateOrderArea(new BigDecimal("99.99"))
        );

        // Invalid - negative
        assertThrows(
                FlooringMasteryDataValidationException.class,
                () -> service.validateOrderArea(new BigDecimal("-100"))
        );
    }

    @Test
    void getAvailableOrderDates() {
        List<LocalDate> dates = service.getAvailableOrderDates();

        assertEquals(1, dates.size());
        assertEquals(orderDate, dates.get(0));
    }

    @Test
    void getAvailableOrdersNum() {
        List<Integer> orderNumbers =
                service.getAvailableOrdersNum(orderDate);

        assertEquals(1, orderNumbers.size());
        assertEquals(10, orderNumbers.get(0));
        assertTrue(orderNumbers.contains(10));
    }

    @Test
    void createEditedOrder() {
        Order editedOrder = service.createEditedOrder(
                10,
                "John Smith",
                "WA",
                "Tile",
                new BigDecimal("100")
        );

        assertEquals(10, editedOrder.getOrderNumber());
        assertEquals("John Smith", editedOrder.getOrderName());
        assertEquals("WA", editedOrder.getState());
        assertEquals("Tile", editedOrder.getProductType());
        assertEquals(new BigDecimal("100"), editedOrder.getArea());

        assertEquals(
                new BigDecimal("350.00"),
                editedOrder.getMaterialCost()
        );

        assertEquals(
                new BigDecimal("415.00"),
                editedOrder.getLabourCost()
        );

        assertEquals(
                new BigDecimal("70.76"),
                editedOrder.getTax()
        );

        assertEquals(
                new BigDecimal("835.76"),
                editedOrder.getTotal()
        );
    }
}