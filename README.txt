Запуск приложения.
Для корректной обработки UTF-8 строк необходимо указать кодировку по умолчанию
при запуске приложения, для чего следует добавить следующий ключ: -Dfile.encoding=UTF-8
Полная команда будет выглядеть так:
java -Dfile.encoding=UTF-8 -Xmx512m -jar sort.jar input.txt output.txt 


Реализация.
Сортировка реализована следующим образом. Файл-источник разбивается на части, каждая
из которых сортируется (с помощью стандартного метода Arrays.sort()) и записывается на
диск в виде отдельного файла. Затем файлы объединяются. После небольших экспериментов
я остановился на размере одной части равной 500000 строк (что при моих тестовых данных
соответствовало 89-ти мегабайтам. Поскольку, на моей довольно старой машине, увеличение
размера порции отрицательно влияло на общее время работы программы. При размере
файла-источника в 4 Gb получается менее 50-ти файлов, что достаточно мало. Так что еще имеется
достаточный запас по памяти. Также я исходил из того что email не может быть длинней 255-ти
символов, так что даже если часть символов будет занимать 6 байт нам ничто не грозит.    
Для корректной работы приложения также необходимо дополнительное место на диске равное
размеру файла-источника. Это место используется для хранения отсортированных частей.


Оптимизация.
У меня идея по оптимизации приложения, а именно: можно распараллелить процесс разбивки
файла-источника на части. Пока сортируется одна часть в это время другая может читаться
с диска и парситься в объекты Record, также одновременно сортироваться одновременно могут
несколько частей. Думаю у нас есть достаточно свободной памяти для одновременной обработки
2-3 частей.
