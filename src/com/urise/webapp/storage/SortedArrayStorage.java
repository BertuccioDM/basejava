package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertResume(Resume resume) {
        int insertIndex = (-1) * getIndex(resume.getUuid()) - 1;
        for (int i = size; i > insertIndex; i--) {
            storage[i] = storage[i - 1];
        }
        storage[insertIndex] = resume;
    }

    @Override
    protected void deleteResume(String uuid) {
        int index = getIndex(uuid);
        System.arraycopy(storage, index + 1, storage, index, storage.length - index - 1);
    }
}
