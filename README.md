### 🚀 Инструкция по запуску утилиты фильтрации содержимого файлов

#### 📌 **Требования**

- **ОС:** Linux
- **JDK:** OpenJDK 21.0.6
- **Gradle:** 8.12

---

### 📥 **Сборка проекта**

1. Необходимо клонировать проект:
```sh
    git clone https://github.com/matthew8913/util.git
```

2. Перейти в папку с исходным кодом:

    ```sh
    cd util/
    ```

3. Собрать JAR-файл:

    ```sh
    ./gradlew clean build
    ```

4. Готовый JAR-файл будет в:

    ```
    build/libs/util.jar
    ```


---

### ▶️ **Запуск утилиты**

#### **1. Базовый запуск**

```sh
java -jar build/libs/util.jar in1.txt in2.txt
```

👉 Файлы `integers.txt`, `floats.txt`, `strings.txt` создадутся в текущей директории.

#### **2. Изменение пути вывода (`-o`)**

```sh
java -jar build/libs/util.jar -o /home/user/output in1.txt in2.txt
```

👉 Файлы создадутся в `/home/user/output`.

#### **3. Добавление в файлы вместо перезаписи (`-a`)**

```sh
java -jar build/libs/util.jar -a in1.txt in2.txt
```

👉 Результаты дописываются в файлы, если они уже существуют.

#### **4. Использование префикса для файлов (`-p`)**

```sh
java -jar build/libs/util.jar -p sample- in1.txt in2.txt
```

👉 Будут созданы `sample-integers.txt`, `sample-floats.txt`, `sample-strings.txt`.

#### **5. Включение краткой статистики (`-s`)**

```sh
java -jar build/libs/util.jar -s in1.txt in2.txt
```

👉 Выводит только количество записанных элементов.

#### **6. Включение полной статистики (`-f`)**

```sh
java -jar build/libs/util.jar -f in1.txt in2.txt
```

👉 Для чисел дополнительно выводит мин/макс, сумму, среднее.  
👉 Для строк — длину самой короткой и длинной.

#### **7. Комбинация опций**

```sh
java -jar build/libs/util.jar -s -a -p result_ -o /tmp in1.txt in2.txt
```

👉 Выводит статистику, дописывает в файлы `/tmp/result_integers.txt`, `/tmp/result_floats.txt`, `/tmp/result_strings.txt`.

#### **8. Важно!!!**
Все возможные команды, которые не относятся к перечисленным выше флагам будут относиться к входным файлам. Поэтому некорректные команды или некорректные пути к входным файлам будут игнорироваться программой. Но описание ошибки выведется.

