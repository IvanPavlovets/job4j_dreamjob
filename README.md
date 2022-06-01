![](https://img.shields.io/badge/Java-%3E%3D%208-orange)
![](https://img.shields.io/badge/Maven-3-red)
![](https://img.shields.io/badge/Spring%20boot-%202.5.2-green)
![](https://img.shields.io/badge/-Bootstrap-blueviolet)
![](https://img.shields.io/badge/-Thymeleaf-darkgreen)
![](https://img.shields.io/badge/PostgreSQL-%3E%3D%209-informational)
![](https://img.shields.io/badge/-JDBC-blue)
![](https://img.shields.io/badge/-H2%20-blueviolet)
![](https://img.shields.io/badge/-Liquibase-blue)
![](https://img.shields.io/badge/JUnit-%3E%3D%204-yellowgreen)
![](https://img.shields.io/badge/-Mockito-brightgreen)
![](https://img.shields.io/badge/-checkstyle-lightgrey)

# job4j_dreamjob
 - [О проекте]()
 - [Технологии]() 
 - [Как использовать]()  

О проекте
=
Данное веб приложении представляет собой биржу труда.<br>

 - Кандидаты публикуют резюме с информацией о себе.<br>
 - Кадровики публикуют актуальные вакансии о работе.<br>
 
С помощью реализованного функционала добавления ***вакансий***<br>
и ***кандидатов***, наполняеться внутряняя база данных приложения. <br>
Для доступа к функционалу приложения необходимо зарегестрироваться,<br>
 а потом авторизироваться.
 
Технологии
=
 * Frontend - **HTML**, **CSS**, **BOOTSTRAP**, **Thymeleaf**;
 * Backend - **Java 12**, **JDBC**, **Spring Boot**;
 * Сборщик проектов - **Maven**;
 * СУБД - **PostgreSQL**, **H2**;
 * библиотека для управления обновлений схем БД - **Liquibase**;
 * библиотека для модульного тестирова    ния - **JUnit**;
 * библиотеки для тестирования - **JUnit**, **Mockito**;
 * Инструмент анализа стиля кода - **Checkstyle**;

Как использовать
=
1. В самом начале работы приложения мы попадает на страницу авторизации:<br>

![Image of login](https://github.com/IvanPavlovets/job4j_dreamjob/blob/master/images/login.png)<br>
Варианты возможных дейсвий - **войти** под своей учетной записью или **перейти на страницу регистрацции**. <br>
После входа под своей учетной записью на панели навигации появиться строка с именем учетно записи,<br>
нажав на которую произойдет выход из учетной записи.<br>
___
2. Страница регистрации предлагает завести свою учетную запись:<br>

![Image of registration](https://github.com/IvanPavlovets/job4j_dreamjob/blob/master/images/reg.png)<br>
___

3. Страница вакансий:<br>

![Image of posts](https://github.com/IvanPavlovets/job4j_dreamjob/blob/master/images/posts.png)<br>
На этой странице показаны все добавленные вакансии системы.<br>
Есть возможность редактировать записи вакансий.<br> 
___

4. Страница добавления вакансии:<br>

![Image of addPost](https://github.com/IvanPavlovets/job4j_dreamjob/blob/master/images/addPost.png)<br>
___

5. Страница кандидатов:<br>

![Image of candidates](https://github.com/IvanPavlovets/job4j_dreamjob/blob/master/images/candidates.png)<br>
На этой странице показаны все добавленные резюме кандидатов системы.<br>
Есть возможность редактировать записи кандидатов.<br>
___

6. Страница добавления кандидата:<br>

![Image of addCandidate](https://github.com/IvanPavlovets/job4j_dreamjob/blob/master/images/addCandidate.png)<br> 