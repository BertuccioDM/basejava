package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        int lowerIndex = 0;
        int upperIndex = size - 1;
        int insertIndex;

        if (size < storage.length) {
            if (getIndex(resume.getUuid()) >= 0) {
                System.out.println("uuid: " + resume.getUuid() + " уже используется.");
                return;
            }
            if (size == 0) {
                storage[0] = resume;
            } else if ((storage[0].compareTo(resume) > 0)) {
                insertIndex = 0;
                for (int i = size; i > insertIndex; i--) {
                    storage[i] = storage[i - 1];
                }
                storage[insertIndex] = resume;
            } else if (storage[size - 1].compareTo(resume) < 0) {
                insertIndex = size;
                storage[insertIndex] = resume;
            } else {
                while (lowerIndex < upperIndex) {
                    insertIndex = (lowerIndex + upperIndex) / 2;
                    if (storage[insertIndex].compareTo(resume) > 0) {
                        upperIndex = insertIndex;
                    } else if (storage[insertIndex + 1].compareTo(resume) < 0) {
                        lowerIndex = insertIndex;
                    } else if ((storage[insertIndex].compareTo(resume) < 0)
                            && (storage[insertIndex + 1].compareTo(resume) > 0)) {
                        insertIndex += 1;
                        for (int i = size; i > insertIndex; i--) {
                            storage[i] = storage[i - 1];
                        }
                        storage[insertIndex] = resume;
                        break;
                    }
                }
            }
            size++;
            System.out.println("Резюме с uuid: [" + resume.getUuid() + "] сохранено.");
        } else {
            System.out.println("Ошибка! Переполнение массива.");
        }
    }

    @Override
    public void delete(String uuid) {
        int deleteIndex = getIndex(uuid);
        if (deleteIndex == size - 1) {
            storage[deleteIndex] = null;
        } else if (deleteIndex >= 0) {
            for (int i = deleteIndex; i < size; i++) {
                storage[i] = storage[i + 1];
            }
            size--;
            System.out.println("Резюме с uuid: [" + uuid + "] удалено.");
        } else {
            System.out.println("Резюме с uuid [" + uuid + "] не найдено.");
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void insertResume(Resume resume) {
        //TODO
    }
}
