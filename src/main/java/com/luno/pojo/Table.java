package com.luno.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/20.
 */
public class Table {

    private String tableName;

    private List<Field> fieldList;

    public Table(String tableName,List<Field> fieldList){
        this.tableName = tableName;
        this.fieldList = fieldList;
    }

    public Table(String tableName,String[][] fieldArray){
        this.tableName = tableName;
        this.fieldList = stringConvertToList(fieldArray);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<Field> fieldList) {
        this.fieldList = fieldList;
    }

    public List<Field> stringConvertToList(String[][] fieldArray){

        List<Field> fieldList = new ArrayList<>();
        if (fieldArray == null){
            return fieldList;
        }
        for (String[] field : fieldArray){
            if (field.length == 2){
                fieldList.add(new Field(field[0],field[1]));
            }
        }

        return fieldList;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", fieldList=" + fieldList +
                '}';
    }

    public static void main(String[] args){

        String[][] fieldArray = {{"LIBID","int"},{"CERT_ID","string"},{"READER_TYPE","string"},
                {"DEPT","string"},{"CERT_FLAG","string"},{"CREATE_DATA","data"},{"NAME","string"}};
        String tableName = "READER_INFO";
        Table table = new Table(tableName,fieldArray);

        System.out.println(table);
    }
}
