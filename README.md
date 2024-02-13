<br/>
<p align="center">
  <a href="https://github.com/Zazergel/java-filmorate">
  </a>

  <h1 align="center">Filmorate</h1>

  <p align="center">
    <a href="https://github.com/Zazergel/java-filmorate/issues">Report Bug</a>
    .
    <a href="https://github.com/Zazergel/java-filmorate/issues">Request Feature</a>
  </p>
</p>

<div class="myWrapper" markdown="1" align="center">
  
![Downloads](https://img.shields.io/github/downloads/Zazergel/java-filmorate/total) 
![Contributors](https://img.shields.io/github/contributors/Zazergel/java-filmorate?color=dark-green) 
![Issues](https://img.shields.io/github/issues/Zazergel/java-filmorate) 
![License](https://img.shields.io/github/license/Zazergel/java-filmorate) 
</div>

## Table Of Contents

* [About the Project](#about-the-project)
* [Built With](#built-with)
* [DB scheme](#db-scheme)
* [Features](#features)
* [Getting Started](#getting-started)
  * [Prerequisites](#prerequisites)
  * [Installation](#installation)
* [Authors](#authors)


## About The Project

Представьте, что после напряженного трудового дня вы решили отдохнуть и провести вечер за просмотром фильма. Вкусная еда уже готовится, любимый плед уютно свернулся на кресле — а вы всё ещё не выбрали, что же посмотреть!

Фильмов много — и с каждым годом становится всё больше. Чем их больше, тем больше разных оценок. Чем больше оценок, тем сложнее сделать выбор. Однако не время сдаваться! Представляем вашему вниманию сервис, который работает с фильмами и оценками пользователей, а также возвращает топ-5 фильмов, рекомендованных к просмотру. Теперь ни вам, ни вашим друзьям не придётся долго размышлять, что посмотреть вечером.

## Features
1. Регистрация новых пользователей, а также добавление ими новых фильмов в базу данных (H2). 
2. Реализована возможность добавления в друзья между пользователями.
3. Пользователи могут выставлять оценки фильмам на основе своих предпочтений и вкусов. 
4. Составление рейтинга фильмов: Filmorate использует оценки пользователей и количество лайков для составления рейтинга фильмов. Это позволяет пользователям узнать о популярных и хорошо оцененных фильмах, а также помогает открыть новые интересные картины.

## Built With

<p align="left">
    <img src="https://skillicons.dev/icons?i=java,maven,spring" />
</p>

## DB scheme

<details>
  <summary><b>Schema of database</b></summary>
 <img src='https://raw.githubusercontent.com/Zazergel/java-filmorate/main/DB%20Filmorate.png' border='0' alt='Filmorate_DB'/>
</details>

## Getting Started

### Prerequisites

Чтобы установить и запустить Filmorate на своем компьютере, выполните следующие шаги:

1. Убедитесь, что на вашей системе установлена Java Development Kit (JDK) версии 11 или выше. 

### Installation

1. Клонируйте репозиторий

```sh
git clone https://github.com/Zazergel/java-filmorate.git
```
Или скачайте ZIP-архив.

2. Откройте проект в вашей любимой интегрированной среде разработки (IDE).

3. Соберите проект, используя команду:
```sh
mvn clean install
 ```
5. Запустите проект.

Поздравляю! Теперь вы можете начать использовать Filmorate для работы с фильмами, оценками и рейтингами.

## Authors

**[Zazergel](https://github.com/Zazegel)**


 
