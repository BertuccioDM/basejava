package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    private final static String UUID_1 = "uuid1";
    private final static String UUID_2 = "uuid2";
    private final static String UUID_3 = "uuid3";
    private final static String UUID_TEST = "test";
    private final Resume resume_1 = new Resume(UUID_1);
    private final Resume resume_2 = new Resume(UUID_2);
    private final Resume resume_3 = new Resume(UUID_3);
    private final Resume resume_test = new Resume(UUID_TEST);

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(resume_1);
        storage.save(resume_2);
        storage.save(resume_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
        storage.get(UUID_2);
    }

    @Test
    public void save() {
        storage.save(resume_test);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(resume_test, storage.get("test"));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(resume_3);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("Premature storage overflow");
        }
        storage.save(new Resume());
    }

    @Test
    public void get() {
        storage.get(UUID_2);
        Assert.assertEquals(resume_2, storage.get(UUID_2));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(resume_test.getUuid());
    }

    @Test
    public void getAll() {
        Resume[] result = storage.getAll();
        Assert.assertEquals(storage.size(), result.length);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void update() {
        Resume before = storage.get(UUID_2);
        Resume test = new Resume(UUID_2);
        storage.update(test);
        Resume after = storage.get(UUID_2);
        Assert.assertNotSame(before, after);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(resume_test);
    }
}