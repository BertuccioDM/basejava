package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, null);
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
        if (indexOfResume(uuid) >= 0) {
            return storage[indexOfResume(uuid)];
        } else {
            System.out.println("Резюме с uuid [" + uuid + "] не найдено.");
            return null;
        }
    }

    public void delete(String uuid) {
        if (indexOfResume(uuid) >= 0) {
            for (int i = 0; i < size; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    if (i != size - 1) {
                        storage[i] = storage[size - 1];
                    }
                    storage[size - 1] = null;
                    size--;
                    System.out.println("Резюме с uuid: [" + uuid + "] удалено.");
                    return;
                }
            }
        } else {
            System.out.println("Резюме с uuid [" + uuid + "] не найдено.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] result = Arrays.copyOf(storage, size);
        return result;
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        if (indexOfResume(resume.getUuid()) >= 0) {
            storage[indexOfResume(resume.getUuid())] = resume;
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
