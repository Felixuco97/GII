select departure_airport, b.book_ref from
(
select distinct f1.departure_airport from flights f1 join flights f2
on f1.departure_airport = f2.arrival_airport
) as s
join ticket_flights tf on flight_id = tf.flight_id
join tickets t on t.ticket_no = tf.ticket_no join bookings b
on b.book_ref = t.book_ref
