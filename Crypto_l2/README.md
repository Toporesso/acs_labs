# Практическая работа 2. Spring

### Задание 1

Создал модель крипто-бирж и монет со связями на основе первой работы. Написал SQL-скрипты для создания таблиц и заполнения тестовыми данными.

### Задание 2

Разработал JPA-сущности Exchange и CryptoCoin с аннотациями Hibernate для маппинга таблиц и отношений многие-ко-многим.

### Задание 3

Реализовал сервисы ExchangeService и CryptoCoinService с транзакционными методами для CRUD операций и управления связями между сущностями.

### Задание 4

Создал Spring MVC контроллеры с Thymeleaf шаблонами для отображения списков бирж/монет, форм добавления/редактирования и удаления записей.

### Задание 5

Запускаем проект и видим пустые таблицы:

<img width="308" height="95" alt="image" src="https://github.com/user-attachments/assets/4e8a788c-d8bd-4d47-9d7a-446c3c0bb9e1" />

<img width="329" height="101" alt="image" src="https://github.com/user-attachments/assets/ca873deb-74a1-465f-a129-d89aa4194092" />

Для начала убидимся в правильности работы

Создадим биржу через форму:

<img width="275" height="171" alt="image" src="https://github.com/user-attachments/assets/60a9c842-a4e8-445d-a58f-924f942dd9b1" />

Получим:

<img width="504" height="158" alt="image" src="https://github.com/user-attachments/assets/89ece568-7fd0-48bc-a3bc-b82a7acc8281" />

Создадим монету через форму:

<img width="291" height="272" alt="image" src="https://github.com/user-attachments/assets/107bda83-8371-42b3-8289-ff7d15f5f9de" />

Получим:

<img width="557" height="159" alt="image" src="https://github.com/user-attachments/assets/9a0ea759-5e44-47fa-a55f-913ec7efae8e" />

И

<img width="561" height="172" alt="image" src="https://github.com/user-attachments/assets/f8f0c42d-f3d7-4b5f-acad-acfb1d8fe488" />

Теперь заполним таблицу через SQL файл с запуском .ps1 и получим:

<img width="1453" height="372" alt="image" src="https://github.com/user-attachments/assets/ad14aa5a-f3fd-4a0d-8716-1a938377bba9" />

<img width="1010" height="371" alt="image" src="https://github.com/user-attachments/assets/8c51eaba-38e0-4f54-9e2a-058f91cd1780" />

