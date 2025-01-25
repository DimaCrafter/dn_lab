package ru.csit.shiryaev_lab.core.data;

import java.util.ArrayList;


public class DataFileShape<T> {
    public int nextId = 1;
    public ArrayList<T> data = new ArrayList<T>();
}
