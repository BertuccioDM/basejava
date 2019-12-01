package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Хранилище очищено.");
    }

    public void save(Resume resume) {
        if (size != storage.length) {
            if (indexOfResume(resume.getUuid()) >= 0) {
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

    public Resume get(String uuid) {
        int id = indexOfResume(uuid);
        if (id >= 0) {
            return storage[id];
        } else {
            System.out.println("Резюме с uuid [" + uuid + "] не найдено.");
            return null;
        }
    }

    public void delete(String uuid) {
        int id = indexOfResume(uuid);
        if (id >= 0) {
            if (storage[id].getUuid().equals(uuid)) {
                if (id != size - 1) {
                    storage[id] = storage[size - 1];
                }
                storage[size - 1] = null;
                size--;
                System.out.println("Резюме с uuid: [" + uuid + "] удалено.");
            }
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

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int id = indexOfResume(resume.getUuid());
        if (id >= 0) {
            storage[id] = resume;
            System.out.println("Резюме с uuid: [" + resume.getUuid() + "] обновлено.");
        } else {
            System.out.println("Резюме с uuid [" + resume.getUuid() + "] не найдено.");
        }
    }

    private int indexOfResume(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
