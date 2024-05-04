select f.flight_no, avg(f.actual_arrival - f.scheduled_arrival) as retraso from flights f
group by flight_no
order by retraso desc limit 1