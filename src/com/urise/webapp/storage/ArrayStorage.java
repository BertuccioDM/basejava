package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    private static final int STORAGE_LIMIT = 10_000;

    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Хранилище очищено.");
    }

    public void save(Resume resume) {
        if (size < storage.length) {
            if (getIndex(resume.getUuid()) >= 0) {
                System.out.println("uuid: " + resume.getUuid() + " уже используется.");
                return;
            }
            storage[size] = resume;
            size++;
            System.out.println("Резюме с uuid: [" + resume.getUuid() + "] сохранено.");
        } else {
            System.out.println("Ошибка! Переполнение массива.");
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            if (index != size - 1) {
                storage[index] = storage[size - 1];
            }
            storage[size - 1] = null;
            size--;
            System.out.println("Резюме с uuid: [" + uuid + "] удалено.");
        } else {
            System.out.println("Резюме с uuid [" + uuid + "] не найдено.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
            System.out.println("Резюме с uuid: [" + resume.getUuid() + "] обновлено.");
        } else {
            System.out.println("Резюме с uuid [" + resume.getUuid() + "] не найдено.");
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
