package DAO;

import Model.Member;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MemberDAO extends AbstractDAO<String, Member> {

    HashMap<String, Member> members;

    public MemberDAO() {
        this.members = readCVSFile("member.cvs");

    }

    @Override
    public Member findOne(String id) {
        return this.members.get(id);
    }

    @Override
    public List<Member> findAll() {
        return List.copyOf(this.members.values());
    }

    @Override
    public void create(Member obj) throws FileNotFoundException {
        String[] values = {
                obj.getCustomerID(),
                obj.getName(),
                obj.getAddress(),
                obj.getUsername(),
                obj.getPassword(),
                obj.getType()
        };

        String line = Stream.of(values).collect(Collectors.joining(","));

        PrintWriter writer = new PrintWriter("member.cvs");

        writer.println(line);

    }

    @Override
    public void deletebyId(String id) {

    }

    private HashMap<String,Member> readCVSFile (String filePath){
        BufferedReader reader;
        HashMap<String, Integer> attributeNameToIndex = new HashMap<>();
        HashMap<String, Member> output = new HashMap<>();

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
        for (int i = 0; i < header.length; i ++) {
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

            Member member = new Member();
            String customerID;

            member.setCustomerID(attributeValues[attributeNameToIndex.get("CustomerID")]);
            member.setName(attributeValues[attributeNameToIndex.get("Name")]);
            member.setPhoneNumb(attributeValues[attributeNameToIndex.get("PhoneNumber")]);
            member.setAddress(attributeValues[attributeNameToIndex.get("Address")]);
            member.setUsername((attributeValues[attributeNameToIndex.get("UserName")]));
            member.setPassword(attributeValues[attributeNameToIndex.get("Password")]);
            member.setType(attributeValues[attributeNameToIndex.get("Type")]);

            customerID = attributeValues[attributeNameToIndex.get("CustomerID")];

            output.put(customerID, member);

        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
