select distinct b.book_ref, tf.flight_id from bookings b join tickets t
on b.book_ref = t.book_ref join ticket_flights tf
on t.ticket_no = tf.ticket_no where not exists
(
	select bp.ticket_no from boarding_passes bp 
	where tf.ticket_no = bp.ticket_no and tf.flight_id = bp.flight_id
)
order by b.book_ref, tf.flight_id
