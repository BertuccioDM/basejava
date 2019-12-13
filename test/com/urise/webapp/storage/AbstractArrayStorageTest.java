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
    private final static Resume RESUME_1 = new Resume(UUID_1);
    private final static Resume RESUME_2 = new Resume(UUID_2);
    private final static Resume RESUME_3 = new Resume(UUID_3);
    private final static Resume RESUME_TEST = new Resume(UUID_TEST);

    AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
        storage.get(UUID_2);
    }

    @Test
    public void save() {
        storage.save(RESUME_TEST);
        Assert.assertEquals(4, storage.size());
        Assert.assertEquals(RESUME_TEST, storage.get(UUID_TEST));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_3);
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
        Assert.assertEquals(RESUME_2, storage.get(UUID_2));
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
        storage.delete(UUID_TEST);
    }

    @Test
    public void getAll() {
        Resume[] result = storage.getAll();
        Resume[] array = new Resume[] {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertEquals(storage.size(), result.length);
        Assert.assertArrayEquals(array, result);
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void update() {
        Resume test = new Resume(UUID_2);
        storage.update(test);
        Resume after = storage.get(UUID_2);
        Assert.assertSame(test, after);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_TEST);
    }
}