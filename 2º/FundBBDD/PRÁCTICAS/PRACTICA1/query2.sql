select b.book_ref, b.total_amount, sum(tf.amount) as suma_total from bookings b join
tickets t on b.book_ref = t.book_ref join
ticket_flights tf on t.ticket_no = tf.ticket_no
group by b.book_ref, b.total_amount
order by b.book_ref