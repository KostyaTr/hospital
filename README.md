# hospital
MySQL 8.0.15 version was used (it can display russian symbols without any additional adjustments)
md5 method was used to hash passwords

проект примерно описывает больницу
заходить /hospital/login

Обычные пользователи:
могут получать список доступных врачей, список мед услуг
регистрироваться на прием
получать информацию о текущих приемах

Врачи:
получать только своих пациентов
лечить пациентов
класть сильно больных в пациентов в стационар, если того позволяет вместимость палаты
ставить/обновлять диагноз
назначать/создавать курсы лечения 
обновлять статус стационарного пациента
выписать пациентов

Гости:
могут регистрироваться на прием, но в отличии от обычных пользователей, придется вводить больше информации

Можно пройти регистрацию
при регистрации есть проверка на повторный пароль
а также есть проверка на логин, что не может существовать два одинаковых
нужно составить сведения для карточки больного

дополнительные таблицы(не задействованные на данный момент) были созданы для последующих расширений проекта
