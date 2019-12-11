package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractArrayStorageTest {
    private Storage storage = new ArrayStorage();
    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";

    @Before
    public void srtUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
    }

    @Test
    public void save() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test
    public void delete() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void size() {
    }

    @Test
    public void update() {
    }
}