# Perfect store

## Идеальный магазин. Управляй магазином и заработай больше всех!

Твоя задача создать игрока, который выступает в роли управляющего магазингом. Время игры ограничено и задается количеством раундов (тиков), условно 1 тик равен 1 минуте

Описание взаимодействия предоставляется в виде OpenAPI 3 схемы
Все что происходит в текущий момент времени, доступно через эндпоинт **/api/v1/loadWorld**
При этом, игра пошаговая и игрок сам крутит счетчик времени вперед. Так, чтобы прожить еще один тик, вызывается эндпоинт **/api/v1/tick** и на вход ему передаются все ваши управленческие команды. Сначала применяются они, потом проживается еще один тик игры и возвращается новое состояние магазина.
Время самостоятельно на сервере не движется, так что **/api/v1/loadWorld** скорее предназначен для первичной загрузки информации о магазине
Основные сущности игры:

**Stock** - Склад. Он представляет все виды товаров, которые можно продавать в магазине, их закупочную цену а также какое количество товара сейчас доступно на складе. Закупать товар на склад можно в неограниченных количествах по указанной цене. Условно считается, что у тебя неограниченный запас денег и есть лишь статья расходов на закупку. При покупке товара на склад вы оплачиваете услуги транспортной компании в размере 5000р. Поэтому старайтесь заказывать реже и по многу

**RackCell** - Полка в торговом зале, на которой может быть выставлен товар. Количеством полок ты не управляешь, оно фиксировано с начала игры. Однако некоторые полки более на виду чем другие. Один вид товара можно располагать только на одной полке. По мере того, как покупатели опустошают полки, ты, как управляющий можешь пополнять их со склада и выставлять розничную цену. Но имей ввиду, чем выше наценка тем меньше покупателей решатся на покупку.

**CheckoutLine** - касса. После того как покупатель прошел по магазину, он встает в очередь на кассы. Если есть свободная касса, то он проходит на нее, чтобы расплатиться. Количество касс задается при старте игры и не регулируется, однако ты можешь управлять кассирами: ставить их на определенную кассу, снимать с кассы или даже увольнять.

**Employee** - Кассир. Сидит на кассе и пробивает товар клиента. Кассир получает зарплату поминутно, пока он в штате и не уволен. Кассиры могут быть новичками, средними и экспертами. Более опытный обслуживает клиентов быстрее.  Кассир работает 8 часов, после чего ему требуется 16 часов отдыха. Если ты увольняешь кассира, то он все равно имеет 16 часов оплаченного отдыха перед увольнением.

**Product** - товары которые игрок закупает на склад, а оттуда выставляет на полки в торговом зале. 

**Customer** - покупатель. Единственная часть игры, которой ты не управляешь напрямую. Покупатели произвольно приходят в магазин, выбирают то что им понравилось и по карману, пробивают на кассе, тем самым дают  тебе возможность заработать. 


В информации о мире тебе доступно отведенное игровое время и текущее время (ввиде прожитых тиков или минут). Магазин работает круглосуточно, но у него могут быть периоды наплыва покупателей в час-пик или наоборот мертвого сезона. Также заранее неизвестно в насколько бойком месте находится магазин: проходное место возле метро с большим наплывом или же где-то в спальном районе с меньшим наплывом посетитей.
По истечении игрового времени возвращается специальный флаг Game Over, игра останавливается и фиксируется итоговая прибыль (или убыток) магазина. Удачи! 




