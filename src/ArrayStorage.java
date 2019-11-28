/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    void save(Resume r) {
        if (storage.length != size) {
            for (int i = 0; i < size; i++) {
                if (storage[i].uuid.equals(r.uuid)) {
                    System.out.println("Такой uuid уже используется");
                    return;
                }
            }
            storage[size] = r;
            size++;
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid.equals(uuid)) {
                for (int j = i; j < size; j++) {
                    if (j == size - 1) {
                        storage[j] = null;
                    } else {
                        storage[j] = storage[j + 1];
                    }
                }
                size--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumesInStorage = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumesInStorage[i] = storage[i];
        }
        return resumesInStorage;
    }

    int size() {
        return size;
    }
}
