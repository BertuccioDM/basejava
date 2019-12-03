package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume " + resume.getUuid() + " already exists.");
        } else if (size == storage.length) {
            System.out.println("Array overflow.");
        } else {        //Резюме нет в storage?
            insertResume(resume);
            size++;
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {       //Резюме есть в storage?
            return storage[index];
        }
        System.out.println("Resume " + uuid + " not found.");
        return null;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {       //Резюме есть в storage?
            deleteResume(uuid);
            size--;
        } else {
            System.out.println("Resume " + uuid + " not found.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {       //Резюме есть в storage?
            storage[index] = resume;
        } else {
            System.out.println("Resume " + resume.getUuid() + " not found.");
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insertResume(Resume resume);

    protected abstract void deleteResume(String uuid);
}
