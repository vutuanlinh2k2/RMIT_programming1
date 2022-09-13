//package DAO;

//import Model.Member;
//import Model.Order;

import java.io.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderDAO extends AbstractDAO<String, Order> {

    HashMap<String, Order> orders;

    public OrderDAO() {
        this.orders = readCVSFile("order.csv");
    }

    @Override
    public Order findOne(String id) {
        return this.orders.get(id);
    }

    @Override
    public List<Order> findAll() {
        return List.copyOf(this.orders.values());
    }

    @Override
    public void create(Order obj) throws FileNotFoundException {

    }

    @Override
    public void deletebyId(String id) {

    }

    private HashMap<String, Order> readCVSFile(String filePath) {
        BufferedReader reader;
        HashMap<String, Integer> attributeNameToIndex = new HashMap<>();
        HashMap<String, Order> output = new HashMap<>();

        //initialize file reader and return if file not found
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        //Collect headers from cvs file
        String[] header = new String[0];
        try {
            header = reader.readLine().split(",");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //associate each element in header array, name of an attribute of the object, to an index
        for (int i = 0; i < header.length; i++) {
            attributeNameToIndex.put(header[i], i);
        }

        //read file into returned map
        String line = null;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] attributeValues = line.split(",");

            Order order = new Order();
            String orderID;

            order.setOrderID(attributeValues[attributeNameToIndex.get("OrderID")]);
            order.setCustomerId(attributeValues[attributeNameToIndex.get("CustomerID")]);
            order.setAddress(attributeValues[attributeNameToIndex.get("Address")]);
            order.setTotal(Double.valueOf(attributeValues[attributeNameToIndex.get("Total")]));
            order.setDate(LocalDate.parse(attributeValues[attributeNameToIndex.get("Date")]));
            order.setStatus(attributeValues[attributeNameToIndex.get("Status")]);
            orderID = attributeValues[attributeNameToIndex.get("CustomerID")];

            output.put(orderID, order);

        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;

    }

}

